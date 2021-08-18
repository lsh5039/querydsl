package com.example.querydsl.qfileTest;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

@SpringBootTest
@Transactional
public class ItemTest {
	
	@Autowired
	EntityManager em;
	
	@Test
	public void itemTest() {
		Item item = new Item();
		
		em.persist(item);
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		QItem qItem = new QItem("h");
		
		Item resultItem = query.selectFrom(qItem).fetchOne();
		
		
		Assertions.assertThat(resultItem).isEqualTo(item);
		
		
		
	}
}
