package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.entity.Product;
import utils.ProductTestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.Constants.PRODUCT_INCORRECT_UUID;
import static utils.Constants.PRODUCT_UUID;

@ExtendWith(MockitoExtension.class)
class InMemoryProductRepositoryTest {

    @InjectMocks
    private InMemoryProductRepository inMemoryProductRepository;

    @ParameterizedTest
    @MethodSource("getArgumentsForTest")
    void findByIdShouldReturnExpectedOptional(UUID uuid, boolean isPresent) {
        // when
        Optional<Product> actual = inMemoryProductRepository.findById(uuid);

        // then
        assertThat(actual.isPresent()).isEqualTo(isPresent);
    }

    @Test
    void findAllShouldReturnListOfProduct() {
        // when
        List<Product> result = inMemoryProductRepository.findAll();

        // then
        assertThat(result).isNotEmpty();
    }

    @Test
    void saveShouldReturnSavedProduct() {
        // given
        Product expected = ProductTestData.builder()
                .withUuid(null).build()
                .buildProduct();

        // when
        Product actual = inMemoryProductRepository.save(expected);

        // then
        assertThat(actual)
                .hasNoNullFieldsOrPropertiesExcept(Product.Fields.uuid)
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                .hasNoNullFieldsOrPropertiesExcept(Product.Fields.created);
        assertThat(actual.getName())
                .isNotNull()
                .isNotEmpty()
                .hasSizeBetween(5, 10);
        assertThat(actual.getDescription())
                .matches(str -> (str == null || str.matches(".{10,30}")));
        assertThat(actual.getPrice())
                .isNotNull()
                .isPositive();
    }

    @Test
    void deleteShouldDeleteProductById() {
        // given
        UUID uuid = ProductTestData.builder().build()
                .buildProduct()
                .getUuid();

        // when
        inMemoryProductRepository.delete(uuid);

        // then
        assertThat(inMemoryProductRepository.findById(uuid)).isEmpty();
    }

    static Stream<Arguments> getArgumentsForTest() {
        return Stream.of(
                Arguments.of(PRODUCT_UUID, true),
                Arguments.of(PRODUCT_INCORRECT_UUID, false)
        );
    }
}