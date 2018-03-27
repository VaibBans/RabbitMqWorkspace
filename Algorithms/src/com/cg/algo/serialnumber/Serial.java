package com.cg.algo.serialnumber;

public class Serial {
	private int serialNumber;
	SerialTest serialTest = new SerialTest();
	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public int sequencing(int serial){
		int count = 0;
		count = serial+1;
//		System.out.println("Value of count "+count);
		return count;
	}
	public void exitSystem(){
		System.exit(1);
	}
}
