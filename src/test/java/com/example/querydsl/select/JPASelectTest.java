package com.example.querydsl.select;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.querydsl.dto.MemberDTO;
import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.QMember;
import com.example.querydsl.entity.Team;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import antlr.StringUtils;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class JPASelectTest {
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
	
	//dto로 값을 return 하기위한 jpql 방식 dto의 경로를 정확히 적어주는게 귀찮음
	@Test
	public void jpqlSelect() {
		List<MemberDTO> result = em.createQuery(
									"select new com.example.querydsl.dto.MemberDTO(m.userName, m.age) from Member m"
								,MemberDTO.class).getResultList();
		
		for(MemberDTO m : result)
			log.info("result ::: "+ m);
	}
	
	
	
	//querydsl의 setter 방식 기본생성자 getter setter 필요 mybatis처럼 getter를 사용해서 받아오는듯
	@Test
	public void querydslSetter() {
		QMember member = QMember.member;
		query = new JPAQueryFactory(em);
		
		List<MemberDTO> result = query.select(Projections.bean(
									MemberDTO.class
									, member.userName, member.age
									))
								.from(member)
								.fetch();
		
		for(MemberDTO m : result)
			log.info("result ::: "+ m);
	}
	
	
	//querydsl의 field방식 getter setter 필요 x 이름을 기준으로 감   private으로 지정된 캡슐화객체도 바꿀수있음 가장좋은방법
	//필드명이 무조건 같아야만 성공적으로 조회가능 타입이 같은 다른필드명의 dto 불가 -> but .as()메소드로 변경가능 ex) member.userName.as("name")
	
	@Test
	public void querydslFields() {
		QMember member = QMember.member;
		query = new JPAQueryFactory(em);
		
		List<MemberDTO> result = query.select(Projections.fields(
									MemberDTO.class
									, member.userName, member.age
									))
								.from(member)
								.fetch();
		
		for(MemberDTO m : result)
			log.info("result ::: "+ m);
	}
	
	
	//querydsl의 생성자 방식의 select 기본생성자의 폼을 지켜 사용해야함 type을 따라 매핑 기본 생성자가 있어야만 가능
	@Test
	public void querydslConstructor() {
		QMember member = QMember.member;
		query = new JPAQueryFactory(em);
		
		List<MemberDTO> result = query.select(Projections.constructor(
									MemberDTO.class
									, member.userName, member.age
									))
								.from(member)
								.fetch();
		
		for(MemberDTO m : result)
			log.info("result ::: "+ m);
	}
	
	
}
