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
import java.sql.SQLException;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderProductDao orderProductDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    ProductDao productDao;

    public void deleteOrderBy(int orderId) {
        orderDao.deleteOrderProduct(orderId);
        orderDao.deleteOrderBy(orderId);
    }

    public List<OrderProduct> getOrderProductBy(int customerId, int orderId) {
        return orderProductDao.getOrderProductBy(orderId);
    }

    public Order getOrderBy(int customerId, int orderId) {
        for (Order order : orderDao.getOrdersBy(customerId)) {
            if (order.getId() == orderId) {
                return order;
            }
        }
        return null;
    }
    private OrderProduct getOrderProduct2(List<OrderProduct>intermediateOpList,OrderProduct orderProduct){
        for(OrderProduct op: intermediateOpList){
            if(op.getProduct().getId()==orderProduct.getProduct().getId()){
                return op;
            }
        }
        return null;
    }
    //insert functionality for adding order
    public OrderProduct prepareForInsertion(OrderProduct orderProduct, int orderID) {
//        orderProductDao.insetIntoOrderProducts(orderProduct,orderProduct.getId());
        OrderProduct orderProduct1 = new OrderProduct();
        Product product = productDao.getProductById(orderProduct);
        BigDecimal value = orderProduct.getQuantity().multiply(product.getPrice());
//        HINT TEMA: Logica de insert/update product va sta in service
        orderProduct1.setId(orderID);
//        orderProduct1.setName(orderProduct.getName());
        orderProduct1.setQuantity(orderProduct.getQuantity());
        orderProduct1.setValue(value);
        orderProduct1.setProduct(product);

        return orderProduct1;

    }
//    public OrderProduct insertOrderProduct(OrderProduct orderProduct,int customerId) {
//    for(OrderProduct op:orderProductDao.getOrderProductBy(orderId)){
//        op.
//    }
//    return orderProduct;
//    }

    public OrderProduct getOrderProductByh(int orderId, int productId){
        for(OrderProduct orderProduct: orderProductDao.getOrderProductBy(orderId)){
            if(orderProduct.getId()==orderId){
                return orderProduct;
            }
        }
        return null;
    }
    public OrderProduct getOrderProduct(OrderProduct orderProduct,int customerId, int orderId){
        List<OrderProduct> workingList= orderProductDao.getOrderProductBy(orderId);

        OrderProduct orderProduct1 = this.getOrderProduct2(workingList,orderProduct);
        if(workingList.contains(orderProduct1)){
            for(OrderProduct op: workingList){
                if(op.getProduct().getId()== orderProduct.getProduct().getId()){
                    BigDecimal quantity = op.getQuantity().add(orderProduct.getQuantity());
                    op.setQuantity(quantity);
                    op.setValue(quantity.multiply(op.getProduct().getPrice()));
                    orderProductDao.update(op,orderId);
                    return op;
                }
              }
            }else {
            orderProduct1 = this.prepareForInsertion(orderProduct,orderId);
            orderProductDao.insetIntoOrderProducts(orderProduct1,orderId);
            workingList.add(orderProduct1);
            return orderProduct1;
        }
        return null;
    }

    public Order addOrder(int customerId) {
       orderDao.addOrder(customerId);
       return null;
    }

}
