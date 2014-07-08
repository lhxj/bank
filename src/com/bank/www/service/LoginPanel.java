package com.bank.www.service;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.bank.www.dao.UserDao;
import com.bank.www.entity.User;
import com.bank.www.entity.UserAccounts;

public class LoginPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4894251824971653968L;

	private JFrame frame;
	private JTextField no;
	private JPasswordField pass;
	private JButton login, exit;

	public LoginPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Border title = BorderFactory.createTitledBorder("登录窗口");
		this.setBorder(title);

		this.add(initPanel());
	}

	public LoginPanel(JFrame frame) {
		this();
		this.frame = frame;
	}

	private JPanel initPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();

		panel.add(panel1);
		panel1.add(new Label("卡号"));
		no = new JTextField(16);
		panel1.add(no);

		JPanel panel2 = new JPanel();
		panel2.add(new Label("密码"));
		pass = new JPasswordField(16);
		panel2.add(pass);
		panel.add(panel2);

		JPanel panel3 = new JPanel();
		login = new JButton("登录");
		login.addActionListener(this);
		panel3.add(login);
		exit = new JButton("退出");
		exit.addActionListener(this);
		panel3.add(exit);
		panel.add(panel3);
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == login) {
			if ("".equals(no.getText())) {
				JOptionPane.showMessageDialog(null, "请输入卡号", null, JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (pass.getPassword().length <= 0) {
				JOptionPane.showMessageDialog(null, "请输入密码", null, JOptionPane.WARNING_MESSAGE);
				return;
			}
			UserDao udao = new UserDao();
			User user = udao.login(no.getText(), String.valueOf(pass.getPassword()));
			if (user == null) {
				JOptionPane.showMessageDialog(null, "卡号或密码不正确，请重新输入", null, JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (user.getType() == 0) {
				UserAccounts ua = udao.findUserByNO(no.getText());
				if (ua.getState() == 0) {
					JOptionPane.showMessageDialog(null, "该用户的账户已销户，请重新开户", null, JOptionPane.WARNING_MESSAGE);
					return;
				} else if (ua.getState() == 2) {
					JOptionPane.showMessageDialog(null, "该用户的账户已挂失", null, JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			MainJFrame frame = (MainJFrame) this.frame;
			frame.setNo(user.getNo());
			frame.setType(user.getType());
			this.frame.remove(this);
			this.frame.getContentPane().add(new InitPanel(this.frame));
			this.frame.setVisible(true);
			this.frame.repaint();
		}
		if (e.getSource() == exit) {
			this.frame.removeAll();
			this.frame.setVisible(false);
			System.exit(0);
		}
	}

}
