package com.tms.Repository;

import com.tms.Entity.DeliveryMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeliveryMemoRepository extends JpaRepository<DeliveryMemo, Long>, JpaSpecificationExecutor<DeliveryMemo> {
}