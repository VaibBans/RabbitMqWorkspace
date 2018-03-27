package com.cg.combinatorials.products;

import java.util.Scanner;

public class CombinatorialsProducts {

	public static void main(String[] args) {

		int i=0,j=0,count=0;
		long bigMultiple = 1L;
		long sumBigMultiple = 0L;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number to factorize");
		int number = sc.nextInt();
		long multiple[] = new long[number];
		int factors[] = new int[number+1];
		for(i=1;i<=number;i++){
			if(number%i==0){
				factors[i] = i;
			}
			else
				continue;	
		}
		System.out.println("Factors are: ");
		for(i=1;i<factors.length;i++){
			if(factors[i]==0)
				continue;
			else{
				for(j=0;j<factors.length;j++){
					
				}
				System.out.println(factors[i]);
//				multiple = factors[i]*multiple;
			}
		}
		System.out.println("Multiplication of all factors:- "+multiple);

		for(i=1;i<=number;i++){
			for(j=i;j<=number;j++){
				if(factors[i]!=0&&factors[j]!=0&&factors[i]!=factors[j]){
					bigMultiple = factors[i]*factors[j];
					sumBigMultiple = sumBigMultiple+bigMultiple;
				}				
			}
		}
		System.out.println("Sum of all the factors of "+number+" is "+sumBigMultiple);
		sc.close();
	}
}
