package com.Rahul.Payment_Gateway.Controller;

import com.Rahul.Payment_Gateway.Entity.PaymentOrder;
import com.Rahul.Payment_Gateway.Service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class controller {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody PaymentOrder paymentOrder) {

        try {
            String createdOrder = paymentService.createOrder(paymentOrder);
            return ResponseEntity.ok(createdOrder);
        }
        catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating order");
        }

    }
    @PostMapping("/update-order")
    public ResponseEntity<String> updateOrderStatus(@RequestParam String paymentId,
                                                    @RequestParam String orderId,
                                                    @RequestParam String status)
    {
        paymentService.updateOrderStatus(paymentId,orderId,status);
        System.out.println("Email sent successfully...");
        return ResponseEntity.ok("Order updated successfully And Email sent");
    }
}
