package org.isfce.pid.GradleDispenses2526.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import org.isfce.pid.GradleDispenses2526.model.ExemptionRequest;


public interface IExemptionRequestDao extends JpaRepository<ExemptionRequest, UUID> {
        
    @EntityGraph(attributePaths = {"externalCourses", "documents", "items", "items.ue"})
    Optional<ExemptionRequest> findWithAllById(UUID id);
    
    @EntityGraph(attributePaths = {"externalCourses","documents","items","items.ue"})
    List<ExemptionRequest> findAllByEtudiantEmail(String email);
}