package com.example.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.model.CourseModel;

@Service
public class CourseDAOImpl implements CourseDAO {

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public CourseModel selectCourse(String id_course) {
		CourseModel course =
				restTemplate.getForObject(
				"http://localhost:8080/rest/course/view/"+ id_course,
		CourseModel.class);
		return course;
	}

	@Override
	public List<CourseModel> selectAllCourses() {
		ResponseEntity<List<CourseModel>> courseResponse =
			restTemplate.exchange("http://localhost:8080/rest/course/viewall",
			HttpMethod.GET, null, new ParameterizedTypeReference<List<CourseModel>>() {
			});
		List<CourseModel> courses = courseResponse.getBody();
		return courses;
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
