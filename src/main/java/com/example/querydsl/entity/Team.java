package com.example.querydsl.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(of = {"id", "name"})
@Entity	@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {
	
	@Id @GeneratedValue	@Column(name = "team_id")
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "team")
	private List<Member> members = new ArrayList<Member>();
	
	
	public Team(String name) {
		this.name = name;
	}
	
	
	
	
	
}
