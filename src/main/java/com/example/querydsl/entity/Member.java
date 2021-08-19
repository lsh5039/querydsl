package com.example.querydsl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString(of = {"id", "userName", "age"})
@Entity	@Getter	@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id @GeneratedValue	@Column(name = "member_id")
	private Long id;
	
	private String userName;
	
	private int age;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;
	
	
	public Member(String userName, int age, Team team) {
		this.userName = userName;
		this.age = age;
		
		if(team != null) {
			chageTeam(team);
		}
	}
	
	public Member(String userName, int age) {
		new Member(userName, age, null);
	}
	
	public Member(String userName) {
		new Member(userName, 0);
	}
	
	
	
	public void chageTeam(Team team) {
		this.team = team;
		team.getMembers().add(this);
	}
	
	
}
