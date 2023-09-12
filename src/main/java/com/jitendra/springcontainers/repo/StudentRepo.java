package com.jitendra.springcontainers.repo;

import com.jitendra.springcontainers.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,Long> {
}
