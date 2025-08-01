package com.example.eduplatform.repository;

import com.example.eduplatform.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    boolean existsByIdAndCourseId(Long moduleId, Long courseId);

    boolean existsByCourseIdAndModuleIndex(Long courseId, Integer moduleIndex);

    List<Module> findAllByCourseId(Long courseId);

}
