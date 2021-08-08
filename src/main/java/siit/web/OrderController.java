package siit.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import siit.ValidationException;
import siit.db.OrderProductDao;
import siit.model.Order;
import siit.model.OrderProduct;
import siit.model.Product;
import siit.sevices.OrderProductService;
import siit.sevices.OrderService;

import java.util.List;

@RestController
@RequestMapping("api/customers/{customerId}/orders/{orderId}")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderProductDao orderProductDao;
    @Autowired
    OrderProductService orderProductService;

    @GetMapping
    public Order getOrderBy(@PathVariable int customerId, @PathVariable int orderId) {
        return orderService.getOrderBy(customerId, orderId);
    }

    @GetMapping("/products")
    public List<OrderProduct> getOrderProducts(@PathVariable int customerId, @PathVariable int orderId) {
        return orderService.getOrderProductBy(customerId, orderId);
    }

    @PostMapping("/products")
    public OrderProduct addProduct(@RequestBody OrderProduct orderProduct, @PathVariable int customerId, @PathVariable int orderId) {
        return orderService.getOrderProduct(orderProduct,customerId,orderId);
    }

    @RequestMapping ("/products/{orderProductId}")
    public ModelAndView removeOrderProduct(@PathVariable int orderProductId){
        ModelAndView modelAndView = new ModelAndView();
        orderProductService.deleteOrderBy(orderProductId);
        modelAndView.setViewName("customer-orders");
        return modelAndView;
    }
}
