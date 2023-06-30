package com.example.demo.Exports.CSVExporter;

import com.example.demo.Exports.ExportInfo;
import com.example.demo.Exports.ExportInfoService;
import com.example.demo.Exports.ExportMessage;
import com.example.demo.Exports.ExportMessageService;
import com.example.demo.User.UserEntity;
import com.example.demo.User.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/export-as-CSV")
public class ExportCSVController extends HttpServlet {
    private final UserService userService = new UserService();
    private final ExportInfoService exportInfoService = new ExportInfoService();
    private final ExportMessageService exportMessageService = new ExportMessageService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        try {
            Object o = req.getAttribute("email");
            String email = o.toString();
            UserEntity user = userService.findByEmail(email);
            System.out.println(user.getEmail());
            List<ExportInfo> exportInfos = exportInfoService.getExportInfos(email);
            String userInfo = user.getName() + ", " + user.getEmail() + ", " + user.getPictureurl() + ", " + user.getCreatedat() + ", " + user.getUpdatedat();
            resp.setStatus(200);
            resp.setContentType("text/csv");
            resp.setHeader("Content-Disposition", "attachment; filename=\"data.csv\"");
            out.println(userInfo);
            List<ExportMessage> exportMessages = exportMessageService.getExportMessages();
            for(ExportMessage exportMessage : exportMessages){
                out.println(exportMessage.getMessage());
            }
            for (ExportInfo exportInfo : exportInfos) {
                System.out.println("Exporting");
                out.println(exportInfo.toString());
            }
            out.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            resp.setStatus(400);
            out.println("Bad Request");
        }
    }
}
