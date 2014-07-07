package com.bank.www.entity;

public class Bank {
	private Integer id;
	private String bankName;//银行名称
	private Long samebankCost;//同行转账费用
	private Long interbankCost;//跨行转账费用
	private Float rate;// 活期利率

	public Bank() {
		super();
	}

	public Bank(Integer id, String bankName, Long samebankCost, Long interbankCost, Float rate) {
		super();
		this.id = id;
		this.bankName = bankName;
		this.samebankCost = samebankCost;
		this.interbankCost = interbankCost;
		this.rate = rate;
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

	public Long getSamebankCost() {
		return samebankCost;
	}

	public void setSamebankCost(Long samebankCost) {
		this.samebankCost = samebankCost;
	}

	public Long getInterbankCost() {
		return interbankCost;
	}

	public void setInterbankCost(Long interbankCost) {
		this.interbankCost = interbankCost;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

}
