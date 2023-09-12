package com.jitendra.springcontainers.controller;

import com.jitendra.springcontainers.entity.Student;
import com.jitendra.springcontainers.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepo studentRepo;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody Student student){
        return studentRepo.save(student);
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return studentRepo.findAll();
    }

}
