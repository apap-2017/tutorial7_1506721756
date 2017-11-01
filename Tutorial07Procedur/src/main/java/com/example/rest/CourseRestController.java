package com.example.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.CourseModel;
import com.example.model.StudentModel;
import com.example.service.StudentService;

@RestController
@RequestMapping("/rest")
public class CourseRestController {

	
	@Autowired
	StudentService studentService;
	
	@RequestMapping("/course/view/{npm}")
	public CourseModel view (@PathVariable(value = "npm") String npm) {
		CourseModel course  = studentService.selectCourse (npm);
		return course;
	}
	
	@RequestMapping("/course/viewall")
    public List<CourseModel> view (Model model)
    {
        List<CourseModel> courses = studentService.selectAllCourses ();
        return courses;
    }
}
