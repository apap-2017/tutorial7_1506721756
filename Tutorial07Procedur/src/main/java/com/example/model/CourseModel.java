package com.example.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseModel 
{
	@NotEmpty
	private String id_course;
	
	@NotEmpty
	private String name;
	
	@NotNull
	private double credits;

	private List<StudentModel> students;
}
