package com.bank.www.entity;

public class BankTimeDepositRate {

	private Integer id;
	private Bank bank;// 银行
	private Integer yearCount;// 定期年限
	private Float rate;// 定期利率

	public BankTimeDepositRate() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
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
