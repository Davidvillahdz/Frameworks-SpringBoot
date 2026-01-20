package ec.edu.ups.icc.fundamentos01.users.services;

import ec.edu.ups.icc.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
import ec.edu.ups.icc.fundamentos01.users.dtos.*;
import ec.edu.ups.icc.fundamentos01.users.entities.User;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public UserServiceImpl(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(User::fromEntity)
                .map(User::toResponseDto)
                .toList();
    }

    @Override
    public Object findOne(int id) {
        return userRepository.findById((long) id)
                .map(User::fromEntity)
                .map(User::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));
    }

    @Override
    public UserResponseDto create(CreateUserDto dto) {
        if (userRepository.findByEmail(dto.email).isPresent()) {
            throw new ConflictException("El email ya está registrado: " + dto.email);
        }

        User user = User.fromDto(dto);
        var savedEntity = userRepository.save(user.toEntity());
        return User.fromEntity(savedEntity).toResponseDto();
    }

    @Override
    public Object update(int id, UpdateUserDto dto) {
        return userRepository.findById((long) id)
                .map(User::fromEntity)
                .map(u -> u.update(dto))
                .map(User::toEntity)
                .map(userRepository::save)
                .map(User::fromEntity)
                .map(User::toResponseDto)
                .orElseThrow(
                        () -> new NotFoundException("No se puede actualizar. Usuario no encontrado con ID: " + id));
    }

    @Override
    public Object partialUpdate(int id, PartialUpdateUserDto dto) {
        return userRepository.findById((long) id)
                .map(User::fromEntity)
                .map(u -> u.partialUpdate(dto))
                .map(User::toEntity)
                .map(userRepository::save)
                .map(User::fromEntity)
                .map(User::toResponseDto)
                .orElseThrow(
                        () -> new NotFoundException("No se puede actualizar. Usuario no encontrado con ID: " + id));
    }

    @Override
    public Object delete(int id) {
        if (!userRepository.existsById((long) id)) {
            throw new NotFoundException("No se puede eliminar. Usuario no encontrado con ID: " + id);
        }
        userRepository.deleteById((long) id);
        return "Deleted successfully";
    }

    @Override
    public List<ProductResponseDto> getProductsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Usuario no encontrado con ID: " + userId);
        }
        return productRepository.findByOwnerId(userId).stream()
                .map(this::mapProductToDto)
                .toList();
    }

    private ProductResponseDto mapProductToDto(ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.price = entity.getPrice();
        dto.description = entity.getDescription();

        dto.createdAt = entity.getCreatedAt();
        dto.updatedAt = entity.getUpdatedAt();

        if (entity.getCategories() != null) {
            dto.categories = entity.getCategories().stream()
                    .map(cat -> {
                        ProductResponseDto.CategorySummaryDto c = new ProductResponseDto.CategorySummaryDto();
                        c.id = cat.getId();
                        c.name = cat.getName();
                        c.description = cat.getDescription();
                        return c;
                    }).toList();
        }
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByUserIdWithFilters(Long userId, String name, Double minPrice,
            Double maxPrice, Long categoryId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Usuario no encontrado con ID: " + userId);
        }
        List<ProductEntity> products = productRepository.findByOwnerWithFilter(
                userId, name, minPrice, maxPrice, categoryId);
        return products.stream()
                .map(this::mapProductToDto)
                .toList();
    }
}