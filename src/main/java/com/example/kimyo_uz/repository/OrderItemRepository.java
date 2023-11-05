package com.example.kimyo_uz.repository;


import com.example.kimyo_uz.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findByIdAndDeletedAtIsNull(Long id);

    Page<OrderItem> findAllByDeletedAtIsNull(Pageable pageable);

    @Query(
            nativeQuery = true,
            value = "select * from orderItem"
    )
    List<OrderItem> getAllByQuery();
}
