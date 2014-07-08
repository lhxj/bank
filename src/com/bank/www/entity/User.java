package com.bank.www.entity;

public class User {

	private Integer id;
	private String no;// 卡号 唯一的
	private String userName;// 用户名
	private String identityCard;// 身份证号
	private String phone;// 手机号码
	private Integer type;// 0--普通用户 1--柜台用户 2--经理用户
	private String password;// 用户密码

	public User() {
		super();
	}

	public User(Integer id, String no, String userName, String identityCard, String phone, Integer type, String password) {
		super();
		this.id = id;
		this.no = no;
		this.userName = userName;
		this.identityCard = identityCard;
		this.phone = phone;
		this.type = type;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
