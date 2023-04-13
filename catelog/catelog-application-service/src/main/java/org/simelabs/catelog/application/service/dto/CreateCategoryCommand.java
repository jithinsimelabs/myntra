package org.simelabs.catelog.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * class for creating category object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryCommand {

    @NotNull(message = "Name should not be null")
    @NotBlank(message = "Name should not be blank")
    @Size(min = 3, message = "Name should have minimum length of 3")
    private String name;
}
