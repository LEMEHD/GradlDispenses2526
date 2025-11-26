package org.isfce.pid.GradleDispenses2526.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.isfce.pid.GradleDispenses2526.model.Student;
import java.util.*;



public interface IEtudiantDao extends JpaRepository<Student, UUID> {
    Optional<Student> findByEmail(String email);
}
