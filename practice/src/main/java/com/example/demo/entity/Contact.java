package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Entity
@Data
@Table(name = "contacts")
public class Contact {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@NotBlank(message = "姓は必須です")
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@NotBlank(message = "名は必須です")
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@NotBlank(message = "メールアドレスは必須です")
	@Email(message = "メールアドレスの形式で入力してください")
	@Column(name = "email", nullable = false)
	private String email;
	
	@NotBlank(message = "電話番号は必須です")
	@Pattern(regexp = "\\d{10,11}", message = "電話番号は10〜11桁の数字で入力してください")
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@NotBlank(message = "郵便番号は必須です")
	@Column(name = "zip_code", nullable = false)
	private String zipCode;
	
	@NotBlank(message = "住所は必須です")
	@Column(name = "address", nullable = false)
	private String address;
	
	@NotBlank(message = "建物名は必須です")
	@Column(name = "building_name", nullable = false)
	private String buildingName;
	
	@NotBlank(message = "お問い合わせ種別は必須です")
	@Column(name = "contact_type", nullable = false)
	private String contactType;
	
	@NotBlank(message = "内容は必須です")
	@Column(name = "body", nullable = false)
	private String body;
	
	@Column(name = "created_at")
	private java.time.LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private java.time.LocalDateTime updatedAt;
	
	@jakarta.persistence.PrePersist
	public void onPrePersist() {
		this.createdAt = java.time.LocalDateTime.now();
		this.updatedAt = java.time.LocalDateTime.now();
	}
	
	@jakarta.persistence.PreUpdate
	public void onPreUpdate() {
		this.updatedAt = java.time.LocalDateTime.now();
	}
}
