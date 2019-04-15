package com.zxf.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息发送
 * @author lancoo
 *
 */
@Component
public class OrderSender implements RabbitTemplate.ReturnCallback  {

	@Autowired
	private RabbitTemplate rabbitAmqpTemplate;
	
	@Value("${mq.config.exchange}")
	private String exchange;
	/**
	 * 消息发送者
	 * @param msg
	 */
	public void send(String msg){
		//向消息队列发送消息 //参数一：交换器名称。参数二：路由键 //参数三：消息
		rabbitAmqpTemplate.setReturnCallback(this);
		rabbitAmqpTemplate.setConfirmCallback((correlationData, ack, cause) -> {
			if (!ack) {
				System.out.println("OrderSender消息发送失败" + cause + correlationData.toString());
			} else {
				System.out.println("OrderSender 消息发送成功 ");
			}
		});
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"order.log.debug","order.log.debug....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"order.log.info","order.log.info....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"order.log.warn","order.log.warn....."+msg); 
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"order.log.error","order.log.error....."+msg); 
	}
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		// TODO Auto-generated method stub
		System.out.println("sender return success" + message.toString() + "===" + replyCode + "===" + replyText + "===" + exchange);
	}
		
}
