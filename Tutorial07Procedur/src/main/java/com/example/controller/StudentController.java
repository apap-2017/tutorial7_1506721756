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
    public String index ()
    {
        return "index";
    }


    @RequestMapping("/student/add")
    public String add ()
    {
        return "form-add";
    }


    @RequestMapping("/student/add/submit")
    public String addSubmit (
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa)
    {
        StudentModel student = new StudentModel (npm, name, gpa, null);
        studentDAO.addStudent (student);

        return "success-add";
    }


    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/view/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);

        return "viewall";
    }


    @RequestMapping(value = {"/student/delete", "/student/delete/{npm}"})
    public String delete (@PathVariable Optional<String> npm, Model model)
    {
        if (!npm.isPresent() || studentDAO.selectStudent (npm.get()) == null) {
        	String npm2 = npm.isPresent() ? npm.get() : "Tidak Ada";
        	model.addAttribute ("npm", npm2);
        	return "not-found";
        } else {
        	studentDAO.deleteStudent (npm.get());
        	return "delete";
        }
    }

    @RequestMapping("/student/update/{npm}")
    public String update(Model model, @PathVariable(value = "npm") String npm)
    {
    	StudentModel student = studentDAO.selectStudent(npm);
        if (student == null) {
        	model.addAttribute ("npm", npm);
        	return "not-found";
        } else {
        	model.addAttribute("student", student);
        	return "form-update";
        }
    }
    
    @RequestMapping (value = "/student/update/submit" , method = RequestMethod.POST)
    public String updateSubmit (
    		@ModelAttribute StudentModel student) 
    {
    	if (student.getNpm() != null && student.getName() != null) {
    		studentDAO.updateStudent(student);
    		return "success-update";
    	} else {
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
    	return "course";
    }
}
