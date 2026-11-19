package com.tms.Repository;



import com.tms.Entity.Consigner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ConsignerRepository extends JpaRepository<Consigner, Long>, JpaSpecificationExecutor<Consigner> {
    List<Consigner> findAllByNameIgnoreCase(String name);
    Optional<Consigner> findByGstNumber(String gstNumber);
}
