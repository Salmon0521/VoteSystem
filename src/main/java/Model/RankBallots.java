package Model;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

public class RankBallots extends Ballots {
    private List<RankBallot> ballotList = new ArrayList<>();

    public RankBallots(){
        this.name = "序位投票";
    }

    @Override
    public void ResetVotedBallots() {
        ballotList = new ArrayList<>();
    }

    @Override
    protected List<XSSFWorkbook> updateResultFile() {
        List<LinkedHashMap<String, String>> scores = new ArrayList<>();
        for (RankBallot ballot : ballotList) {
            for (int i = 0; i <  ballot.getRankList().size(); i++) {
                if(scores.size() < ballot.getRankList().size()){
                    scores.add(new LinkedHashMap<>(ballot.getRankList().get(i)));
                }
                else{
                    scores.get(i).put("score", String.valueOf(Integer.parseInt(scores.get(i).get("score")) + Integer.parseInt(ballot.getRankList().get(i).get("score"))));
                }
            }
        }

        List<XSSFWorkbook> workbooks = new ArrayList<>();
        workbooks.add(generateStatisticResultCSV(scores));
        workbooks.add(generateBallotsResultCSV(scores));
        return workbooks;
    }

    @Override
    public void updateBallot(List<LinkedHashMap<String, String>> rankBallot, String userUUID) {
        RankBallot ballot = new RankBallot();

        ballot.setRankList(rankBallot);
        ballot.setUserUUID(userUUID);
        ballotList.add(ballot);
    }

    private XSSFWorkbook generateStatisticResultCSV(List<LinkedHashMap<String, String>> scores) {
        String resultTemplate = basePath + resultFile;
        String result = basePath + "序位投票結果test.xlsx";

        List<Object[]> data;
        XSSFSheet sheet;
        XSSFWorkbook wb = getWorkbook(resultTemplate);

        data = transformHashMapToArray(scores);
        sheet = wb.getSheet("工作表1");
        writeCellValue(wb, 2, sheet, data, true);
        exportFile(result, wb);
        return wb;
    }

    private XSSFWorkbook generateBallotsResultCSV(List<LinkedHashMap<String, String>> scores) {
        String resultTemplate = basePath + ballotFile;
        String result = basePath + "序位投票_選票檔test.xlsx";

        int sheetNum = 0;
        int cellNum = 0;
        List<Object[]> data;
        XSSFSheet sheet;
        XSSFWorkbook ballotWb = getWorkbook(resultTemplate);

        for (RankBallot rankBallot : ballotList) {
            ballotWb.cloneSheet(sheetNum++);
            ballotWb.setSheetName(sheetNum, rankBallot.getUserUUID());

            data = transformHashMapToArray(rankBallot.getRankList());
            sheet = ballotWb.getSheet(rankBallot.getUserUUID());
            writeCellValue(ballotWb, 3, sheet, data, false);
        }

        sheetNum = 0;
        sheet = ballotWb.createSheet("票數統整");
        Row titleRow = sheet.createRow(sheetNum++);

        titleRow.createCell(cellNum++).setCellValue("委員編號");
        for (LinkedHashMap<String, String> score : scores) {
            titleRow.createCell(cellNum++).setCellValue(score.get("col1") + ':' + score.get("col2"));
        }

        for (RankBallot rankBallot : ballotList) {
            cellNum = 0;
            Row ballotRow = sheet.createRow(sheetNum++);
            ballotRow.createCell(cellNum++).setCellValue(rankBallot.getUserUUID());

            for (LinkedHashMap<String, String> ballot : rankBallot.getRankList()) {
                ballotRow.createCell(cellNum++).setCellValue(Double.parseDouble(ballot.get("score")));
            }
        }

        ballotWb.removeSheetAt(0);
        exportFile(result, ballotWb);
        return ballotWb;
    }

