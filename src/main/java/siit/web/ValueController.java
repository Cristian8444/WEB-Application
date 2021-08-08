package siit.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import siit.sevices.CustomerService;
import siit.sevices.GetValueService;
import siit.sevices.OrderService;
import siit.sevices.ProductService;

import java.math.BigDecimal;

@RestController
public class ValueController {
    @Autowired
    GetValueService getValueService;

    @GetMapping("api/customer/orders")
    public BigDecimal ValueController (@RequestParam ("customerId") int customerId){
        return getValueService.getOrderBy(customerId);
    }
}
