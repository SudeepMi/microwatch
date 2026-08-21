package com.microwatch.monitoring.repository;

import com.microwatch.monitoring.model.ServiceEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceEventRepository extends JpaRepository<ServiceEvent, Long> {
    List<ServiceEvent> findByServiceIdOrderByCreatedAtDesc(Long serviceId);
    List<ServiceEvent> findTop10ByOrderByCreatedAtDesc();
}
