package com.codes.persistence.hibernate.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.codes.persistence.hibernate.domain.support.SupportTreeEntity;

@Entity
@Table
public class Person extends SupportTreeEntity {
	@Column
    private Integer age;
	@Column
    private String sex;
	@Column
    private String firstname;
	@Column
    private String lastname;
	@Column
    private String phone;
	@Column
    private String email;
	@Column
    private String nickname;
	@Column
    private String qq;
	@Column
    private String job;
    
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	
    
}