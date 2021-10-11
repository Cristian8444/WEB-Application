package siit.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import siit.model.Order;
import siit.model.OrderProduct;
import siit.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

@Repository
public class ProductDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Product> getProductsBy(String term) {
        return jdbcTemplate.query("SELECT * FROM products WHERE LOWER(name) LIKE ?",
                this::getOrderProduct, "%" + term.toLowerCase() + "%");

    }

    private Product getOrderProduct(ResultSet resultSet, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setWeight(resultSet.getBigDecimal("weight"));
        product.setPrice(resultSet.getBigDecimal("price"));

        return product;
    }

    public Product getProductById(OrderProduct orderProduct){
        return jdbcTemplate.queryForObject("select * from products where id=?", this::getOrderProduct,orderProduct.getProduct().getId());

    }

    public Order getTotalValueForOrder(int orderId) {
        return  jdbcTemplate.query(""
                + "SELECT sum(p.price * op.quantity) AS value from ORDERS_PRODUCTS op "
                + "JOIN PRODUCTS p on p.ID = op.product_Id "
                + "JOIN ORDERS o on op.order_id = o.Id "
                + "WHERE op.order_id= ?", this::getTotalValueForOrderFromDatabase, orderId);
    }

    private Order getTotalValueForOrderFromDatabase(ResultSet resultset) throws SQLException {
        Order order = new Order();
        if(null != resultset && resultset.next())
        { order.setValue(resultset.getDouble("value"));}
        return order;
    }
}