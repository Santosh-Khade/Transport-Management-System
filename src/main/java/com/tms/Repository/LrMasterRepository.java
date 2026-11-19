package com.tms.Repository;

import com.tms.Entity.LrMaster;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface LrMasterRepository extends JpaRepository<LrMaster, Long>, JpaSpecificationExecutor<LrMaster> {

    Optional<LrMaster> findByLrNumber(String lrNumber);

    boolean existsByLrNumber(String lrNumber);

    @EntityGraph(attributePaths = {"consigner", "consignee", "deliveryMemo"})
    Optional<LrMaster> findWithDetailsById(Long id);

    @Override
    @EntityGraph(attributePaths = {"consigner", "consignee", "deliveryMemo"})
    Page<LrMaster> findAll(@Nullable Specification<LrMaster> spec, Pageable pageable);
}