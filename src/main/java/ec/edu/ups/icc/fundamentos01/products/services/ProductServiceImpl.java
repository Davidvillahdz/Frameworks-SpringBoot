package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.products.entities.Product;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return repository.findAll().stream()
                .map(Product::fromEntity).map(Product::toResponseDto).toList();
    }

    @Override
    public Object findOne(int id) {
        return repository.findById((long) id)
                .map(Product::fromEntity).map(Product::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));
    }

    @Override
    public ProductResponseDto create(CreateProductDto dto) {
        if (repository.findByName(dto.name).isPresent()) {
            throw new ConflictException("Ya existe un producto con el nombre: " + dto.name);
        }

        Product product = Product.fromDto(dto);
        var savedEntity = repository.save(product.toEntity());
        return Product.fromEntity(savedEntity).toResponseDto();
    }

    @Override
    public Object update(int id, UpdateProductDto dto) {
        return repository.findById((long) id)
                .map(Product::fromEntity)
                .map(p -> p.update(dto))
                .map(Product::toEntity)
                .map(repository::save)
                .map(Product::fromEntity).map(Product::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado para actualizar"));
    }

    @Override
    public Object partialUpdate(int id, PartialUpdateProductDto dto) {
        return repository.findById((long) id)
                .map(Product::fromEntity)
                .map(p -> p.partialUpdate(dto))
                .map(Product::toEntity)
                .map(repository::save)
                .map(Product::fromEntity).map(Product::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado para actualizar"));
    }

    @Override
    public Object delete(int id) {
        if (!repository.existsById((long) id)) {
            throw new NotFoundException("No se puede eliminar. Producto no encontrado con ID: " + id);
        }
        repository.deleteById((long) id);
        return "Deleted successfully";
    }
}