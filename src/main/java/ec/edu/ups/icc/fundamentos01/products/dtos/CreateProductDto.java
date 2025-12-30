package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.*;

public class CreateProductDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    public String name;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    public String description;

    @Min(value = 0, message = "El precio no puede ser negativo")
    public double price;

    @Min(value = 0, message = "El stock no puede ser negativo")
    public int stock;
}