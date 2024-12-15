package cat.mood.koryto.controller;

import cat.mood.koryto.model.*;
import cat.mood.koryto.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@Slf4j
public class ApiOrdersController {
    final OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody Order order, @AuthenticationPrincipal UserDetails user) {
        order.setUserId(user.getId());
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        int orderId = 0;
        try {
            orderId = orderService.createOrder(order);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        log.debug("Created order with id {}", orderId);

        return ResponseEntity.ok(Integer.toString(orderId));
    }

    @PostMapping("/create-order-body")
    public ResponseEntity<String> createOrderBody(@RequestBody List<OrderBody> orderBodies, @AuthenticationPrincipal UserDetails user) {
        try {
            orderService.createOrderBody(orderBodies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}
