package Model;

import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.time.LocalDate;

import java.io.*;
import java.util.*;

public class AgreementBallots extends Ballots {
    private List<AgreementBallot> ballotList = new ArrayList<>();

    public AgreementBallots(){
        this.name = "同意不同意";
    }

    @Override
    public void ResetVotedBallots() {
        ballotList = new ArrayList<>();
    }

    @Override
    protected List<XSSFWorkbook> updateResultFile() {
        List<LinkedHashMap<String, String>> scores = new ArrayList<>();
        for (AgreementBallot ballot : ballotList) {
            for (int i = 0; i <  ballot.getApprovalIssueList().size(); i++) {
                if(scores.size() < ballot.getApprovalIssueList().size()){
                    LinkedHashMap<String, String> ballotHashMap = new LinkedHashMap<>();
                    ballotHashMap.put("col1", ballot.getApprovalIssueList().get(i).get("col1"));
                    ballotHashMap.put("col2", ballot.getApprovalIssueList().get(i).get("col2"));
                    if (ballot.getApprovalIssueList().get(i).get("agreement").equals("agree")) {
                        ballotHashMap.put("agree", "1");
                        ballotHashMap.put("disagree", "0");
                    }
                    else {
                        ballotHashMap.put("agree", "0");
                        ballotHashMap.put("disagree", "1");
                    }
                    scores.add(ballotHashMap);
                }
                else{
                    if (ballot.getApprovalIssueList().get(i).get("agreement").equals("agree")) {
                        scores.get(i).put("agree", String.valueOf(Integer.parseInt(scores.get(i).get("agree")) + 1));
                    }
                    else {
                        scores.get(i).put("disagree", String.valueOf(Integer.parseInt(scores.get(i).get("disagree")) + 1));
                    }
                }
            }
        }

        List<XSSFWorkbook> workbooks = new ArrayList<>();
        workbooks.add(generateStatisticResultCSV(scores));
        workbooks.add(generateBallotsResultCSV(scores));
        return workbooks;
    }

    private XSSFWorkbook generateStatisticResultCSV(List<LinkedHashMap<String, String>> scores) {
        String resultTemplate = basePath + resultFile;
        String result = basePath + "同意票結果test.xlsx";

        XSSFSheet sheet;
        XSSFWorkbook wb = getWorkbook(resultTemplate);

        List<Object[]> data = transformHashMapToArray(scores);
        sheet = wb.getSheet("工作表1");
        writeCellValue(2, sheet, data, true);
        exportFile(result, wb);
        return wb;
    }

    private XSSFWorkbook generateBallotsResultCSV(List<LinkedHashMap<String, String>> scores) {
        String resultTemplate = basePath + ballotFile;
        String result = basePath + "同意票_選票檔test.xlsx";

        int sheetNum = 0;
        int cellNum = 0;
        List<Object[]> data;
        XSSFSheet sheet;
        XSSFWorkbook ballotWb = getWorkbook(resultTemplate);

        for (AgreementBallot agreementBallot : ballotList) {
            ballotWb.cloneSheet(sheetNum++);
            ballotWb.setSheetName(sheetNum, agreementBallot.getUserUUID());

            data = transformHashMapToArray(agreementBallot.getApprovalIssueList());
            List<Object[]> preprocessData = new ArrayList<>();

            for (Object[] obj : data) {
                List<Object> row = new ArrayList<>();
                row.add(obj[0]);
                row.add(obj[1]);
                if (obj[2].equals("agree")) {
                    row.add("1");
                    row.add("0");
                } else {
                    row.add("0");
                    row.add("1");
                }
                preprocessData.add(row.toArray());
            }

            sheet = ballotWb.getSheet(agreementBallot.getUserUUID());
            writeCellValue(3, sheet, preprocessData, false);
        }

        sheetNum = 0;
        sheet = ballotWb.createSheet("票數統整");
        Row titleRow = sheet.createRow(sheetNum++);

        titleRow.createCell(cellNum++).setCellValue("委員編號");
        for (LinkedHashMap<String, String> score : scores) {
            titleRow.createCell(cellNum++).setCellValue(score.get("col1") + ':' + score.get("col2"));
        }

        for (AgreementBallot agreementBallot : ballotList) {
            cellNum = 0;
            Row ballotRow = sheet.createRow(sheetNum++);
            ballotRow.createCell(cellNum++).setCellValue(agreementBallot.getUserUUID());

            for (LinkedHashMap<String, String> ballot : agreementBallot.getApprovalIssueList()) {
                ballotRow.createCell(cellNum++).setCellValue(ballot.get("agreement").equals("agree")? "同意" : "不同意");
            }
        }

        ballotWb.removeSheetAt(0);
        exportFile(result, ballotWb);
        return ballotWb;
    }

