package controller;

import util.Utility;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(
        name = "SharingController",
        urlPatterns = {"/ShareScreen", "/ViewScreen", "/ShareFile", "/ViewFile", "/DownloadFile", "/UploadShareFile", "/DeleteSingleFile", "/ResetFileList"}
)

@MultipartConfig
public class SharingController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath().replace("/", "");
        String servletPath = getServletContext().getRealPath("/");
        String savePath = servletPath + "template/Download/";
        File rankDir = new File(savePath);
        File[] files = rankDir.listFiles();
        String filename = request.getParameter("Filename");
        List<String> fileNameList = new ArrayList<>();

        switch (path) {
            case "ShareScreen":
                request.getRequestDispatcher("jsp/view/Streamer.jsp").forward(request, response);
                break;
            case "ViewScreen":
                request.getRequestDispatcher("jsp/view/Viewer.jsp").forward(request, response);
                break;
            case "ShareFile":
            case "ViewFile":
                if (files != null) {
                    for (File file : files) {
                        fileNameList.add(file.getName());
                    }
                }

                request.setAttribute("fileNameList", fileNameList);
                if("ShareFile".equals(path)){
                    request.getRequestDispatcher("jsp/view/ShareFile.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("jsp/view/ViewFile.jsp").forward(request, response);
                }

                break;
            case "DownloadFile":
                response.setContentType(getServletContext().getMimeType(filename));
                response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(filename, "UTF-8"));
                String fullFileName = savePath + filename;
                InputStream in = new FileInputStream(fullFileName);
                OutputStream out = response.getOutputStream();

                int b;
                while((b=in.read())!= -1)
                {
                    out.write(b);
                }

                in.close();
                out.close();
                break;
            case "DeleteSingleFile":
                if (files != null) {
                    for (File file : files) {
                        if ( file.getName().equals(filename) ){
                            file.delete();
                        }
                    }
                }
                response.sendRedirect("/ShareFile");
                break;
            case "ResetFileList":
                if (files != null) {
                    for (File file : files) {
                        file.delete();
                    }
                }

                response.sendRedirect("/ShareFile");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath().replace("/", "");

        switch (path) {
            case "ShareScreen":
                break;
            case "UploadShareFile":
                String servletPath = getServletContext().getRealPath("/");
                Utility.makeTemplateDir(servletPath, "Download");
                String savePath = servletPath + "template/Download/";

                for (Part part : request.getParts()) {
                    if (part.getContentType() != null){
                        part.write(savePath + part.getSubmittedFileName());
                    }
                }

                response.sendRedirect("/ShareFile");
                break;
        }
    }
}