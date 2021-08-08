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
}
