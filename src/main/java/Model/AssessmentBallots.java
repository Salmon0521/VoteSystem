package Model;

import java.io.File;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.aspose.cells.Workbook;
import java.time.LocalDate;

import java.io.*;
import java.util.*;

public class AssessmentBallots extends Ballots {
    private List<AssessmentBallot> ballotList = new ArrayList<>();
    private LinkedHashMap<String, String> checkboxLabelMap = new LinkedHashMap<>();
    private String ratingMethod = "";

    public AssessmentBallots(){
        this.name = "教評會評分";
    }

    @Override
    public void ResetVotedBallots() {
        ballotList = new ArrayList<>();
    }
    public void ResetBallotInfo() {
        checkboxLabelMap = new LinkedHashMap<>();
        ratingMethod = "";
    }

    @Override
    protected List<XSSFWorkbook> updateResultFile() {
        List<XSSFWorkbook> workbooks = new ArrayList<>();

        String ballotTemplate = this.basePath + this.ballotFile;
        String resultTemplate = basePath + resultFile;
        String result = basePath + "升等評分結果test.xlsx";
        String result_copy = this.basePath + "升等評分結果copy.xlsx";

        List<Object[]> data;
        XSSFSheet sheet;
        XSSFWorkbook wb = getXSSFWorkbook(resultTemplate);

        // 投票統整sheet
        data = transformHashMapToArray();
        wb.setSheetName(0, "投票統整");
        sheet = wb.getSheet("投票統整");
        writeCellValue(sheet, data);

        // 未通過理由sheet
        createReasonSheet(wb);
        wb.removeSheetAt(1);
        exportFile(result, wb);

        // 各別選票sheet
        cloneBallotSheet(result, ballotTemplate, result_copy);
        fillBallotSheetValue(result_copy);

        workbooks.add(getXSSFWorkbook(result_copy));
        return workbooks;
    }

    private void fillBallotSheetValue(String result_copy) {
        Map<String, int[]> rowColMap = new HashMap<String, int[]>(){{
            put("checkbox0", new int[]{18, 0});
            put("checkbox1", new int[]{18, 2});
            put("checkbox2", new int[]{18, 6});
            put("checkbox3", new int[]{19, 0});
            put("checkbox4", new int[]{19, 2});
            put("checkbox5", new int[]{19, 6});
            put("checkbox6", new int[]{20, 0});
            put("checkbox7", new int[]{20, 6});
            put("checkbox8", new int[]{21, 0});
            put("checkbox9", new int[]{21, 2});
            put("checkbox10", new int[]{21, 6});
        }};

        XSSFWorkbook wb = getXSSFWorkbook(result_copy);
        int sheetIdx = 2;
        for (AssessmentBallot assessmentBallot : ballotList) {
            XSSFSheet cloneSheet = wb.getSheetAt(sheetIdx);
            wb.setSheetName(sheetIdx++, assessmentBallot.getUserUUID());

            // 修改專任教師B評分方式
            if (!cloneSheet.getRow(1).getCell(7).getStringCellValue().contains("兼任")) {
                XSSFFont font = wb.createFont();
                font.setFontName("標楷體");
                font.setFontHeightInPoints((short) 12);

                XSSFCellStyle cellStyle = wb.createCellStyle();
                cellStyle.setFont(font);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                cloneSheet.getRow(10).getCell(3).setCellValue(this.ratingMethod);
                cloneSheet.getRow(10).getCell(3).setCellStyle(cellStyle);
            }

            // 修改BCD評分
            cloneSheet.getRow(10).getCell(8).setCellValue(assessmentBallot.getResearchScore());
            cloneSheet.getRow(12).getCell(8).setCellValue(assessmentBallot.getEducationScore());
            cloneSheet.getRow(14).getCell(8).setCellValue(assessmentBallot.getServiceScore());

            // 修改checkbox勾選
            for (Map.Entry<String, Boolean> checkbox : assessmentBallot.getReasonList().entrySet()) {
                if (checkbox.getValue()) {
                    int[] rowColArray = rowColMap.get(checkbox.getKey());
                    Cell checkboxCell = cloneSheet.getRow(rowColArray[0]).getCell(rowColArray[1]);
                    String checkboxCellValue = checkboxCell.getStringCellValue().replace('□', '■');
                    checkboxCell.setCellValue(checkbox.getKey().equals("checkbox10")?
                            checkboxCellValue.split("_")[0] + assessmentBallot.getOtherReason() : checkboxCellValue);
                }
            }
        }
        wb.removeSheetAt(this.ballotList.size()+2);
        exportFile(result_copy, wb);
    }

