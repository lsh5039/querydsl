package com.example.querydsl;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.Team;

@SpringBootTest
@Transactional
public class JPQLTest {
	
	@Autowired
	EntityManager em;
	
	
	@BeforeEach
	public void before() {
		Team team1 = new Team("A-TEAM");
		Team team2 = new Team("B-TEAM");
		
		em.persist(team1);
		em.persist(team2);
		
		
		Member member1 = new Member("승훈", 22, team1);
		Member member2 = new Member("나리", 21, team1);
		Member member3 = new Member("수리", 24, team1);
		
		Member member4 = new Member("재훈", 20, team2);
		Member member5 = new Member("후니", 23, team2);
		Member member6 = new Member("짱구", 21, team2);
		
		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
		em.persist(member4);
		em.persist(member5);
		em.persist(member6);
	}
	
	
	@Test
	public void jpqlTest() {
		
		
		//승훈 find 동적 쿼리
		String paramName = "승훈";
		Member result = em.createQuery(
						" select m from Member m "
						+" where m.userName = :userName "
				, Member.class).setParameter("userName", paramName).getSingleResult();
		
		
		
		Assertions.assertThat(result.getUserName()).isEqualTo("승훈");
		
	}
	
	
	
	
}
