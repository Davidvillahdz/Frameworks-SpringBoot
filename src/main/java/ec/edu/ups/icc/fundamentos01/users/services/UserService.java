package ec.edu.ups.icc.fundamentos01.users.services;

import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.*;
import java.util.List;

public interface UserService {
    List<UserResponseDto> findAll();

    Object findOne(int id);

    UserResponseDto create(CreateUserDto dto);

    Object update(int id, UpdateUserDto dto);

    Object partialUpdate(int id, PartialUpdateUserDto dto);

    Object delete(int id);

    List<ProductResponseDto> getProductsByUserId(Long userId);
}