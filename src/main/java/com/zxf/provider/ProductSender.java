package com.zxf.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.bytebuddy.asm.Advice.This;

/**
 * 消息发送者
 * @author lancoo
 *
 */
@Component
public class ProductSender implements RabbitTemplate.ReturnCallback  {
	
	@Autowired
	private RabbitTemplate rabbitAmqpTemplate;
	
	//交换机名称
	@Value("${mq.config.exchange}")
	private String exchange;
	
	public void send(String msg) {
		//向消息队列发送消息 //参数一：交换器名称  参数二：路由键 //参数三：消息
		rabbitAmqpTemplate.setReturnCallback(this);
		rabbitAmqpTemplate.setConfirmCallback((correlationData, ack, cause) -> {
			if (!ack) {
				System.out.println("ProductSender消息发送失败" + cause + correlationData.toString());
			} else {
				System.out.println("ProductSender 消息发送成功 ");
			}
		});
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"product.log.debug","product.log.debug....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"product.log.info","product.log.info....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"product.log.warn","product.log.warn..."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"product.log.error","product.log.error..."+msg);
	}

	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		// TODO Auto-generated method stub
		System.out.println("sender return success" + message.toString() + "===" + replyCode + "===" + replyText + "===" + exchange);
	}	

}
