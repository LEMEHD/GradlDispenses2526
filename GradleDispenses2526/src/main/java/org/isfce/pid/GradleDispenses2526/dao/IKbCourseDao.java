package org.isfce.pid.GradleDispenses2526.dao;

import org.isfce.pid.GradleDispenses2526.model.KbCourse;
import org.isfce.pid.GradleDispenses2526.model.KbSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface IKbCourseDao extends JpaRepository<KbCourse, UUID> {
    // Pour trouver un cours externe précis dans la KB
    Optional<KbCourse> findByEcoleAndCodeIgnoreCase(KbSchool ecole, String code);
}