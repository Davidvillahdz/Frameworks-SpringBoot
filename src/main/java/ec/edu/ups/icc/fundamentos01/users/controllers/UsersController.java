package ec.edu.ups.icc.fundamentos01.users.controllers;

import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.*;
import ec.edu.ups.icc.fundamentos01.users.services.UserService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponseDto>> getProductsByUserId(@PathVariable Long id) {
        List<ProductResponseDto> products = userService.getProductsByUserId(id);
        return ResponseEntity.ok(products);

    }

    public ResponseEntity<List<ProductResponseDto>> getUserProductsWithFilters(
            @PathVariable("id") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Long categoryId) {
        List<ProductResponseDto> products = userService.getProductsByUserIdWithFilters(
                id, name, minPrice, maxPrice, categoryId);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public UserResponseDto create(@Valid @RequestBody CreateUserDto dto) {
        return userService.create(dto);
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable int id, @Valid @RequestBody UpdateUserDto dto) {
        return userService.update(id, dto);
    }

    @PatchMapping("/{id}")
    public Object partialUpdate(@PathVariable int id, @RequestBody PartialUpdateUserDto dto) {
        return userService.partialUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id) {
        return userService.delete(id);
    }
}