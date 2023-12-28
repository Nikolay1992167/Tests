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
        // given
        int expectedSize = inMemoryProductRepository.findAll().size();

        // when
        List<Product> result = inMemoryProductRepository.findAll();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(expectedSize);
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
                .hasNoNullFieldsOrPropertiesExcept(Product.Fields.uuid);
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