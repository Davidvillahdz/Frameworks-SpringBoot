package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class ProductResponseDto {
    public Long id;
    public String name;
    public String description;
    public double price;

    public UserSummaryDto user;

    public List<CategorySummaryDto> categories;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public static class UserSummaryDto {
        public Long id;
        public String name;
        public String email;
    }

    public static class CategorySummaryDto {
        public Long id;
        public String name;
        public String description;
    }
}