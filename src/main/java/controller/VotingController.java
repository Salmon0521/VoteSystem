package controller;

import io.socket.client.IO;
import io.socket.client.Socket;
import service.VoteService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

import com.google.gson.Gson;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.Utility;


@WebServlet(
        name = "VotingController",
        urlPatterns = {"/Admin", "/Participant", "/CreateMeeting", "/EditMeeting", "/Invoicing",
                "/InvoicingTwo", "/Vote", "/Upload", "/UploadAndStatistic", "/index", "/Reset",
                "/VoteIndex", "/VoteBallot", "/ChangeStatus", "/GetCount", "/DownloadExampleFiles", "/BallotPage"}
)
@MultipartConfig
public class VotingController extends HttpServlet {

    VoteService service = new VoteService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath().replace("/", "");
        String meetingType = request.getParameter("MeetingType");
        String[] fileNames;
        String servletPath = getServletContext().getRealPath("/");

        switch (path) {
            case "Admin":
                request.getRequestDispatcher("/WEB-INF/jsp/view/AdminMeeting.jsp").forward(request, response);
                break;
            case "Participant":
                request.getRequestDispatcher("/WEB-INF/jsp/view/ParticipantMeeting.jsp").forward(request, response);
                break;
            case "UploadAndStatistic":
                Boolean status = service.checkVotingStatus(meetingType);
                String title = service.getName(meetingType);
                fileNames = service.checkUpload(meetingType);
                request.setAttribute("ballotFile", fileNames[0]);
                request.setAttribute("resultFile", fileNames[1]);
                request.setAttribute("status", status);
                request.setAttribute("meetingType", meetingType);
                request.setAttribute("title", title);
                request.getRequestDispatcher("jsp/view/ManageBallot.jsp").forward(request, response);
                break;
            case "index":
                request.getRequestDispatcher("jsp/view/Index.jsp").forward(request, response);
                break;
            case "VoteIndex":
                Object sessionUserUUID = request.getSession().getAttribute("userUUID");
                String userUUID = "";
                if (sessionUserUUID == null || sessionUserUUID.toString().length() == 0) {
                    userUUID = Utility.generateUUID();
                    request.getSession().setAttribute("userUUID", userUUID);
                } else {
                    userUUID = sessionUserUUID.toString();
                }
                request.setAttribute("userUUID", userUUID);
                request.getRequestDispatcher("jsp/view/VoteIndex.jsp").forward(request, response);
                break;
            case "VoteBallot":
                PrintWriter out = response.getWriter();
                fileNames = service.checkUpload(meetingType);
                if (fileNames[0].length() == 0) {
                    out.print("0");
                } else {
                    if (!service.checkVotingStatus(meetingType)) {
                        out.print("1");
                    } else if (!service.checkVotingUUID(request.getSession().getAttribute(meetingType), meetingType)) {
                        out.print("2");
                    } else {
                        out.print("/BallotPage?MeetingType="+meetingType);
                    }
                }
                out.flush();
                break;
            case "BallotPage":
                String jspPath = "template/" + meetingType + "/" + meetingType.toLowerCase() + "File.jsp";
                File ballotJsp = new File(servletPath + jspPath);
                if (ballotJsp.exists()) {
                    request.getRequestDispatcher(jspPath).forward(request, response);
                } else {
                    response.sendRedirect("/VoteIndex?reset=reset");
                }
                break;
            case "Invoicing":
                int downloadType = Integer.parseInt(request.getParameter("DownloadType"));
                XSSFWorkbook workbook = service.Invoicing(meetingType).get(downloadType);
                ServletOutputStream outputStream = response.getOutputStream();
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" +
                        URLEncoder.encode(service.getInvoicingFileName(meetingType, downloadType), "UTF-8"));

                workbook.write(outputStream);
                workbook.close();
                outputStream.flush();
                outputStream.close();
                break;
            case "GetCount":
                PrintWriter out2 = response.getWriter();
                int count = service.countVotedBallots(meetingType);
                out2.print(count);
                out2.flush();
                break;
            case "DownloadExampleFiles":
                String filePath = "";
                ServletOutputStream exampleOutputStream = response.getOutputStream();
                response.setCharacterEncoding("UTF-8");

                String fileName = service.getExampleFileName(meetingType, Integer.parseInt(request.getParameter("DownloadType")));
                response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
                filePath = servletPath + "../../" + meetingType + "/" + fileName;

