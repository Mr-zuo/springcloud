package com.ron.combat.microserviceorder.demo.rabbitmq.ack;

import com.ron.combat.microserviceorder.demo.entity.Employee;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //消息确认机制，如果消息已经发出，但是rabbitmq并没有回应或者是拒绝接收消息了呢？就可以通过回调函数的方式将原因打印出来
    RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {

        public void confirm(CorrelationData correlationData, boolean isack, String cause) {
            System.out.println("本次消息的唯一标识是:" + correlationData);
            System.out.println("是否存在消息拒绝接收？" + isack);
            if(isack == false){
                System.out.println("消息拒绝接收的原因是:" + cause);
            }else{
                System.out.println("消息发送成功");
            }
        }
    };

    //有关消息被退回来的具体描述消息
    RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {

        @Override
        public void returnedMessage(Message message,
                                    int replyCode,
                                    String desc,
                                    String exchangeName,
                                    String routeKey) {
            System.out.println("err code :" + replyCode);
            System.out.println("错误消息的描述 :" + desc);
            System.out.println("错误的交换机是 :" + exchangeName);
            System.out.println("错误的路右键是 :" + routeKey);

        }
    };

    //发送对象消息时
    /**
     * CorrelationData  标识消息唯一性的主体对象，可以自己设定相关的参数，方便后续对某条消息做精确的定位
     * confirmCallback  消息投递到rabbitmq是否成功的回调函数，如果不成功，我们可以在该回调函数中做相关的处理
     * returnCallback   消息被退回的回调函数
     * @param employee
     */
    public void sendMessage(Employee employee){
        CorrelationData cData = new CorrelationData(employee.getEmpno() + "-" + new Date().getTime());
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.convertAndSend("mybootexchange", "hr.employee",employee,cData);
    }

}
