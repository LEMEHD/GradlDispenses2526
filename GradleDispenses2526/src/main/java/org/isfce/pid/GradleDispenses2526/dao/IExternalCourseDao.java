package org.isfce.pid.GradleDispenses2526.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import org.isfce.pid.GradleDispenses2526.model.ExternalCourse;


public interface IExternalCourseDao extends JpaRepository<ExternalCourse, UUID> { }