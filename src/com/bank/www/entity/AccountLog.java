package com.bank.www.entity;

import java.sql.Timestamp;

public class AccountLog {

	private Integer id;
	private Integer userId;// 用户帐户ID
	private Long drawAmount;// 取款金额
	private Long depositAmount;// 存款金额
	private Timestamp time;// 时间
	private Integer type;// 类型 0--活期 1--定期 2--定期转活期
	private String remark;// 备注

	public AccountLog() {
		super();
	}

	public AccountLog(Integer id, Integer userId, Long drawAmount, Long depositAmount, Timestamp time, Integer type,
			String remark) {
		super();
		this.id = id;
		this.userId = userId;
		this.drawAmount = drawAmount;
		this.depositAmount = depositAmount;
		this.time = time;
		this.type = type;
		this.remark = remark;
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

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
