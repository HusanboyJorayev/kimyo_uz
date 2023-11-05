package com.example.kimyo_uz.repository;

import com.example.kimyo_uz.entity.Basket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket,Long> {
    Optional<Basket>findByIdAndDeletedAtIsNull(Long id);

    Page<Basket>findAllByDeletedAtIsNull(Pageable pageable);

    @Query(
            nativeQuery = true,
            value = "select * from basket"
    )
    List<Basket>getAllByQuery();
}
