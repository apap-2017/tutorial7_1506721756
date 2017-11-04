package com.example.dao;

import java.util.List;

import com.example.model.CourseModel;

public interface CourseDAO {

	CourseModel selectCourse (String id_course);
	List<CourseModel> selectAllCourses ();

	
}
