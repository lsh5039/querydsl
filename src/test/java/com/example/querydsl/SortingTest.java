package com.example.querydsl;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.QMember;
import com.example.querydsl.entity.QTeam;
import com.example.querydsl.entity.Team;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
@Transactional
@Commit
public class SortingTest {

	@Autowired
	private EntityManager em;
	
	private JPAQueryFactory query;
	
	
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
	
	
	// 나이 내림차순
	// 이름 오름차순 and 이름이 없으면 맨 마지막
	@Test
	public void sort() {
		query = new JPAQueryFactory(em);
		QMember member = QMember.member;

		List<Member> result = query.select(member).from(member)
				.orderBy(member.age.desc(), member.userName.asc().nullsLast()).fetch();

		
		for(Member m : result) {
			log.info("result ::: " + m);
		}
		
		
		
		
	}
	
	
	@Test
	public void aggregationTest() {
		query = new JPAQueryFactory(em);
		QMember member = QMember.member;
		
		Tuple tuple	 =	query.select(
								member.count(),
								member.age.avg(),
								member.age.max()
								)
						.from(member)
						.where(member.age.isNotNull())
						.fetchOne();
		
		
		log.info("tuple ::::: "+ tuple);
		
	}
	
	/**
	 * 팀이름, 각팀의 평균나이 select
	 */
//	SELECT 
//		a.name
//		, avg(b.age)
//	from team as a
//	INNER JOIN member as b
//	ON a.team_id = b.team_id
//	GROUP BY a.name
	
	@Test
	public void selectTest() {
		query = new JPAQueryFactory(em); 
		QMember member = QMember.member;
		QTeam team = QTeam.team;
		
		List<Tuple> result = query.select(team.name, member.age.avg())
									.from(member)
									
									.innerJoin(team)
									.where(member.team.id.eq(team.id))
									//.on(member.team.id.eq(team.id))//inner join은 on절과 where절이 동일
									.groupBy(team.name)
									.fetch();
		
		for(Tuple t : result) {
			log.info("size ::: " + t.toString());
		}
			
		
		
		
		
	}
	
	
}
