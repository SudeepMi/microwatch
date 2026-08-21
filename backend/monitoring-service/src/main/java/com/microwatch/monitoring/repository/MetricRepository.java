package com.microwatch.monitoring.repository;

import com.microwatch.monitoring.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {
    List<Metric> findByServiceIdOrderByCheckedAtDesc(Long serviceId);
    
    List<Metric> findByServiceIdAndCheckedAtAfterOrderByCheckedAtDesc(Long serviceId, LocalDateTime since);

    @Query(value = "SELECT DISTINCT ON (service_id) * FROM metrics ORDER BY service_id, checked_at DESC", nativeQuery = true)
    List<Metric> findLatestMetricsPerService();
}
