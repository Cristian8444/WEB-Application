package siit.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siit.db.OrderDao;
import siit.db.OrderProductDao;
import siit.db.ProductDao;
import siit.model.Order;
import siit.model.OrderProduct;
import siit.model.Product;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GetValueService {
    @Autowired
    ProductDao productDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderProductDao orderProductDao;
    BigDecimal value;
    public BigDecimal getOrderBy(int customerId) {
        for (Order order : orderDao.getOrdersBy(customerId)) {
            for (OrderProduct orderProduct: orderProductDao.getOrderProductBy(order.getId())){
                value =value.add((BigDecimal) orderProductDao.getOrderProductBy(order.getId()));
            }
        }
        return value;
    }
}
