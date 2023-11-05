package com.example.kimyo_uz.repository;

import com.example.kimyo_uz.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {
    Optional<Card>findByIdAndDeletedAtIsNull(Long id);
    Page<Card>findAllByDeletedAtIsNull(Pageable pageable);

    @Query(
            value = "select * from card",
            nativeQuery = true
    )
    List<Card>getAllByQuery();
}
