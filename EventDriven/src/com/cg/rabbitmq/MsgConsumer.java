package com.cg.rabbitmq;

import java.io.IOException;
import java.util.Scanner;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MsgConsumer {

	private static final String QUEUE1 = "eventQ";
	private static final String EXCHANGE1 = "eventEx";

	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE1, "fanout",false);
		channel.queueDeclare(QUEUE1, false, false, true, null);
		channel.queueBind(QUEUE1, EXCHANGE1, "");

		Consumer consumer1 = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				int count = 0,sum;
				String num1="",num2="";		
				String oper = "";
				String message = new String(body, "UTF-8");
				char ch[] = new char[message.length()+1];
				char newChar[] = new char[message.length()+1];
				for(int i = 0;i<message.length();i++){
					ch[i] =	message.charAt(i);
				}
				for(int i=0;i<message.length();i++){

					if((ch[i]>=65&&ch[i]<90)||(ch[i]>=97&&ch[i]<122))
					{
						newChar[i] = ch[i];		
						oper =  oper + newChar[i];			
					}

					else if((ch[i]>=48&&ch[i]<=57)||ch[i]==32){

						if(ch[i]==' ')
							count++;

						if(count==1&&ch[i]!=32)
							num1 = num1+ch[i];

						else if(count==2&&ch[i]!=32)
							num2 = num2+ch[i];
					}					
				}
				System.out.println(" [x] Received Message :-'" + message + "'");		
				if("addition".equalsIgnoreCase(oper)){				
					System.out.println("Hello addition");
					sum = Integer.parseInt(num1)+Integer.parseInt(num2);
					System.out.println("Sum of "+num1+" and "+num2+ " is "+sum);
				}
				
				if("multiply".equalsIgnoreCase(oper)){				
					System.out.println("Hello Mulitplication");
					sum = Integer.parseInt(num1)*Integer.parseInt(num2);
					System.out.println("Product of "+num1+" and "+num2+ " is "+sum);
				}
				
				if("subtraction".equalsIgnoreCase(oper)){				
					System.out.println("Hello Subraction");
					sum = Integer.parseInt(num1)-Integer.parseInt(num2);
					System.out.println("Subtraction of "+num1+" and "+num2+ " is "+sum);
				}
				
				if("division".equalsIgnoreCase(oper)){				
					System.out.println("Hello Division");
					if("0".equals(num2))
						System.out.println("Can't divide by zero");
					else if(Integer.parseInt(num2)!=0){
					sum = Integer.parseInt(num1)/Integer.parseInt(num2);
					if(sum==0)
						System.out.println(num1+" is smaller than "+num2);
					else
					System.out.println("Subtraction of "+num1+" and "+num2+ " is "+sum);
					}
				}
			}
		};
		channel.basicConsume(QUEUE1, false, consumer1);
		sc.close();
	}
}
