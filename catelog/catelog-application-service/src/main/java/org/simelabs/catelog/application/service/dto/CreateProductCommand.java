package org.simelabs.catelog.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.simelabs.catelog.application.service.models.Brand;
import org.simelabs.catelog.application.service.models.Category;
import org.simelabs.catelog.application.service.models.ProductSize;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateProductCommand {

    @NotBlank()
    private String name;
    @NotBlank()
    private String category_name;
    @NotBlank()
    private String brand_name;
    @NotNull
    @PositiveOrZero
    private BigDecimal price;
    @NotBlank
    private String color;

    @NotBlank
    private String description;
    @NotNull
    private ProductSize size;


}
