package com.Rahul.Payment_Gateway.Service;

import com.Rahul.Payment_Gateway.Entity.PaymentOrder;
import com.Rahul.Payment_Gateway.Repository.PaymentRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    @Value("{$razorpay.key_id}")
    private String key_id;

    @Value("{$razorpay.key_secret}")
    private String key_secret;

    @Autowired
    PaymentRepo paymentRepo;

    public String createOrder(PaymentOrder orderDetails) throws RazorpayException {

        RazorpayClient client = new RazorpayClient(key_id,key_secret);

        //Json object
        //It's a method for building a structured data payload, typically to send to a web service or API.
        JSONObject orderRequest = new JSONObject();

        orderRequest.put("amount",(int)(orderDetails.getAmount()*100)); //paise to rupees
        orderRequest.put("currency","INR");
        orderRequest.put("receipt","txn_"+ UUID.randomUUID());

        Order razorpayOrder = client.orders.create(orderRequest);

        orderDetails.setOrderId(razorpayOrder.get("id"));
        orderDetails.setStatus("CREATED");
        orderDetails.setCreatedAt(LocalDateTime.now());

        paymentRepo.save(orderDetails);
        return razorpayOrder.toString();
    }

    public void updateOrderStatus(String paymentId, String orderId, String status) {
        PaymentOrder order = paymentRepo.findByOrderId(orderId);
        order.setPaymentId(paymentId);
        order.setStatus(status);
        paymentRepo.save(order);


    }
}
