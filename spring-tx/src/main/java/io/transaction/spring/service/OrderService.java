package io.transaction.spring.service;

import io.transaction.spring.dao.OrderDao;
import io.transaction.spring.dao.ProductDao;
import io.transaction.spring.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

/**
 * @author binghe
 * @version 1.0.0
 * @description 订单Servcie
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductService productService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void submitOrder() {
        //生成订单
        Order order = new Order();
        long number = Math.abs(new Random().nextInt(500));
        order.setId(number);
        order.setOrderNo("order_" + number);
        orderDao.saveOrder(order);
        //减库存
        this.updateProductStockCountById(1, 1L);
        int i = 1 / 0;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateProductStockCountById(Integer stockCount, Long id) {
        productDao.updateProductStockCountById(stockCount, id);
    }
}
