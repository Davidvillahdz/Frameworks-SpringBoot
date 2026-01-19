package ec.edu.ups.icc.fundamentos01.categories.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCategoryDto {

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 100)
    public String name;

    @Size(max = 500)
    public String description;
}