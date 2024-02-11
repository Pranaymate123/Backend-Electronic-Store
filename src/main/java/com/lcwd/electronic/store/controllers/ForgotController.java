package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.EmailOtpResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.services.EmailService;
import com.lcwd.electronic.store.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class ForgotController {

    @Autowired
    private EmailService emailService;

    private int myOtp;


    @Autowired
    private UserService userService;

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<EmailOtpResponse> sendOtp(@PathVariable("email") String email)
    {
       User user=userService.findUserByEmailOptional(email).orElse(null);
        if(user ==null )
        {

            EmailOtpResponse res=EmailOtpResponse.builder().message("Email is not registered ").status(HttpStatus.OK).success(true).build();
            return  new ResponseEntity<>(res,HttpStatus.OK);
        }
        System.out.println("Email is "+ email);

        //generating otp of four digits
        int min=100000;
        int max=999999;
       myOtp= (int)(Math.random()*(max-min+1)+min);



        System.out.println("OTP "+ myOtp);
        // Code to send otp via email

        String subject = "OTP from ElectraCart India's No. 1 Ecommerce platform for electronic products ";
        String message=" <div style=\"max-width: 600px; margin: 0 auto; padding: 20px;\">\n" +
                "    <h2 style=\"text-align: center;\">Your One-Time Password (OTP)</h2>\n" +
                "    <p style=\"font-size: 16px;\">Dear User,</p>\n" +
                "    <p style=\"font-size: 16px;\">Your OTP for verification is: <strong> "+myOtp+" </strong>. Please use this code to proceed with your action.</p>\n" +
                "    <p style=\"font-size: 16px;\">This OTP is valid for a limited time and should not be shared with anyone. If you did not request this OTP, please ignore this message.</p>\n" +
                "    <p style=\"font-size: 16px;\">Thank you!</p>\n" +
                "    <hr>\n" +
                "    <p style=\"font-size: 12px; color: #777;\">This is an automated message. Please do not reply to this email.</p>\n" +
                "  </div>";
        String to=email;

        boolean flag=emailService.sendEmail(subject,message,to);

        EmailOtpResponse response=null;
        if(flag)
        {




           response= EmailOtpResponse.builder().message("Otp sent to your registered mail SuccessFully ....")
                   .otp(myOtp)
                    .success(true).status(HttpStatus.CREATED).build();
        }
        else {
            response= EmailOtpResponse.builder().message("Unable to send the email ")
                    .success(false).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/register/{email}")
    public ResponseEntity<EmailOtpResponse> sendOtpToRegister(@PathVariable("email") String email)
    {
        System.out.println("Email is "+ email);

        //generating otp of four digits
        int min=100000;
        int max=999999;
        myOtp= (int)(Math.random()*(max-min+1)+min);



        System.out.println("OTP "+ myOtp);
        // Code to send otp via email

        String subject = "OTP from ElectraCart India's No. 1 Ecommerce platform for electronic products ";
        String message=" <div style=\"max-width: 600px; margin: 0 auto; padding: 20px;\">\n" +
                "    <h2 style=\"text-align: center;\">Your One-Time Password (OTP)</h2>\n" +
                "    <p style=\"font-size: 16px;\">Dear User,</p>\n" +
                "    <p style=\"font-size: 16px;\">Your OTP for verification is: <strong> "+myOtp+" </strong>. Please use this code to proceed with your action.</p>\n" +
                "    <p style=\"font-size: 16px;\">This OTP is valid for a limited time and should not be shared with anyone. If you did not request this OTP, please ignore this message.</p>\n" +
                "    <p style=\"font-size: 16px;\">Thank you!</p>\n" +
                "    <hr>\n" +
                "    <p style=\"font-size: 12px; color: #777;\">This is an automated message. Please do not reply to this email.</p>\n" +
                "  </div>";
        String to=email;

        boolean flag=emailService.sendEmail(subject,message,to);

        EmailOtpResponse response=null;
        if(flag)
        {




            response= EmailOtpResponse.builder().message("Otp sent to your registered mail SuccessFully ....")
                    .otp(myOtp)
                    .success(true).status(HttpStatus.CREATED).build();
        }
        else {
            response= EmailOtpResponse.builder().message("Unable to send the email ")
                    .success(false).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


        @PostMapping("/forgot-password/verify-otp/{otp}")
        public ResponseEntity<EmailOtpResponse> verifyOtp(@PathVariable("otp") Integer otp)
        {


            EmailOtpResponse response=null;
            if(otp==myOtp)
            {
                response=EmailOtpResponse.builder().message("Email is verified SuccessFully ").status(HttpStatus.OK).verified(true).success(true).build();
            }
            else {
                response=EmailOtpResponse.builder().message("Otp is incorrect ").status(HttpStatus.OK).verified(false).success(true).build();
            }
            return new  ResponseEntity<>(response,HttpStatus.OK);
        }


}
