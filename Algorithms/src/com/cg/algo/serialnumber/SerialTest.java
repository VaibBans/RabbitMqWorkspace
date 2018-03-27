package com.cg.algo.serialnumber;

public class SerialTest {

	public static void main(String[] args) {
		
	Serial serial = new Serial();
	int count=0;
	for(int i= count;i<=count;i++){
		count = serial.sequencing(i);
		System.out.println("The system ran for "+count+" times");
		serial.exitSystem();
	}
	}
}
