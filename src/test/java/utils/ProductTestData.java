package utils;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static utils.Constants.*;

@Data
@Builder(setterPrefix = "with")
public class ProductTestData {

    @Builder.Default
    private UUID uuid = PRODUCT_UUID;

    @Builder.Default
    private String name = PRODUCT_NAME;

    @Builder.Default
    private String description = PRODUCT_DESCRIPTION;

    @Builder.Default
    private BigDecimal price = PRODUCT_PRICE;

    @Builder.Default
    private LocalDateTime created = PRODUCT_CREAT_DATE;

    public Product buildProduct() {
        return new Product(uuid, name, description, price, created);
    }

    public ProductDto buildProductDto() {
        return new ProductDto(name, description, price);
    }

    public InfoProductDto buildInfoProductDto() {
        return new InfoProductDto(uuid, name, description, price);
    }
}
