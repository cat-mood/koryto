package cat.mood.koryto.service;

import cat.mood.koryto.model.*;
import cat.mood.koryto.repository.OrderDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {
    final OrderDAO orderDao;

    Order toOrder(OrdersView ordersView) {
        return new Order(
                ordersView.getOrderId(),
                ordersView.getUserId(),
                ordersView.getCost(),
                ordersView.getCreatedAt()
        );
    }

    public List<Order> getOnlyOrders(List<OrdersView> fullOrders) {
        Set<Order> onlyOrders = new HashSet<>();

        for (OrdersView ordersView : fullOrders) {
            onlyOrders.add(toOrder(ordersView));
        }

        return new ArrayList<>(onlyOrders);
    }

    public List<OrdersView> getOrdersByUserId(int userId) {
        return orderDao.getOrdersByUserId(userId);
    }

    public void createOrder(Order order) {
        orderDao.createOrder(order);
    }
}
