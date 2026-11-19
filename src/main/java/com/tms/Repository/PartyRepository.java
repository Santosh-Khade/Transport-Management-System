package com.tms.Repository;

import com.tms.Entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
public interface PartyRepository extends JpaRepository<Party, Long>, JpaSpecificationExecutor<Party> {

    Optional<Party> findByNameIgnoreCase(String name);

    List<Party> findAllByNameIgnoreCase(String name);

    Optional<Party> findByGstNumber(String gstNumber);

    boolean existsByGstNumber(String gstNumber);
}