    private void writeCellValue(XSSFWorkbook wb, int rowNum, XSSFSheet sheet, List<Object[]> data, boolean needStatistic) {
        XSSFFont font = wb.createFont();
        font.setFontName("標楷體");
        font.setFontHeightInPoints((short) 14);

        XSSFCellStyle cellStyleCenterAlign = wb.createCellStyle();
        cellStyleCenterAlign.setFont(font);
        cellStyleCenterAlign.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleCenterAlign.setAlignment(HorizontalAlignment.CENTER);
        cellStyleCenterAlign.setBorderBottom(BorderStyle.THICK);
        cellStyleCenterAlign.setBorderTop(BorderStyle.THICK);
        cellStyleCenterAlign.setBorderLeft(BorderStyle.THICK);
        cellStyleCenterAlign.setBorderRight(BorderStyle.THICK);

        XSSFCellStyle cellStyleLeftAlign = wb.createCellStyle();
        cellStyleLeftAlign.setFont(font);
        cellStyleLeftAlign.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleLeftAlign.setAlignment(HorizontalAlignment.LEFT);
        cellStyleLeftAlign.setBorderBottom(BorderStyle.THICK);
        cellStyleLeftAlign.setBorderTop(BorderStyle.THICK);
        cellStyleLeftAlign.setBorderLeft(BorderStyle.THICK);
        cellStyleLeftAlign.setBorderRight(BorderStyle.THICK);

        for (Object[] objects : data) {
            Row ballotRow = sheet.getRow(rowNum++);
            int cellNum = 0;
            for (Object obj : objects) {
                Cell cell = ballotRow.getCell(cellNum);
                if (cellNum == 2) {
                    cell.setCellValue(Double.parseDouble(obj.toString()));
                    cell.setCellStyle(cellStyleCenterAlign);
                } else {
                    cell.setCellValue((String)obj);
                    cell.setCellStyle(cellStyleLeftAlign);
                }
                cellNum += 1;
            }
            if (needStatistic) {
                Cell cell = ballotRow.getCell(cellNum);
                cell.setCellFormula("RANK(C"+ rowNum +",$C$3:$C$9,1)");
                cell.setCellStyle(cellStyleCenterAlign);
            }
        }
        if (needStatistic) {
            int idx = data.size()+2;
            while (sheet.getRow(idx) != null) {
                sheet.removeRow(sheet.getRow(idx));
                idx += 1;
            }
            sheet.shiftRows(idx, 2*idx-data.size()-2, data.size()+2-idx);

            Row lastRow = sheet.getRow(data.size()+1);
            for (int i=0; i<=3; i++) {
                lastRow.getCell(i).setCellStyle(i == 0 || i == 1? cellStyleLeftAlign : cellStyleCenterAlign);
            }
        }
    }

    private void exportFile(String result, XSSFWorkbook wb) {
        try {
            FileOutputStream out = new FileOutputStream(result);
            wb.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private XSSFWorkbook getWorkbook(String resultTemplate) {
        XSSFWorkbook ballotWb = null;
        try {
            InputStream ballotIs = new FileInputStream(resultTemplate);
            ballotWb = new XSSFWorkbook(ballotIs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ballotWb;
    }

    private List<Object[]> transformHashMapToArray(List<LinkedHashMap<String, String>> ballots) {
        List<Object[]> data = new ArrayList<>();
        for (LinkedHashMap<String, String> ballotRow : ballots) {
            List<String> row = new ArrayList<>();
            for (Map.Entry<String, String> ballotRowSet : ballotRow.entrySet()) {
                row.add(ballotRowSet.getValue());
            }
            data.add(row.toArray());
        }
        return data;
    }

    @Override
    protected void modifyHTML() {
        String ballotPath = this.basePath + this.ballotFile;
        String htmlPath = this.basePath + "rankFile.jsp";

        Workbook wb = new Workbook();
        wb.loadFromFile(ballotPath);

        Worksheet sheet = wb.getWorksheets().get(0);
        sheet.saveToHtml(htmlPath);
        File input = new File(htmlPath);
        try {
            Document html = Jsoup.parse(input, "UTF-8");

            int rowIndex = 3;
            while(!"".equals(html.select("tr").eq(rowIndex).text())) {
                rowIndex += 1;
            }
            Map<String, String> attrMap = new HashMap<String, String>(){{
                put("type", "number");
                put("style", "height: 100%; font-size: 80%; width: 100%; text-align: center");
                put("min", "1");
                put("required", "required");
            }};
            attrMap.put("max", String.valueOf(rowIndex-3));

            rowIndex = 3;
            while(!"".equals(html.select("tr").eq(rowIndex).text())) {
                Elements trElement = html.select("tr").eq(rowIndex);
                Element tdElement = trElement.select("td").eq(2).first();
                Element inputElement = tdElement.appendElement("input");

                for (Map.Entry<String, String> attr : attrMap.entrySet()) {
                    inputElement.attr(attr.getKey(), attr.getValue());
                }
                rowIndex += 1;
            }

            html.select("h2").remove();

            Element tr = html.select("tbody").first().appendElement("tr");
            Element td = tr.appendElement("td");
            td.attr("colspan", "10");
            td.attr("style", "padding-top: 10px; padding-left: 20px; height: 90px;");

            Element buttonElement = td.appendElement("button");
            buttonElement.attr("type", "button");
            buttonElement.attr("style", "width:30%; height: 80%; font-size: 130%; border-radius: 30px; display: block; margin: auto;");
            buttonElement.attr("onclick", "sendBallot()");
            buttonElement.text("送出選票");

            html.select("head").first().appendElement("script").attr("src", "https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js");
            html.select("head").first().appendElement("script").attr("src", "../../js/RankBallot.js");
            html.select("head").first().appendElement("meta").attr("charset", "utf-8");

            String includeHead = "<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" %><jsp:include page=\"../../jsp/view/VoteHeader.jsp\" />";
            String includeFoot = "<jsp:include page=\"../../jsp/view/MeetingFooter.jsp\" />";
            String combineJSP = includeHead + html.toString() + includeFoot;

            FileWriter writer=new FileWriter(htmlPath);
            writer.write(combineJSP);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<RankBallot> getBallotList() {
        return ballotList;
    }
}

