package com.Advance.AdvanceTask.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Size(min = 3, message = "Name must be great then {min} ")
	@Column(name = "name", nullable = false)
	private String name;

	@NotBlank(message = "Enter You Email")
	@Column(name = "email")
	private String email;

	@Size(min = 6, message = "Password must be great then 5 ")
	@Column(name = "password")
	private String password;

	@NotBlank(message = "Enter the Gender")
	@Column(name = "gender")
	private String gender;

//	@Size(min = 10, max = 10, message = "Phone Number Exactly {max} digit long")
//	@Column(name = "mobile")
//	private long mobile;

	@NotNull(message = "Enter a valid phone number")
	@Min(value = 1000000000L, message = "Phone number must be at least {value}")
	@Max(value = 9999999999L, message = "Phone number must not exceed {value}")
	@Column(name = "mobile")
	private Long mobile;

	@Lob
	@Column(name = "image")
	private byte[] imageData;

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

//	@Override
//	public String toString() {
//		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
//				+ gender + ", Mobile=" + Mobile + "]";
//	}
//
//	public Employee(int id, String name, String email, String password, String gender, String mobile) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.email = email;
//		this.password = password;
//		this.gender = gender;
//		Mobile = mobile;
//	}

	public Employee() {
		super();
	}

}
