package com.example.demo.po;

import lombok.Data;

@Data
public class ResponseData {
    private int status;
    private String message;
    private Object data;

}
