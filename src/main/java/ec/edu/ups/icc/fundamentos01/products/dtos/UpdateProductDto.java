package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.*;

public class UpdateProductDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150)
    public String name;

    public String description;

    @Min(0)
    public double price;

    @Min(0)
    public int stock;
}