package org.isfce.pid.GradleDispenses2526.dao;

import org.isfce.pid.GradleDispenses2526.model.KbCorrespondenceRule;
import org.isfce.pid.GradleDispenses2526.model.KbSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface IKbCorrespondenceRuleDao extends JpaRepository<KbCorrespondenceRule, UUID> {
    // Récupérer toutes les règles connues pour une école donnée
    List<KbCorrespondenceRule> findByEcole(KbSchool ecole);
}