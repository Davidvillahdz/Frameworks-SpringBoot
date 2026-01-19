package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.util.Set;

import jakarta.validation.constraints.*;

public class CreateProductDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    public String name;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    public Double price;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    public String description;

    @NotNull(message = "El ID del usuario es obligatorio")
    public Long userId;

    @NotNull(message = "Debe especificar al menos una categoría")
    @Size(min = 1, message = "El producto debe tener al menos una categoría")
    public Set<Long> categoryIds;
}