package com.bank.www.entity;

public class BankTimeDepositRate {

	private Integer id;
	private Integer bankId;
	private Integer yearCount;
	private Float rate;

	public BankTimeDepositRate() {
		super();
	}

	public BankTimeDepositRate(Integer id, Integer bankId, Integer yearCount, Float rate) {
		super();
		this.id = id;
		this.bankId = bankId;
		this.yearCount = yearCount;
		this.rate = rate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public Integer getYearCount() {
		return yearCount;
	}

	public void setYearCount(Integer yearCount) {
		this.yearCount = yearCount;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

}
