package com.bank.www.service;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;

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

import com.bank.www.dao.BankDao;
import com.bank.www.dao.UserDao;
import com.bank.www.entity.Bank;
import com.bank.www.entity.UserAccounts;

public class TransferPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3685959053460290202L;

	private JFrame frame;
	private JTextField camount, money, inno, cost;
	private JRadioButton sameTransfer, interTransfer;
	private JButton back, submit, reset;
	private Bank bank;
	private UserAccounts uaccount;

	public TransferPanel(JFrame frame) {
		this.frame = frame;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Border title = BorderFactory.createTitledBorder("用户转账操作窗口");
		this.setBorder(title);

		initData();

		this.add(basePanel());

	}

	private JPanel basePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		panel1.add(new Label("您当前的账户金额为"));
		camount = new JTextField(8);
		camount.setEnabled(false);
		camount.setText(String.valueOf(uaccount.getAccountAmout() / 100.0));
		panel1.add(camount);
		panel.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.add(new Label("请输入需要转出的金额"));
		money = new JTextField(6);
		panel2.add(money);
		panel.add(panel2);

		JPanel panel3 = new JPanel();
		panel3.add(new Label("请选择需要转账的类型"));
		ButtonGroup bg = new ButtonGroup();
		sameTransfer = new JRadioButton("同行转账", true);
		interTransfer = new JRadioButton("跨行转账");
		bg.add(sameTransfer);
		bg.add(interTransfer);
		panel3.add(sameTransfer);
		panel3.add(interTransfer);
		sameTransfer.addActionListener(this);
		interTransfer.addActionListener(this);
		panel.add(panel3);

		JPanel panel4 = new JPanel();
		panel4.add(new Label("请输入需要转入的账户卡号"));
		inno = new JTextField(16);
		panel4.add(inno);
		panel.add(panel4);

		JPanel panel5 = new JPanel();
		panel5.add(new Label("本次转账所需费用"));
		cost = new JTextField(6);
		cost.setEnabled(false);
		cost.setText(bank.getSamebankCost().toString());
		panel5.add(cost);
		panel.add(panel5);

		JPanel panel6 = new JPanel();
		submit = new JButton("转账");
		submit.addActionListener(this);
		panel6.add(submit);
		reset = new JButton("重置");
		reset.addActionListener(this);
		panel6.add(reset);
		back = new JButton("返回");
		back.addActionListener(this);
		panel6.add(back);
		panel.add(panel6);

		return panel;
	}

	private void initData() {
		BankDao bdao = new BankDao();
		List<Bank> list = bdao.list();
		if (list != null && list.size() > 0) {
			bank = list.get(0);
		}
		UserDao udao = new UserDao();
		uaccount = udao.findUserByNO(((MainJFrame) this.frame).getNo());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == sameTransfer) {
			cost.setText(bank.getSamebankCost().toString());
		}
		if (e.getSource() == interTransfer) {
			cost.setText(bank.getInterbankCost().toString());
		}
		if (e.getSource() == submit) {
			String incardno = inno.getText();
			String amount = money.getText();
			if ("".equals(incardno) || "".equals(amount)) {
				JOptionPane.showMessageDialog(null, "请输入转入的卡号或所需要转账的金额", null, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
			if (!pattern.matcher(amount).matches()) {
				JOptionPane.showMessageDialog(null, "请输入正确的转账金额", null, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			UserDao ud = new UserDao();
			if (Float.parseFloat(amount) * 100 > uaccount.getAccountAmout()) {
				JOptionPane.showMessageDialog(null, "转账金额超出用户账户余额", null, JOptionPane.WARNING_MESSAGE);
				return;
			}
			UserAccounts inu = ud.findUserByNO(incardno);
			if (inu == null) {
				JOptionPane.showMessageDialog(null, "不存在卡号为：" + incardno + "的账户", null, JOptionPane.ERROR_MESSAGE);
				return;
			}
			Long bankamount = sameTransfer.isSelected() ? bank.getSamebankCost() * 100 : bank.getInterbankCost() * 100;
			Long outamount = (long) (Float.parseFloat(amount) * 100.0 + bankamount);
			Long inamount = (long) (Float.parseFloat(amount) * 100.0);
			if (ud.exchange(uaccount.getId(), uaccount.getAccountAmout(), outamount, inu.getId(), inu.getAccountAmout(),
					inamount)) {
				JOptionPane.showMessageDialog(null, "转账成功");
				uaccount = ud.findUserByNO(((MainJFrame) this.frame).getNo());
				camount.setText(String.valueOf(uaccount.getAccountAmout() / 100.0));
				money.setText("");
				inno.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "转账失败", null, JOptionPane.ERROR_MESSAGE);
			}
		}
		if (e.getSource() == reset) {
			money.setText("");
			inno.setText("");
			sameTransfer.setSelected(true);
			cost.setText(bank.getSamebankCost().toString());
		}
		if (e.getSource() == back) {
			this.frame.remove(this);
			this.frame.getContentPane().add(new InitPanel(this.frame));
			this.frame.setVisible(true);
			this.frame.repaint();
		}
	}

}
