package siit.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import siit.model.Order;
import siit.model.OrderProduct;
import siit.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Repository
public class OrderDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Order> getOrdersBy(int customerId) {
        return jdbcTemplate.query("SELECT * FROM orders WHERE customer_id = ?",
                this::getOrder, customerId);


    }

    public void deleteOrderBy(int orderId) {
        jdbcTemplate.update("DELETE FROM orders WHERE id = ?", orderId);
    }

    public void deleteOrderProduct(int orderId) {
        jdbcTemplate.update("DELETE FROM orders_products WHERE order_id = ?", orderId);
    }

    private Order getOrder(ResultSet resultSet, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("id"));
        order.setNumber(resultSet.getString("number"));
        order.setPlaced(resultSet.getTimestamp("placed").toLocalDateTime());
        return order;
    }

    private Order updateOrder(ResultSet resultSet, int orderId) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("id"));
        jdbcTemplate.update("UPDATE order SET product=?, quantity=? WHERE id=?", orderId);
        return order;
    }

    public void addOrder(int customerId) {
        String baseForNumber = "FNR18";
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        jdbcTemplate.update("INSERT INTO ORDERS (NUMBER, PLACED, CUSTOMER_ID)" +
                "VALUES(?,?,?)", baseForNumber + random.nextInt(10000),formattedDate, customerId);
    }

}
