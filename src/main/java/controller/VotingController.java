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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import util.Utility;


@WebServlet(
        name = "VotingController",
        urlPatterns = {"/Login", "/Logout", "/CheckLogin", "/CheckVoting", "/Index", "/BallotPage", "/CreateVoteActivity",
                "/EditBallot", "/CheckVoteActivity", "/GetTitle", "/UpdateTitle", "/GetCandidates", "/AddCandidate", "/DeleteCandidate",
                "/ManageVoteActivity", "/Invoicing", "/Vote", "/Reset", "/CountBallot", "/GetResult", "/CheckResult", "/VotingResult"
                , "/GetBallot"}
)
@MultipartConfig
public class VotingController extends HttpServlet {
    private static final String BASE_URL = "/VoteSystem";
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
            case "VotingResult":
                if (privilege < 2){
                    request.getRequestDispatcher("/WEB-INF/jsp/view/VotingResult.jsp").forward(request, response);
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

        switch (path) {
            case "CheckLogin":
                String account = request.getParameter("Account");
                String password = request.getParameter("Password");

                privilege = userService.login(account, password);
                String UUID = userService.getUserBallotUUID(account);

                if (privilege == null) {
                    if (userService.checkAccount(account)) {
                        out.print("1");
                    } else {
                        out.print("2");
                    }
                }
                else {
                    request.getSession().setAttribute("account", account);
                    request.getSession().setAttribute("privilege", privilege);
                    request.getSession().setAttribute("UUID", UUID);
                    out.print("0");
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
                    if (voteActivity.getVoteActivityTitle() == null || voteActivity.getVoteActivityTitle().equals("")){
                        out.print("2");
                    } else if (voteActivity.checkCandidatesIsNone()){
                        out.print("3");
                    } else{
                        voteActivity.setStatus(true);
                        out.print("0");
                    }
                } else {
                    out.print("1");
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
                    if (!voteActivity.getStatus()) {
                        out.print("1");
                    } else {
                        voteActivity.setStatus(false);
                        voteActivity.invoicing();
                        out.print("0");
                    }
                }
                break;
            case "Reset":
                if (privilege == 1) {
                    List<Candidate> candidates = voteActivity.getCandidates();
                    for (Candidate candidate : candidates) {
                        Utility.deleteCandidateIMG(servletPath, candidate.getImage());
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
                    Utility.setDefaultCandidateIMG(servletPath, candidateData.get("image"));
                }

                voteActivity.addCandidate(candidateData);
                response.sendRedirect(BASE_URL + "/EditBallot");
                break;
            case "DeleteCandidate":
                String candidateUUID = request.getParameter("candidateUUID");
                Utility.deleteCandidateIMG(servletPath, candidateUUID + ".png");
                voteActivity.deleteCandidate(candidateUUID);
                break;
            case "UpdateTitle":
                String voteActivityTitle = request.getParameter("votingActivityTitle");
                voteActivity.setVoteActivityTitle(voteActivityTitle);
                out.print(voteActivityTitle);
                break;
            case "GetTitle":
                out.print(voteActivity.getVoteActivityTitle());
                break;
            case "GetCandidates":
                List<Candidate> candidates = voteActivity.getCandidates();
                String candidatesJson = new Gson().toJson(candidates);
                out.print(candidatesJson);
                break;
            case "CountBallot":
                Map<String, String> ballotData = new LinkedHashMap<>();
                int countNum = voteActivity.countBallot();
                int totalNum = userService.getUsers().size();
                double votingRate = Math.round(countNum / (double) totalNum * 1000.0) / 10.0;

                ballotData.put("countNum", String.valueOf(countNum));
                ballotData.put("totalNum", String.valueOf(totalNum));
                ballotData.put("votingRate", String.valueOf(votingRate) + "%");
                String ballotDataJson = new Gson().toJson(ballotData);
                out.print(ballotDataJson);
                break;
            case "CheckResult":
                if (voteActivity.getVoteActivityTitle().equals("") || voteActivity.getCandidates().size() < 1) {
                    out.print("1");
                } else {
                    out.print("0");
                }
                break;
            case "GetResult":
                if (!voteActivity.getVoteActivityTitle().equals("")) {
                    if (!voteActivity.getStatus()) {
                        List<Map<String, String>>  result = voteActivity.getResult();
                        String resultJson = new Gson().toJson(result);
                        out.print(resultJson);
                    } else {
                        out.print("2");
                    }
                } else {
                    out.print("1");
                }
                break;
            case "GetBallot":
                String userAccount = request.getSession().getAttribute("account").toString();
                String UserBallotUUID = userService.getUserBallotUUID(userAccount);

                if (UserBallotUUID == null) {
                    response.setStatus(400);
                } else {
                    String candidateNameInBallot = voteActivity.getVotedBallot(UserBallotUUID);
                    if (candidateNameInBallot == null) {
                        response.setStatus(500);
                    } else {
                        out.print(candidateNameInBallot);
                    }
                }
                break;
        }
    }
}