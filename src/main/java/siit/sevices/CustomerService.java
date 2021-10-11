package siit.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siit.ValidationException;
import siit.db.CustomerDao;
import siit.db.OrderDao;
import siit.db.ProductDao;
import siit.model.Customer;
import siit.model.Order;

import java.sql.SQLException;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductDao productDao;

    public List<Customer> getCustomers() {
        return customerDao.getAllCustomers();
    }

    public Customer getOrdersByCustomerId(int id) {
        Customer customer = customerDao.getCustomerById(id);
        List<Order> orders = orderDao.getOrdersBy(id);
        customer.setOrders(orders);
        setOrdersValue(customer.getOrders());
        return customer;
    }

    private void setOrdersValue(List<Order> orders){
        for (Order o: orders) {
            Order orderFromDatabase = productDao.getTotalValueForOrder(o.getId());
            o.setValue(orderFromDatabase.getValue());
        }
    }

    public void updateCustomer(Customer customer) {
        if (customer.getPhone() != null && customer.getPhone().matches("\\+?\\d+")) {
            customerDao.updateCustomer(customer);
        } else {
            throw new ValidationException("Invalid phone number");
        }
    }

    public void addNewCustomer(Customer customer) {
        customerDao.addNewCustomer(customer);
    }

    public void deleteCustomer(int customerId){
        customerDao.deleteCustomer(customerId);
    }
}