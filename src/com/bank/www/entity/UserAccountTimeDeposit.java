package com.bank.www.entity;

import java.security.Timestamp;

public class UserAccountTimeDeposit {

	private Integer id;
	private Integer userId;
	private Timestamp begin;
	private Long amount;

	public UserAccountTimeDeposit() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Timestamp getBegin() {
		return begin;
	}

	public void setBegin(Timestamp begin) {
		this.begin = begin;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

}
