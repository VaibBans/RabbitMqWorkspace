package com.example.JAVACassandraConnectivity;


import java.util.Iterator;
import java.util.List;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class CassandraMethods {
	
	CassandraConnector cassandraConnector = new CassandraConnector();
	private Session session;
	public void createSession(){
		cassandraConnector = new CassandraConnector();
		cassandraConnector.connect("172.17.0.2", 9042);
		cassandraConnector.getSession();
	}

	public void createKeyspace() {
		
		session = cassandraConnector.getSession();
		cassandraConnector = new CassandraConnector();
		StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
				.append("MESSAGE_CACHE").append(" WITH replication = {")
				.append(" 'class':'").append("SimpleStrategy").append("','replication_factor':")
				.append(3).append("};");

		String query = sb.toString();
		session.execute(query);
	}

	public void useKeyspace(){
		StringBuilder sb = new StringBuilder("USE MESSAGE_CACHE ;");
		String query = sb.toString();
		session.execute(query);
	}

	public void createTable(){	

		useKeyspace();
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
				.append("MESSAGE_DB").append("(").append("SNo int PRIMARY KEY, ").append("Message text, ")
				.append("MessageCache text);");
		String query = sb.toString();
		session.execute(query);
	}
	
	public void insertIntoMessageDB(String message, String messageFromCache) {
	
		createSession();
		createKeyspace();
		useKeyspace();
		System.out.println("Inserting to DB");
		System.out.println("Message value:- "+message);
		System.out.println("From cache:- "+messageFromCache);
		System.out.println("***********Before execution***********");
		System.out.println("Message value:- "+message);
		System.out.println("From cache:- "+messageFromCache);
		int i = fetchSno();
		String query = "INSERT INTO MESSAGE_DB(SNo,Message,MessageCache) VALUES (?,?,?)";
		PreparedStatement prpStmt = session.prepare(query);
		BoundStatement bdStmt = prpStmt.bind(i+1,message,messageFromCache);
		session.execute(bdStmt);

		System.out.println("***********After execution***********");
		System.out.println("Message value:- "+message);
		System.out.println("From cache:- "+messageFromCache);
	}
	
	public void fetchDatabase(){
		String query = "SELECT * FROM MESSAGE_DB;";
		ResultSet rs = session.execute(query);
		Row row = rs.one();
		System.out.println(row.getInt(0)+"   "+row.getString(1)+"   "+row.getString(2));
	}
	public int fetchSno(){
		String query = "SELECT * FROM MESSAGE_DB;";
		ResultSet rs = session.execute(query);
		int max = 0;
		List<Row> row = rs.all();
		Iterator<Row> itr = row.iterator();
		while(itr.hasNext()){
			int sno = itr.next().getInt(0);
			if(sno>max)
				max = sno;				
		}
		return max;	
	}
}
