package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestBodyParser {
    public static String parseRequest(HttpServletRequest request) throws IOException {
        StringBuilder body = new StringBuilder();
        BufferedReader bufferedReader = request.getReader();
        String line;
        while((line = bufferedReader.readLine())!=null){
            body.append(line);
        }
        bufferedReader.close();
        return body.toString();
    }
}
