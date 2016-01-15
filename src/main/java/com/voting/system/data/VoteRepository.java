package com.voting.system.data;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.voting.system.core.Vote;

public interface VoteRepository  extends JpaRepository<Vote, Long> {

	Collection<Vote> findByUserId(String userId);

	@Query("SELECT v FROM Vote v WHERE timestamp >= CURDATE() AND v.userId = ?1")
	Vote findByUserIdAndToday(String userId);
}