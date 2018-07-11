package com.soupthatisthick.playground.features.log;

import com.soupthatisthick.playground.features.log.model.LogEntity;
import com.soupthatisthick.playground.features.log.model.LogEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {

	@Query("delete from LogEntity e where e.created < :cutoff")
	@Transactional
	void purgeBefore(
		@NotNull @Param("cutoff") LocalDateTime cutoff
	);

	@Query("select new com.soupthatisthick.playground.features.log.model.LogEntry(e) from LogEntity e where e.created >= :startDateInclusive and e.created < :endDateExclusive order by e.created desc")
	List<LogEntry> findBetween(
		@NotNull @Param("startDateInclusive") LocalDateTime startDateInclusive,
		@NotNull @Param("endDateExclusive") LocalDateTime endDateExclusive,
		Pageable pageable
	);

	@Query("select new com.soupthatisthick.playground.features.log.model.LogEntry(e) from LogEntity e where e.created < :endDateExclusive order by e.created desc")
	List<LogEntry> findBeforeExclusive(
		@NotNull @Param("endDateExclusive") LocalDateTime endDateExclusive,
		Pageable pageable
	);

	@Query("select new com.soupthatisthick.playground.features.log.model.LogEntry(e) from LogEntity e where e.created >= :startDateInclusive order by e.created desc")
	List<LogEntry> findAfterInclusive(
		@NotNull @Param("startDateInclusive") LocalDateTime startDateInclusive,
		Pageable pageable
	);

	@Query("select new com.soupthatisthick.playground.features.log.model.LogEntry(e) from LogEntity e order by e.created desc")
	List<LogEntry> findAllLogs(Pageable pageable);
}