    private void writeCellValue(int rowNum, XSSFSheet sheet, List<Object[]> data, boolean needStatistic) {
        if (needStatistic) {
            Cell dateCell = sheet.getRow(0).getCell(5);
            dateCell.setCellValue(LocalDate.now().toString());

            Cell participantAmount = sheet.getRow(25).getCell(3);
            Cell thresholdAmount = sheet.getRow(25).getCell(6);
            participantAmount.setCellFormula("C3+D3");
            thresholdAmount.setCellFormula("ROUND(D26*2/3, 0)");
        }

        for (Object[] objects : data) {
            Row ballotRow = sheet.getRow(rowNum++);
            int cellNum = 0;
            for (Object obj : objects) {
                Cell cell = ballotRow.getCell(cellNum);
                if (cellNum == 2 || cellNum == 3) {
                    cell.setCellValue(Double.parseDouble(obj.toString()));
                } else {
                    cell.setCellValue((String)obj);
                }
                cellNum+=1;
            }
            if (needStatistic) {
                Cell cell = ballotRow.getCell(cellNum);
                cell.setCellValue(Integer.parseInt(objects[objects.length-2].toString()) > Integer.parseInt(objects[objects.length-1].toString())? "通過": "不通過");
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
    public void updateBallot(List<LinkedHashMap<String, String>> agreementBallot, String userUUID) {
        AgreementBallot ballot = new AgreementBallot();

        ballot.setApprovalIssueList(agreementBallot);
        ballot.setUserUUID(userUUID);
        ballotList.add(ballot);
    }

    @Override
    protected void modifyHTML() {
        String ballotPath = this.basePath + this.ballotFile;
        String htmlPath = this.basePath + "agreementFile.jsp";

        Workbook wb = new Workbook();
        wb.loadFromFile(ballotPath);

        Worksheet sheet = wb.getWorksheets().get(0);
        sheet.saveToHtml(htmlPath);
        File input = new File(htmlPath);
        try {
            Document html = Jsoup.parse(input, "UTF-8");

            Map<String, String> attrMap = new HashMap<String, String>(){{
                put("type", "radio");
                put("style", "height: 80%; width: 80%; vertical-align: middle; -webkit-appearance: radio;");
            }};

            int objCount = 1;
            int rowIndex = 3;
            while(!"".equals(html.select("tr").eq(rowIndex).text())) {
                Elements trElement = html.select("tr").eq(rowIndex);
                for (int i = 2; i <= 3; i++) {
                    Element tdElement = trElement.select("td").eq(i).first();
                    tdElement.empty();
                    Element div = tdElement.appendElement("div").attr("style", "width: 100%; height: 100%; vertical-align: middle; text-align: center; padding: 0;");
                    Element inputElement = div.appendElement("input");

                    for (Map.Entry<String, String> attr : attrMap.entrySet()) {
                        inputElement.attr(attr.getKey(), attr.getValue());
                    }
                    inputElement.attr("name", "agreement"+objCount);
                    inputElement.attr("value", i == 2? "agree" : "disagree");
                }
                rowIndex += 1;
                objCount += 1;
            }

            html.select("h2").remove();

            Element tr = html.select("tbody").first().appendElement("tr");
            Element td = tr.appendElement("td");
            td.attr("colspan", "10");
            td.attr("style", "padding-top: 10px; padding-left: 20px; height: 90px;");

            Element buttonElement = td.appendElement("button");
            buttonElement.attr("type", "button");
            buttonElement.attr("style", "width:25%; height: 80%; font-size: 130%; border-radius: 30px; display: block; margin: auto;");
            buttonElement.attr("onclick", "sendBallot()");
            buttonElement.text("送出選票");

            html.select("head").first().appendElement("script").attr("src", "https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js");
            html.select("head").first().appendElement("script").attr("src", "../../js/AgreementBallot.js");
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

    public List<AgreementBallot> getBallotList() {
        return ballotList;
    }
}
