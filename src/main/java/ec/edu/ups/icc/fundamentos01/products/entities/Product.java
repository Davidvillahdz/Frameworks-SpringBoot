package ec.edu.ups.icc.fundamentos01.products.entities;

import ec.edu.ups.icc.fundamentos01.products.dtos.*;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;

    public Product() {
    }

    public Product(int id, String name, String description, double price, int stock) {
        if (price < 0)
            throw new IllegalArgumentException("El precio no puede ser negativo");
        if (stock < 0)
            throw new IllegalArgumentException("El stock no puede ser negativo");

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public static Product fromDto(CreateProductDto dto) {
        return new Product(0, dto.name, dto.description, dto.price, dto.stock);
    }

    public static Product fromEntity(ProductEntity entity) {
        return new Product(
                entity.getId().intValue(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock());
    }

    public ProductEntity toEntity() {
        ProductEntity entity = new ProductEntity();
        if (this.id > 0)
            entity.setId((long) this.id);
        entity.setName(this.name);
        entity.setDescription(this.description);
        entity.setPrice(this.price);
        entity.setStock(this.stock);
        return entity;
    }

    public ProductResponseDto toResponseDto() {
        ProductResponseDto dto = new ProductResponseDto();
        dto.id = this.id;
        dto.name = this.name;
        dto.description = this.description;
        dto.price = this.price;
        return dto;
    }

    public Product update(UpdateProductDto dto) {
        this.name = dto.name;
        this.description = dto.description;
        this.price = dto.price;
        this.stock = dto.stock;
        return this;
    }

    public Product partialUpdate(PartialUpdateProductDto dto) {
        if (dto.name != null)
            this.name = dto.name;
        if (dto.description != null)
            this.description = dto.description;
        if (dto.price != null)
            this.price = dto.price;
        if (dto.stock != null)
            this.stock = dto.stock;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
}