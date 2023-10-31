package ru.clevertec.product.mapper;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import utils.InfoProductDtoTestData;
import utils.ProductTestData;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.Constants.*;

class ProductMapperTest {

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

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
        void shouldReturnProductIfProductDtoContainsEmptyField() {
            // given
            ProductDto productDto = ProductTestData.builder()
                    .withDescription(null).build()
                    .buildProductDto();
            Product expected = ProductTestData.builder()
                    .withUuid(null)
                    .withDescription(null).build()
                    .buildProduct();

            // when
            Product actual = productMapper.toProduct(productDto);

            //then
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                    .hasFieldOrProperty(Product.Fields.created);
        }
    }

    @Nested
    class ToInfoProductDtoTest {

        @Test
        void shouldReturnExpectedInfoProductDto() {
            // given
            Product product = ProductTestData.builder().build()
                    .buildProduct();
            InfoProductDto expected = InfoProductDtoTestData.builder().build()
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

        @Test
        void shouldReturnInfoProductDtoIfFieldDescriptionProductContainsEmptyString() {
            // given
            Product product = ProductTestData.builder()
                    .withDescription("").build()
                    .buildProduct();
            InfoProductDto expected = InfoProductDtoTestData.builder()
                    .withName("").build()
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