package ec.edu.ups.icc.fundamentos01.categories.services;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoryDto;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryEntity create(CreateCategoryDto dto) {
        CategoryEntity category = new CategoryEntity();
        category.setName(dto.name);
        category.setDescription(dto.description);
        return repository.save(category);
    }

    @Override
    public List<CategoryEntity> findAll() {
        return repository.findAll();
    }
}