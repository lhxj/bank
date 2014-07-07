package com.bank.www.service;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.bank.www.dao.BankDao;
import com.bank.www.entity.StatisticsBean;

public class StatisticsPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6891417069532285155L;

	private JFrame frame;
	private JRadioButton month, season, year;
	private JScrollPane timePanel;
	private JComboBox timeSelect, yearSelect;
	private Integer timeType, timeItem, yearItem;
	private Integer[] monthValue = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	private Integer[] seasonValue = { 1, 2, 3, 4 };
	private Integer[] yearValue = { 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015 };
	private Label labelType;
	private JButton timeSearch, back;
	private List<StatisticsBean> timeList;
	private JPanel panel13;
	private ItemListener titemListener, yitemListener;

	public StatisticsPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Border title = BorderFactory.createTitledBorder("统计管理操作窗口");
		this.setBorder(title);

		titemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				timeItem = (Integer) itemEvent.getItem();
			}
		};
		yitemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				yearItem = (Integer) itemEvent.getItem();
			}
		};

		this.add(initPanel());
	}

	public StatisticsPanel(JFrame frame) {
		this();
		this.frame = frame;
	}

	/**
	 * @return
	 */
	private JPanel initPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panel12 = new JPanel();
		panel12.add(new Label("请选择时间类型"));
		ButtonGroup bg = new ButtonGroup();
		month = new JRadioButton("按月统计", true);
		timeType = 1;
		season = new JRadioButton("按季度统计");
		year = new JRadioButton("按年统计");
		bg.add(month);
		bg.add(season);
		bg.add(year);
		month.addActionListener(this);
		season.addActionListener(this);
		year.addActionListener(this);
		panel12.add(month);
		panel12.add(season);
		panel12.add(year);
		panel.add(panel12);

		panel13 = new JPanel();
		panel13.removeAll();
		panel13.add(new Label("请选择对应时间"));
		yearSelect = new JComboBox(yearValue);
		yearSelect.addItemListener(yitemListener);
		timeSelect = new JComboBox(month.isSelected() ? monthValue : seasonValue);
		timeSelect.addItemListener(titemListener);
		timeItem = 1;
		yearItem = 2004;
		labelType = new Label(month.isSelected() ? "月" : "季度");
		timeType = month.isSelected() ? 1 : 2;
		panel13.add(yearSelect);
		panel13.add(new Label("年"));
		panel13.add(timeSelect);
		panel13.add(labelType);
		panel.add(panel13);

		JPanel panel14 = new JPanel();
		panel14.setLayout(new BoxLayout(panel14, BoxLayout.Y_AXIS));
		panel14.add(new Label("按期查询结果"));
		timePanel = new JScrollPane(getTimeTable(timeList));
		timePanel.setPreferredSize(new Dimension(360, 60));
		panel14.add(timePanel);
		panel.add(panel14);

		JPanel panel15 = new JPanel();
		timeSearch = new JButton("查询");
		timeSearch.addActionListener(this);
		back = new JButton("返回");
		back.addActionListener(this);
		panel15.add(timeSearch);
		panel15.add(back);
		panel.add(panel15);

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == month || e.getSource() == season) {
			panel13.removeAll();
			panel13.add(new Label("请选择对应时间"));
			yearSelect = new JComboBox(yearValue);
			yearSelect.addItemListener(yitemListener);
			timeSelect = new JComboBox(month.isSelected() ? monthValue : seasonValue);
			timeSelect.addItemListener(titemListener);
			timeItem = 1;
			labelType = new Label(month.isSelected() ? "月" : "季度");
			timeType = month.isSelected() ? 1 : 2;
			panel13.add(yearSelect);
			panel13.add(new Label("年"));
			panel13.add(timeSelect);
			panel13.add(labelType);
			panel13.repaint();
			panel13.updateUI();
			panel13.setVisible(true);
		}
		if (e.getSource() == year) {
			timeType = 3;
			panel13.removeAll();
			panel13.add(new Label("请选择对应时间"));
			yearSelect = new JComboBox(yearValue);
			yearSelect.addItemListener(yitemListener);
			panel13.add(yearSelect);
			panel13.add(new Label("年"));
			panel13.repaint();
			panel13.updateUI();
			panel13.setVisible(true);
		}
		if (e.getSource() == timeSearch) {
			searchData();
		}
		if (e.getSource() == back) {
			this.frame.remove(this);
			this.frame.getContentPane().add(new InitPanel(this.frame));
			this.frame.setVisible(true);
			this.frame.repaint();
		}
	}

	private void searchData() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, yearItem);
		Timestamp begin;
		Timestamp end;
		if (timeType == 1) {
			// 按月统计
			calendar.set(Calendar.MONTH, timeItem - 1);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
			begin = new Timestamp(calendar.getTimeInMillis());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			end = new Timestamp(calendar.getTimeInMillis() - 1000);
		} else if (timeType == 2) {
			calendar.set(Calendar.MONTH, (timeItem - 1) * 3);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
			begin = new Timestamp(calendar.getTimeInMillis());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 4);
			end = new Timestamp(calendar.getTimeInMillis() - 1000);
		} else {
			calendar.set(calendar.get(Calendar.YEAR), 0, 1, 0, 0, 0);
			begin = new Timestamp(calendar.getTimeInMillis());
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
			end = new Timestamp(calendar.getTimeInMillis() - 1000);
		}
		BankDao bdao = new BankDao();
		timeList = bdao.statistics(begin, end);
		timePanel.setViewportView(getTimeTable(timeList));
		timePanel.repaint();
	}

	private JTable getTimeTable(List<StatisticsBean> list) {
		DefaultTableModel dm = new DefaultTableModel();
		Object[] header = new Object[] { "用户名", "卡号", "取款金额", "存款金额", "类型", "时间" };
		Object[][] data = null;
		if (list != null && list.size() > 0) {
			String formate = "yyyy-MM-dd";
			data = new Object[list.size()][6];
			for (int i = 0; i < list.size(); i++) {
				StatisticsBean st = list.get(i);
				data[i][0] = st.getUserName();
				data[i][1] = st.getNo();
				data[i][2] = st.getDrawAmount() / 100.0;
				data[i][3] = st.getDepositAmount() / 100.0;
				data[i][4] = st.getType() == 0 ? "活期" : "定期";
				data[i][5] = new SimpleDateFormat(formate).format(st.getTime());
			}
		}
		dm.setDataVector(data, header);
		JTable table = new JTable(dm);
		table.setRowHeight(23);
		return table;
	}

}
