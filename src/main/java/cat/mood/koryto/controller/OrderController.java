package cat.mood.koryto.controller;

import cat.mood.koryto.model.OrdersView;
import cat.mood.koryto.model.User;
import cat.mood.koryto.model.UserDetails;
import cat.mood.koryto.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    final OrderService orderService;

    @GetMapping
    public String orders(Model model, @AuthenticationPrincipal UserDetails user) {
        List<OrdersView> ordersViews = orderService.getOrdersByUserId(user.getId());
        model.addAttribute("onlyOrders", orderService.getOnlyOrders(ordersViews));
        model.addAttribute("fullOrders", ordersViews);
        model.addAttribute("user", user);

        return "orders";
    }
}