    private void cloneBallotSheet(String result, String ballotTemplate, String result_copy) {
        Workbook resultWb = getAsposeWorkbook(result);
        Workbook ballotTemplateWb = getAsposeWorkbook(ballotTemplate);
        try {
            resultWb.getWorksheets().add("template");
            for (int i = 2; i<this.ballotList.size()+2; i++) {
                if (i != 2) {resultWb.getWorksheets().add();}
                resultWb.getWorksheets().get(i).copy(ballotTemplateWb.getWorksheets().get(0));
            }
            resultWb.save(result_copy);

            new File(result).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Workbook getAsposeWorkbook(String path) {
        Workbook workbook = null;
        try {
            workbook = new Workbook(path);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    private void createReasonSheet(XSSFWorkbook wb) {
        int rowNum = 0;
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        XSSFSheet reasonSheet = wb.createSheet("未過理由統整");
        reasonSheet.setColumnWidth(0, 90*256);
        reasonSheet.setColumnWidth(1, 15*256);
        reasonSheet.setColumnWidth(2, 90*256);

        Row titleRow = reasonSheet.createRow(rowNum++);
        titleRow.createCell(0).setCellValue("教師升等未通過理由");
        titleRow.createCell(1).setCellValue("勾選次數");
        titleRow.createCell(2).setCellValue("勾選人統整");
        for (int i=0; i<=2; i++) {
            titleRow.getCell(i).setCellStyle(cellStyle);
        }

        Map<String, Integer> checkboxCount = new LinkedHashMap<>();
        Map<String, String> checkboxUUID = new LinkedHashMap<>();
        for (Map.Entry<String, String> checkbox : checkboxLabelMap.entrySet()) {
            checkboxCount.put(checkbox.getKey(), 0);
            checkboxUUID.put(checkbox.getKey(), "");
        }

        for (AssessmentBallot assessmentBallot : ballotList) {
            for (Map.Entry<String, Boolean> reason : assessmentBallot.getReasonList().entrySet()) {
                if (reason.getValue()) {
                    checkboxCount.put(reason.getKey(), checkboxCount.get(reason.getKey()) + 1);
                    if (!reason.getKey().equals("checkbox10")) {
                        checkboxUUID.put(reason.getKey(), checkboxUUID.get(reason.getKey()).length() == 0 ?
                                assessmentBallot.getUserUUID() : checkboxUUID.get(reason.getKey()) + "、" + assessmentBallot.getUserUUID());
                    } else {
                        String otherReason = checkboxLabelMap.get(reason.getKey()) + assessmentBallot.getOtherReason();
                        checkboxLabelMap.put(otherReason, otherReason);
                        checkboxCount.put(otherReason, checkboxCount.containsKey(otherReason)? checkboxCount.get(otherReason) + 1 : 1);
                        checkboxUUID.put(otherReason, checkboxUUID.containsKey(otherReason) && checkboxUUID.get(otherReason).length() != 0?
                                checkboxUUID.get(otherReason) +"、"+ assessmentBallot.getUserUUID() : assessmentBallot.getUserUUID());
                    }
                }
            }
        }

        for (Map.Entry<String, String> checkbox : checkboxLabelMap.entrySet()) {
            if (checkbox.getKey().equals("checkbox10") && checkboxCount.get(checkbox.getKey()) > 0) {
                continue;
            }
            Row reasonRow = reasonSheet.createRow(rowNum++);
            reasonRow.createCell(0).setCellValue(checkbox.getValue());
            reasonRow.createCell(1).setCellValue(checkboxCount.get(checkbox.getKey()));
            reasonRow.getCell(1).setCellStyle(cellStyle);
            reasonRow.createCell(2).setCellValue(checkboxUUID.get(checkbox.getKey()));
        }
    }

    private void writeCellValue(XSSFSheet sheet, List<Object[]> data) {
        Cell dateCell = sheet.getRow(0).getCell(5);
        dateCell.setCellValue(LocalDate.now().toString());

        int rowNum = 4;
        for (Object[] objects : data) {
            Row ballotRow = sheet.getRow(rowNum++);
            int cellNum = 0;
            for (Object obj : objects) {
                Cell cell = ballotRow.getCell(cellNum);
                if (cellNum == 0) {
                    cell.setCellValue((String)obj);
                } else {
                    cell.setCellValue(Double.parseDouble(obj.toString()));
                }
                cellNum += 1;
            }
            String formula = String.join(String.valueOf(rowNum), "SUM(B", ":E", ")");
            Cell cell = ballotRow.getCell(cellNum);
            cell.setCellFormula(formula);
        }
        int lastRowNum = sheet.getLastRowNum();
        while (!sheet.getRow(lastRowNum).getCell(0).getStringCellValue().contains("教評會")) {
            lastRowNum -= 1;
        }
        sheet.removeRow(sheet.getRow(rowNum));
        for (int i=rowNum; i<lastRowNum; i++) {
            if (sheet.getRow(i) != null) {
                sheet.removeRow(sheet.getRow(i));
            }
        }
        sheet.shiftRows(lastRowNum, 3*lastRowNum-2*rowNum, rowNum-lastRowNum);

        sheet.getRow(rowNum).getCell(5).setCellFormula("COUNTIF(F5:F" + rowNum + ",G5)");
        sheet.getRow(rowNum+1).getCell(2).setCellFormula("COUNTA(A5:A" + rowNum + ")");
        sheet.getRow(rowNum+1).getCell(5).setCellFormula("ROUNDUP(C" + (rowNum+2) + "*2/3,0)");
        sheet.getRow(sheet.getLastRowNum()).getCell(2).setCellFormula("IF(AND(F" + (rowNum+1) + ">=F" + (rowNum+2) + "),\"通過\",\"不通過\")");
    }

    private void exportFile(String result, XSSFWorkbook wb) {
        try {
            FileOutputStream out = new FileOutputStream(result);
            wb.write(out);
            wb.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private XSSFWorkbook getXSSFWorkbook(String resultTemplate) {
        XSSFWorkbook ballotWb = null;
        try {
            InputStream ballotIs = new FileInputStream(resultTemplate);
            ballotWb = new XSSFWorkbook(ballotIs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ballotWb;
    }

    private List<Object[]> transformHashMapToArray() {
        List<Object[]> data = new ArrayList<>();
        for (AssessmentBallot assessmentBallot : ballotList) {
            List<Object> row = new ArrayList<>();
            row.add(assessmentBallot.getUserUUID());
            row.add(assessmentBallot.getWritingScore());
            row.add(assessmentBallot.getResearchScore());
            row.add(assessmentBallot.getEducationScore());
            row.add(assessmentBallot.getServiceScore());
            data.add(row.toArray());
        }
        return data;
    }

    @Override
    public void updateBallot(List<LinkedHashMap<String, String>> paramListMap, String userUUID) {
        LinkedHashMap<String, String> paramMap = paramListMap.get(0);
        AssessmentBallot ballot = new AssessmentBallot();
        ballot.setWritingScore(Double.parseDouble(paramMap.get("writingScore")));
        ballot.setResearchScore(Double.parseDouble(paramMap.get("researchScore")));
        ballot.setEducationScore(Double.parseDouble(paramMap.get("educationScore")));
        ballot.setServiceScore(Double.parseDouble(paramMap.get("serviceScore")));

        HashMap<String, Boolean> reasonList = new HashMap<>();
        for (Map.Entry<String, String> checkbox : checkboxLabelMap.entrySet()) {
            reasonList.put(checkbox.getKey(), Boolean.parseBoolean(paramMap.get(checkbox.getKey())));
        }
        ballot.setReasonList(reasonList);
        ballot.setOtherReason(paramMap.get("otherReason"));
        ballot.setUserUUID(userUUID);
        ballotList.add(ballot);
    }

    @Override
    protected void modifyHTML() {
        String ballotPath = this.basePath + this.ballotFile;
        String htmlPath = this.basePath + "assessmentFile.jsp";

        com.spire.xls.Workbook wb = new com.spire.xls.Workbook();
        wb.loadFromFile(ballotPath);

        com.spire.xls.Worksheet sheet = wb.getWorksheets().get(0);
        sheet.saveToHtml(htmlPath);
        File input = new File(htmlPath);
        try {
            Document html = Jsoup.parse(input, "UTF-8");
            html.select("h2").remove();

            String title = html.select("td.X2").first().text();
            String[] scoreTdId = new String[]{"researchScore", "educationScore", "serviceScore"};
            String[] slashClsArray, checkboxClsArray, totalScoreClsArray;
            String scoreCls;
            int[] scoreClsIdxArray;
            int[] points;
            if (!title.contains("兼任")) {
                slashClsArray = new String[]{"X17", "X27", "X36"};
                checkboxClsArray = new String[]{"X46", "X48"};
                totalScoreClsArray = new String[]{"X18", "X21"};
                scoreCls = "X28";
                scoreClsIdxArray = new int[]{1, 2};

                // 計算專任B評分input
                points = !title.contains("副")? new int[]{15, 25} : new int[]{12, 18};
                Element B_score = html.select("td.X28").first();
                float B_point = Float.parseFloat(B_score.text());
                B_score.empty();

                String maxScore, minScore;
                if (B_point <= points[0]) {
                    maxScore = "13.0";
                    minScore = "10.1";
                } else if (B_point <= points[1]) {
                    maxScore = "17.0";
                    minScore = "13.1";
                } else {
                    maxScore = "19.98";
                    minScore = "17.1";
                }

                Map<String, String> attrMap = new HashMap<String, String>(){{
                    put("style", "height: 100%; font-size: 120%; width: 100%; text-align: center");
                    put("type", "number");
                    put("step", "0.01");
                    put("required", "required");
                    put("id", scoreTdId[0]);
                    put("max", maxScore);
                    put("min", minScore);
                    put("onchange", "computeTotalScore()");
                }};
                Element inputElement = B_score.appendElement("input");
                for (Map.Entry<String, String> attr : attrMap.entrySet()) {
                    inputElement.attr(attr.getKey(), attr.getValue());
                }

                // 新增專任B評分方式
                Elements exScoreMethodChildren = html.select("td.X26").eq(1).first().children().clone();
                Element B_scoreMethod = html.select("td.X26").eq(0).first();
                B_scoreMethod.empty();
                Element new_B_scoreMethod = B_scoreMethod.appendChildren(exScoreMethodChildren);
                new_B_scoreMethod.select("font").eq(4).first().text(minScore);
                new_B_scoreMethod.select("font").eq(6).first().text("~"+maxScore);
                ratingMethod = new_B_scoreMethod.text();
            } else {
                slashClsArray = new String[]{"X16", "X26", "X33"};
                checkboxClsArray = new String[]{"X43", "X45"};
                totalScoreClsArray = new String[]{"X17", "X20"};
                scoreCls = "X27";
                scoreClsIdxArray = new int[]{0, 2};
            }

            // 新增著作分數id
            html.select("td."+totalScoreClsArray[1]).first().attr("id", "writingScore");

            // 新增表格斜線
            for (String cls : slashClsArray) {
                for (Element slashTd : html.select("td."+cls)) {
                    slashTd.attr("style", "background-image: linear-gradient(to bottom left,  transparent calc(50% - 1px), black, transparent calc(50% + 1px));");
                }
            }

            // 新增評分input
            for (int scoreClsIdx=scoreClsIdxArray[0]; scoreClsIdx<=scoreClsIdxArray[1]; scoreClsIdx++) {
                Element scoreTd = html.select("td."+scoreCls).eq(scoreClsIdx).first();
                String scoreRange = scoreTd.text();
                scoreTd.empty();

                Map<String, String> scoreInputAttrMap = new HashMap<String, String>(){{
                    put("style", "height: 100%; font-size: 120%; width: 100%; text-align: center");
                    put("type", "number");
                    put("step", "0.01");
                    put("required", "required");
                    put("max", scoreRange.split("~")[1]);
                    put("min", scoreRange.split("~")[0]);
                    put("onchange", "computeTotalScore()");
                }};
                scoreInputAttrMap.put("id", scoreTdId[scoreClsIdx]);

                Element inputElement = scoreTd.appendElement("input");
                for (Map.Entry<String, String> attr : scoreInputAttrMap.entrySet()) {
                    inputElement.attr(attr.getKey(), attr.getValue());
                }
            }

            // 調整總分
            String writingScore = html.select("td."+totalScoreClsArray[1]).first().text();
            html.select("td."+totalScoreClsArray[0]).first().text(writingScore);
            html.select("td."+totalScoreClsArray[0]).first().attr("style", "font-size: 200%; color: red");

            // 新增教師升等為過理由checkbox
            int checkboxIdx = 0;
            int[] colspan = {2, 5, 3, 2, 5, 3, 7, 3, 2, 3, 5};
            boolean checkboxOthers;
            for (String checkboxCls : checkboxClsArray) {
                for (Element checkboxTd : html.select("td."+checkboxCls)) {
                    String checkboxText = checkboxTd.text().replaceAll(" ", "").split("□")[1].split("_")[0];
                    checkboxOthers = checkboxText.contains("其他：");

                    // 調整checkIdx錯亂問題
                    String checkboxName = "checkbox" + (checkboxIdx == 9? 10 : (checkboxIdx == 10? 9 : checkboxIdx));
                    checkboxLabelMap.put(checkboxName, checkboxText);
                    checkboxTd.attr("colspan", String.valueOf(colspan[checkboxIdx]));
                    checkboxIdx += 1;
                    checkboxTd.empty();

                    Element checkboxInput = checkboxTd.appendElement("input");
                    Map<String, String> checkboxAttrMap = new HashMap<String, String>(){{
                        put("type", "checkbox");
                        put("id", checkboxName);
                        put("name", checkboxName);
                        put("value", checkboxName);
                    }};
                    for (Map.Entry<String, String> attr : checkboxAttrMap.entrySet()) {
                        checkboxInput.attr(attr.getKey(), attr.getValue());
                    }

                    Element checkboxLabel = checkboxTd.appendElement("label");
                    checkboxLabel.attr("for", checkboxName);
                    checkboxLabel.text(checkboxText);

                    if (checkboxOthers) {
                        Element otherInput = checkboxTd.appendElement("input");
                        otherInput.attr("type", "text");
                        otherInput.attr("id", "otherReason");
                        otherInput.attr("placeholder", "輸入前請先勾選");
                        otherInput.attr("oninput", "checkOtherReason()");
                    }
                }
            }
            // 調整checkbox9、10未依照順序put進HashMa
            String checkbox10Temp = checkboxLabelMap.get("checkbox10");
            checkboxLabelMap.remove("checkbox10");
            checkboxLabelMap.put("checkbox10", checkbox10Temp);

            // 針對checkbox的tr新增id
            Elements rowElements = html.select("tr");
            for (int idx=17; idx<=21; idx++) {
                rowElements.eq(idx).first().attr("id", "checkboxRow"+idx);
                rowElements.eq(idx).first().attr("style", "display: none;");
            }

            Element tr = html.select("tbody").first().appendElement("tr");
            Element td = tr.appendElement("td");
            td.attr("colspan", "10");
            td.attr("style", "padding-top: 10px; padding-left: 20px; height: 90px;");

            Element buttonElement = td.appendElement("button");
            buttonElement.attr("type", "button");
            buttonElement.attr("style", "width:20%; height: 80%; font-size: 140%; border-radius: 30px; display: block; margin: auto;");
            buttonElement.attr("onclick", "sendBallot()");
            buttonElement.text("送出選票");

            // 新增jquery及js
            html.select("head").first().appendElement("script").attr("src", "https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js");
            html.select("head").first().appendElement("script").attr("src", "../../js/AssessmentBallot.js");
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

    public List<AssessmentBallot> getBallotList() {
        return ballotList;
    }
}