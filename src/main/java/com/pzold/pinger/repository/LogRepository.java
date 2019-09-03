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
}
