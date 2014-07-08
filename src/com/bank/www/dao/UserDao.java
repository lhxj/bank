package com.bank.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bank.www.entity.User;
import com.bank.www.entity.UserAccountTimeDeposit;
import com.bank.www.entity.UserAccounts;
import com.bank.www.utils.DBHelper;

public class UserDao {

	public User login(String no, String password) {
		String sql = "select * from user where no = '" + no + "' and password = '" + password + "'";
		User user = null;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6), null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return user;
	}

	/**
	 * 查询用户
	 * 
	 * @param accountId
	 * @return
	 */
	public UserAccounts loadUser(Integer accountId) {
		String sql = "select ua.*, u.no, u.user_name from user_account ua left join user u on u.id = ua.user_id where ua.id = "
				+ accountId;
		UserAccounts userAccounts = null;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				userAccounts = new UserAccounts(rs.getInt(1), rs.getInt(2), rs.getString(6), rs.getInt(3), rs.getLong(4),
						rs.getTimestamp(5), rs.getString(7));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return userAccounts;
	}

	/**
	 * 根据卡号查询用户
	 * 
	 * @param no
	 * @return
	 */
	public UserAccounts findUserByNO(String no) {
		String sql = "select ua.*, u.no, u.user_name from user_account ua left join user u on ua.user_id = u.id where u.no = "
				+ no;
		UserAccounts userAccounts = null;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				userAccounts = new UserAccounts(rs.getInt(1), rs.getInt(2), rs.getString(6), rs.getInt(3), rs.getLong(4),
						rs.getTimestamp(5), rs.getString(7));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return userAccounts;
	}

	/**
	 * 创建普通用户
	 * 
	 * @param userName
	 *            用户名
	 * @param no
	 *            卡号
	 * @param password
	 *            密码
	 * @param identityCard
	 *            身份证
	 * @param phone
	 *            电话
	 * @return
	 */
	public boolean create(String userName, String no, String password, String identityCard, String phone) {
		String sql = "insert into user(user_name, no, identity_card, phone, type, password) values('" + userName + "','"
				+ no + "','" + identityCard + "','" + phone + "', 0,'" + password + "')";
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			pstmt1 = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			flag = pstmt1.executeUpdate() == 1;
			if (flag) {
				rs = pstmt1.getGeneratedKeys();
				rs.next();
				Integer uid = rs.getInt(1);
				String sql2 = "insert into user_account(user_id, amount_time) values(" + uid + ", '"
						+ new Timestamp(System.currentTimeMillis()).toString().substring(0, 19) + "')";
				pstmt2 = conn.prepareStatement(sql2);
				flag = pstmt2.executeUpdate() == 1;
				if (!flag) {
					throw new Exception();
				}
				conn.commit();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBHelper.closeResources(null, pstmt1, rs);
			DBHelper.closeResources(null, pstmt2, rs);
		}
		return flag;
	}

	/**
	 * 更新用户状态
	 * 
	 * @param accountId
	 *            用户账户 ID
	 * @param state
	 *            状态 0销户 1正常 2挂失
	 * @return
	 */
	public boolean updateState(Integer accountId, Integer state) {
		String sql = "update user_account set state = " + state + " where id = " + accountId;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			pstmt = conn.prepareStatement(sql);
			flag = pstmt.executeUpdate() == 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return flag;
	}

	/**
	 * 转账操作
	 * 
	 * @param outId
	 *            转出的账户ID
	 * @param outoriAmt
	 *            转出账户原始金额
	 * @param outamount
	 *            转出金额
	 * @param inId
	 *            转入账户ID
	 * @param inoriAmt
	 *            转入账户原始金额
	 * @param inamount
	 *            转入金额
	 * @return
	 */
	public boolean exchange(Integer outId, Long outoriAmt, Long outamount, Integer inId, Long inoriAmt, Long inamount) {
		String sql1 = "update user_account set amount = " + (outoriAmt - outamount) + " where id = " + outId;
		String sql2 = "update user_account set amount = " + (inoriAmt + inamount) + " where id = " + inId;
		String sql3 = "insert account_log(account_id, draw_amount, time, type, remark) values(" + outId + "," + outamount
				+ ",'" + new Timestamp(System.currentTimeMillis()).toString().substring(0, 19) + "', 0, '帐户原金额：" + outoriAmt
				+ "，进行转账')";
		String sql4 = "insert account_log(account_id, deposit_amount, time, type, remark) values(" + inId + "," + inamount
				+ ",'" + new Timestamp(System.currentTimeMillis()).toString().substring(0, 19) + "', 0, '帐户原金额：" + inoriAmt
				+ "，进行转账')";
		boolean flag = false;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			pstmt1 = conn.prepareStatement(sql1);
			pstmt2 = conn.prepareStatement(sql2);
			pstmt3 = conn.prepareStatement(sql3);
			pstmt4 = conn.prepareStatement(sql4);
			flag = pstmt1.executeUpdate() == 1 && pstmt2.executeUpdate() == 1 && pstmt3.executeUpdate() == 1
					&& pstmt4.executeUpdate() == 1;
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBHelper.closeResources(null, pstmt1, rs);
			DBHelper.closeResources(null, pstmt2, rs);
			DBHelper.closeResources(null, pstmt3, rs);
			DBHelper.closeResources(null, pstmt4, rs);
		}
		return flag;
	}

	/**
	 * 查询定期存款数据
	 * 
	 * @param cardno
	 *            卡号
	 * @return
	 */
	public List<UserAccountTimeDeposit> searchDeposit(String cardno) {
		String sql = "select ad.* from account_deposit ad left join user_account ua on ua.id = ad.account_id ";
		sql += "left join user u on u.id = ua.user_id where u.no = " + cardno;
		List<UserAccountTimeDeposit> list = new ArrayList<UserAccountTimeDeposit>();
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UserAccountTimeDeposit uatd = new UserAccountTimeDeposit(rs.getInt(1), rs.getInt(2), rs.getTimestamp(3),
						rs.getLong(4), rs.getInt(5));
				list.add(uatd);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return list;
	}

	/**
	 * 查询定期数据
	 * 
	 * @param timeId
	 *            定期ID
	 * @return
	 */
	public UserAccountTimeDeposit find(Integer timeId) {
		String sql = "select * from account_deposit where id = " + timeId;
		UserAccountTimeDeposit uatd = null;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				uatd = new UserAccountTimeDeposit(rs.getInt(1), rs.getInt(2), rs.getTimestamp(3), rs.getLong(4),
						rs.getInt(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return uatd;
	}

	/**
	 * 活期存款操作
	 * 
	 * @param accountId
	 *            存款账户ID
	 * @param originAmount
	 *            存款账户原始金额
	 * @param amount
	 *            活期存款金额
	 * @return
	 */
	public boolean depositMoney(Integer accountId, Long originAmount, Long amount) {
		String sql1 = "update user_account set amount = " + (amount + originAmount) + " where id = " + accountId;
		String sql2 = "insert account_log(account_id, deposit_amount, time, type, remark) values(" + accountId + ","
				+ amount + ",'" + new Timestamp(System.currentTimeMillis()).toString().substring(0, 19) + "', 0, '帐户原金额："
				+ originAmount + "，直接存款')";
		boolean flag = false;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			pstmt1 = conn.prepareStatement(sql1);
			pstmt2 = conn.prepareStatement(sql2);
			flag = pstmt1.executeUpdate() == 1 && pstmt2.executeUpdate() == 1;
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBHelper.closeResources(null, pstmt1, rs);
			DBHelper.closeResources(null, pstmt2, rs);
		}
		return flag;
	}

	/**
	 * 活期取款操作
	 * 
	 * @param accountId
	 *            活期取款ID
	 * @param originAmount
	 *            活期取款账户原始金额
	 * @param amount
	 *            活期取款金额
	 * @return
	 */
	public boolean drawMoney(Integer accountId, Long originAmount, Long amount) {
		String sql1 = "update user_account set amount = " + (originAmount - amount) + " where id = " + accountId;
		String sql2 = "insert account_log(account_id, draw_amount, time, type, remark) values(" + accountId + "," + amount
				+ ",'" + new Timestamp(System.currentTimeMillis()).toString().substring(0, 19) + "', 0,'帐户原金额："
				+ originAmount + "，直接取款')";
		boolean flag = false;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			pstmt1 = conn.prepareStatement(sql1);
			pstmt2 = conn.prepareStatement(sql2);
			flag = pstmt1.executeUpdate() == 1 && pstmt2.executeUpdate() == 1;
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBHelper.closeResources(null, pstmt1, rs);
			DBHelper.closeResources(null, pstmt2, rs);
		}
		return flag;
	}

	/**
	 * 定期存款操作
	 * 
	 * @param accountId
	 *            账户ID
	 * @param year
	 *            定期年限
	 * @param amount
	 *            定期存款金额
	 * @return
	 */
	public boolean timeDeposit(Integer accountId, Integer year, Long amount) {
		String sql1 = "insert account_deposit(account_id, amount_time, amount, year) values(" + accountId + ",'"
				+ new Timestamp(System.currentTimeMillis()).toString().substring(0, 19) + "'," + amount + "," + year + ")";
		String sql2 = "insert account_log(account_id, deposit_amount, time, type, remark) values(" + accountId + ","
				+ amount + ",'" + new Timestamp(System.currentTimeMillis()).toString().substring(0, 19) + "', 0,'定期存款:"
				+ amount + "')";
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			pstmt1 = conn.prepareStatement(sql1);
			pstmt2 = conn.prepareStatement(sql2);
			flag = pstmt1.executeUpdate() == 1 && pstmt2.executeUpdate() == 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt1, rs);
			DBHelper.closeResources(null, pstmt2, rs);
		}
		return flag;
	}

	/**
	 * 计算活期的利息
	 * 
	 * @param accountId
	 *            账户ID
	 * @param amount
	 *            账户原始金额
	 * @param interest
	 *            账户计算出的利息
	 * @param time
	 *            计算利息截止时间
	 * @return
	 */
	public boolean interest(Integer accountId, Long amount, Long interest, Timestamp time) {
		String sql1 = "update user_account set amount = " + (amount + interest) + ", amount_time = '"
				+ time.toString().substring(0, 19) + "' where id = " + accountId;
		String sql2 = "insert account_log(account_id, deposit_amount, time, remark) values(" + accountId + "," + interest
				+ ",'" + new Timestamp(System.currentTimeMillis()).toString().substring(0, 19) + "', 0,'帐户原金额：" + amount
				+ "，计算利息存款')";
		boolean flag = false;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			pstmt1 = conn.prepareStatement(sql1);
			pstmt2 = conn.prepareStatement(sql2);
			flag = pstmt1.executeUpdate() == 1 && pstmt2.executeUpdate() == 1;
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBHelper.closeResources(null, pstmt1, rs);
			DBHelper.closeResources(null, pstmt2, rs);
		}
		return flag;
	}

	public List<UserAccounts> searchBytimeDeposit(Long amount) {
		String sql = "select ua.id, u.user_name, u.no, ua.state, sum(ad.amount) from user_account ua ";
		sql += "left join account_deposit ad on ad.account_id = ua.id left join user u on ua.user_id = u.id ";
		sql += "group by ua.id having sum(ad.amount) >= " + amount;
		List<UserAccounts> list = new ArrayList<UserAccounts>();
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UserAccounts ua = new UserAccounts(rs.getInt(1), null, rs.getString(3), rs.getInt(4), rs.getLong(5), null,
						rs.getString(2));
				list.add(ua);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return list;
	}
}
