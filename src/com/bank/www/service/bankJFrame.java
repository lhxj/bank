package com.bank.www.service;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import java.awt.FlowLayout;

public class bankJFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6707878346285174517L;
	private JTextField text_zhanghao, text_kahao;// 定义账号，卡号文本行
	private JButton button_cunkuan, button_qukuan, button_yue, button_lixi;// 定义存款，取款，查询余额按钮
	private double balance;// 成员变量--查看余额
	private double rlixi;

	public bankJFrame() {
		super("银行存取款管理操作窗口");// 主窗口标题
		this.setBounds(300, 240, 400, 150);// 窗口初弹出坐标及大小
		this.setResizable(false);// 窗口大小不能改变
		this.setBackground(Color.lightGray);// 颜色设置
		this.setLayout(new java.awt.FlowLayout(FlowLayout.CENTER));// 面板布局为居中
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel1 = new JPanel();// 新建第一个面板
		this.getContentPane().add(panel1, "North");

		panel1.add(new JLabel("账户"));
		text_zhanghao = new JTextField("zj", 6);
		text_zhanghao.setEditable(false);// 文本行不可编辑
		panel1.add(text_zhanghao);

		panel1.add(new JLabel("卡号"));// 用户卡号标签及文本行
		text_kahao = new JTextField("622627199310150612", 15);
		text_kahao.setEditable(false);// 文本行不可编辑
		panel1.add(text_kahao);

		this.setVisible(true);// 窗口可见

		JPanel panel2 = new JPanel();
		this.getContentPane().add(panel2, "South");// 面板布局及添加面板至主面板

		button_cunkuan = new JButton("存款");// 存款按钮
		panel2.add(button_cunkuan);
		button_cunkuan.addActionListener(this);

		button_qukuan = new JButton("取款");// 取款按钮
		panel2.add(button_qukuan);
		button_qukuan.addActionListener(this);

		button_yue = new JButton("查询当前余额");// 查询当前余额按钮
		panel2.add(button_yue);
		button_yue.addActionListener(this);

		button_lixi = new JButton("当前利息");// 当前利息按钮
		panel2.add(button_lixi);
		button_lixi.addActionListener(this);

		this.setVisible(true);

	}

	public class MessageJDialog extends JFrame implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6308628086875834528L;
		JTextField text_cunkuanshu, text_huoqi;// 存款数与活期存款年份的输入
		JLabel jlabel_huoqi, jlabel_dingqi;// 活期期限标签，定期期限标签
		JButton button_queding;// 确定按钮
		JRadioButton radio_dingqi, radio_huoqi;// 活、定期单选框
		JComboBox box_dingqi;// 定期年份组合框
		double dnianfeng;// 内部成员变量定期年份的声明

		MessageJDialog(String dingqis[]) {

			super("提示");
			this.setBounds(350, 290, 300, 220);

			JPanel panel1 = new JPanel();
			panel1.add(new JLabel("请输入存款数（元）："));
			panel1.add(text_cunkuanshu = new JTextField("100", 6));
			text_cunkuanshu.addActionListener(this);
			this.getContentPane().add(panel1);
			JPanel panel_rb = new JPanel(new GridLayout(1, 2));
			panel1.add(panel_rb);
			this.setVisible(true);

			ButtonGroup bgroup = new ButtonGroup();

			radio_dingqi = new JRadioButton("定期存款");
			bgroup.add(radio_dingqi);
			panel_rb.add(radio_dingqi);
			radio_dingqi.addActionListener(this);

			radio_huoqi = new JRadioButton("活期存款");
			bgroup.add(radio_huoqi);
			panel_rb.add(radio_huoqi);
			this.setVisible(true);
			radio_huoqi.addActionListener(this);

			JPanel panel_rm = new JPanel();
			panel1.add(panel_rm);

			panel_rm.add(jlabel_dingqi = new JLabel("请选择定期期限（年）"));
			box_dingqi = new JComboBox(dingqis);
			box_dingqi.setEditable(true);
			box_dingqi.addActionListener(this);
			panel_rm.add(box_dingqi);
			this.setVisible(true);

			JPanel panel_rn = new JPanel();
			panel1.add(panel_rn);

			panel_rn.add(jlabel_huoqi = new JLabel("请输入活期期限（年）："));
			panel_rn.add(text_huoqi = new JTextField("1", 7));

			button_queding = new JButton("确定");
			panel1.add(button_queding);

			this.setVisible(true);

			button_queding.addActionListener(this);// 注册文本编辑事件监听器
		}

		public String str() {// 成员方法：获取文本框输入存款数字符串
			String str = text_cunkuanshu.getText();
			return str;
		}

		public double dlixi() {// 成员方法：计算定期利息
			double a = balance;
			double c = dnianfeng;
			switch ((int) (c * 4)) {
			case 1:
				return (a * 0.0248 * c);
			case 2:
				return (a * 0.0264 * c);
			case 4:
				return (a * 0.028 * c);
			case 8:
				return (a * 0.0352 * c);
			case 12:
				return (a * 0.04 * c);

			case 20:
				return (a * 0.044 * c);
			default:
				return 0;
			}
		}

		public double hlixi() {// 成员方法：计算活期利息方法
			double hnianfeng = Double.parseDouble((text_huoqi.getText()));
			double a = balance;
			double c = hnianfeng;
			double s = a * 0.004 * c;
			return s;
		}

		public void actionPerformed(ActionEvent e) {// 内部对话框类单击事件处理方法
			if (radio_dingqi.isSelected()) {// 定期单选框选中
				text_huoqi.setVisible(false);
				jlabel_huoqi.setVisible(false);
				jlabel_dingqi.setVisible(true);
				box_dingqi.setVisible(true);
				rlixi = dlixi();
			} else if (radio_huoqi.isSelected()) {// 活期单选框选中
				jlabel_dingqi.setVisible(false);
				box_dingqi.setVisible(false);
				text_huoqi.setVisible(true);
				jlabel_huoqi.setVisible(true);
				rlixi = hlixi();
			}
			if (e.getSource() == button_queding) {// 单击确定按钮
				double cunkuanzhi = Integer.parseInt(text_cunkuanshu.getText());// 存款数文本框字符串强制转换双精度型
				if (cunkuanzhi > 0) {// 存款操作
					balance += cunkuanzhi; // 存款操作使余额值增加
				}
				JOptionPane.showMessageDialog(this, "存款" + cunkuanzhi + "成功！");
				if (radio_dingqi.isSelected())// 定、活期单选框利息计算传送
					rlixi = dlixi();
				if (radio_huoqi.isSelected())
					rlixi = hlixi();
			}
			if (e.getSource() == box_dingqi) {// 定期组合框的选择
				String nianfeng = (String) box_dingqi.getSelectedItem();
				dnianfeng = Double.parseDouble(nianfeng);
			}
		}
	}

	public double get() {// 取款操作，参数为取款金额，返回实际取到金额
		String password = JOptionPane.showInputDialog(this, "请输入取款数");
		double qukuanzhi = Integer.parseInt(password);
		if (qukuanzhi > 0) {
			if (qukuanzhi <= this.balance) {
				this.balance -= qukuanzhi; // 取款操作使余额值减少
			} else {// 账户余额不够所取时
				qukuanzhi = this.balance + rlixi; // 取走全部余额
				this.balance = 0;
				JOptionPane.showMessageDialog(this, "您的余额不足，已为您取出全部余额！");
			}
			return qukuanzhi; // 返回实际取款额
		}
		return 0;
	}

	public double balance() {// 查看账户余额
		return balance + rlixi;
	}

	public void actionPerformed(ActionEvent e) {// 单击事件处理方法
		if (e.getSource() == button_cunkuan) {// 单击存款按钮
			String dingqis[] = { "0.25", "0.5", "1", "2", "3", "5" };// 组合框内容数组
			new MessageJDialog(dingqis);// 新建MessageJDialog类对象
		}
		if (e.getSource() == button_yue) {// 单击查询余额按钮
			double relixi = balance();
			JOptionPane.showMessageDialog(this, "尊敬的zj先生，您的当前账户余额为：" + (relixi)
					+ "元");
		}
		if (e.getSource() == button_qukuan) {// 单击取款按钮
			double qk = get();
			JOptionPane.showMessageDialog(this, "取款" + qk + "元成功！");
		}

		if (e.getSource() == button_lixi) {// 单击计算利息按钮
			JOptionPane.showMessageDialog(this, "尊敬的zj先生，您的当前账户利息为：" + rlixi
					+ "元");
		}
	}

	public static void main(String args[]) {// 框架bankJFrame主函数
		new bankJFrame();
	}
}