                XSSFWorkbook ballotWb;
                try {
                    InputStream ballotIs = new FileInputStream(filePath);
                    ballotWb = new XSSFWorkbook(ballotIs);
                    ballotWb.write(exampleOutputStream);
                    ballotWb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                exampleOutputStream.flush();
                exampleOutputStream.close();
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath().replace("/", "");
        String meetingType = request.getParameter("MeetingType");
        PrintWriter out = response.getWriter();

        switch (path) {
            case "Invoicing": {
                String downloadType = request.getParameter("DownloadType");
                try {
                    XSSFWorkbook workbook = service.Invoicing(meetingType).get(Integer.parseInt(downloadType));
                    out.print("/Invoicing?MeetingType=" + meetingType + "&DownloadType=" + downloadType);
                } catch (Exception e) {
                    e.printStackTrace();
                    out.print("1");
                }
                out.flush();
                break;
            }
            case "Vote":
                String userUUID = request.getSession().getAttribute("userUUID").toString();
                if (!service.checkVotingStatus(meetingType)) {
                    response.setStatus(400);
                } else if (service.checkIsVoted(userUUID, meetingType)) {
                    response.setStatus(450);
                } else if (service.checkUpload(meetingType)[0].length() == 0 || service.checkUpload(meetingType)[1].length() == 0) {
                    response.setStatus(500);
                } else {
                    if (meetingType.equals("Assessment")) {
                        Enumeration<String> enumeration = request.getParameterNames();
                        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
                        while (enumeration.hasMoreElements()) {
                            String parameterName = enumeration.nextElement();
                            paramMap.put(parameterName, request.getParameter(parameterName));
                        }
                        List<LinkedHashMap<String, String>> paramListMap = new ArrayList<>();
                        paramListMap.add(paramMap);
                        service.Vote(meetingType, paramListMap, userUUID);
                    } else {
                        String VoteData = request.getParameter("VoteData");
                        Type mapType = new TypeToken<Map<String, LinkedHashMap<String, String>>>() {}.getType();
                        Map<String, LinkedHashMap<String, String>> voteTemp = new Gson().fromJson(VoteData, mapType);
                        List<LinkedHashMap<String, String>> voteList = new ArrayList<>(voteTemp.values());
                        service.Vote(meetingType, voteList, userUUID);
                    }

                    request.getSession().setAttribute(meetingType, service.getVotingUUID(meetingType));
                    sendCountToSocket(meetingType, service.countVotedBallots(meetingType));
                }
                break;
            case "Upload":
                String servletPath = getServletContext().getRealPath("/");
                String savePath = servletPath + "template/" + meetingType + "/";

                Utility.makeTemplateDir(servletPath);
                clearTemplateDir(servletPath, meetingType);

                List<String> pathList = new ArrayList<>();
                pathList.add(savePath);
                try {
                    for (Part part : request.getParts()) {
                        if (part.getContentType() != null) {
                            pathList.add(part.getSubmittedFileName());
                            part.write(savePath + part.getSubmittedFileName());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    service.Upload(meetingType, pathList.get(0), pathList.get(1), pathList.get(2));
                    service.setVotingUUID(meetingType);
                    if (service.checkResultFile(meetingType)) {
                        service.ResetVotedBallots(meetingType);
                        out.print("1");
                    } else {
                        service.Reset(meetingType);
                        out.print("3");
                    }
                } catch (Exception e) {
                    service.Reset(meetingType);
                    out.print("2");
                }
                out.flush();
                break;
            case "Reset":
                request.getSession().setAttribute(meetingType, null);
                clearTemplateDir(getServletContext().getRealPath("/"), meetingType);
                service.Reset(meetingType);
                break;
            case "ChangeStatus":
                if (service.countVotedBallots(meetingType) == 0) {
                    out.print("1");
                } else {
                    out.print("2");
                    service.changeStatus(meetingType);
                }
                break;
        }
    }

    private void clearTemplateDir(String servletPath, String meetingType) {
        String savePath = servletPath + "template/" + meetingType + "/";
        File rankDir = new File(savePath);
        File[] files = rankDir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
            
    private void sendCountToSocket(String meetingType, int count){
        try {
            Socket socket = IO.socket("http://localhost:8088");
            socket.on("updateVoteMsg",objects -> System.out.println("SocketIO: " + objects[0].toString() + ", " + objects[1].toString()));
            socket.connect();
            socket.emit("updateVoteMsg", meetingType, count);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}