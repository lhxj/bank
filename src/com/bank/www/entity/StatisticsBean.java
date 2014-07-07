package com.bank.www.entity;

import java.sql.Timestamp;

public class StatisticsBean {

	private String userName;// 用户名
	private String no;// 卡号
	private Long drawAmount;// 取款
	private Long depositAmount;// 存款
	private Integer type;// 类型 0--活期 1--定期
	private Timestamp time;// 操作时间

	public StatisticsBean() {
		super();
	}

	public StatisticsBean(String userName, String no, Long drawAmount, Long depositAmount, Integer type, Timestamp time) {
		super();
		this.userName = userName;
		this.no = no;
		this.drawAmount = drawAmount;
		this.depositAmount = depositAmount;
		this.type = type;
		this.time = time;
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

	public Long getDrawAmount() {
		return drawAmount;
	}

	public void setDrawAmount(Long drawAmount) {
		this.drawAmount = drawAmount;
	}

	public Long getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Long depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}
