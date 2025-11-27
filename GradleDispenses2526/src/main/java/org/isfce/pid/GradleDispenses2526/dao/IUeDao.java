package org.isfce.pid.GradleDispenses2526.dao;

import java.util.Optional;
import java.util.UUID;

import org.isfce.pid.GradleDispenses2526.model.UE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUeDao extends JpaRepository<UE, UUID>
{
	Optional<UE> findByCode(String code);
    boolean existsByCode(String code);
}
