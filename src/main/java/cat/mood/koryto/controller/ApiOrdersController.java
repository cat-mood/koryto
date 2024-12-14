package cat.mood.koryto.controller;

import cat.mood.koryto.model.*;
import cat.mood.koryto.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class ApiOrdersController {
    final OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<Void> createOrder(@RequestBody Order order, @AuthenticationPrincipal UserDetails user) {
        order.setUserId(user.getId());
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        orderService.createOrder(order);

        return ResponseEntity.ok().build();
    }
}
