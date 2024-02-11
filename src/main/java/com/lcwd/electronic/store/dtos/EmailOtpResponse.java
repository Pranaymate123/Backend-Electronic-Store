package com.lcwd.electronic.store.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailOtpResponse {

    private String message;
    private boolean success;
    private int otp;
    private boolean verified;
    private HttpStatus status;
}
