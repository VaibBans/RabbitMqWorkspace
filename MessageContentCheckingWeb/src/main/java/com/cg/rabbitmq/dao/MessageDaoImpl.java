package com.cg.rabbitmq.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.cg.rabbitmq.constants.Constants;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@Component
public class MessageDaoImpl implements MessageDao {

	boolean bool;

	@Override
	public boolean validateUserName(String userName) {

		if ("user".equals(userName))
			bool = true;
		else
			bool = false;
		return bool;
	}

	@Override
	public boolean validatePassword(String password) {

		if ("book".equals(password))
			bool = true;
		else
			bool = false;
		return bool;
	}

	@Override
	public boolean sendMessage(String message) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put("x-message-ttl", 5000);
		arguments.put("x-dead-letter-exchange", Constants.TTLDLEX);

		BasicProperties properties = new BasicProperties.Builder().contentType(new String()).build();

		channel.exchangeDeclare(Constants.TTLEX, "fanout", false);
		channel.queueDeclare(Constants.TTLQUEUE, false, false, false, arguments);
		channel.queueBind(Constants.TTLQUEUE, Constants.TTLEX, "");

		channel.exchangeDeclare(Constants.TTLDLEX, "fanout", false);
		channel.queueDeclare(Constants.TTLDLQUEUE, false, false, false, null);
		channel.queueBind(Constants.TTLDLQUEUE, Constants.TTLDLEX, "");

		channel.basicPublish("", Constants.TTLQUEUE, properties, message.getBytes());
		System.out.println("Message Sent Successfully");
		return true;
	}

	@Override
	public boolean receiveMessage() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put("x-message-ttl", 5000);
		arguments.put("x-dead-letter-exchange", Constants.TTLDLEX);

		channel.exchangeDeclare(Constants.TTLEX, "fanout", false);
		channel.queueDeclare(Constants.TTLQUEUE, false, false, false, arguments);
		channel.queueBind(Constants.TTLQUEUE, Constants.TTLEX, "");

		channel.exchangeDeclare(Constants.TTLDLEX, "fanout", false);
		channel.queueDeclare(Constants.TTLDLQUEUE, false, false, false, null);
		channel.queueBind(Constants.TTLDLQUEUE, Constants.TTLDLEX, "");

		System.out.println("Before creating instance of consumer");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				System.out.println("After creating instance");
				String message = new String(body, "UTF-8");
				System.out.println("After getting message "+message);
				String exchange = envelope.getExchange();
				System.out.println("Content type is " + properties.getContentType());
				System.out.println("Exchange is " + exchange);

				FileWriter fw = new FileWriter("msgfile.txt");
				
					fw.write(message);
					fw.close();

				System.out.println(" [x] Received '" + message + "'");
				channel.basicAck(envelope.getDeliveryTag(), true);
			}
		};
		channel.basicConsume(Constants.TTLQUEUE, false, consumer);
		//		channel.basicConsume(Constants.TTLDLQUEUE, false, consumer);
		return true;
	}

	@Override
	public int checkContent(String message) throws Exception {

		int count = 0;
		char ch[] = new char[message.length() + 1];
		for (int i = 0; i < message.length(); i++) {
			ch[i] = message.charAt(i);
			if ((ch[i] >= 65 && ch[i] < 90) || (ch[i] >= 97 && ch[i] < 122)) {
				count = 1;
			} else {
				count = 0;
			}
		}
		if (count == 1) {
		} else if (count == 0) {
			System.out.println("Message contains other than Characters");
		}
		return count;
	}

	@Override
	public int numberOfMessages() throws Exception {
		int count = 0, val = 0;
		RandomAccessFile raf = new RandomAccessFile("file.txt", "rws");
		FileWriter fw = new FileWriter("file.txt", true);
		int c = raf.read();
		if (c == -1) {
			fw.write((char) 49);
		}
		while (c != -1 && c < 52 && count < 1) {
			long j;
			int k = 1;
			for (j = 0; k > 0;) {
				j = raf.getFilePointer();
				k = raf.read();
			}
			raf.seek(j - 1);
			c = raf.read();
			val = c + 1;
			if (val <= 51) {
				fw.write((char) val);
			} else {
				val = 52;
				break;
			}
			count++;
		}
		fw.close();
		return val;
	}

	public boolean checkContentInFile() throws Exception {
		RandomAccessFile raf = new RandomAccessFile("msgfile.txt", "rws");
		int c = raf.read();
		if (c == -1) {
			System.out.println("Value of c " + c);
			bool = false;
		} else {
			System.out.println("Value of c " + c);
			bool = true;
		}
		return bool;
	}

	/*public void ttlFunction(String exchange, String message) throws Exception {
		RandomAccessFile raf = new RandomAccessFile("msgfile.txt", "rws");
		FileWriter fw = new FileWriter("msgfile.txt");
		int c;
		c = raf.read();
		if ("ttldlex".equals(exchange)) {
			if (c == -1) {
				System.out.println("In ttldlex if clause");
			} else {
				message = null;
				System.out.println("Writing null message");
				fw.write(message);
			}
		} else {
			System.out.println("In ttlex if clause");
			fw.write(message);
			fw.close();
		}
	}
	 */
	public void clearFileContent() throws Exception {
		FileWriter fwr = new FileWriter("file.txt");
		fwr.write("");
		System.out.println("Successfully clearing the content of file");
		fwr.close();
	}
}


/*
 * 
 * @Override public boolean receiveMessage() throws Exception {
 * ConnectionFactory factory = new ConnectionFactory();
 * factory.setHost("172.17.0.2"); Connection connection =
 * factory.newConnection(); Channel channel = connection.createChannel();
 * 
 * Map<String, Object> arguments = new HashMap<String, Object>();
 * arguments.put("x-message-ttl", 5000); arguments.put("x-dead-letter-exchange",
 * Constants.TTLDLEX);
 * 
 * channel.exchangeDeclare(Constants.TTLEX, "fanout", false);
 * channel.queueDeclare(Constants.TTLQUEUE, false, false, false, arguments);
 * channel.queueBind(Constants.TTLQUEUE, Constants.TTLEX, "");
 * 
 * channel.exchangeDeclare(Constants.TTLDLEX, "fanout", false);
 * channel.queueDeclare(Constants.TTLDLQUEUE, false, false, false, null);
 * channel.queueBind(Constants.TTLDLQUEUE, Constants.TTLDLEX, "");
 * 
 * Consumer consumer = new DefaultConsumer(channel) {
 * 
 * @Override public void handleDelivery(String consumerTag, Envelope envelope,
 * AMQP.BasicProperties properties, byte[] body) throws IOException { String
 * message = new String(body, "UTF-8"); String exchange =
 * envelope.getExchange();
 * System.out.println("Content type is "+properties.getContentType());
 * System.out.println("Exchange is "+exchange); RandomAccessFile raf = new
 * RandomAccessFile("msgfile.txt", "rws"); try{ ttlFunction(exchange, message);
 * }catch(Exception e){ System.out.println(e.getMessage()); }
 * System.out.println(" [x] Received '" + message + "'");
 * channel.basicAck(envelope.getDeliveryTag(), true); } };
 * channel.basicConsume(Constants.TTLQUEUE, false,consumer);
 * channel.basicConsume(Constants.TTLDLQUEUE, false,consumer); return true; }
 * 
 * 
 */
