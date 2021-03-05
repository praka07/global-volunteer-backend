package com.global.volunteer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private int userId;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
    private String createdDate;
    private boolean active;
    private int createdBy;
    private int role;

}