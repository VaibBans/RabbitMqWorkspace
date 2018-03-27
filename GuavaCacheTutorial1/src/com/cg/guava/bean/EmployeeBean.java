package com.cg.guava.bean;

import com.google.common.base.MoreObjects;

public class EmployeeBean {

	private String name;
	private String dept;
	private String empId;

	public EmployeeBean() {
		
	}

	public EmployeeBean(String name, String dept, String empId) {
		super();
		this.name = name;
		this.dept = dept;
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {

		return MoreObjects.toStringHelper(EmployeeBean.class).add("Name", name).add("department", dept).add("Emp Id", empId)
				.toString();
	}

}