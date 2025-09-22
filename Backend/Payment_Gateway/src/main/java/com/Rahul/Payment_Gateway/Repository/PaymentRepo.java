package com.Rahul.Payment_Gateway.Repository;

import com.Rahul.Payment_Gateway.Entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo  extends JpaRepository<PaymentOrder, Long> {

    PaymentOrder findByOrderId(String id);
}
