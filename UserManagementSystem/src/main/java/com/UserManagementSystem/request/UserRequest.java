package com.UserManagementSystem.request;

import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String email;
    private String password;
    private String confirmpassword;
    private String address;
    private Long contact;


}
