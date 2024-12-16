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
    public ResponseEntity<String> addPartToCart(@RequestBody Cart cart, @AuthenticationPrincipal User user) {
        cart.setUserId(user.getUserId());
        log.debug("ApiCartController.addPartToCart(): {}", cart);
        try {
            cartService.addPartToCart(cart);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-part")
    public ResponseEntity<String> updatePart(@RequestBody Cart cart, @AuthenticationPrincipal User user) {
        cart.setUserId(user.getUserId());
        try {
            cartService.updateCart(cart);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-part")
    public ResponseEntity<String> deletePart(@RequestBody Cart cart, @AuthenticationPrincipal User user) {
        cart.setUserId(user.getUserId());
        try {
            cartService.deletePart(cart);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-total")
    public ResponseEntity<String> getCartTotalByUserId(@RequestParam int id) {
        double total = 0;
        try {
            total = cartService.getTotal(id);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok(Double.toString(total));
    }
}
