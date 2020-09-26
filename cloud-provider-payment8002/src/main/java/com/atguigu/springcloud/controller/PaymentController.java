package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    public PaymentService paymentServiceImpl;
    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create",consumes =MediaType.APPLICATION_JSON_VALUE)
    public CommonResult create(@RequestBody Payment payment){
        log.info("生产者接受到的值："+payment);
        int index = paymentServiceImpl.create(payment);
        log.info("******插入结果："+index);
        if(index>0){
            return new CommonResult(200,"成功,serverPort:"+serverPort,payment);
        }else{
            return new CommonResult(444,"插入失败serverPort:"+serverPort,null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable(value = "id") Long id){
        Payment result = paymentServiceImpl.getPaymentById(id);
        log.info("查询结果："+result);
        if(result!=null){
            return new CommonResult(200,"查询成功,serverPort:"+serverPort,result);
        }else{
            return new CommonResult(444,"没有对应的记录,查询ID:"+id,null);
        }
    }

    //手写Ribbon负载均衡算法新增加的方法
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }


}
