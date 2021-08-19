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
import com.example.querydsl.entity.QMember;
import com.example.querydsl.entity.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;



@SpringBootTest
@Transactional
public class QuerydslTest {
	
	@Autowired
	EntityManager em;
	
	JPAQueryFactory query;	//필드로 빼서 사용해도 멀티쓰레드 환경에서 문제없이 작동하게 설계되어있음.
	
	
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
	public void queryDslTest() {
		//승훈 find 동적 쿼리
		query = new JPAQueryFactory(em);
		QMember m = new QMember("m");
		
		Member result = query.select(m)
						.from(m)
						.where(m.userName.eq("승훈")).fetchOne();
		
		Assertions.assertThat(result.getUserName()).isEqualTo("승훈");
				
		
		query = new JPAQueryFactory(em);
		
		Member m2 = query.select(member).from(member).where(member.userName.eq("승훈")).fetchOne();	//Q class 를 static import 하여 코드를 줄인 형태
		
		Assertions.assertThat(m2.getUserName()).isEqualTo("승훈");
		
	}
	
	
	
	
}
