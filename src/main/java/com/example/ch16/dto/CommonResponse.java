package com.example.ch16.dto;

public enum CommonResponse {


    SUCCESS(0,"SUCCESS"),FAIL(-1,"FAIL");
    int code;
    String msg;
    CommonResponse(int cod, String msg){
        this.code = code;
        this.msg = msg;
    }
    public int getCode(){
        return code;

    }

    public String getMsg(){
        return msg;
    }
}
