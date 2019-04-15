package com.zxf.consumer;

import java.io.IOException;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
/**
*	消息接收者
*	@author Administrator
*	@RabbitListener bindings:绑定队列
*	@QueueBinding value:绑定队列的名称
*	exchange:配置交换器
*
*	@Queue value:配置队列名称
*	autoDelete:是否是一个可删除的临时队列
*
*	@Exchange value:为交换器起个名称
*	type:指定具体的交换器类型
*/
@Component
@RabbitListener(bindings=@QueueBinding(
value = @Queue(value=("${mq.config.queue.logs}"),autoDelete="false"),
exchange = @Exchange(value=("${mq.config.exchange}"),type=ExchangeTypes.TOPIC), 
key="*.log.*"))
public class LogsReceiver {
   /**
	*	接收消息的方法。采用消息队列监听机制
	*	@param msg
	*/
	@RabbitHandler
	public void process(String msg,Channel channel, Message message){
		// 采用手动应答模式, 手动确认应答更为安全稳定
		try {
		      channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
		    } catch (IOException e) {
		      e.printStackTrace();
		      //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
		    }
		System.out.println("......All........receiver:"+msg);
	}

}
