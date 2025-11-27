package org.isfce.pid.GradleDispenses2526.dao;

import java.util.UUID;

import org.isfce.pid.GradleDispenses2526.model.Acquis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAcquisDao extends JpaRepository<Acquis, UUID>
{

}
