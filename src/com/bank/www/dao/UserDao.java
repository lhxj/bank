package com.bank.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bank.www.entity.UserAccountTimeDeposit;
import com.bank.www.entity.UserAccounts;
import com.bank.www.utils.DBHelper;

public class UserDao {

	public UserAccounts loadUser(Integer userId) {
		String sql = "select * from user_account where id = " + userId;
		UserAccounts userAccounts = null;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				userAccounts = new UserAccounts(rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getInt(4), rs.getLong(5),
						rs.getTimestamp(6));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return userAccounts;
	}

	public UserAccounts findUserByNO(String no) {
		String sql = "select * from user_account where no = " + no;
		UserAccounts userAccounts = null;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				userAccounts = new UserAccounts(rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getInt(4), rs.getLong(5),
						rs.getTimestamp(6));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return userAccounts;
	}

	public boolean create(String userName, String no) {
		String sql = "insert into user_account(user_name, no) values('"
				+ userName + "','" + no + "')";
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

	public boolean updateState(Integer userId, Integer state) {
		String sql = "update user_account set state = " + state
				+ " where id = " + userId;
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

	public boolean updateAmount(Integer userId, Long amount) {
		String sql = "update user_account set amount = " + amount
				+ " where id = " + userId;
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

	public boolean exchange(Integer outId, Long outamount, Integer inId,
			Long inamount) {
		String sql1 = "update user_account set amount = " + outamount
				+ " where id = " + outId;
		String sql2 = "update user_account set amount = " + inamount
				+ " where id = " + inId;
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

	public List<UserAccountTimeDeposit> searchDeposit(String cardno) {
		String sql = "select ad.* from account_deposit ad left join user_account ua on ua.id = ad.user_id ";
		sql += "where ua.no = " + cardno;
		List<UserAccountTimeDeposit> list = new ArrayList<UserAccountTimeDeposit>();
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UserAccountTimeDeposit uatd = new UserAccountTimeDeposit(
						rs.getInt(1), rs.getInt(2), rs.getTimestamp(3),
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
				uatd = new UserAccountTimeDeposit(rs.getInt(1), rs.getInt(2),
						rs.getTimestamp(3), rs.getLong(4), rs.getInt(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return uatd;
	}

	public boolean depositMoney(Integer accountId, Long originAmount,
			Long amount) {
		String sql1 = "update user_account set amount = "
				+ (amount + originAmount) + " where id = " + accountId;
		String sql2 = "insert account_log(user_id, deposit_amount, time, remark) values("
				+ accountId
				+ ","
				+ amount
				+ ",'"
				+ new Timestamp(System.currentTimeMillis()).toString()
						.substring(0, 19)
				+ "', '帐户原金额："
				+ originAmount
				+ "，直接存款')";
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

	public boolean drawMoney(Integer accountId, Long originAmount, Long amount) {
		String sql1 = "update user_account set amount = "
				+ (originAmount - amount) + " where id = " + accountId;
		String sql2 = "insert account_log(user_id, draw_amount, time, remark) values("
				+ accountId
				+ ","
				+ amount
				+ ",'"
				+ new Timestamp(System.currentTimeMillis()).toString()
						.substring(0, 19)
				+ "', '帐户原金额："
				+ originAmount
				+ "，直接取款')";
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

	public boolean timeDeposit(Integer userId, Integer year, Long amount) {
		String sql = "insert account_deposit(user_id, amount_time, amount, year) values("
				+ userId
				+ ",'"
				+ new Timestamp(System.currentTimeMillis()).toString()
						.substring(0, 19) + "'," + amount + "," + year + ")";
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

	public boolean interest(Integer userId, Long amount, Long interest,
			Timestamp time) {
		String sql1 = "update user_account set amount = " + (amount + interest)
				+ ", amount_time = '" + time.toString().substring(0, 19)
				+ "' where id = " + userId;
		String sql2 = "insert account_log(user_id, deposit_amount, time, remark) values("
				+ userId
				+ ","
				+ interest
				+ ",'"
				+ new Timestamp(System.currentTimeMillis()).toString()
						.substring(0, 19) + "', '帐户原金额：" + amount + "，计算利息存款')";
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
}
