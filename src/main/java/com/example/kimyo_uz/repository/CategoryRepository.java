package com.example.kimyo_uz.repository;

import com.example.kimyo_uz.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByIdAndDeletedAtIsNull(Long id);

    Page<Category> findAllByDeletedAtIsNull(Pageable pageable);

    @Query(
            value = "select * from category",
            nativeQuery = true
    )
    List<Category> getAllByQuery();
}
