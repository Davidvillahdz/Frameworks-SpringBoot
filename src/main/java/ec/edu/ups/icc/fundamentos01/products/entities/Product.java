package ec.edu.ups.icc.fundamentos01.products.entities;

import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;

public class Product {
    private Long id;
    private String name;
    private String description;
    private Double price;

    public Product() {
    }

    public Product(String name, Double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;

    }

    public Product(Long id, String name, String description, Double price, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;

    }

    public static Product fromDto(CreateProductDto dto) {
        return new Product(dto.name, dto.price, dto.description);
    }

    public static Product fromEntity(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock());
    }

    public ProductEntity toEntity(UserEntity owner) {
        ProductEntity entity = new ProductEntity();
        if (this.id != null && this.id > 0) {
            entity.setId(this.id);
        }
        entity.setName(this.name);
        entity.setPrice(this.price);
        entity.setDescription(this.description);
        entity.setOwner(owner);

        return entity;
    }

    public Product update(UpdateProductDto dto) {
        this.name = dto.name;
        this.description = dto.description;
        this.price = dto.price;
        return this;
    }

    public Product partialUpdate(PartialUpdateProductDto dto) {
        if (dto.name != null)
            this.name = dto.name;
        if (dto.description != null)
            this.description = dto.description;
        if (dto.price != null)
            this.price = dto.price;
        return this;
    }

    public Long getId() {
        return id;
    }
}