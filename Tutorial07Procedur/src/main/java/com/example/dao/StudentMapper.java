package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Many;

import com.example.model.CourseModel;
import com.example.model.StudentModel;

@Mapper
public interface StudentMapper
{
	@Select("select npm, name, gpa from student where npm = #{npm}") //utk ngedapetin detail student beserta course yang dambil
	@Results(value = {
		@Result(property="npm", column="npm"), //Property = nama variable di model, column nama kolom di database
		@Result(property="name", column="name"),
		@Result(property="gpa", column="gpa"),
		@Result(property="courses", column="npm",  //npm adalah parameter dari selectCourses
			javaType = List.class,
			many=@Many(select="selectCourses")) // nama method yang mengembalikan tipe data yang sama dengan courses
	})
	StudentModel selectStudent (@Param("npm") String npm);
	
    @Select("select npm, name, gpa from student")
    @Results(value = {
    		@Result(property="npm", column="npm"), //Property = nama variable di model, column nama kolom di database
    		@Result(property="name", column="name"),
    		@Result(property="gpa", column="gpa"),
    		@Result(property="courses", column="npm",
    			javaType = List.class,
    			many=@Many(select="selectCourses"))
    	})
    List<StudentModel> selectAllStudents ();

    @Insert("INSERT INTO student (npm, name, gpa) VALUES (#{npm}, #{name}, #{gpa})")
    void addStudent (StudentModel student);
    
    @Delete("DELETE FROM student where npm = #{npm}")
    void deleteStudent (@Param("npm") String npm);
    
    @Update("UPDATE student SET gpa = #{gpa}, name = #{name} WHERE npm = #{npm}")
    void updateStudent (StudentModel student);
    
    @Select("select course.id_course, name, credits " +
    		"from studentcourse join course " +
    		"on studentcourse.id_course = course.id_course " +
    		"where studentcourse.npm = #{npm}")
    List<CourseModel> selectCourses (@Param("npm") String npm);
    
    
    @Select("select id_course, name, credits from course where id_course = #{id_course}")
    @Results(value = {
    		@Result(property="id_course", column="id_course"), //Property = nama variable di model, column nama kolom di database
    		@Result(property="name", column="name"),
    		@Result(property="credits", column="credits"),
    		@Result(property="students", column="id_course", //id_course adalah parameter untuk selectStudentCourse
    			javaType = List.class,
    			many=@Many(select="selectStudentCourse"))
    	})
    CourseModel selectCourse (@Param("id_course") String id_course);

    @Select("SELECT student.npm, student.name " + 
    		"from studentcourse " + 
    		"join student on student.npm = studentcourse.npm " + 
    		"where id_course = #{id_course}")
    List<StudentModel> selectStudentCourse(@Param("id_course") String id_course);
    
    @Select ("SELECT id_course, name, credits from course")
    @Results(value = {
    		@Result(property="id_course", column="id_course"), //Property = nama variable di model, column nama kolom di database
    		@Result(property="name", column="name"),
    		@Result(property="credits", column="credits"),
    		@Result(property="students", column="id_course", //id_course adalah parameter untuk selectStudentCourse
    			javaType = List.class,
    			many=@Many(select="selectStudentCourse"))
    	})
    List<CourseModel> selectAllCourses();
}
