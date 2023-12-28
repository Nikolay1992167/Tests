package ru.clevertec.product.mapper;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import utils.ProductTestData;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.Constants.UPDATE_PRODUCT_DESCRIPTION;
import static utils.Constants.UPDATE_PRODUCT_NAME;
import static utils.Constants.UPDATE_PRODUCT_PRICE;

class ProductMapperTest {

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Nested
    class ToProductTest {

        @Test
        void shouldReturnExpectedProduct() {
            // given
            ProductDto productDto = ProductTestData.builder().build()
                    .buildProductDto();
            Product expected = ProductTestData.builder()
                    .withUuid(null).build()
                    .buildProduct();

            // when
            Product actual = productMapper.toProduct(productDto);

            // then
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                    .hasFieldOrProperty(Product.Fields.created);
        }

        @Test
        void shouldCheckProductDtoWhenNull() {
            // given
            ProductDto productDto = null;

            // when
            Product actual = productMapper.toProduct(productDto);

            // then
            assertThat(actual).isNull();
        }
    }

    @Nested
    class ToInfoProductDtoTest {

        @Test
        void shouldReturnExpectedInfoProductDto() {
            // given
            Product product = ProductTestData.builder().build()
                    .buildProduct();
            InfoProductDto expected = ProductTestData.builder().build()
                    .buildInfoProductDto();

            // when
            InfoProductDto actual = productMapper.toInfoProductDto(product);

            // then
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.uuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.name())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.description())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.price());
        }
    }

    @Nested
    class MergeTest {

        @Test
        void shouldReturnExpectedUpdatedProduct() {
            // given
            Product product = ProductTestData.builder().build()
                    .buildProduct();
            ProductDto productDto = ProductTestData.builder()
                    .withName(UPDATE_PRODUCT_NAME)
                    .withDescription(UPDATE_PRODUCT_DESCRIPTION)
                    .withPrice(UPDATE_PRODUCT_PRICE).build()
                    .buildProductDto();
            Product expected = ProductTestData.builder()
                    .withName(UPDATE_PRODUCT_NAME)
                    .withDescription(UPDATE_PRODUCT_DESCRIPTION)
                    .withPrice(UPDATE_PRODUCT_PRICE).build()
                    .buildProduct();

            // when
            Product actual = productMapper.merge(product, productDto);

            // then
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                    .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
        }

        @Test
        void shouldReturnExpectedUpdatedProductIfProductDtoContainsEmptyField() {
            // given
            Product product = ProductTestData.builder().build()
                    .buildProduct();
            ProductDto productDto = ProductTestData.builder()
                    .withName(UPDATE_PRODUCT_NAME)
                    .withDescription(UPDATE_PRODUCT_DESCRIPTION)
                    .build()
                    .buildProductDto();
            Product expected = ProductTestData.builder()
                    .withName(UPDATE_PRODUCT_NAME)
                    .withDescription(UPDATE_PRODUCT_DESCRIPTION)
                    .build()
                    .buildProduct();

            // when
            Product actual = productMapper.merge(product, productDto);

            // then
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                    .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
        }

        @Test
        void shouldReturnNonUpdatedProductIfProductDtoNull() {
            // given
            Product product = ProductTestData.builder().build()
                    .buildProduct();
            ProductDto productDto = null;
            Product expected = ProductTestData.builder().build()
                    .buildProduct();

            // when
            Product actual = productMapper.merge(product, productDto);

            // then
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                    .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
        }
    }
}