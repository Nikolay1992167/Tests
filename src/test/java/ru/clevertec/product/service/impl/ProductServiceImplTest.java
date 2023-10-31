package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import utils.InfoProductDtoTestData;
import utils.ProductTestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static utils.Constants.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductMapper mapper;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Nested
    class GetTest {

        @Test
        void getShouldReturnExpectedInfoProductDtoUUID() {
            // given
            InfoProductDto expected = InfoProductDtoTestData.builder().build()
                    .buildInfoProductDto();
            Product sourceProduct = ProductTestData.builder().build()
                    .buildProduct();
            Optional<Product> optionalProduct = Optional.of(sourceProduct);

            when(productRepository.findById(sourceProduct.getUuid()))
                    .thenReturn(optionalProduct);
            when(mapper.toInfoProductDto(optionalProduct.get()))
                    .thenReturn(expected);

            // when
            InfoProductDto actual = productService.get(sourceProduct.getUuid());

            // then
            assertThat(actual).isNotNull().isEqualTo(expected);
            verify(productRepository).findById(sourceProduct.getUuid());
            verify(mapper).toInfoProductDto(sourceProduct);
        }

        @Test
        void getShouldReturnProductNotFoundException() {
            // given
            Product sourceProduct = ProductTestData.builder()
                    .withUuid(PRODUCT_INCORRECT_UUID)
                    .build()
                    .buildProduct();
            Optional<Product> empty = Optional.empty();

            when(productRepository.findById(sourceProduct.getUuid()))
                    .thenReturn(empty);

            // when, then
            assertThrows(ProductNotFoundException.class, () -> productService.get(sourceProduct.getUuid()));
            verify(productRepository).findById(sourceProduct.getUuid());
        }
    }

    @Nested
    class GetAllTest {

        @Test
        void getAllShouldReturnInfoProductDtoList() {
            // given
            InfoProductDto infoProductDto = InfoProductDtoTestData.builder().build()
                    .buildInfoProductDto();
            List<InfoProductDto> expected = List.of(infoProductDto);
            Product sourceProduct = ProductTestData.builder().build()
                    .buildProduct();
            List<Product> products = List.of(sourceProduct);

            when(productRepository.findAll())
                    .thenReturn(products);
            when(mapper.toInfoProductDto(sourceProduct))
                    .thenReturn(infoProductDto);

            // when
            List<InfoProductDto> actual = productService.getAll();

            // then
            assertThat(actual)
                    .hasSize(expected.size())
                    .isEqualTo(expected);
            verify(productRepository, atLeastOnce()).findAll();
            verify(mapper).toInfoProductDto(sourceProduct);
            verify(mapper, atLeastOnce()).toInfoProductDto(any(Product.class));
        }

        @Test
        void getAllShouldReturnEmptyList() {
            // given
            List<Product> products = List.of();

            when(productRepository.findAll())
                    .thenReturn(products);

            // when
            List<InfoProductDto> actual = productService.getAll();

            // then
            assertThat(actual).isNotNull().isEmpty();
            verify(productRepository).findAll();
            verify(mapper, never()).toInfoProductDto(any(Product.class));
        }
    }

    @Nested
    class CreateTest {

        @Test
        void createShouldReturnUuidOfCreatedProduct() {
            // given
            UUID expected = UUID.fromString("602422ae-3c27-4d89-9cfb-0a2906c67ffc");
            ProductDto productDto = ProductTestData.builder()
                    .build()
                    .buildProductDto();
            Product productToSave = ProductTestData.builder()
                    .withUuid(null).build()
                    .buildProduct();
            Product createdProduct = ProductTestData.builder()
                    .withUuid(expected).build()
                    .buildProduct();

            when(mapper.toProduct(productDto))
                    .thenReturn(productToSave);
            when(productRepository.save(productToSave))
                    .thenReturn(createdProduct);

            // when
            productService.create(productDto);

            // then
            verify(productRepository).save(productCaptor.capture());
            assertThat(productCaptor.getValue())
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, productToSave.getUuid());
        }

        @Test
        void createShouldReturnIllegalArgumentExceptionIfProductDtoNull() {
            // given
            ProductDto productDto = null;
            IllegalArgumentException exception = new IllegalArgumentException();

            when(productRepository.save(null))
                    .thenThrow(exception);

            // when, then
            assertThrows(IllegalArgumentException.class, () -> productService.create(productDto));
            verify(mapper, never()).toProduct(any(ProductDto.class));
            verify(productRepository, never()).save(any(Product.class));
        }

        @Test
        void createShouldInvokeRepositoryWithoutProductUuid(){
            // given
            Product productToSave = ProductTestData.builder()
                    .withUuid(null)
                    .build().buildProduct();
            Product expected = ProductTestData.builder()
                    .build().buildProduct();
            ProductDto productDto = ProductTestData.builder()
                    .withUuid(null)
                    .build().buildProductDto();

            doReturn(expected)
                    .when(productRepository).save(productToSave);
            doReturn(productToSave)
                    .when(mapper).toProduct(productDto);

            // when
            productService.create(productDto);

            // then
            verify(productRepository).save(productCaptor.capture());
            assertThat(productCaptor.getValue())
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, null);
        }

        @Test
        void createShouldInvokeRepositoryWithProductUuid(){
            // given
            Product productToSave = ProductTestData.builder()
                    .build().buildProduct();
            Product expected = ProductTestData.builder()
                    .build().buildProduct();
            ProductDto productDto = ProductTestData.builder()
                    .build().buildProductDto();

            doReturn(expected)
                    .when(productRepository).save(productToSave);
            doReturn(productToSave)
                    .when(mapper).toProduct(productDto);

            // when
            productService.create(productDto);

            // then
            verify(productRepository).save(productCaptor.capture());
            assertThat(productCaptor.getValue())
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, productToSave.getUuid());

        }
    }

    @Nested
    class UpdateTest {

        @Test
        void updateShouldUpdateProduct() {
            // given
            Product sourceProduct = ProductTestData.builder().build().buildProduct();
            UUID uuid = sourceProduct.getUuid();
            Optional<Product> optionalProduct = Optional.of(sourceProduct);
            ProductDto updatedProductDto = ProductTestData.builder()
                    .withName(UPDATE_PRODUCT_NAME)
                    .withDescription(UPDATE_PRODUCT_DESCRIPTION)
                    .withPrice(UPDATE_PRODUCT_PRICE).build()
                    .buildProductDto();
            Product updatedProduct = ProductTestData.builder()
                    .withUuid(uuid)
                    .withName(UPDATE_PRODUCT_NAME)
                    .withDescription(UPDATE_PRODUCT_DESCRIPTION)
                    .withPrice(UPDATE_PRODUCT_PRICE).build()
                    .buildProduct();

            when(productRepository.findById(uuid))
                    .thenReturn(optionalProduct);
            when(mapper.merge(sourceProduct, updatedProductDto))
                    .thenReturn(updatedProduct);
            when(productRepository.save(updatedProduct))
                    .thenReturn(updatedProduct);

            // when
            productService.update(uuid, updatedProductDto);

            //then
            verify(productRepository).findById(uuid);
            verify(mapper).merge(sourceProduct, updatedProductDto);
            verify(productRepository).save(updatedProduct);
        }

        @Test
        void updateShouldReturnProductNotFoundException() {
            // given
            Product sourceProduct = ProductTestData.builder()
                    .withUuid(PRODUCT_INCORRECT_UUID)
                    .build()
                    .buildProduct();
            ProductDto updatedProductDto = ProductTestData.builder()
                    .withName(UPDATE_PRODUCT_NAME)
                    .withDescription(UPDATE_PRODUCT_DESCRIPTION)
                    .withPrice(UPDATE_PRODUCT_PRICE).build()
                    .buildProductDto();

            when(productRepository.findById(sourceProduct.getUuid())).thenReturn(Optional.empty());

            // when, then
            assertThrows(ProductNotFoundException.class, () -> productService.update(sourceProduct.getUuid(), updatedProductDto));
            verify(productRepository).findById(sourceProduct.getUuid());
            verify(mapper, never()).toInfoProductDto(sourceProduct);
            verify(productRepository, never()).save(any(Product.class));
        }
    }

    @Nested
    class DeleteTest {

        @Test
        void deleteShouldDeleteProduct() {
            // given
            UUID uuid = ProductTestData.builder().build().getUuid();

            doNothing()
                    .when(productRepository)
                    .delete(uuid);

            // when
            productService.delete(uuid);

            //then
            verify(productRepository).delete(uuid);
        }
    }
}