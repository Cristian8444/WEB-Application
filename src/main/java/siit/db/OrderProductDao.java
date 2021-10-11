package siit.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import siit.model.OrderProduct;
import siit.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderProductDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<OrderProduct> getOrderProductBy(int orderId) {
        return jdbcTemplate.query(""
                + "SELECT  op.order_id, op.id,  op.quantity, p.name, "
                + "        op.quantity * p.price AS value, p.id as product_id, p.weight, p.price AS price "
                + "FROM ORDERS_PRODUCTS op "
                + "JOIN products p on p.id = op.product_id "
                + "WHERE op.order_id = ?", this::getOrderProduct, orderId);
    }

    private OrderProduct getOrderProduct(ResultSet resultSet, int rowNum) throws SQLException {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrderID(resultSet.getInt("order_id"));
        orderProduct.setId(resultSet.getInt("id"));
        orderProduct.setName(resultSet.getString("name"));
        orderProduct.setQuantity(resultSet.getBigDecimal("quantity"));
        orderProduct.setValue(resultSet.getBigDecimal("value"));

        Product product = new Product();
        product.setId(resultSet.getInt("product_id"));
        product.setName(resultSet.getString("name"));
        product.setWeight(resultSet.getBigDecimal("weight"));
        product.setPrice(resultSet.getBigDecimal("price"));

        orderProduct.setProduct(product);

        return orderProduct;
    }

    public void update(OrderProduct orderProduct,int orderId){
        jdbcTemplate.update("update orders_products set quantity=? where product_id=? and order_id=?",
                orderProduct.getQuantity(),orderProduct.getProduct().getId(),orderId);
    }

    public void insetIntoOrderProducts(OrderProduct orderProduct, int orderId) {
        jdbcTemplate.update("INSERT INTO ORDERS_PRODUCTS (ORDER_ID, PRODUCT_ID, QUANTITY)" +
                "VALUES(?,?,?)", orderId, orderProduct.getProduct().getId(), orderProduct.getQuantity());
    }

    public void deleteOrderProduct(int orderProductId){
        jdbcTemplate.update("DELETE FROM orders_products where id = ?",orderProductId);
    }

}
