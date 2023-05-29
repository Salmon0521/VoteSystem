package controller;

import model.Candidate;
import com.google.gson.Gson;
import service.UserService;
import service.VoteActivity;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import util.Utility;


@WebServlet(
        name = "VotingController",
        urlPatterns = {"/Login", "/Logout", "/CheckLogin", "/CheckVoting", "/Index", "/BallotPage", "/CreateVoteActivity",
                "/EditBallot", "/CheckVoteActivity", "/GetTitle", "/UpdateTitle", "/GetCandidates", "/AddCandidate", "/DeleteCandidate",
                "/ManageVoteActivity", "/Invoicing", "/Vote", "/Reset", "/CountBallot"}
)
@MultipartConfig
public class VotingController extends HttpServlet {
    private static final String BASE_URL = "/web-meeting-java";
    UserService userService = new UserService();
    VoteActivity voteActivity = new VoteActivity();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath().replace("/", "");
        Integer privilege = (Integer) request.getSession().getAttribute("privilege");

        if (privilege == null || privilege > 1) {
            path = "Login";
        }

        switch (path) {
            case "Index":
                if (privilege == 0){
                    request.getRequestDispatcher("/WEB-INF/jsp/view/ParticipantIndex.jsp").forward(request, response);
                }
                else if (privilege == 1){
                    request.getRequestDispatcher("/WEB-INF/jsp/view/AdminIndex.jsp").forward(request, response);
                }
                else {
                    response.sendRedirect(BASE_URL + "/Login");
                }
                break;
            case "EditBallot":
                if (privilege == 1){
                    request.getRequestDispatcher("/WEB-INF/jsp/view/EditBallot.jsp").forward(request, response);
                }
                else {
                    response.sendRedirect(BASE_URL + "/Index");
                }
                break;
            case "ManageVoteActivity":
                if (privilege == 1){
                    request.getRequestDispatcher("/WEB-INF/jsp/view/ManageVoteActivity.jsp").forward(request, response);
                }
                else {
                    response.sendRedirect(BASE_URL + "/Index");
                }
                break;
            case "BallotPage":
                if (privilege == 0){
                    request.getRequestDispatcher("/WEB-INF/jsp/view/Ballot.jsp").forward(request, response);
                }
                else {
                    response.sendRedirect(BASE_URL + "/Index");
                }
                break;
            case "Login":
                request.getRequestDispatcher("/WEB-INF/jsp/view/Login.jsp").forward(request, response);
                break;
            case "Logout":
                request.getSession().removeAttribute("account");
                request.getSession().removeAttribute("privilege");
                response.sendRedirect(BASE_URL + "/Login");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath().replace("/", "");
        String servletPath = getServletContext().getRealPath("/");
        PrintWriter out = response.getWriter();
        Integer privilege = (Integer) request.getSession().getAttribute("privilege");

        if (privilege == null || privilege > 1) {
            this.doGet(request, response);
        }

        switch (path) {
            case "CheckLogin":
                String account = request.getParameter("Account");
                String password = request.getParameter("Password");

                privilege = userService.login(account, password);
                String UUID = userService.getUserBallotUUID(account);

                request.getSession().setAttribute("account", account);
                request.getSession().setAttribute("privilege", privilege);
                request.getSession().setAttribute("UUID", UUID);

                if (privilege == null) {
                    out.print("error");
                }
                break;
            case "CheckVoting":
                if (!voteActivity.getStatus()) {
                    out.print("0");
                } else if (userService.getUserVoted((String) request.getSession().getAttribute("account"))) {
                    out.print("1");
                } else if (privilege == 0) {
                    out.print("2");
                }
                else {
                    response.sendRedirect(BASE_URL + "/Login");
                }
                break;
            case "CheckVoteActivity":
                if (!voteActivity.getStatus()) {
                    out.print("0");
                } else {
                    out.print("1");
                }
                break;
            case "CreateVoteActivity":
                if (!voteActivity.getStatus()) {
                    if (voteActivity.getCandidates().size() > 0){
                        voteActivity.setStatus(true);
                        out.print("0");
                    }
                    else {
                        out.print("1");
                    }
                } else if (voteActivity.getStatus()) {
                    out.print("2");
                }
                break;
            case "Vote":
                if (!voteActivity.getStatus()) {
                    response.setStatus(400);
                } else if (userService.getUserVoted((String) request.getSession().getAttribute("account"))) {
                    response.setStatus(500);
                } else {
                    String VoteData = request.getParameter("VoteData");
                    String ballotUUID = voteActivity.vote(VoteData);
                    userService.updateUserVoted((String) request.getSession().getAttribute("account"), ballotUUID);
                    request.getSession().setAttribute("UUID", ballotUUID);
                    out.print(voteActivity.getCandidateName(VoteData));
                }
                break;
            case "Invoicing":
                if (privilege == 1) {
                    voteActivity.setStatus(false);
                }
                break;
            case "Reset":
                if (privilege == 1) {
                    List<Candidate> candidates = voteActivity.getCandidates();
                    for (Candidate candidate : candidates) {
                        File file = new File(servletPath + "img/candidateIMG/" + candidate.getImage());
                        file.delete();
                    }
                    voteActivity.reset();
                    userService.resetUserVoted();
                }
                break;
            case "AddCandidate":
                String savePath = servletPath + "img/candidateIMG/";
                String uuid = Utility.generateUUID();

                Map<String, String> candidateData = new LinkedHashMap<>();
                candidateData.put("uuid", uuid);
                candidateData.put("name", request.getParameter("candidateName"));
                candidateData.put("introduction", request.getParameter("candidateIntroduction"));
                candidateData.put("image", uuid + ".png");

                Part part = request.getPart("candidateIMG");
                if (!part.getSubmittedFileName().equals("")) {
                    part.write(savePath + candidateData.get("image"));
                } else {
                    String sourcePath = servletPath + "img/candidate.png" ;
                    File sourceFile = new File(sourcePath);
                    File targetFile = new File(savePath + candidateData.get("image"));
                    Files.copy(sourceFile.toPath(), targetFile.toPath());
                }

                voteActivity.addCandidate(candidateData);
                response.sendRedirect(BASE_URL + "/EditBallot");
                break;
            case "DeleteCandidate":
                String candidateUUID = request.getParameter("candidateUUID");
                String targetFile = servletPath + "img/candidateIMG/" + candidateUUID + ".png";

                Path targetFilePath = Paths.get(targetFile );
                Files.deleteIfExists(targetFilePath);

                voteActivity.deleteCandidate(candidateUUID);
                break;
            case "UpdateTitle":
                String voteActivityTitle = request.getParameter("votingActivityTitle");
                voteActivity.setTitle(voteActivityTitle);
                out.print(voteActivityTitle);
                break;
            case "GetTitle":
                out.print(voteActivity.getTitle());
                break;
            case "GetCandidates":
                List<Candidate> candidates = voteActivity.getCandidates();
                String candidatesJson = new Gson().toJson(candidates);
                out.print(candidatesJson);
                break;
            case "CountBallot":
                int count = voteActivity.countBallot();
                out.print(count);
                break;
        }
    }
}