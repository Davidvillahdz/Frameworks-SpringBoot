package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.products.entities.Product;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private List<Product> products = new ArrayList<>();
    private int currentId = 1;

    @Override
    public List<ProductResponseDto> findAll() {
        return products.stream().map(ProductMapper::toResponse).toList();
    }

    @Override
    public Object findOne(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(ProductMapper::toResponse)
                .orElseGet(null);
    }

    @Override
    public ProductResponseDto create(CreateProductDto dto) {
        Product newProduct = ProductMapper.toEntity(currentId++, dto);
        products.add(newProduct);
        return ProductMapper.toResponse(newProduct);
    }

    @Override
    public Object update(int id, UpdateProductDto dto) {
        Product product = products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        if (product == null)
            return new Object() {
                public String error = "Product not found";
            };

        product.setName(dto.name);
        product.setDescription(dto.description);
        product.setPrice(dto.price);

        return ProductMapper.toResponse(product);
    }

    @Override
    public Object partialUpdate(int id, PartialUpdateProductDto dto) {
        Product product = products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        if (product == null)
            return new Object() {
                public String error = "Product not found";
            };

        if (dto.name != null)
            product.setName(dto.name);
        if (dto.description != null)
            product.setDescription(dto.description);
        if (dto.price != null)
            product.setPrice(dto.price);

        return ProductMapper.toResponse(product);
    }

    @Override
    public Object delete(int id) {
        boolean removed = products.removeIf(p -> p.getId() == id);
        if (!removed)
            return new Object() {
                public String error = "Product not found";
            };

        return new Object() {
            public String message = "Product deleted successfully";
        };
    }
}