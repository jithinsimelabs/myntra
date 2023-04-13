package org.simelabs.catelog.application.service.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * class for creating brand object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBrandCommand {

    @NotNull(message = "Name should not be null")
    @NotBlank(message = "Name should not be blank")
    @Size(min = 3, message = "Name should have minimum length of 3")
    private String name;
}
