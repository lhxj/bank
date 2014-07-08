package com.bank.www.entity;

import java.sql.Timestamp;

public class UserAccounts {
	private Integer id;
	private Integer userId;// 用户名称 对应User的ID
	private String no;// 卡号 对应User表的no
	private Integer state;// 状态 1--正常 2--挂失 0--销户
	private Long accountAmout;// 用户账户活期余额
	private Timestamp amountTime;// 用户账户活期余额利率最后计算时间
	private String userName;// 用户名 对应User表的user_name

	public UserAccounts() {
		super();
	}

	public UserAccounts(Integer id, Integer userId, String no, Integer state, Long accountAmout, Timestamp amountTime,
			String userName) {
		super();
		this.id = id;
		this.userId = userId;
		this.no = no;
		this.state = state;
		this.accountAmout = accountAmout;
		this.amountTime = amountTime;
		this.userName = userName;
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

	public Timestamp getAmountTime() {
		return amountTime;
	}

	public void setAmountTime(Timestamp amountTime) {
		this.amountTime = amountTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
