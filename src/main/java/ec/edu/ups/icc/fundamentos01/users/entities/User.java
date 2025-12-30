package ec.edu.ups.icc.fundamentos01.users.entities;

import ec.edu.ups.icc.fundamentos01.users.dtos.*;
import java.time.LocalDateTime;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt;

    public User() {
    }

    public User(int id, String name, String email, String password) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nombre inválido");
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Email inválido");
        if (password == null || password.length() < 8)
            throw new IllegalArgumentException("Password muy corto");

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public static User fromDto(CreateUserDto dto) {
        return new User(0, dto.name, dto.email, dto.password);
    }

    public static User fromEntity(UserEntity entity) {
        return new User(
                entity.getId().intValue(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword());
    }

    public UserEntity toEntity() {
        UserEntity entity = new UserEntity();
        if (this.id > 0)
            entity.setId((long) this.id);
        entity.setName(this.name);
        entity.setEmail(this.email);
        entity.setPassword(this.password);
        return entity;
    }

    public UserResponseDto toResponseDto() {
        UserResponseDto dto = new UserResponseDto();
        dto.id = this.id;
        dto.name = this.name;
        dto.email = this.email;
        return dto;
    }

    public User update(UpdateUserDto dto) {
        this.name = dto.name;
        this.email = dto.email;
        this.password = dto.password;
        return this;
    }

    public User partialUpdate(PartialUpdateUserDto dto) {
        if (dto.name != null)
            this.name = dto.name;
        if (dto.email != null)
            this.email = dto.email;
        if (dto.password != null)
            this.password = dto.password;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}