package ru.clevertec.product.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.ProductService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    @Override
    public InfoProductDto get(UUID uuid) {
        Product productToMapper = productRepository.findById(uuid)
                .orElseThrow(() -> new ProductNotFoundException(uuid));
        return mapper.toInfoProductDto(productToMapper);
    }

    @Override
    public List<InfoProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(mapper::toInfoProductDto)
                .toList();
    }

    @Override
    public UUID create(ProductDto productDto) {
        Product product = mapper.toProduct(productDto);
        Product productToSave = productRepository.save(product);
        return productToSave.getUuid();
    }

    @Override
    public void update(UUID uuid, ProductDto productDto) {
        productRepository.findById(uuid).ifPresentOrElse(
                product -> {
                    Product updatedProduct = mapper.merge(product, productDto);
                    productRepository.save(updatedProduct);
                },
                () -> { throw new ProductNotFoundException(uuid); }
        );
    }

    @Override
    public void delete(UUID uuid) {
        productRepository.delete(uuid);
    }
}
