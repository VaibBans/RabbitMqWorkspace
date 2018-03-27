package com.cg.guava.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class EmployeeTester {
	

	public static void main(String[] args) throws Exception {

		LoadingCache<String, EmployeeBean> employeeCache = CacheBuilder.newBuilder().maximumSize(3).
				expireAfterWrite(1, TimeUnit.SECONDS).build(new CacheLoader<String, EmployeeBean>(){
					@Override
		            public EmployeeBean load(String empId) throws Exception {
		               return getFromDatabase(empId);
		            } 
				});
		Logger logger = Logger.getLogger(com.cg.guava.bean.EmployeeTester.class);

		try{
			
			System.out.println("Waiting for 2 seconds");   
			
			/*
			 * For expiration after write reduce the time in expireAfterWrite() and delay the program execution
			 * by Thread.sleep() function
			 * 
			 * 
			 */
//			Thread.sleep(2000);
			employeeCache.asMap().put("1",EmployeeTester.getFromDatabase("100"));
			employeeCache.asMap().put("2",EmployeeTester.getFromDatabase("101"));
			employeeCache.asMap().put("3",EmployeeTester.getFromDatabase("102"));
			employeeCache.asMap().put("4",EmployeeTester.getFromDatabase("104"));
//			employeeCache.asMap().put("5",EmployeeTester.getFromDatabase("53101"));

			
//			System.out.println("First time printing");			
//			
//			System.out.println("Details of Employee "+employeeCache.get("1"));
//			System.out.println("Details of Employee "+employeeCache.get("2"));
			/*System.out.println("Details of Employee "+employeeCache.get("3"));
			System.out.println("Details of Employee "+employeeCache.get("4"));			
			System.out.println("Details of Employee "+employeeCache.get("5"));*/
			
			logger.info("Values are added"+ employeeCache.asMap().get("1"));
			logger.info("Values are added"+ employeeCache.asMap().get("2"));
			logger.info("Values are added"+ employeeCache.asMap().get("3"));
			logger.info("Values are added"+ employeeCache.asMap().get("4"));
			
			Thread.sleep(2000);
			
//			System.out.println("Again printing values of cache");
			System.out.println("Details of Employee "+employeeCache.get("100"));
			
//			   // .refresh(key) causes to prevent changes in cache

//			employeeCache.cleanUp();
			map.get("101").setName("Test name");
			map.get("101").setDept("Test dept");
//			map.get("101").setEmpId("Test ID");
			
			employeeCache.put("5", map.get("101"));
			employeeCache.put("2", EmployeeTester.getFromDatabase("101"));

			System.out.println("Details of Employee of key = 5:- "+employeeCache.get("5"));
			System.out.println("Details of Employee of key = 2:- "+employeeCache.get("2"));
//			System.out.println("Details of Employee "+employeeCache.get("2"));
//			System.out.println("Details of Employee "+employeeCache.get("3"));
//			System.out.println("Details of Employee "+employeeCache.get("4"));
//			
			System.out.println("Size of cache:- "+employeeCache.size());

			System.out.println("After removing");

//			Thread.sleep(2000);
//			System.out.println("Value in cache: " +employeeCache.get("53101"));
			System.out.println("loaded cache="+employeeCache.get("101").getName());
		    map.get("101").setName("XYZ");
		    map.get("101").setDept("Hi");
			System.out.println("Data source updated= "+map.get("101").getName());
			System.out.println("Cache updated= "+employeeCache.get("101"));
			
			System.out.println("Fetching all values");
	        System.out.println(employeeCache.asMap().values());
			
		}catch(Exception ee){
			ee.printStackTrace();
		}
	}
	
	static Map<String, EmployeeBean> map = new HashMap<>();
	private static EmployeeBean getFromDatabase(String empId){
		EmployeeBean e1 = new EmployeeBean();
		e1.setEmpId("100");
		e1.setName("XYZ");
		e1.setDept("Production");
		
		EmployeeBean e2 = new EmployeeBean();
		EmployeeBean e3 = new EmployeeBean();
		EmployeeBean e4 = new EmployeeBean();
		EmployeeBean e5 = new EmployeeBean();
		
		e2.setEmpId("101");
		e2.setName("Dell");
		e2.setDept("Hardware");
		
		e3.setEmpId("102");
		e3.setName("Sam");
		e3.setDept("IT");

		e4.setEmpId("104");
		e4.setName("Sammy");
		e4.setDept("Finance");
		
		e5.setEmpId("53101");
		e5.setName("Test");
		e5.setDept("Developer");
	
		map.put("100", e1);
		map.put("101", e2);
		map.put("102", e3);
		map.put("104", e4);
//		map.put("53101", e5);
		
		
		return map.get(empId);
	}
}




/*//System.out.println("#1 Invocation");
//			Map<String, EmployeeBean> data = new HashMap<>();
		//	data.put("1", employeeCache.get("101"));
//			System.out.println(data.toString());
			
			//if(data.get("1").getEmpId().equals("101")){
//				data.remove("1");
		//	}
		 //   System.out.println(data.containsKey("1"));
			
			/*EmployeeBean e1 = new EmployeeBean();
			EmployeeBean e2 = new EmployeeBean();
			e1 = employeeCache.get("100");
			System.out.println(e1.getDept());

//			System.out.println(employeeCache.get("1"));
 			
//			System.out.println("Modified Name:= "+map);
			
//			employeeCache.asMap().put("1", map.get("101"));
			
			e2 = employeeCache.get("104");
			System.out.println(e2.getName());
		
			employeeCache.refresh("100");
		//	System.out.println(employeeCache.get("101"));
			System.out.println("For 3rd employee");
			System.out.println(employeeCache.get("102"));
			System.out.println(employeeCache.size());
		
employeeCache.asMap().remove("1", EmployeeTester.getFromDatabase("101"));
//			employeeCache.invalidate("1");

			map.get("101").setName("New Name");       //changing the name from database
			
			System.out.println("Above map");
			System.out.println(map.get("101").getName());   // printing the value form database
			System.out.println("After map");
			employeeCache.invalidateAll();				//deleting the value from cache
//			employeeCache.get("101");
			Thread.sleep(5000);
			employeeCache.cleanUp();
			employeeCache.refresh("101");
		    System.out.println("loaded cache="+employeeCache.get("101").getName());
*/			 
				
//			employeeCache.asMap().put("1",EmployeeTester.getFromDatabase("101"));     important line 
			//Thread.sleep(2000);*/