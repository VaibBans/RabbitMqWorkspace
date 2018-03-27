package com.rabbit.custom.annotations;

import java.lang.annotation.Target;
 
import org.springframework.amqp.rabbit.annotation.RabbitListener;

 
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


import com.rabbit.configs.RabbitConfigs;

/**
 * @author Justin Mathew
 *
 * Created On 16-Mar-2018
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
/*@RabbitListener(bindings = @QueueBinding(
 value = @Queue,
 exchange = @Exchange(value = "metaFanout", type = "fanout")))*/

@RabbitListener(containerFactory = "OrderListenerFactory", queues = RabbitConfigs.ROURING_KEY_1_1)
public @interface CustomDirectListener {
}