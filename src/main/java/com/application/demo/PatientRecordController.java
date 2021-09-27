package com.application.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="/patient")
public class PatientRecordController {

	@Autowired
	PatientRecordRepository patientRecordRepository;
	
	
	@GetMapping("/alldetails")
	public List<PatientRecordModel> getAllRecords() {
	    return patientRecordRepository.findAll();
	}

	@GetMapping("/particularpatient/{patientId}")
	public PatientRecordModel getPatientById(@PathVariable(value="patientId") Long patientId) {
	    return patientRecordRepository.findById(patientId).get();
	}
	
	@PostMapping("/savepatient")
	public PatientRecordModel createRecord(@RequestBody PatientRecordModel patientRecordModel) {
	    return patientRecordRepository.save(patientRecordModel);
	}
	
	
	@PutMapping("/editpatient/{patientId}")
	public PatientRecordModel updatePatientRecord(@RequestBody PatientRecordModel patientRecord, @PathVariable(value="patientId") Long patientId) throws NotFoundException {
		/*
		 * if (patientRecord == null || patientRecord.getPatientId() == null) { throw
		 * new NotFoundException("PatientRecord or ID must not be null!"); }
		 */
	    Optional<PatientRecordModel> optionalRecord = patientRecordRepository.findById(patientId);
	    if (optionalRecord.isEmpty()) {
	        throw new NotFoundException("Patient with ID " + patientRecord.getPatientId() + " does not exist.");
	    }
	    PatientRecordModel existingPatientRecord = optionalRecord.get();

	    existingPatientRecord.setName(patientRecord.getName());
	    existingPatientRecord.setAge(patientRecord.getAge());
	    existingPatientRecord.setAddress(patientRecord.getAddress());
		
	    return patientRecordRepository.save(existingPatientRecord);
	}
	
	@DeleteMapping("/deletepatient/{patientId}")
	public void deletePatientById(@PathVariable(value = "patientId") Long patientId) throws NotFoundException {
	    if (patientRecordRepository.findById(patientId).isEmpty()) {
	        throw new NotFoundException("Patient with ID " + patientId + " does not exist.");
	    }
	    patientRecordRepository.deleteById(patientId);
	}
}
