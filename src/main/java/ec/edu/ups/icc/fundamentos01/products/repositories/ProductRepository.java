package ec.edu.ups.icc.fundamentos01.products.repositories;

import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
        Optional<ProductEntity> findByName(String name);

        List<ProductEntity> findByOwnerId(Long userId);

        List<ProductEntity> findByCategoriesId(Long categoryId);

        List<ProductEntity> findByOwnerName(String name);

        List<ProductEntity> findByCategoriesName(String name);

        List<ProductEntity> findByCategoriesIdAndPriceGreaterThan(Long categoryId, Double price);

        @Query("SELECT p FROM ProductEntity p " +
                        "WHERE SIZE(p.categories) >= :categoryCount " +
                        "AND :categoryCount = " +
                        "(SELECT COUNT(c) FROM p.categories c WHERE c.id IN :categoryIds)")
        List<ProductEntity> findByAllCategories(@Param("categoryIds") List<Long> categoryIds,
                        @Param("categoryCount") long categoryCount);

}