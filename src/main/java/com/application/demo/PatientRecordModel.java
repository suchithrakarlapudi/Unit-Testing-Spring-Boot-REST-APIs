package com.application.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "patient_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientRecordModel {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long patientId;
    
    @NonNull
    private String name;
 
    @NonNull
    private Integer age;
    
    @NonNull 
    private String address;

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public PatientRecordModel()
	{
		
	}
	
	public PatientRecordModel(Long patientId, @NonNull String name, @NonNull Integer age, @NonNull String address) {
		super();
		this.patientId = patientId;
		this.name = name;
		this.age = age;
		this.address = address;
	}

}
