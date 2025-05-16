package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "admins")
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	 @Column(nullable = false, unique = true)
	 private String email;
	 
	 @Column(nullable = false)
	 private String password;
	 
	 @Column(name = "current_sign_in_at")
	 private LocalDateTime currentSignInAt;
	 
	 @Column(name = "created_at", updatable = false)
	 private LocalDateTime createdAt;
	 
	 @Column(name = "updated_at")
	 private LocalDateTime updatedAt;
	 
	 @PrePersist
	 public void onPrePersist() {
		 this.createdAt = LocalDateTime.now();
		 this.updatedAt = LocalDateTime.now();
	 }
	 
	 @PreUpdate
	 public void onPreUpdate() {
		 this.updatedAt = LocalDateTime.now();
	 }
}
