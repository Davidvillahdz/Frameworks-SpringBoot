package ec.edu.ups.icc.fundamentos01.categories.services;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoryDto;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import java.util.List;

public interface CategoryService {
    CategoryEntity create(CreateCategoryDto dto);

    List<CategoryEntity> findAll();
}