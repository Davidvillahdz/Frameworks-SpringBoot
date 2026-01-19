package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.util.Set;

import jakarta.validation.constraints.*;

public class UpdateProductDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150)
    public String name;

    public String description;

    @Min(0)
    public double price;

    @NotNull(message = "El ID de la categoría es obligatorio")
    public Set<Long> categoryIds;

}