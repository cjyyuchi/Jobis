package com.example.jobis.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GeneralResponse {

    private int code;
    private String message;

    public GeneralResponse(){
        this.code = 200;
        this.message = "Success";
    }

    public GeneralResponse(int code, String message){
        this.code = 200;
        this.message = message;
    }


}
