package com.bank.www.entity;

import java.sql.Timestamp;

public class UserAccountTimeDeposit {

	private Integer id;
	private Integer userId;// 账户ID 对应UserAccounts中的id
	private Timestamp begin;// 定期开始时间
	private Long amount;// 定期金额
	private Integer year;// 定期年限

	public UserAccountTimeDeposit() {
		super();
	}

	public UserAccountTimeDeposit(Integer id, Integer userId, Timestamp begin, Long amount, Integer year) {
		super();
		this.id = id;
		this.userId = userId;
		this.begin = begin;
		this.amount = amount;
		this.year = year;
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}
