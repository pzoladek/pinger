package com.pzold.pinger.repository;

import com.pzold.pinger.model.LogMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogMessage, Integer> {
    List<LogMessage> findTop15ByOrderByIdDesc();

    @Query(value = "DELETE FROM logs l WHERE l.id NOT IN (SELECT l.id FROM logs l ORDER BY l.id ASC LIMIT 5)", nativeQuery = true)
    void deleteLatest();
}
