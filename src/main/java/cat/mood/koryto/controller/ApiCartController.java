package cat.mood.koryto.controller;

import cat.mood.koryto.model.Cart;
import cat.mood.koryto.model.User;
import cat.mood.koryto.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class ApiCartController {
    final CartService cartService;

    @PostMapping("/add-part-to-cart")
    public ResponseEntity<Void> addPartToCart(@RequestBody Cart cart, @AuthenticationPrincipal User user) {
        cart.setUserId(user.getUserId());
        log.debug("ApiCartController.addPartToCart(): {}", cart);
        cartService.addPartToCart(cart);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-part")
    public ResponseEntity<Void> updatePart(@RequestBody Cart cart, @AuthenticationPrincipal User user) {
        cart.setUserId(user.getUserId());
        cartService.updateCart(cart);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-part")
    public ResponseEntity<Void> deletePart(@RequestBody Cart cart, @AuthenticationPrincipal User user) {
        cart.setUserId(user.getUserId());
        cartService.deleteCart(cart);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-total")
    public ResponseEntity<Double> getCartTotalByUserId(@RequestParam int id) {
        double total = cartService.getTotal(id);

        return ResponseEntity.ok(total);
    }
}
