package com.zxf;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zxf.provider.OrderSender;
import com.zxf.provider.ProductSender;
import com.zxf.provider.UserSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoRabbitmqApplicationTests {
	

		@Autowired UserSender usersender;
		@Autowired
		private ProductSender productsender;
		@Autowired
		private OrderSender ordersender;

		@Test
		public void test1() throws InterruptedException {
			this.usersender.send("UserSender..... "); 
//			this.productsender.send("ProductSender...."); 
//			this.ordersender.send("OrderSender......"); 
		}

}
