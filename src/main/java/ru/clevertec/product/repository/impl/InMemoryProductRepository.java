package ru.clevertec.product.repository.impl;

import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryProductRepository implements ProductRepository {

    //private final List<Product> products = new CopyOnWriteArrayList<>();
    List<Product> products = new ArrayList<>(
            List.of(new Product[]{
                    new Product(UUID.fromString("76a4a999-92d7-452f-9a7b-34607ecb688e"),
                            "Printer",
                            "Multi-colored printing.",
                            new BigDecimal(5),
                            LocalDateTime.of(2023, 10, 27, 18, 30, 0))
            })
    );

    @Override
    public Optional<Product> findById(UUID uuid) {
        return products.stream()
                .filter(product -> product.getUuid().equals(uuid))
                .findAny();
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product save(Product product) {
        product.setUuid(UUID.randomUUID());
        product.setCreated(LocalDateTime.now());
        products.add(product);
        return product;
    }

    @Override
    public void delete(UUID uuid) {
        products.removeIf(product -> product.getUuid().equals(uuid));
    }
}
