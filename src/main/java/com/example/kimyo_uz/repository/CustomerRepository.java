package com.example.kimyo_uz.repository;

import com.example.kimyo_uz.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByIdAndDeletedAtIsNull(Long id);

    Page<Customer> findAllByDeletedAtIsNull(Pageable pageable);

    @Query(
            value = "select * from customer",
            nativeQuery = true
    )
    List<Customer> getAllByQuery();
}
