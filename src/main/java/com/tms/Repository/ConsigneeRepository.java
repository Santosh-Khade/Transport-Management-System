package com.tms.Repository;



import com.tms.Entity.Consignee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ConsigneeRepository extends JpaRepository<Consignee, Long>, JpaSpecificationExecutor<Consignee> {
    List<Consignee> findAllByNameIgnoreCase(String name);
    Optional<Consignee> findByGstNumber(String gstNumber);
}