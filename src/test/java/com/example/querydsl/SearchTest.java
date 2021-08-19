package com.example.querydsl;

import static com.example.querydsl.entity.QMember.member;

import java.util.List;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.Team;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

@SpringBootTest
@Transactional
public class SearchTest {
	
	
	@Autowired
	EntityManager em;
	
	private JPAQueryFactory query;
	
	
	@BeforeEach
	public void setData() {
		
		Team teamA = new Team("A");
		Team teamB = new Team("B");
		
		em.persist(teamA);
		em.persist(teamB);
		
		
		Member member1 = new Member("승훈", 23, teamA);
		Member member2 = new Member("지덕", 21, teamA);
		Member member3 = new Member("지라", 20, teamA);
		Member member4 = new Member("모바", 24, teamB);
		Member member5 = new Member("후니", 25, teamB);
		Member member6 = new Member("루이", 26, teamB);
		
		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
		em.persist(member4);
		em.persist(member5);
		em.persist(member6);
		
		
	}
	
	
	@Test
	public void select() {
		query = new JPAQueryFactory(em);
		
		Member result = query.select(member).from(member).where(member.userName.eq("승훈").and(member.age.eq(23))).fetchOne();
		
		Assertions.assertThat(result.getUserName()).isEqualTo("승훈");
		
	}
	
	
	
	@Test
	public void listSelect() {//list 조회
		query = new JPAQueryFactory(em);
		
		List<Member> list = query.select(member)
								.from(member)
								.fetch();
	}
	
	@Test
	public void queryResult() {//페이징을 위해 지원되는 QueryResult 클래스가 존재하지만 특정 문제 등에서는 fetchCount()와 select를 나눠서 2개의 쿼리로 진행
		query = new JPAQueryFactory(em);
		
		QueryResults<Member> queryResult = query.select(member)
												.from(member)
												.fetchResults();
		queryResult.getTotal();//페징을 위한 count sql을 실행
		
		List<Member> content = queryResult.getResults();//투플을 다 get
		
	}
}

