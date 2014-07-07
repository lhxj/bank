package com.bank.www.service;

import java.awt.Dimension;
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
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.bank.www.dao.UserDao;
import com.bank.www.entity.UserAccounts;

public class depositPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1242072433773301510L;

	private JFrame frame;
	private JButton search, back;
	private List<UserAccounts> userList;
	private JScrollPane searchPanel;

	public depositPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Border title = BorderFactory.createTitledBorder("定期存款统计操作窗口");
		this.setBorder(title);

		this.add(initPanel());
	}

	public depositPanel(JFrame frame) {
		this();
		this.frame = frame;
	}

	private JPanel initPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel1.add(new Label("定期存款超过20W金额的用户信息统计查询"));
		searchPanel = new JScrollPane(getTable(userList));
		searchPanel.setPreferredSize(new Dimension(360, 150));
		panel1.add(searchPanel);
		panel.add(panel1);

		JPanel panel2 = new JPanel();
		search = new JButton("查询");
		search.addActionListener(this);
		back = new JButton("返回");
		back.addActionListener(this);
		panel2.add(search);
		panel2.add(back);
		panel.add(panel2);
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == search) {
			UserDao udao = new UserDao();
			userList = udao.searchBytimeDeposit(200000l * 100);
			searchPanel.setViewportView(getTable(userList));
			searchPanel.repaint();
		}
		if (e.getSource() == back) {
			this.frame.remove(this);
			this.frame.getContentPane().add(new InitPanel(this.frame));
			this.frame.setVisible(true);
			this.frame.repaint();
		}
	}

	private JTable getTable(List<UserAccounts> list) {
		DefaultTableModel dm = new DefaultTableModel();
		Object[] header = new Object[] { "用户名", "卡号", "状态", "定期存款总金额" };
		Object[][] data = null;
		if (list != null && list.size() > 0) {
			data = new Object[list.size()][4];
			for (int i = 0; i < list.size(); i++) {
				UserAccounts st = list.get(i);
				data[i][0] = st.getUserName();
				data[i][1] = st.getNo();
				data[i][2] = st.getState() == 1 ? "正常" : st.getState() == 2 ? "挂失" : "销户";
				data[i][3] = st.getAccountAmout() / 100.0;
			}
		}
		dm.setDataVector(data, header);
		JTable table = new JTable(dm);
		table.setRowHeight(23);
		return table;
	}

}
