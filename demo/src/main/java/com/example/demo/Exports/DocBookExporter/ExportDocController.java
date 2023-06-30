package com.example.demo.Exports.DocBookExporter;

import com.example.demo.Exports.ExportInfo;
import com.example.demo.Exports.ExportInfoService;
import com.example.demo.Exports.ExportMessage;
import com.example.demo.Exports.ExportMessageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

@WebServlet(urlPatterns = {"/export-as-docBook"})
public class ExportDocController extends HttpServlet {
    private final ExportInfoService exportInfoService = new ExportInfoService();
    private final ExportMessageService exportMessageService = new ExportMessageService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Object o = req.getAttribute("email");
            String email = o.toString();
            resp.setStatus(200);
            resp.setContentType("application/xml");
            resp.setHeader("Content-Disposition", "attachment; filename=\"myBooks.xml\"");
            List<ExportInfo> exportInfoList =exportInfoService.getExportInfos(email);

            StringWriter stringWriter = new StringWriter();
            for(ExportInfo e : exportInfoList){
                stringWriter.append("<book><entry>").append(e.toString()).append("</entry></book>");
            }
            List<ExportMessage> exportMessages = exportMessageService.getExportMessages();
            for(ExportMessage exportMessage : exportMessages){
                stringWriter.append("<statistics><entry>").append(exportMessage.getMessage()).append("</entry></statistics>");
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            String xmlContent ="<table>"+stringWriter.toString()+"</table>";
            StringWriter result = new StringWriter();
            transformer.transform(new javax.xml.transform.stream.StreamSource(new StringReader(xmlContent)), new StreamResult(result));
            try(OutputStream outputStream = resp.getOutputStream()){
                outputStream.write(result.toString().getBytes());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            resp.setStatus(400);
            PrintWriter out = resp.getWriter();
            out.println("Bad Request");
            out.close();
        }
    }
}
