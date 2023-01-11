package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob
	private String content; // 섬머노트 라이브러리
	
	private int count; // 조회
	
	@ManyToOne(fetch = FetchType.EAGER) // EAGER - 기본 전략 무조건들고와, LAZY - 안필요할 수 도있으니 안들고와도됨 
	@JoinColumn(name = "userId")
	private User user;
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // 연관 관계의 주인이 아니다 DB에 컬럼을 만들지마. 
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
}
