package utils;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.product.data.InfoProductDto;

import java.math.BigDecimal;
import java.util.UUID;

import static utils.Constants.*;

@Data
@Builder(setterPrefix = "with")
public class InfoProductDtoTestData {
    @Builder.Default
    private UUID uuid = PRODUCT_UUID;
    @Builder.Default
    private String name = PRODUCT_NAME;
    @Builder.Default
    private String description = PRODUCT_DESCRIPTION;
    @Builder.Default
    private BigDecimal price = PRODUCT_PRICE;

    public InfoProductDto buildInfoProductDto() {
        return new InfoProductDto(uuid, name, description, price);
    }
}
