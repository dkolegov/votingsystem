package com.voting.system.repository.springdatajpa;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.voting.system.core.Vote;
import com.voting.system.repository.VoteRepository;

public interface VoteRepositoryImpl  extends VoteRepository, JpaRepository<Vote, Integer> {

	@Override
	Collection<Vote> findByUserId(String userId);

	@Override
	@Query("SELECT v FROM Vote v WHERE v.votedate = ?2 AND v.userId = ?1")
	Collection<Vote> findByUserIdAndDate(String userId, LocalDate startdate);
}