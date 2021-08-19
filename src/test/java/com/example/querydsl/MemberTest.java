package com.example.querydsl;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.Team;

@SpringBootTest
@Transactional	@Commit
public class MemberTest {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;

	@Test
	public void memberTest() {
		
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
		
		
		em.flush();
		em.clear();
		
		List<Member> result = 	em.createQuery("select m from Member m", Member.class).getResultList();
		
		for(Member m : result) {
			log.info("member : "+m);
			log.info(":::::team ::: "+m.getTeam());
		}
		
		
		
	}
}
