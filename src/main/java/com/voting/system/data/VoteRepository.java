package com.voting.system.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.voting.system.core.Vote;

public interface VoteRepository  extends JpaRepository<Vote, Long> {

	Collection<Vote> findByUserId(String userId);

	@Query("SELECT v FROM Vote v WHERE v.votedate = ?2 AND v.userId = ?1")
	Collection<Vote> findByUserIdAndDate(String userId, LocalDate startdate);
}