package siit.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siit.db.OrderProductDao;

@Service
public class OrderProductService {
    @Autowired
    OrderProductDao orderProductDao;

    public void deleteOrderBy(int orderProductId){
        orderProductDao.deleteOrderProduct(orderProductId);
    }
}
