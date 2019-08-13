package com.pzold.pinger.repository;

import com.pzold.pinger.model.LogMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogMessage, Integer> {
}
