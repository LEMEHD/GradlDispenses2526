package org.isfce.pid.GradleDispenses2526.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import org.isfce.pid.GradleDispenses2526.model.ExemptionItem;


public interface IExemptionItemDao extends JpaRepository<ExemptionItem, UUID> { }