package org.simelabs.catelog.application.service;

import com.github.javafaker.Faker;
import org.simelabs.catelog.application.service.models.Brand;
import org.simelabs.catelog.application.service.models.Category;
import org.simelabs.catelog.application.service.models.ProductEntity;
import org.simelabs.catelog.application.service.models.ProductSize;
import org.simelabs.catelog.application.service.repository.BrandRepository;
import org.simelabs.catelog.application.service.repository.CategoryRepository;
import org.simelabs.catelog.application.service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;

@SpringBootApplication
public class CatelogApplicationService implements CommandLineRunner {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BrandRepository brandRepository;

    public static void main(String[] args) {
        SpringApplication.run(CatelogApplicationService.class);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer(){
       return new WebMvcConfigurer() {
           @Override
           public void addCorsMappings(CorsRegistry registry) {
               registry.addMapping("/**")
                       .allowedOrigins("http://localhost:3000");
           }
       };
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        String brandName = faker.commerce().department();
        String categoryName = faker.company().profession();

        Brand brand = brandRepository.save(
                Brand.builder().name(brandName).build()
        );
        Category category = categoryRepository.save(
                Category.builder()
                        .name(categoryName)
                        .build()
        );

        for (int i = 0; i < 25; i++) {
            productRepository.save(
                    ProductEntity
                            .builder()
                            .name("product-"+i)
                            .category(category)
                            .brand(brand)
                            .price(BigDecimal.valueOf(faker.number().numberBetween(1000,9000)))
                            .size(ProductSize.L)
                            .color(faker.color().name())
                            .description("This is description ")
                            .build()
            );
        }



    }
}
