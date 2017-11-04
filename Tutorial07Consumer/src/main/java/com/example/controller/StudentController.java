package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.CourseModel;
import com.example.model.StudentModel;
import com.example.service.StudentService;

@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;


    @RequestMapping("/")
    public String index (Model model)
    {
    	model.addAttribute("title", "Index");
        return "index";
    }


    @RequestMapping("/student/add")
    public String add (Model model)
    {
    	model.addAttribute("title", "Add Student");
        return "form-add";
    }


    @RequestMapping("/student/add/submit")
    public String addSubmit (
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa,
            Model model)
    		
    {
        StudentModel student = new StudentModel (npm, name, gpa, null);
        studentDAO.addStudent (student);
        
        model.addAttribute("title", "Add Student Succeed");
        return "success-add";
    }


    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            model.addAttribute("title", "View Students by NPM");
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            model.addAttribute("title", "Not found");
            return "not-found";
        }
    }


    @RequestMapping("/student/view/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
        	model.addAttribute("title", "Detail Student");
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            model.addAttribute("title", "Not found");
            return "not-found";
        }
    }


    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);
        model.addAttribute("title", "All Students");
        return "viewall";
    }


    @RequestMapping(value = {"/student/delete", "/student/delete/{npm}"})
    public String delete (@PathVariable Optional<String> npm, Model model)
    {
        if (!npm.isPresent() || studentDAO.selectStudent (npm.get()) == null) {
        	String npm2 = npm.isPresent() ? npm.get() : "Tidak Ada";
        	model.addAttribute ("npm", npm2);
        	model.addAttribute("title", "Not found");
        	return "not-found";
        } else {
        	studentDAO.deleteStudent (npm.get());
        	model.addAttribute("title", "Delete Student");
        	return "delete";
        }
    }

    @RequestMapping("/student/update/{npm}")
    public String update(Model model, @PathVariable(value = "npm") String npm)
    {
    	StudentModel student = studentDAO.selectStudent(npm);
        if (student == null) {
        	model.addAttribute ("npm", npm);
        	model.addAttribute("title", "Not found");
        	return "not-found";
        } else {
        	model.addAttribute("student", student);
        	return "form-update";
        }
    }
    
    @RequestMapping (value = "/student/update/submit" , method = RequestMethod.POST)
    public String updateSubmit (
    		@ModelAttribute StudentModel student, Model model) 
    {
    	if (student.getNpm() != null && student.getName() != null) {
    		studentDAO.updateStudent(student);
    		model.addAttribute("title", "Update Student Succeed");
    		return "success-update";
    	} else {
    		model.addAttribute("title", "Not found");
    		return "not-found";
    	}
    }
    
//    @RequestMapping (value = "/student/update/submit" , method = RequestMethod.POST)
//    public String updateSubmit (
//    		@RequestParam ( value = "npm" , required = false ) String npm ,
//    		@RequestParam ( value = "name" , required = false ) String name ,
//    		@RequestParam ( value = "gpa" , required = false ) double gpa) 
//    {
//    	
//    	StudentModel student = new StudentModel(npm, name, gpa);
//    	studentDAO.updateStudent(student);
//    	return "success-update";
//    	
//    }
    
    @RequestMapping (value = "/course/view/{id}")
    public String course (@PathVariable (value = "id") String id_course, Model model) {
    	CourseModel course = studentDAO.selectCourse(id_course);
    	model.addAttribute("course", course);
    	model.addAttribute("title", "Detail Course");
    	return "course";    	
    }
    
    @RequestMapping("/course/viewall")
    public String viewCourse (Model model)
    {
        List<CourseModel> courses = studentDAO.selectAllCourses ();
        model.addAttribute ("courses", courses);
        model.addAttribute("title", "All Courses");
        return "viewall-course";
    }

}
