package com.example.demo.Exports;

import java.io.Serializable;

public class ExportMessage implements Serializable {
    private String message;

    public ExportMessage(String message){
        this.message = message;
    }
    public ExportMessage(){}
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
