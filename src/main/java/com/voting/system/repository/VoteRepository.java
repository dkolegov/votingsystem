
package com.voting.system.repository;

import java.time.LocalDate;
import java.util.Collection;

import com.voting.system.core.Vote;

public interface VoteRepository {

	Collection<Vote> findByUserId(String userId);

	Collection<Vote> findByUserIdAndDate(String userId, LocalDate startdate);
}