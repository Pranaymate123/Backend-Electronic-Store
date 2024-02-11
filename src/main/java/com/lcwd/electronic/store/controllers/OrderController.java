package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.services.EmailService;
import com.lcwd.electronic.store.services.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
@SecurityRequirement(name="scheme1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailService emailService;
    //create
    @PostMapping("/{email}")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request,
                                                @PathVariable("email") String email) {
        OrderDto order = orderService.createOrder(request);

        String subject="Confirmation: Your Order Has Been Successfully Placed";
        String to=email;
        String message="<h2>Confirmation: Your Order Has Been Successfully Placed</h2>\n" +
                "<p>Dear " +email+" ,</p>\n" +
                "<p>We are thrilled to inform you that your order has been successfully placed! Thank you for choosing [Your Company Name] for your [product/service] needs.</p>\n" +
                "<p>Here are the details of your order:</p>\n" +
                "<ul>\n" +

                "    <li><strong>Billing Name :</strong> "+request.getBillingName()+" </li>\n" +
                "    <li><strong>Billing Phone:</strong> "+request.getBillingPhone()+"</li>\n" +
                "    <li><strong>Billing Address:</strong>"+request.getBillingAddress()+"</li>\n" +
                "</ul>\n" +
                "<p>If you have any questions or concerns regarding your order, please feel free to reach out to our customer service team. We're here to assist you every step of the way.</p>\n" +
                "<p>Thank you for trusting us with your business. We look forward to serving you and ensuring a seamless experience.</p>\n" +
                "<p>Warm regards,</p>\n" +
                "<p>Manager<br>\n" +

                "ElectraCart</p>";

        System.out.println("Email Is "+email);
            boolean flag=emailService.sendEmail(subject,message,to);
            if(flag) System.out.println("Mail is sent ");

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }


    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId) {
        orderService.removeOrder(orderId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .status(HttpStatus.OK)
                .message("order is removed !!")
                .success(true)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);

    }

    //get orders of the user

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId) {
        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(ordersOfUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> getOrders(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderedDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ) {
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(
            @RequestBody CreateOrderRequest orderDto,
            @PathVariable String orderId
    )
    {
        OrderDto updatedOrder=orderService.updateOrder(orderDto,orderId);
        return new ResponseEntity<>(updatedOrder,HttpStatus.OK);
    }


}
