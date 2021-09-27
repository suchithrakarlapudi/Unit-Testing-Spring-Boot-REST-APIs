package com.application.demo;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import javassist.NotFoundException;

@WebMvcTest(PatientRecordController.class)
public class PatientRecordControllerTest {
	
	@Autowired
    MockMvc mockMvc;
   
	@Autowired
    ObjectMapper mapper;
    
    @MockBean
    PatientRecordRepository patientRecordRepository;
    
	
	  PatientRecordModel RECORD_1 = new PatientRecordModel(19l,"tarak", 33,"hyderabad");
	  PatientRecordModel RECORD_2 = new PatientRecordModel(20l,"Bhanu", 25, "Vijayawada");
	  PatientRecordModel RECORD_3 = new	PatientRecordModel(21l,"Rohini", 18, "Guntur");
	  
	  
	  @Test 
	  public void getAllRecords_success() throws Exception 
	  {
		  List<PatientRecordModel> records = new ArrayList<>(Arrays.asList(RECORD_1,
		  RECORD_2, RECORD_3));
		  
		  Mockito.when(patientRecordRepository.findAll()).thenReturn(records);
		  
		  mockMvc.perform(MockMvcRequestBuilders 
				  .get("/patient/alldetails")
				  .contentType(MediaType.APPLICATION_JSON)) 
		  		  .andExpect(status().isOk())
		  		  .andExpect(jsonPath("$", hasSize(3))) 
		  		  .andExpect(jsonPath("$[2].name",is("Rohini"))); 
	  }
	  
	  
	  @Test 
	  public void getPatientById_success() throws Exception 
	  {
		  Mockito.when(patientRecordRepository.findById(RECORD_1.getPatientId())).
		  thenReturn(java.util.Optional.of(RECORD_1));
		  
		  mockMvc.perform(MockMvcRequestBuilders 
				  .get("/patient/particularpatient/19")
				  .contentType(MediaType.APPLICATION_JSON)) 
		  		  .andExpect(status().isOk())
		  		  .andExpect(jsonPath("$", notNullValue())) 
		  		  .andExpect(jsonPath("$.name",is("tarak"))); }
	 
	
	  @Test 
	  public void createRecord_success() throws Exception 
	  {
			/*
			 * PatientRecordModel record = PatientRecordModel.builder() .name("John Doe")
			 * .age(47) .address("New York USA") .build();
			 * 
			 * Mockito.when(patientRecordRepository.save(record)).thenReturn(record);
			 * 
			 *   MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				  .post("/patient/savepatient")
				  .contentType(MediaType.APPLICATION_JSON) 
				  .accept(MediaType.APPLICATION_JSON)
				  .content(this.mapper.writeValueAsString(record));
				  
				    mockMvc.perform(mockRequest) 
		  .andExpect(status().isOk())
		  .andExpect(jsonPath("$", notNullValue())) 
		  .andExpect(jsonPath("$.name",is("John Doe"))); 
			 */
		  
		  PatientRecordModel record = new PatientRecordModel(22l,"ABC",46,"AP");
		  Mockito.when(patientRecordRepository.save(record)).thenReturn(record);
		  
		  mockMvc.perform(MockMvcRequestBuilders.post("/patient/savepatient")
	                .content(this.mapper.writeValueAsString(record))
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());
	               // .andExpect(content().contentType("application/json;charset=UTF-8"));
		
	}
	  
