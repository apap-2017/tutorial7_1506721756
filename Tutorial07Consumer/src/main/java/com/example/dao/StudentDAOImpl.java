package com.example.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.model.StudentModel;

@Service
public class StudentDAOImpl implements StudentDAO {

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public StudentModel selectStudent (String npm)
	{
		StudentModel student =
		restTemplate.getForObject(
		"http://localhost:8080/rest/student/view/"+npm,
		StudentModel.class);
		return student;
	}
	
	@Override
	public List<StudentModel> selectAllStudents ()
	{
		ResponseEntity<List<StudentModel>> rateResponse =
			restTemplate.exchange("http://localhost:8080/rest/student/viewall",
					HttpMethod.GET, null, new ParameterizedTypeReference<List<StudentModel>>() {
			});
	  List<StudentModel> students = rateResponse.getBody();
	  return students;
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
