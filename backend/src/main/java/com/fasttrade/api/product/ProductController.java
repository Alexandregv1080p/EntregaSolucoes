package com.fasttrade.api.product;

import com.fasttrade.api.user.User;
import com.fasttrade.api.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository products;
    private final UserRepository users;

    public ProductController(ProductRepository products, UserRepository users) {
        this.products = products;
        this.users = users;
    }

    public record ProductRequest(@NotBlank String title,
                                 String description,
                                 @NotNull @PositiveOrZero BigDecimal price,
                                 String kind) {}

    @GetMapping
    public List<Product> list() {
        return products.findAll();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return products.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody ProductRequest req,
                          @AuthenticationPrincipal String email) {
        User seller = users.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        Product p = new Product();
        p.setTitle(req.title());
        p.setDescription(req.description());
        p.setPrice(req.price());
        p.setKind(req.kind() == null ? "SALE" : req.kind());
        p.setSellerId(seller.getId());
        return products.save(p);
    }
}
