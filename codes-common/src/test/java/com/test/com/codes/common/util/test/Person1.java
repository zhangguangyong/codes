package com.test.com.codes.common.util.test;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

public class Person1 implements Comparable<Person1> {
	
	private String firstName;
	private String lastName;
	private Integer zipCode;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getZipCode() {
		return zipCode;
	}
	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
	@Override
	public int compareTo(Person1 o) {
		return ComparisonChain.start()
				.compare(this.firstName, o.firstName)
				.compare(this.lastName, o.lastName)
				.compare(this.zipCode, o.zipCode)
				.result();
	}
	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return Objects.toStringHelper(getClass()).add(firstName, firstName).toString();
	}
	
	
	
}
