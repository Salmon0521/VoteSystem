package controller;

import service.UserService;
import service.VoteActivity;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@WebServlet(
        name = "VotingController",
        urlPatterns = {"/Login", "/CheckLogin", "/Logout", "/CheckVoting", "/Index", "/AdminIndex",
                "/Invoicing", "/Vote", "/Reset", "/BallotPage"}
)
@MultipartConfig
public class VotingController extends HttpServlet {
    private static final String BASE_URL = "/web-meeting-java";
    UserService userService = new UserService();
    VoteActivity voteActivity = new VoteActivity();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath().replace("/", "");
        String servletPath = getServletContext().getRealPath("/");
        Integer privilege = (Integer) request.getSession().getAttribute("privilege");

        if (privilege == null || privilege > 1) {
            path = "Login";
        }

        switch (path) {
            case "AdminIndex":
                if (privilege == 1){
                    request.getRequestDispatcher("/WEB-INF/jsp/view/Index.jsp").forward(request, response);
                }
                else {
                    response.sendRedirect(BASE_URL + "/Index");
                }
                break;
            case "Index":
                if (privilege == 0){
                    request.getRequestDispatcher("/WEB-INF/jsp/view/ParticipantIndex.jsp").forward(request, response);
                }
                else if (privilege == 1){
                    request.getRequestDispatcher("/WEB-INF/jsp/view/Index.jsp").forward(request, response);
                }
                else {
                    response.sendRedirect(BASE_URL + "/Login");
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
        PrintWriter out = response.getWriter();
        Integer privilege = (Integer) request.getSession().getAttribute("privilege");

        switch (path) {
            case "CheckLogin":
                String account = request.getParameter("Account");
                String password = request.getParameter("Password");

                privilege = userService.login(account, password);
                String UUID = userService.getUserUUID(account);
                request.getSession().setAttribute("account", account);
                request.getSession().setAttribute("privilege", privilege);
                request.getSession().setAttribute("UUID", UUID);
                if (privilege == null) {
                    out.print("error");
                }
                break;
            case "CheckVoting":
                if (!voteActivity.checkVoteActivityStatus()) {
                    out.print("0");
                } else if (userService.getVoted((String) request.getSession().getAttribute("account"))) {
                    out.print("1");
                } else if (privilege == 0) {
                    out.print("2");
                }
                else {
                    response.sendRedirect(BASE_URL + "/Index");
                }
                break;
            case "Vote":
                if (!voteActivity.checkVoteActivityStatus()) {
                    response.setStatus(400);
                } else if (userService.getVoted((String) request.getSession().getAttribute("account"))) {
                    response.setStatus(500);
                } else {
                    String VoteData = request.getParameter("VoteData");
                    String ballotUUID = voteActivity.vote(VoteData);
                    userService.setUserUUID((String) request.getSession().getAttribute("account"), ballotUUID);
                    request.getSession().setAttribute("UUID", ballotUUID);
                }
                break;
        }
    }
}