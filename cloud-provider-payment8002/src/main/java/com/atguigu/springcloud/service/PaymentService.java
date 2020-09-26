package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    //插入支付记录
    public int create(Payment payment);
    //查询支付记录
    public Payment getPaymentById(@Param("id") Long id);
}