	// for update
	  
	
	  @Test 
	  public void updatePatientRecord_success() throws Exception 
	  {
			/*
			 * PatientRecordModel updatedRecord = PatientRecordModel.builder()
			 * .patientId(1l) .name("Rayven Zambo") .age(23) .address("Cebu Philippines")
			 * .build();
			 */
		  PatientRecordModel updatedRecord = new PatientRecordModel(19l,"ABC",50,"hyderabad");
		  
		  Mockito.when(patientRecordRepository.findById(RECORD_1.getPatientId())).
		  thenReturn(Optional.of(RECORD_1));
		  Mockito.when(patientRecordRepository.save(updatedRecord)).thenReturn(
		  updatedRecord);
		  
		  MockHttpServletRequestBuilder mockRequest =
		  MockMvcRequestBuilders.post("/patient/editpatient/19")
		  .contentType(MediaType.APPLICATION_JSON) .accept(MediaType.APPLICATION_JSON)
		  .content(this.mapper.writeValueAsString(updatedRecord));
		  
		  mockMvc.perform(mockRequest) 
		  .andExpect(status().isOk())
		  .andExpect(jsonPath("$", notNullValue())) 
		  .andExpect(jsonPath("$.name",is("ABC")));
	 }
	 
	   
    
	  @Test 
	  public void updatePatientRecord_nullId() throws Exception 
	  {
			/*
			 * PatientRecordModel updatedRecord = PatientRecordModel.builder()
			 * .name("Sherlock Holmes") .age(40) .address("221B Baker Street") .build();
			 */
		  
		  PatientRecordModel updatedRecord = new PatientRecordModel(19l,"ABC",50,"hyderabad");
		  
		  MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				  .post("/patient/editpatient/19")
				  .contentType(MediaType.APPLICATION_JSON) 
				  .accept(MediaType.APPLICATION_JSON)
				  .content(this.mapper.writeValueAsString(updatedRecord));
		  
		  mockMvc.perform(mockRequest) 
		  		 .andExpect(status().isBadRequest())
		  		 .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException)) 
		  		 .andExpect(result -> assertEquals("PatientRecord or ID must not be null!",result.getResolvedException().getMessage())); 
		
	  }
	  
	  @Test 
	  public void updatePatientRecord_recordNotFound() throws Exception 
	  {
			/*
			 * PatientRecordModel updatedRecord = PatientRecordModel.builder()
			 * .patientId(5l) .name("Sherlock Holmes") .age(40)
			 * .address("221B Baker Street") .build();
			 */
		  
		  PatientRecordModel updatedRecord=new PatientRecordModel(19l,"ABC",50,"hyderabad");
		  Mockito.when(patientRecordRepository.findById(updatedRecord.getPatientId())).
		  thenReturn(null);
		  
		  MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				  .post("/patient/editpatient/19")
				  .contentType(MediaType.APPLICATION_JSON) 
				  .accept(MediaType.APPLICATION_JSON)
				  .content(this.mapper.writeValueAsString(updatedRecord));
		  
		  mockMvc.perform(mockRequest) 
		  .andExpect(status().isBadRequest())
		  .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException)) 
		  .andExpect(result -> assertEquals("Patient with ID 19 does not exist.",result.getResolvedException().getMessage())); 
		  
	  }
	  
	 
    
    // for delete
    
	
	  @Test 
	  public void deletePatientById_success() throws Exception 
	  {
		  Mockito.when(patientRecordRepository.findById(RECORD_2.getPatientId())).
		  thenReturn(Optional.of(RECORD_2));
		  
		  mockMvc.perform(MockMvcRequestBuilders 
				  .delete("/patient/deletepatient/20")
				  .contentType(MediaType.APPLICATION_JSON)) 
		  		  .andExpect(status().isOk()); 
	  }
		  
	  @Test 
	  public void deletePatientById_notFound() throws Exception 
	  {
		  Mockito.when(patientRecordRepository.findById(20l)).thenReturn(null);
		  
		  mockMvc.perform(MockMvcRequestBuilders 
				  .delete("/patient/deletepatient/20")
				  .contentType(MediaType.APPLICATION_JSON)) 
		  		  .andExpect(status().isBadRequest())
		  		  .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException)) 
		  		  .andExpect(result -> assertEquals("Patient with ID 20 does not exist.",result.getResolvedException().getMessage())); 
	 }
	 
}
