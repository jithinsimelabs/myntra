package org.simelabs.catelog.application.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.simelabs.catelog.application.service.dto.CreateProductCommand;
import org.simelabs.catelog.application.service.models.Brand;
import org.simelabs.catelog.application.service.models.Category;
import org.simelabs.catelog.application.service.models.ProductEntity;
import org.simelabs.catelog.application.service.models.ProductSize;
import org.simelabs.catelog.application.service.repository.BrandRepository;
import org.simelabs.catelog.application.service.repository.CategoryRepository;
import org.simelabs.catelog.application.service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private final ObjectMapper om = new ObjectMapper();
    private final String categoryName = "first_category";
    private final String brandName = "brand_name";
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private BrandRepository brandRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void initializerMethod() {


    }

    @Test
    void shouldReturnListOfObjects() throws Exception {
        when(productRepository.findAll()).thenReturn(productList());
        this.mockMvc.perform(get("/api/product"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(status().isOk());

    }

    @Test
    void shouldReturn400IfNameIsNullOrBlank() throws Exception {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .category_name(categoryName)
                .brand_name(brandName)
                .color("yellow")
                .size(ProductSize.L)
                .price(BigDecimal.ONE)
                .description("This is an description")
                .build();
        this.mockMvc.perform(post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(createProductCommand))
                )
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturn400IfNameIsOrBlank() throws Exception {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .name(" ")
                .category_name(categoryName)
                .brand_name(brandName)
                .color("yellow")
                .size(ProductSize.L)
                .price(BigDecimal.ONE)
                .description("This is an description")
                .build();
        this.mockMvc.perform(post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(createProductCommand))
                )
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturn400IfPriceIsLessThanZero() throws Exception {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .name("first_name")
                .category_name(categoryName)
                .brand_name(brandName)
                .color("yellow")
                .size(ProductSize.L)
                .price(BigDecimal.valueOf(-1))
                .description("This is an description")
                .build();
        this.mockMvc.perform(post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(createProductCommand))
                )
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturnInvalidBodyIfNoCategoryFoundWithName() throws Exception {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .name("invalid_name")
                .category_name(categoryName)
                .brand_name(brandName)
                .color("yellow")
                .size(ProductSize.L)
                .price(BigDecimal.ONE)
                .description("This is an description")
                .build();
        this.mockMvc.perform(post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(createProductCommand))
                )
                .andExpect(jsonPath("$.message", containsString("category")))
                .andExpect(status().isNotFound());

    }


    @Test
    void shouldReturnInvalidBodyIfNoBrandFoundWithName() throws Exception {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(
                Category.builder().name(categoryName).build()
        ));
        when(brandRepository.findByName(anyString())).thenReturn(Optional.empty());

        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .name("invalid_name")
                .category_name(categoryName)
                .brand_name(brandName)
                .color("yellow")
                .size(ProductSize.L)
                .price(BigDecimal.ONE)
                .description("This is an description")
                .build();

        this.mockMvc.perform(post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(createProductCommand))
                )
                .andExpect(jsonPath("$.message", containsString("brand")))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldReturnCreatedWithBody() throws Exception {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(
                Category.builder().name(categoryName).build()
        ));
        when(brandRepository.findByName(anyString())).thenReturn(Optional.of(
                Brand.builder().name(brandName).build()
        ));
        when(productRepository.save(ArgumentMatchers.any(ProductEntity.class)))
                .thenReturn(productList().get(0));

        String productName = "valid_name";
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .name(productName)
                .category_name(categoryName)
                .brand_name(brandName)
                .color("yellow")
                .size(ProductSize.L)
                .price(BigDecimal.ONE)
                .description("This is an description")
                .build();

        this.mockMvc.perform(post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(createProductCommand))
                )
                .andDo(print())
                .andExpect(status().isCreated());

    }


    private List<ProductEntity> productList() {
        return List.of(
                ProductEntity.builder()
                        .id("first_id")
                        .name("valid_name")
                        .price(BigDecimal.valueOf(123.90))
                        .color("yellow")
                        .description("This is description")
                        .category(
                                Category.builder().name(categoryName).build()
                        ).brand(
                                Brand.builder().name(brandName).build()
                        )
                        .size(ProductSize.S)
                        .build(),
                ProductEntity.builder()
                        .id("second_id")
                        .name("First name")
                        .price(BigDecimal.valueOf(123.90))
                        .color("yellow")
                        .description("This is description")
                        .category(
                                Category.builder().name("second_category_name").build()
                        ).brand(
                                Brand.builder().name("second_brand_name").build()
                        )
                        .size(ProductSize.S)
                        .build(),
                ProductEntity.builder()
                        .id("third_id")
                        .name("First name")
                        .price(BigDecimal.valueOf(123.90))
                        .color("yellow")
                        .description("This is description")
                        .category(
                                Category.builder().name("third_category_name").build()
                        ).brand(
                                Brand.builder().name("third_brand_name").build()
                        )
                        .size(ProductSize.S)
                        .build()


        );
    }
}











