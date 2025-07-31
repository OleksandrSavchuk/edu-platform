package com.example.eduplatform.repository;

import com.example.eduplatform.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByIdAndInstructorId(Long courseId, Long instructorId);

    List<Course> findAllByInstructorId(Long instructorId);

    Optional<Course> findByIdAndInstructorId(Long id, Long instructorId);


}
