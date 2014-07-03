package com.bank.www.entity;

public class Bank {
	private Integer id;
	private String bankName;
	private Float samebankCost;// 同行转账费用
	private Float interbankCost;// 跨行转账费用
	private Float rate;// 活期利率

	public Bank() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Float getSamebankCost() {
		return samebankCost;
	}

	public void setSamebankCost(Float samebankCost) {
		this.samebankCost = samebankCost;
	}

	public Float getInterbankCost() {
		return interbankCost;
	}

	public void setInterbankCost(Float interbankCost) {
		this.interbankCost = interbankCost;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

}
