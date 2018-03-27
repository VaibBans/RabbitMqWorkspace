package com.cg.guava;

import com.google.common.base.Optional;

public class GuavaTester {

	public static void main(String[] args) {
/*
		GuavaTester guavaTester = new GuavaTester();

		Integer a =  null;
		Integer b =  new Integer(10);
		System.out.println(guavaTester.sum(a,b));      
	}

	public Integer sum(Integer a, Integer b) {
		return a+b;
	}
*/

	GuavaTester guavaTester = new GuavaTester();

	      Integer invalidInput = null;

	      Optional<Integer> a =  Optional.of(invalidInput);
	      Optional<Integer> b =  Optional.of(new Integer(10));
	      System.out.println(guavaTester.sum(a,b));      
	   }

	   public Integer sum(Optional<Integer> a, Optional<Integer> b) {
	      return a.get() + b.get();
	   }	
}
