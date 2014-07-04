package com.bank.www.service;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.bank.www.dao.UserDao;
import com.bank.www.entity.UserAccounts;

public class UserPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8248277696854599000L;

	private JRadioButton register, update, delete;
	private JFrame jFrame;
	private JPanel registerPanel;
	private JPanel updatePanel;
	private JPanel deletePanel;
	private JButton regSubmit, resetSubmit, updSubmit, delSubmit, back;
	private JTextField userName, no;
	private UserDao udao = new UserDao();

	public UserPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Border title = BorderFactory.createTitledBorder("用户账户操作窗口");
		this.setBorder(title);

		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1, 3));
		this.add(panel1);
		ButtonGroup bg = new ButtonGroup();
		register = new JRadioButton("开户", true);
		update = new JRadioButton("挂失", false);
		delete = new JRadioButton("销户", false);
		bg.add(register);
		bg.add(update);
		bg.add(delete);
		panel1.add(register);
		panel1.add(update);
		panel1.add(delete);
		register.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);

		registerPanel = regPanel();
		this.add(registerPanel);
	}

	public UserPanel(JFrame jFrame) {
		this();
		this.jFrame = jFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == register) {
			remove();
			registerPanel = regPanel();
			this.add(registerPanel);
			this.jFrame.setVisible(true);
			this.jFrame.repaint();
		}
		if (e.getSource() == update) {
			remove();
			updatePanel = upPanel();
			this.add(updatePanel);
			this.jFrame.setVisible(true);
			this.jFrame.repaint();
		}
		if (e.getSource() == delete) {
			remove();
			deletePanel = delPanel();
			this.add(deletePanel);
			this.jFrame.setVisible(true);
			this.jFrame.repaint();
		}

		if (e.getSource() == resetSubmit) {
			if (userName != null && !userName.getText().equals("")) {
				userName.setText("");
			}
			if (no != null && !no.getText().equals("")) {
				no.setText("");
			}
		}
		if (e.getSource() == regSubmit) {
			String user = userName.getText();
			String cardno = no.getText();
			if ("".equals(user) || "".equals(cardno)) {
				JOptionPane.showMessageDialog(null, "请输入用户名称或者卡号", null, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (udao.create(user, cardno)) {
				JOptionPane.showMessageDialog(null, "开户成功");
			} else {
				JOptionPane.showMessageDialog(null, "开户失败", null, JOptionPane.ERROR_MESSAGE);
			}
		}
		if (e.getSource() == updSubmit) {
			String cardno = no.getText();
			if ("".equals(cardno)) {
				JOptionPane.showMessageDialog(null, "请输入正确的卡号", null, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			UserAccounts ua = udao.findUserByNO(cardno);
			if (ua == null) {
				JOptionPane.showMessageDialog(null, "不存在卡号为：" + cardno + "的账户", null, JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (udao.updateState(ua.getId(), 2)) {
				JOptionPane.showMessageDialog(null, "挂失卡号：" + cardno + "成功");
			} else {
				JOptionPane.showMessageDialog(null, "挂失失败", null, JOptionPane.ERROR_MESSAGE);
			}
		}
		if (e.getSource() == delSubmit) {
			String cardno = no.getText();
			if ("".equals(cardno)) {
				JOptionPane.showMessageDialog(null, "请输入正确的卡号", null, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			UserAccounts ua = udao.findUserByNO(cardno);
			if (ua == null) {
				JOptionPane.showMessageDialog(null, "不存在卡号为：" + cardno + "的账户", null, JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (udao.updateState(ua.getId(), 0)) {
				JOptionPane.showMessageDialog(null, "账户：" + ua.getUserName() + "销户成功");
			} else {
				JOptionPane.showMessageDialog(null, "销户失败", null, JOptionPane.ERROR_MESSAGE);
			}
		}
		if (e.getSource() == back) {
			this.jFrame.remove(this);
			this.jFrame.getContentPane().add(new InitPanel(this.jFrame));
			this.jFrame.setVisible(true);
			this.jFrame.repaint();
		}
	}

	private JPanel regPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		panel1.add(new Label("请输入需要开户的账户名称"));
		userName = new JTextField(16);
		panel1.add(userName);
		panel.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.add(new Label("请输入需要开户的账户卡号"));
		no = new JTextField(16);
		panel2.add(no);
		panel.add(panel2);

		JPanel panel3 = new JPanel();
		regSubmit = new JButton("开户");
		regSubmit.addActionListener(this);
		panel3.add(regSubmit);
		resetSubmit = new JButton("重置");
		resetSubmit.addActionListener(this);
		panel3.add(resetSubmit);
		back = new JButton("返回");
		back.addActionListener(this);
		panel3.add(back);
		panel.add(panel3);

		return panel;
	}

	private JPanel upPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panel2 = new JPanel();
		panel2.add(new Label("请输入需要开户的账户卡号"));
		no = new JTextField(16);
		panel2.add(no);
		panel.add(panel2);

		JPanel panel3 = new JPanel();
		updSubmit = new JButton("挂失");
		updSubmit.addActionListener(this);
		panel3.add(updSubmit);
		resetSubmit = new JButton("重置");
		resetSubmit.addActionListener(this);
		panel3.add(resetSubmit);
		back = new JButton("返回");
		back.addActionListener(this);
		panel3.add(back);
		panel.add(panel3);

		return panel;
	}

	private JPanel delPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panel2 = new JPanel();
		panel2.add(new Label("请输入需要开户的账户卡号"));
		no = new JTextField(16);
		panel2.add(no);
		panel.add(panel2);

		JPanel panel3 = new JPanel();
		delSubmit = new JButton("销户");
		delSubmit.addActionListener(this);
		panel3.add(delSubmit);
		resetSubmit = new JButton("重置");
		resetSubmit.addActionListener(this);
		panel3.add(resetSubmit);
		back = new JButton("返回");
		back.addActionListener(this);
		panel3.add(back);
		panel.add(panel3);

		return panel;
	}

	private void remove() {
		if (registerPanel != null) {
			this.remove(registerPanel);
			registerPanel = null;
		}
		if (updatePanel != null) {
			this.remove(updatePanel);
			registerPanel = null;
		}
		if (deletePanel != null) {
			this.remove(deletePanel);
			registerPanel = null;
		}
	}
}
