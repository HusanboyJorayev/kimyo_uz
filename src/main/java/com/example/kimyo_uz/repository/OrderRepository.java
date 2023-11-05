package com.example.kimyo_uz.repository;

import com.example.kimyo_uz.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndDeletedAtIsNull(Long id);

    Page<Order> findAllByDeletedAtIsNull(Pageable pageable);

    @Query(
            value = "select * from order",
            nativeQuery = true
    )
    List<Order> getAllByQuery();
}
