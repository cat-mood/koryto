package cat.mood.koryto.controller;

import cat.mood.koryto.model.Cart;
import cat.mood.koryto.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class ApiCartController {
    final CartService cartService;

    @PostMapping("/add-part-to-cart")
    public ResponseEntity<Void> addPartToCart(@RequestBody Cart cart) {
        log.debug("ApiCartController.addPartToCart(): {}", cart);
        cartService.addPartToCart(cart);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-size")
    public int getCartSizeByUserId(@RequestParam int id) {
        return cartService.getCartSizeByUserId(id);
    }
}
