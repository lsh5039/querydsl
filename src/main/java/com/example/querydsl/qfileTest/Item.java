package com.example.querydsl.qfileTest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Item {//QClass 파일 생성 테스트를 위한 entity
	
	
	@Id @GeneratedValue
	private Long id;
	
	private int age;
}
