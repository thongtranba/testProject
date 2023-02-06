package bathongshop.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bathongshop.constant.PublicConstant;
import bathongshop.entity.Order;
import bathongshop.jdbcutil.JDBCUtil;

public class OrderDAO {
	private static Logger logger = LogManager.getLogger(OrderDAO.class);
	private static OrderDAO orderDAO = null;

	public static OrderDAO getOrderDAO() {
		if (orderDAO == null) {
			orderDAO = new OrderDAO();
		}
		return orderDAO;
	}

	public int addOrder(Order order) throws SQLException {
		int insertedId = Integer.parseInt(PublicConstant.CONSTANT_0);
		try (Connection connection = JDBCUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(PublicConstant.INSERT_NEW_ORDER,
						Statement.RETURN_GENERATED_KEYS)) {
			logger.info(preparedStatement);
			preparedStatement.setInt(1, order.getCustomerId());
			preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
			preparedStatement.execute();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error(PublicConstant.THIS_IS_ERROR, e.getMessage());
		}
		return insertedId;
	}

	public List<Order> selectAllOrderByCustomerId(int customerId) {
		List<Order> orders = new ArrayList<>();
		try (Connection connection = JDBCUtil.getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(PublicConstant.SELECT_ORDER_BY_CUTOMER_ID);) {
			preparedStatement.setInt(1, customerId);
			logger.info(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(PublicConstant.ID);
				Date createdDate = rs.getDate(PublicConstant.CREATED_DATE_COLUMN);
				orders.add(new Order(id, createdDate));
			}
		} catch (Exception e) {
			logger.error(PublicConstant.THIS_IS_ERROR, e.getMessage());
		}
		return orders;
	}

}