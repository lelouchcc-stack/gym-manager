package com.lelcc.dto;

import lombok.Data;

@Data
public class LoginVO {
    private Long id;
    private String username;
    private String token ;
    public LoginVO() {}
}
