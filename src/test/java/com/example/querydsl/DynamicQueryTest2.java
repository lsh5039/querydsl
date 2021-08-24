package com.example.querydsl;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.QMember;
import com.example.querydsl.entity.Team;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.extern.slf4j.Slf4j;

@Transactional
@SpringBootTest
@Slf4j
public class DynamicQueryTest2 {
	
	
	@Autowired
	private EntityManager em;
	
	private JPAQueryFactory query;
	
	QMember member = QMember.member;
	

	@BeforeEach
	public void dataSet() {
		
		Team a = new Team("A");
		Team b = new Team("B");
		
		em.persist(a);
		em.persist(b);
		
		Member m1 = new Member("승훈", 24, a);
		Member m2 = new Member("나리", 14, a);
		Member m3 = new Member(null, 15, a);
		
		Member m4 = new Member("우진", 27, b);
		Member m5 = new Member("그루", 24, b);
		em.persist(m1);
		em.persist(m2);
		em.persist(m3);
		em.persist(m4);
		em.persist(m5);
		
	}
	
	
	@Test
	public void dynamicQueryOfBooleanBuilder() {
		String userName = "";
		Integer age = 24;
		
		List<Member> result = searchMember(userName, age);
		
		for(Member m : result) {
			log.info("member ::: "+ m);
		}
		
		
		
	}
	
	
	public List<Member> searchMember(String userName, Integer age) {
		
		query = new JPAQueryFactory(em);
		
	return query.selectFrom(member)
				.where(
						userNameEqFn(userName)
						,ageEqFn(age)
						).fetch();
	}
	
	public BooleanExpression userNameEqFn(String userName) {
		if(!StringUtils.isEmpty(userName))
			return member.userName.eq(userName);
		else
			return null;
	}
	
	public BooleanExpression ageEqFn(Integer age) {
		if(age != null)
			return member.age.eq(age);
		else
			return null;
		
	}
	
	
	
	
}
