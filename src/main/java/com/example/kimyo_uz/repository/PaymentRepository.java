package com.example.kimyo_uz.repository;

import com.example.kimyo_uz.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByIdAndDeletedAtIsNull(Long id);

    Page<Payment> findAllByDeletedAtIsNull(Pageable pageable);

    @Query(
            value = "select * from customer",
            nativeQuery = true
    )
    List<Payment> getAllByQuery();
}
