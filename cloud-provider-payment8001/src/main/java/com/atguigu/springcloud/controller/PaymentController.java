package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    public PaymentService paymentServiceImpl;
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private DiscoveryClient discoveryClient;

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

    @GetMapping("/payment/discovery")
    public Object discoversy(){
        List<String> services = this.discoveryClient.getServices();
        for(String elememnt:services){
            log.info("all the service:"+elememnt);
        }
        List<ServiceInstance> instances = this.discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance:instances){
            log.info("all the instances which named CLOUD-PAYMENT-SERVICE："+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }

    @PostMapping("/payment/list")
    public void test1(@RequestParam List<Integer> aaa){
        System.out.println(aaa);
        List result = paymentServiceImpl.getPaymentByList(aaa);
        System.out.println(result.get(0).toString());
        System.out.println(result.get(1).toString());
    }

    //手写Ribbon负载均衡算法新增加的方法
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    //故意演示超时异常
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){
            e.printStackTrace();
        }
        return serverPort;
    }



}
