package com.tms.Repository;



import com.tms.Entity.LrSequence;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LrSequenceRepository extends JpaRepository<LrSequence, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM LrSequence s WHERE s.seqKey = :seqKey")
    Optional<LrSequence> findByIdForUpdate(@Param("seqKey") String seqKey);
}
