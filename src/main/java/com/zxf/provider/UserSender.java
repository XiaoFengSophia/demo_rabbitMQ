package com.zxf.provider;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



/**
 * 消息发送者
 * @author lancoo
 *
 */
@Component
public class UserSender implements RabbitTemplate.ReturnCallback {
	@Autowired
	private  RabbitTemplate rabbitAmqpTemplate;
	
	//exchange 交换机名称
	@Value("${mq.config.exchange}")
	private  String exchange;
	/**
	 * 发送消息的方法
	 * @param msg
	 */
	public void send(String msg) {
		//向消息队列发送消息 //参数一：交换器名称 参数二：路由键 //参数三：消息
		rabbitAmqpTemplate.setReturnCallback(this);
		rabbitAmqpTemplate.setConfirmCallback((correlationData, ack, cause) -> {
			if (!ack) {
				System.out.println("UserSender消息发送失败" + cause + correlationData.toString());
			} else {
				System.out.println("UserSender 消息发送成功 ");
			}
		});
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"user.log.debug","user.log.debug......"+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"user.log.info","user.log.info....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"user.log.warn","user.log.warn....."+msg);
		this.rabbitAmqpTemplate.convertSendAndReceive(this.exchange,"user.log.error","user.log.error....."+msg); 
	}
	/**
	 * 回调消息确认
	 * @param correlationData
	 * @param ack
	 * @param cause
	 */
//	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        System.out.println("CallBackConfirm UUID: " + correlationData.getId());
//        
//        if(ack) {
//        	System.out.println("CallBackConfirm 消息消费成功！");
//        }else {
//        	System.out.println("CallBackConfirm 消息消费失败！");
//        }
//        
//        if(cause!=null) {
//            System.out.println("CallBackConfirm Cause: " + cause);
//        }
//    }
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		// TODO Auto-generated method stub
		System.out.println("sender return success" + message.toString() + "===" + replyCode + "===" + replyText + "===" + exchange);
	}
	
	
	

}
