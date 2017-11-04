package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.dao.CourseDAO;
import com.example.dao.StudentDAO;
import com.example.model.CourseModel;
import com.example.model.StudentModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Primary
public class StudentServiceRest implements StudentService {

	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private CourseDAO courseDAO;
	
	@Override
	public StudentModel selectStudent(String npm) {
		log.info ("REST - select student with npm {}", npm);
		return studentDAO.selectStudent (npm);
	}

	@Override
	public List<StudentModel> selectAllStudents() {
		log.info ("REST - select all students");
		return studentDAO.selectAllStudents();
	}

	@Override
	public void addStudent(StudentModel student) {}

	@Override
	public void deleteStudent(String npm) {}

	@Override
	public void updateStudent(StudentModel student) {}

	@Override
	public CourseModel selectCourse(String id_course) {
		log.info ("REST - select course with id_course {}", id_course);
		return courseDAO.selectCourse(id_course) ;
	}

	@Override
	public List<CourseModel> selectAllCourses() {
		return courseDAO.selectAllCourses();
	}

}
