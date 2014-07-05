package com.bank.www.entity;

import java.sql.Date;

public class UserAccounts {
	private Integer id;
	private String userName;
	private String no;
	private Integer state;
	private Long accountAmout;
	private Date amountTime;

	public UserAccounts() {
		super();
	}

	public UserAccounts(Integer id, String userName, String no, Integer state, Long accountAmout, Date amountTime) {
		super();
		this.id = id;
		this.userName = userName;
		this.no = no;
		this.state = state;
		this.accountAmout = accountAmout;
		this.amountTime = amountTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getAccountAmout() {
		return accountAmout;
	}

	public void setAccountAmout(Long accountAmout) {
		this.accountAmout = accountAmout;
	}

	public Date getAmountTime() {
		return amountTime;
	}

	public void setAmountTime(Date amountTime) {
		this.amountTime = amountTime;
	}

}
