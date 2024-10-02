package com.assignment.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OTP {
    private String first;
    private String second;
    private String third;
    private String fourth;
    private String fifth;
    private String sixth;

    public String getOTP() {
        return first + second + third + fourth + fifth + sixth;
    }
}
