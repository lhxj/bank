package com.bank.www.service;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import java.awt.FlowLayout;

public class BankJFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6707878346285174517L;
	private JTextField text_zhanghao, text_kahao;// �����˺ţ������ı���
	private JButton button_cunkuan, button_qukuan, button_yue, button_lixi;// �����ȡ���ѯ��ť
	private double balance;// ��Ա����--�鿴���
	private double rlixi;

	public BankJFrame() {
		super("���д�ȡ������������");// �����ڱ���
		this.setBounds(300, 240, 400, 150);// ���ڳ�������꼰��С
		this.setResizable(false);// ���ڴ�С���ܸı�
		this.setBackground(Color.lightGray);// ��ɫ����
		this.setLayout(new java.awt.FlowLayout(FlowLayout.CENTER));// ��岼��Ϊ����
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel1 = new JPanel();// �½���һ�����
		this.getContentPane().add(panel1, "North");

		panel1.add(new JLabel("�˻�"));
		text_zhanghao = new JTextField("zj", 6);
		text_zhanghao.setEditable(false);// �ı��в��ɱ༭
		panel1.add(text_zhanghao);

		panel1.add(new JLabel("����"));// �û����ű�ǩ���ı���
		text_kahao = new JTextField("622627199310150612", 15);
		text_kahao.setEditable(false);// �ı��в��ɱ༭
		panel1.add(text_kahao);

		this.setVisible(true);// ���ڿɼ�

		JPanel panel2 = new JPanel();
		this.getContentPane().add(panel2, "South");// ��岼�ּ��������������

		button_cunkuan = new JButton("���");// ��ť
		panel2.add(button_cunkuan);
		button_cunkuan.addActionListener(this);

		button_qukuan = new JButton("ȡ��");// ȡ�ť
		panel2.add(button_qukuan);
		button_qukuan.addActionListener(this);

		button_yue = new JButton("��ѯ��ǰ���");// ��ѯ��ǰ��ť
		panel2.add(button_yue);
		button_yue.addActionListener(this);

		button_lixi = new JButton("��ǰ��Ϣ");// ��ǰ��Ϣ��ť
		panel2.add(button_lixi);
		button_lixi.addActionListener(this);

		this.setVisible(true);

	}

	public class MessageJDialog extends JFrame implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6308628086875834528L;
		JTextField text_cunkuanshu, text_huoqi;// ���������ڴ����ݵ�����
		JLabel jlabel_huoqi, jlabel_dingqi;// �������ޱ�ǩ���������ޱ�ǩ
		JButton button_queding;// ȷ����ť
		JRadioButton radio_dingqi, radio_huoqi;// ����ڵ�ѡ��
		JComboBox box_dingqi;// ���������Ͽ�
		double dnianfeng;// �ڲ���Ա����������ݵ�����

		MessageJDialog(String dingqis[]) {

			super("��ʾ");
			this.setBounds(350, 290, 300, 220);

			JPanel panel1 = new JPanel();
			panel1.add(new JLabel("����������Ԫ����"));
			panel1.add(text_cunkuanshu = new JTextField("100", 6));
			text_cunkuanshu.addActionListener(this);
			this.getContentPane().add(panel1);
			JPanel panel_rb = new JPanel(new GridLayout(1, 2));
			panel1.add(panel_rb);
			this.setVisible(true);

			ButtonGroup bgroup = new ButtonGroup();

			radio_dingqi = new JRadioButton("���ڴ��");
			bgroup.add(radio_dingqi);
			panel_rb.add(radio_dingqi);
			radio_dingqi.addActionListener(this);

			radio_huoqi = new JRadioButton("���ڴ��");
			bgroup.add(radio_huoqi);
			panel_rb.add(radio_huoqi);
			this.setVisible(true);
			radio_huoqi.addActionListener(this);

			JPanel panel_rm = new JPanel();
			panel1.add(panel_rm);

			panel_rm.add(jlabel_dingqi = new JLabel("��ѡ�������ޣ��꣩"));
			box_dingqi = new JComboBox(dingqis);
			box_dingqi.setEditable(true);
			box_dingqi.addActionListener(this);
			panel_rm.add(box_dingqi);
			this.setVisible(true);

			JPanel panel_rn = new JPanel();
			panel1.add(panel_rn);

			panel_rn.add(jlabel_huoqi = new JLabel("������������ޣ��꣩��"));
			panel_rn.add(text_huoqi = new JTextField("1", 7));

			button_queding = new JButton("ȷ��");
			panel1.add(button_queding);

			this.setVisible(true);

			button_queding.addActionListener(this);// ע���ı��༭�¼�������
		}

		public String str() {// ��Ա��������ȡ�ı������������ַ�
			String str = text_cunkuanshu.getText();
			return str;
		}

		public double dlixi() {// ��Ա���������㶨����Ϣ
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

		public double hlixi() {// ��Ա���������������Ϣ����
			double hnianfeng = Double.parseDouble((text_huoqi.getText()));
			double a = balance;
			double c = hnianfeng;
			double s = a * 0.004 * c;
			return s;
		}

		public void actionPerformed(ActionEvent e) {// �ڲ��Ի����൥���¼����?��
			if (radio_dingqi.isSelected()) {// ���ڵ�ѡ��ѡ��
				text_huoqi.setVisible(false);
				jlabel_huoqi.setVisible(false);
				jlabel_dingqi.setVisible(true);
				box_dingqi.setVisible(true);
				rlixi = dlixi();
			} else if (radio_huoqi.isSelected()) {// ���ڵ�ѡ��ѡ��
				jlabel_dingqi.setVisible(false);
				box_dingqi.setVisible(false);
				text_huoqi.setVisible(true);
				jlabel_huoqi.setVisible(true);
				rlixi = hlixi();
			}
			if (e.getSource() == button_queding) {// ����ȷ����ť
				double cunkuanzhi = Integer.parseInt(text_cunkuanshu.getText());// ������ı����ַ�ǿ��ת��˫������
				if (cunkuanzhi > 0) {// ������
					balance += cunkuanzhi; // ������ʹ���ֵ����
				}
				JOptionPane.showMessageDialog(this, "���" + cunkuanzhi + "�ɹ���");
				if (radio_dingqi.isSelected())// �������ڵ�ѡ����Ϣ���㴫��
					rlixi = dlixi();
				if (radio_huoqi.isSelected())
					rlixi = hlixi();
			}
			if (e.getSource() == box_dingqi) {// ������Ͽ��ѡ��
				String nianfeng = (String) box_dingqi.getSelectedItem();
				dnianfeng = Double.parseDouble(nianfeng);
			}
		}
	}

	public double get() {// ȡ�����������Ϊȡ�������ʵ��ȡ�����
		String password = JOptionPane.showInputDialog(this, "������ȡ����");
		double qukuanzhi = Integer.parseInt(password);
		if (qukuanzhi > 0) {
			if (qukuanzhi <= this.balance) {
				this.balance -= qukuanzhi; // ȡ�����ʹ���ֵ����
			} else {// �˻�������ȡʱ
				qukuanzhi = this.balance + rlixi; // ȡ��ȫ�����
				this.balance = 0;
				JOptionPane.showMessageDialog(this, "������㣬��Ϊ��ȡ��ȫ����");
			}
			return qukuanzhi; // ����ʵ��ȡ���
		}
		return 0;
	}

	public double balance() {// �鿴�˻����
		return balance + rlixi;
	}

	public void actionPerformed(ActionEvent e) {// �����¼����?��
		if (e.getSource() == button_cunkuan) {// ������ť
			String dingqis[] = { "0.25", "0.5", "1", "2", "3", "5" };// ��Ͽ���������
			new MessageJDialog(dingqis);// �½�MessageJDialog�����
		}
		if (e.getSource() == button_yue) {// ������ѯ��ť
			double relixi = balance();
			JOptionPane.showMessageDialog(this, "�𾴵�zj������ĵ�ǰ�˻����Ϊ��" + (relixi)
					+ "Ԫ");
		}
		if (e.getSource() == button_qukuan) {// ����ȡ�ť
			double qk = get();
			JOptionPane.showMessageDialog(this, "ȡ��" + qk + "Ԫ�ɹ���");
		}

		if (e.getSource() == button_lixi) {// ����������Ϣ��ť
			JOptionPane.showMessageDialog(this, "�𾴵�zj������ĵ�ǰ�˻���ϢΪ��" + rlixi
					+ "Ԫ");
		}
	}

	public static void main(String args[]) {// ���bankJFrame������
		new BankJFrame();
	}
}
