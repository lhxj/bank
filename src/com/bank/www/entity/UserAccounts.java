package com.bank.www.entity;

public class UserAccounts {
	private Integer id;
	private String userName;
	private Integer state;// �û��ʻ�״̬
	private Long accountAmout;// �����ʻ����

	public UserAccounts() {
		super();
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

}
