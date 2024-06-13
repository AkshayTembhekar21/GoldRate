package com.gold.goldrate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gold.goldrate.entity.GoldRate;

@Repository
public interface GoldRateRepository extends JpaRepository<GoldRate, Long> {
	
	GoldRate findByTimestamp(Long timestamp);
}
