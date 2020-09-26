package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Auther: Vector
* @Date: ${DATE} * @Description: ${PACKAGE_NAME} * @version: 1.0
*/
@Mapper//如果使用maybatis推荐使用Mapper 有时候@Repository在插入的时候会有问题
public interface PaymentDao {
    /*
    插入一条支付记录
     */
    int create(Payment payment);

     /*
    查询一条支付记录
     */
     Payment getPaymentById(@Param("id") Long id);

    List<Payment> getPaymentByList(@Param("list666") List list);
}
