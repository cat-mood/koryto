package cat.mood.koryto.controller;

import cat.mood.koryto.model.OrdersView;
import cat.mood.koryto.model.Order;
import cat.mood.koryto.model.User;
import cat.mood.koryto.service.CartService;
import cat.mood.koryto.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    final OrderService orderService;
    final CartService cartService;

    @GetMapping
    public String orders(Model model, @AuthenticationPrincipal User user) {
        List<OrdersView> ordersViews = orderService.getOrdersByUserId(user.getUserId());
        List<Order> onlyOrders = orderService.getOnlyOrders(ordersViews);
        onlyOrders.sort(Order.timestampComparator);
        model.addAttribute("onlyOrders", onlyOrders);
        model.addAttribute("fullOrders", ordersViews);
        model.addAttribute("user", user);
        model.addAttribute("cartSize", cartService.getCartSizeByUserId(user.getUserId()));

        return "orders";
    }
}
