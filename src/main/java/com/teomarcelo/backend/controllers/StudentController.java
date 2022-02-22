package com.teomarcelo.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teomarcelo.backend.exceptions.ResourceNotFoundException;
import com.teomarcelo.backend.models.Student;
import com.teomarcelo.backend.repositories.StudentRepository;


// ===== RESTCONTROLLER =====
// RestController is a Spring annotation that is used to build REST API. This will tell Spring to do it's configurations and allows us to make RESTful web service at runtime.

// SIDE NOTE: You may see some older code or some code using @Controller typically with @ResponseBody. @Controller has been used by traditional Spring MVC for a long time. @RestController was introduced in later versions of Spring which simplifies creating RESTful web services. @RestController is a combination of @Controller and @ResponseBody.

// ===== REQUESTMAPPING =====
// One of the most common annotation in Spring Web application. It handles our REST controllers in our MVC design pattern. We put this on top of our model controller. We can pass properties in here, but for simplicity and cleaner code we can just pass in a string that represents the beginning part of the URI (endpoints). This means our endpoiunts will have to have this in their URI.
// Difference between URL and URI: https://danielmiessler.com/study/difference-between-uri-url/
@RestController
@RequestMapping("/api/v1/")
public class StudentController {
	
//	AUTOWIRED
//	Reminder: all Spring beans are handled in our Spring container AKA Application Context.
//	Our Spring container handles all of our objects (Beans), that includes definitions, WIRING them automatically, sending them out whenever we need them, creation, and deletion.
//	Under the hood, all of our beans are AUTOWIRED in the Spring Container
//	Autowiring happens by placing an instance of one bean into an instance of another bean.
//	Annotaion Autowired is a feature in Spring framework that enables dependency injection implicitly.
//	Tells oour application context to inject an instance of StudentRepository in this class.
	
	@Autowired
	private StudentRepository studentRepo;
	
	@GetMapping("allstudents")
	public List<Student> getAllStudents() {
//		You can pass on some sorting logic inside findAll() 
		return studentRepo.findAll();
	}
	
//	ResponseEntity represents the whole HTTP response: status code, headers, and body.
//	@PathVariable is a Spring annotation which indicates that a method parameter should be bound to a URI template variable
	@GetMapping("student/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable int id) {
		Student student = studentRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found."));
				return ResponseEntity.ok(student);
	}
	
	@GetMapping("allstudents/{firstname}")
	public List<Student> getStudentsByFirstname(@PathVariable String firstname) {
		List<Student> students = studentRepo.findByFirstname(firstname);
		if(students.isEmpty()) {
			System.out.println(new ResourceNotFoundException("Student(s) with the name " + firstname + " not found."));
		}
		return studentRepo.findByFirstname(firstname);
	}
	
//	Saves a given entity. Create student
	@PostMapping("addstudent")
	public Student newStudent(@RequestBody Student student) {
		return studentRepo.save(student);
	}
	
	
	@PutMapping("student/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student newStudentInfo) {
		Student foundStudent = studentRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found."));
		
//		Update info of found student using setters with the new info from req.body using getters.
		foundStudent.setFirstname(newStudentInfo.getFirstname());
		foundStudent.setLastname(newStudentInfo.getLastname());
		
		Student updatedStudent = studentRepo.save(foundStudent);
		
		return ResponseEntity.ok(updatedStudent);
	}
	
//	Use PathVariable to grab the value of {id} parameter from URI
	@DeleteMapping("student/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable int id) {
//		Find user we want to delete
		studentRepo.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Student not found."));
		
		String message =  "Student has been deleted.";
		
//		Delete method from Jpa. Deletes entity from database.
		studentRepo.deleteById(id);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}









