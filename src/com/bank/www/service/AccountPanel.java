package com.bank.www.service;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.bank.www.entity.UserAccountTimeDeposit;

public class AccountPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1935013564367236838L;

	private JFrame frame;
	private JTextField no, userName, userAmount;
	private JButton find;
	private JScrollPane scrollPane;
	private JTable table;
	private List<UserAccountTimeDeposit> depositList;

	public AccountPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Border title = BorderFactory.createTitledBorder("账户存取款操作窗口");
		this.setBorder(title);

		initData();

		this.add(initPanel());
	}

	public AccountPanel(JFrame frame) {
		this();
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	private void initData() {

	}

	private JPanel initPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		panel1.add(new Label("请输入账户卡号"));
		no = new JTextField(16);
		no.addActionListener(this);
		find = new JButton("账户查询");
		find.addActionListener(this);
		panel1.add(no);
		panel1.add(find);
		panel.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.add(new Label("账户详细信息："));
		panel.add(panel2);

		JPanel panel3 = new JPanel();
		panel3.add(new Label("用户名"));
		userName = new JTextField(8);
		userName.setEnabled(false);
		userName.addActionListener(this);
		userAmount = new JTextField(8);
		userAmount.setEnabled(false);
		userAmount.addActionListener(this);
		panel3.add(userName);
		panel3.add(new Label("账户活期余额"));
		panel3.add(userAmount);
		panel.add(panel3);

		JPanel panel4 = new JPanel();
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
		panel4.add(new Label("账户定期余额"));
		scrollPane = new JScrollPane();
		table = new JTable(5, 10);
		scrollPane.add(table);
		panel4.add(scrollPane);
		panel.add(panel4);

		return panel;
	}
}
