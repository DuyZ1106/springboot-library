package com.example.library.dto.user.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserSearchRequest {

    private String username;

    private String fullName;

    private String phoneNumber;

    private String address;

    private Integer age;

    private LocalDate birthday;
}
