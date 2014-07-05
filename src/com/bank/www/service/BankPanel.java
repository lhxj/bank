package com.bank.www.service;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.bank.www.entity.Bank;
import com.bank.www.entity.BankTimeDepositRate;

public class BankPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -30490616466008139L;

	private JFrame frame;
	private Bank bank;
	private Map<Integer, BankTimeDepositRate> regularRateMap;
	private JTextField bankName, currentRate, regularRate, updateCR, updateRR;
	private JRadioButton regularOne, regularThree, regularFine;
	private JButton currentBtn, regularBtn, back;
	private BankDao bdao = new BankDao();

	public BankPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Border title = BorderFactory.createTitledBorder("银行管理操作窗口");
		this.setBorder(title);

		initData();

		this.add(initPanel());
	}

	public BankPanel(JFrame frame) {
		this();
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == regularOne) {
			regularRate.setText(regularRateMap.get(1).getRate().toString());
		}
		if (e.getSource() == regularThree) {
			regularRate.setText(regularRateMap.get(3).getRate().toString());
		}
		if (e.getSource() == regularFine) {
			regularRate.setText(regularRateMap.get(5).getRate().toString());
		}
		if (e.getSource() == currentBtn) {
			modifyCurrentRate();
			this.frame.remove(this);
			this.frame.add(new BankPanel(this.frame));
			this.frame.setVisible(true);
			this.frame.repaint();
		}
		if (e.getSource() == regularBtn) {
			modifyRegularRate();
			this.frame.remove(this);
			this.frame.add(new BankPanel(this.frame));
			this.frame.setVisible(true);
			this.frame.repaint();
		}
		if (e.getSource() == back) {
			this.frame.remove(this);
			this.frame.getContentPane().add(new InitPanel(this.frame));
			this.frame.setVisible(true);
			this.frame.repaint();
		}
	}

	private JPanel initPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		panel1.add(new Label("银行名称"));
		bankName = new JTextField(8);
		bankName.setEnabled(false);
		bankName.setText(bank.getBankName());
		panel1.add(bankName);
		panel1.add(new Label("活期利率"));
		currentRate = new JTextField(8);
		currentRate.setEnabled(false);
		currentRate.setText(bank.getRate().toString());
		panel1.add(currentRate);
		panel.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.add(new Label("定期利率"));
		ButtonGroup bg = new ButtonGroup();
		regularOne = new JRadioButton("一年", true);
		regularThree = new JRadioButton("三年");
		regularFine = new JRadioButton("五年");
		bg.add(regularOne);
		bg.add(regularThree);
		bg.add(regularFine);
		panel2.add(regularOne);
		panel2.add(regularThree);
		panel2.add(regularFine);
		regularOne.addActionListener(this);
		regularThree.addActionListener(this);
		regularFine.addActionListener(this);
		regularRate = new JTextField(8);
		regularRate.setEnabled(false);
		regularRate.setText(regularRateMap.get(1).getRate().toString());
		panel2.add(regularRate);
		panel.add(panel2);

		JPanel panel3 = new JPanel();
		currentBtn = new JButton("活期利率修改");
		regularBtn = new JButton("定期利率修改");
		back = new JButton("返回");
		currentBtn.addActionListener(this);
		regularBtn.addActionListener(this);
		back.addActionListener(this);
		panel3.add(currentBtn);
		panel3.add(regularBtn);
		panel3.add(back);
		panel.add(panel3);
		return panel;
	}

	private void initData() {
		BankDao bdao = new BankDao();
		List<Bank> list = bdao.list();
		if (list != null && list.size() > 0) {
			bank = list.get(0);
		}
		regularRateMap = new HashMap<Integer, BankTimeDepositRate>();
		regularRateMap.put(1, null);
		regularRateMap.put(3, null);
		regularRateMap.put(5, null);
		List<BankTimeDepositRate> listRate = bdao.listRate(bank.getId());
		if (listRate != null && listRate.size() > 0) {
			for (BankTimeDepositRate rate : listRate) {
				regularRateMap.put(rate.getYearCount(), rate);
			}
		}
	}

	private void modifyCurrentRate() {
		Object[] options = { "修改", "取消" };
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel panel1 = new JPanel();
		panel1.add(new Label("原活期利率"));
		JTextField field1 = new JTextField(8);
		field1.setText(bank.getRate().toString());
		field1.setEnabled(false);
		panel1.add(field1);
		panel.add(panel1);
		JPanel panel2 = new JPanel();
		panel2.add(new Label("请输入新的活期利率（最多4位小数）"));
		updateCR = new JTextField(8);
		panel2.add(updateCR);
		panel.add(panel2);
		int response = JOptionPane.showOptionDialog(this, panel, "活期利率修改", JOptionPane.NO_OPTION,
				JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
		if (response == 0) {
			if ("".equals(updateCR.getText())) {
				JOptionPane.showMessageDialog(null, "请输入新的活期利率", null, JOptionPane.INFORMATION_MESSAGE);
				modifyCurrentRate();
				return;
			}
			Pattern pattern = Pattern.compile("^([1-9]+[0-9]*|0)(\\.[\\d]+)?$");
			if (!pattern.matcher(updateCR.getText()).matches()) {
				JOptionPane.showMessageDialog(null, "请输入正确的活期利率", null, JOptionPane.INFORMATION_MESSAGE);
				modifyCurrentRate();
				return;
			}
			if (bdao.updateCRate(bank.getId(), Float.parseFloat(updateCR.getText()))) {
				JOptionPane.showMessageDialog(null, "修改成功");
			} else {
				JOptionPane.showMessageDialog(null, "修改失败", null, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void modifyRegularRate() {
		Object[] options = { "修改", "取消" };
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel panel3 = new JPanel();
		ButtonGroup bg = new ButtonGroup();
		regularOne = new JRadioButton("一年", true);
		regularThree = new JRadioButton("三年");
		regularFine = new JRadioButton("五年");
		bg.add(regularOne);
		bg.add(regularThree);
		bg.add(regularFine);
		panel3.add(regularOne);
		panel3.add(regularThree);
		panel3.add(regularFine);
		regularOne.addActionListener(this);
		regularThree.addActionListener(this);
		regularFine.addActionListener(this);
		panel.add(panel3);
		JPanel panel1 = new JPanel();
		panel1.add(new Label("原定期利率"));
		regularRate = new JTextField(8);
		regularRate.setText(regularRateMap.get(1).getRate().toString());
		regularRate.setEnabled(false);
		panel1.add(regularRate);
		panel.add(panel1);
		JPanel panel2 = new JPanel();
		panel2.add(new Label("请输入新的定期利率（最多4位小数）"));
		updateRR = new JTextField(8);
		panel2.add(updateRR);
		panel.add(panel2);
		int response = JOptionPane.showOptionDialog(this, panel, "定期利率修改", JOptionPane.NO_OPTION,
				JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
		if (response == 0) {
			if ("".equals(updateRR.getText())) {
				JOptionPane.showMessageDialog(null, "请输入新的定期利率", null, JOptionPane.INFORMATION_MESSAGE);
				modifyRegularRate();
				return;
			}
			Pattern pattern = Pattern.compile("^([1-9]+[0-9]*|0)(\\.[\\d]+)?$");
			if (!pattern.matcher(updateRR.getText()).matches()) {
				JOptionPane.showMessageDialog(null, "请输入正确的定期利率", null, JOptionPane.INFORMATION_MESSAGE);
				modifyRegularRate();
				return;
			}
			Integer rateId = regularOne.isSelected() ? regularRateMap.get(1).getId()
					: regularThree.isSelected() ? regularRateMap.get(3).getId() : regularRateMap.get(5).getId();
			if (bdao.updateRRate(rateId, Float.parseFloat(updateRR.getText()))) {
				JOptionPane.showMessageDialog(null, "修改成功");
			} else {
				JOptionPane.showMessageDialog(null, "修改失败", null, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
