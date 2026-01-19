package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repository.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.products.entities.Product;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;
import ec.edu.ups.icc.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;

    public ProductServiceImpl(ProductRepository productRepo, UserRepository userRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return productRepo.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto findById(Long id) {
        return productRepo.findById(id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public ProductResponseDto create(CreateProductDto dto) {

        UserEntity owner = userRepo.findById(dto.userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + dto.userId));

        Set<CategoryEntity> categories = validateAndGetCategories(dto.categoryIds);

        if (productRepo.findByName(dto.name).isPresent()) {
            throw new ConflictException("El nombre del producto ya existe");
        }

        Product product = Product.fromDto(dto);
        ProductEntity entity = product.toEntity(owner);

        entity.setCategories(categories);

        ProductEntity saved = productRepo.save(entity);
        return toResponseDto(saved);
    }

    @Override
    @Transactional
    public ProductResponseDto update(Long id, UpdateProductDto dto) {
        ProductEntity existing = productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        Set<CategoryEntity> newCategories = validateAndGetCategories(dto.categoryIds);

        Product product = Product.fromEntity(existing);
        product.update(dto);

        ProductEntity updated = product.toEntity(existing.getOwner());
        updated.setId(id);

        updated.setCategories(newCategories);

        ProductEntity saved = productRepo.save(updated);
        return toResponseDto(saved);
    }

    @Override
    public ProductResponseDto partialUpdate(Long id, PartialUpdateProductDto dto) {
        ProductEntity existing = productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        Product product = Product.fromEntity(existing);
        product.partialUpdate(dto);

        ProductEntity updated = product.toEntity(existing.getOwner());
        updated.setCategories(existing.getCategories());
        updated.setId(id);

        ProductEntity saved = productRepo.save(updated);
        return toResponseDto(saved);
    }

    @Override
    public void delete(Long id) {
        if (!productRepo.existsById(id)) {
            throw new NotFoundException("Producto no encontrado");
        }
        productRepo.deleteById(id);
    }

    @Override
    public List<ProductResponseDto> findByUserId(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new NotFoundException("Usuario no encontrado con ID: " + userId);
        }
        return productRepo.findByOwnerId(userId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public List<ProductResponseDto> findByCategoryId(Long categoryId) {
        if (!categoryRepo.existsById(categoryId)) {
            throw new NotFoundException("Categoría no encontrada con ID: " + categoryId);
        }
        return productRepo.findByCategoriesId(categoryId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    private Set<CategoryEntity> validateAndGetCategories(Set<Long> categoryIds) {
        Set<CategoryEntity> categories = new HashSet<>();
        for (Long catId : categoryIds) {
            CategoryEntity category = categoryRepo.findById(catId)
                    .orElseThrow(() -> new NotFoundException("Categoría no encontrada: " + catId));
            categories.add(category);
        }
        return categories;
    }

    private ProductResponseDto toResponseDto(ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.price = entity.getPrice();
        dto.description = entity.getDescription();
        dto.createdAt = entity.getCreatedAt();
        dto.updatedAt = entity.getUpdatedAt();

        if (entity.getOwner() != null) {
            ProductResponseDto.UserSummaryDto userDto = new ProductResponseDto.UserSummaryDto();
            userDto.id = entity.getOwner().getId();
            userDto.name = entity.getOwner().getName();
            userDto.email = entity.getOwner().getEmail();
            dto.user = userDto;
        }

        if (entity.getCategories() != null) {
            dto.categories = entity.getCategories().stream()
                    .map(cat -> {
                        ProductResponseDto.CategorySummaryDto catDto = new ProductResponseDto.CategorySummaryDto();
                        catDto.id = cat.getId();
                        catDto.name = cat.getName();
                        catDto.description = cat.getDescription();
                        return catDto;
                    })
                    .sorted((c1, c2) -> c1.name.compareTo(c2.name))
                    .toList();
        }

        return dto;
    }
}