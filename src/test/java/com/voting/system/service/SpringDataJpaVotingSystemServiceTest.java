package com.voting.system.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.voting.system.config.VotingsystemApplication;

@ContextConfiguration(classes = VotingsystemApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("spring-data-jpa")
public class SpringDataJpaVotingSystemServiceTest extends AbstractVotingSystemServiceTest {

}
