package com.bank.www.service;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EventObject;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.bank.www.dao.BankDao;
import com.bank.www.dao.UserDao;
import com.bank.www.entity.Bank;
import com.bank.www.entity.BankTimeDepositRate;
import com.bank.www.entity.UserAccountTimeDeposit;
import com.bank.www.entity.UserAccounts;

public class AccountPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1935013564367236838L;

	private JFrame frame;
	private JTextField userName, userAmount, drawAmount, depositAmount;
	private JButton drawMoney, deposit, back, timeDeposit;
	private JScrollPane scrollPane;
	private List<UserAccountTimeDeposit> depositList = null;
	private UserAccounts ua;
	private Integer year = 1;

	public AccountPanel(JFrame frame) {
		this.frame = frame;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Border title = BorderFactory.createTitledBorder("账户存取款操作窗口");
		this.setBorder(title);

		this.add(initPanel());

		find();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == drawMoney) {
			drawMoney();
			UserDao udao = new UserDao();
			ua = udao.findUserByNO(((MainJFrame) this.frame).getNo());
			userAmount.setText(String.valueOf(ua.getAccountAmout() / 100.0));
		}
		if (e.getSource() == deposit) {
			depositMoney();
			UserDao udao = new UserDao();
			ua = udao.findUserByNO(((MainJFrame) this.frame).getNo());
			userAmount.setText(String.valueOf(ua.getAccountAmout() / 100.0));
		}
		if (e.getSource() == timeDeposit) {
			UserDao udao = new UserDao();
			timeDeposit();
			depositList = udao.searchDeposit(((MainJFrame) this.frame).getNo());
			JTable table = getTable(depositList);
			scrollPane.setViewportView(table);
			scrollPane.repaint();
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
		scrollPane = new JScrollPane(getTable(depositList));
		scrollPane.setPreferredSize(new Dimension(360, 90));
		panel4.add(scrollPane);
		panel.add(panel4);

		JPanel panel5 = new JPanel();
		drawMoney = new JButton("活期取款");
		deposit = new JButton("活期存款");
		timeDeposit = new JButton("定期存款");
		back = new JButton("返回");
		drawMoney.addActionListener(this);
		deposit.addActionListener(this);
		timeDeposit.addActionListener(this);
		back.addActionListener(this);
		panel5.add(drawMoney);
		panel5.add(deposit);
		panel5.add(timeDeposit);
		panel5.add(back);
		panel.add(panel5);

		return panel;
	}

	private void find() {
		UserDao udao = new UserDao();
		ua = udao.findUserByNO(((MainJFrame) this.frame).getNo());
		long current = System.currentTimeMillis();
		long time = ua.getAmountTime().getTime();
		int day = (int) ((current - time) / (24 * 60 * 60 * 1000));
		if (day >= 1) {
			BankDao bdao = new BankDao();
			List<Bank> list = bdao.list();
			Long interest = (long) ((ua.getAccountAmout() / 100.0) * list.get(0).getRate() * day / 360 * 100);
			Timestamp interestTime = new Timestamp(time + day * 24 * 60 * 60 * 1000);
			if (udao.interest(ua.getId(), ua.getAccountAmout(), interest, interestTime)) {
				ua = udao.findUserByNO(((MainJFrame) this.frame).getNo());
			}
		}
		userName.setText(ua.getUserName());
		userAmount.setText(String.valueOf(ua.getAccountAmout() / 100.0));
		depositList = udao.searchDeposit(((MainJFrame) this.frame).getNo());
		BankDao bdao = new BankDao();
		for (int i = 0; i < depositList.size(); i++) {
			UserAccountTimeDeposit timeDeposit = depositList.get(i);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(timeDeposit.getBegin());
			calendar.add(Calendar.YEAR, timeDeposit.getYear());
			Long future = calendar.getTimeInMillis();
			Long current1 = ua.getAmountTime().getTime();
			if (current1 >= future) {
				// 定期时间到，多余的算做活期
				BankTimeDepositRate bankTimeRate = bdao.findRate(1, timeDeposit.getYear());
				Long amount = (long) ((timeDeposit.getAmount() / 100.0) * bankTimeRate.getRate() * timeDeposit.getYear() * 100);// 这里只计算了利息部分
				if (bdao.timeOver(ua.getId(), ua.getAccountAmout(), timeDeposit.getAmount(), amount, new Timestamp(future),
						timeDeposit.getId())) {
					timeToLife(timeDeposit.getId());
				}
			}
		}
		JTable table = getTable(depositList);
		scrollPane.setViewportView(table);
		scrollPane.repaint();
	}

	private void depositMoney() {
		Object[] options = { "存款", "取消" };
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel1.add(new Label("帐户信息：" + ua.getUserName() + "， 卡号：" + ua.getNo()));
		panel1.add(new Label("帐户余额：" + ua.getAccountAmout() / 100.0));
		panel.add(panel1);
		JPanel panel2 = new JPanel();
		panel2.add(new Label("请输入需要存款的金额"));
		depositAmount = new JTextField(8);
		panel2.add(depositAmount);
		panel.add(panel2);
		int response = JOptionPane.showOptionDialog(this, panel, "帐户存款", JOptionPane.NO_OPTION,
				JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
		if (response == 0) {
			if ("".equals(depositAmount.getText())) {
				JOptionPane.showMessageDialog(null, "请输入存款金额", null, JOptionPane.INFORMATION_MESSAGE);
				depositMoney();
				return;
			}
			Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
			if (!pattern.matcher(depositAmount.getText()).matches()) {
				JOptionPane.showMessageDialog(null, "请输入正确的存款金额", null, JOptionPane.INFORMATION_MESSAGE);
				depositMoney();
				return;
			}
			UserDao udao = new UserDao();
			Long amount = (long) (Double.parseDouble(depositAmount.getText()) * 100);
			if (udao.depositMoney(ua.getId(), ua.getAccountAmout(), amount)) {
				JOptionPane.showMessageDialog(null, "存款成功");
			} else {
				JOptionPane.showMessageDialog(null, "存款失败", null, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void drawMoney() {
		Object[] options = { "取款", "取消" };
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel1.add(new Label("帐户信息：" + ua.getUserName() + "， 卡号：" + ua.getNo()));
		panel1.add(new Label("帐户余额：" + ua.getAccountAmout() / 100.0));
		panel.add(panel1);
		JPanel panel2 = new JPanel();
		panel2.add(new Label("请输入需要取款的金额"));
		drawAmount = new JTextField(8);
		panel2.add(drawAmount);
		panel.add(panel2);
		int response = JOptionPane.showOptionDialog(this, panel, "帐户取款", JOptionPane.NO_OPTION,
				JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
		if (response == 0) {
			if ("".equals(drawAmount.getText())) {
				JOptionPane.showMessageDialog(null, "请输入取款金额", null, JOptionPane.INFORMATION_MESSAGE);
				drawMoney();
				return;
			}
			Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
			if (!pattern.matcher(drawAmount.getText()).matches()) {
				JOptionPane.showMessageDialog(null, "请输入正确的取款金额", null, JOptionPane.INFORMATION_MESSAGE);
				drawMoney();
				return;
			}
			UserDao udao = new UserDao();
			Long amount = (long) (Double.parseDouble(drawAmount.getText()) * 100);
			if (ua.getAccountAmout() < amount) {
				JOptionPane.showMessageDialog(null, "帐户余额不足取款金额", null, JOptionPane.INFORMATION_MESSAGE);
				drawMoney();
				return;
			}
			if (udao.drawMoney(ua.getId(), ua.getAccountAmout(), amount)) {
				JOptionPane.showMessageDialog(null, "取款成功");
			} else {
				JOptionPane.showMessageDialog(null, "取款失败", null, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void timeDeposit() {
		Object[] options = { "存款", "取消" };
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel1.add(new Label("帐户信息：" + ua.getUserName() + "， 卡号：" + ua.getNo()));
		panel1.add(new Label("帐户余额：" + ua.getAccountAmout() / 100.0));
		panel.add(panel1);
		JPanel panel2 = new JPanel();
		panel2.add(new Label("请选择存款定期年限"));
		ButtonGroup bg = new ButtonGroup();
		final JRadioButton one = new JRadioButton("一年", true);
		one.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == one) {
					year = 1;
				}
			}
		});
		final JRadioButton three = new JRadioButton("三年");
		three.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == three) {
					year = 3;
				}
			}
		});
		final JRadioButton fine = new JRadioButton("五年");
		fine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == fine) {
					year = 5;
				}
			}
		});
		bg.add(one);
		bg.add(three);
		bg.add(fine);
		panel2.add(one);
		panel2.add(three);
		panel2.add(fine);
		panel.add(panel2);
		JPanel panel3 = new JPanel();
		panel3.add(new Label("请输入需要存款的金额"));
		depositAmount = new JTextField(8);
		panel3.add(depositAmount);
		panel.add(panel3);
		int response = JOptionPane.showOptionDialog(this, panel, "帐户存款", JOptionPane.NO_OPTION,
				JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
		if (response == 0) {
			if ("".equals(depositAmount.getText())) {
				JOptionPane.showMessageDialog(null, "请输入存款金额", null, JOptionPane.INFORMATION_MESSAGE);
				timeDeposit();
				return;
			}
			Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
			if (!pattern.matcher(depositAmount.getText()).matches()) {
				JOptionPane.showMessageDialog(null, "请输入正确的存款金额", null, JOptionPane.INFORMATION_MESSAGE);
				timeDeposit();
				return;
			}
			UserDao udao = new UserDao();
			Long amount = (long) (Double.parseDouble(depositAmount.getText()) * 100);
			if (udao.timeDeposit(ua.getId(), year, amount)) {
				JOptionPane.showMessageDialog(null, "存款成功");
			} else {
				JOptionPane.showMessageDialog(null, "存款失败", null, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private JTable getTable(List<UserAccountTimeDeposit> list) {
		DefaultTableModel dm = new DefaultTableModel();
		Object[] header = new Object[] { "编号", "存款时间", "存款金额", "定期年限", "操作" };
		Object[][] data = null;
		if (list != null && list.size() > 0) {
			String formate = "yyyy-MM-dd";
			data = new Object[list.size()][5];
			for (int i = 0; i < list.size(); i++) {
				UserAccountTimeDeposit ua = list.get(i);
				data[i][0] = ua.getId();
				data[i][1] = new SimpleDateFormat(formate).format(ua.getBegin());
				data[i][2] = ua.getAmount() / 100.0;
				data[i][3] = ua.getYear() == 1 ? "一年" : ua.getYear() == 3 ? "三年" : "五年";
				data[i][4] = "转为活期";
			}
		}
		dm.setDataVector(data, header);
		JTable table = new JTable(dm);
		table.setRowHeight(23);
		table.getColumnModel().getColumn(0).setPreferredWidth(1);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumn("操作").setCellRenderer(new MyButtonRenderer());
		table.getColumn("操作").setCellEditor(new MyButtonEditor(new JTextField()));
		return table;
	}

	private boolean timeToLife(Integer timeId) {
		UserDao udao = new UserDao();
		UserAccountTimeDeposit timeDeposit = udao.find(timeId);
		UserAccounts uas = udao.loadUser(timeDeposit.getUserId());
		Long current = uas.getAmountTime().getTime();
		Long time = timeDeposit.getBegin().getTime();
		int day = (int) ((current - time) / (24 * 60 * 60 * 1000));
		BankDao bdao = new BankDao();
		Bank bankLifeRate = bdao.list().get(0);
		Long amount = (long) (timeDeposit.getAmount() + (timeDeposit.getAmount() / 100.0) * bankLifeRate.getRate() * day
				/ 360.0 * 100);
		if (bdao.timeToLife(uas.getId(), uas.getAccountAmout(), timeDeposit.getAmount(), amount, timeId,
				timeDeposit.getAmount())) {
			find();
			return true;
		} else {
			return false;
		}
	}

	class MyButtonEditor extends DefaultCellEditor {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8566032904301644884L;
		protected JButton button;
		private String label;
		private Integer id;

		public MyButtonEditor(JTextField checkBox) {
			super(checkBox);
			this.setClickCountToStart(1);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});

		}

		public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					id = (Integer) table.getValueAt(table.getSelectedRow(), 0);
				}
			});
			return button;
		}

		public Object getCellEditorValue() {
			if (timeToLife(id)) {
				JOptionPane.showMessageDialog(null, "定期存款转成活期成功");
			} else {
				JOptionPane.showMessageDialog(null, "定期存款转成活期失败", null, JOptionPane.ERROR_MESSAGE);
			}
			return new String(label);
		}

		public boolean stopCellEditing() {
			return super.stopCellEditing();
		}

		@Override
		public boolean shouldSelectCell(EventObject anEvent) {
			return super.shouldSelectCell(anEvent);
		}

	}

	class MyButtonRenderer extends JButton implements TableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6096313383437339336L;

		public MyButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("UIManager"));
			}
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

}
