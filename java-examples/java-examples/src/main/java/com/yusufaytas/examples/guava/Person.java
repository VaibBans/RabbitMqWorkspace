// <copyright file="Person.java">
// Copyright (c) 2012 All Rights Reserved, yusufaytas.com
// <author>Yusuf Aytas</author>
// </copyright>
package com.yusufaytas.examples.guava;

import java.io.Serializable;

public class Person implements Serializable{
	
	String name;
	String surname;
	String username;
	int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
