package org.isfce.pid.GradleDispenses2526.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.isfce.pid.GradleDispenses2526.dao.CoursDao;
import org.isfce.pid.GradleDispenses2526.model.Cours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoursService {
	private List<Cours> listeCours = new ArrayList<>();
	@Autowired
	CoursDao daoCours;
	public CoursService() {
		listeCours.add(new Cours("IPID", "Projet d'intégration", (short) 100));
		listeCours.add(new Cours("IPDB", "Projet de SGBDR", (short) 80));
		listeCours.add(new Cours("IIBD", "Initiation aux bases de données", (short) 60));
	}

	public List<Cours> getListe() {
		return daoCours.findAll();
	}

	public Optional<Cours> getCours(String id) {
		return daoCours.findById(id);
				//listeCours.stream().filter((e) -> e.getCode().equals(id)).findFirst();
	}

	public void addCours(Cours cours) {
		daoCours.save(cours);
		//listeCours.add(cours);
	}

	public boolean existCours(String code) {
		return daoCours.existsById(code);
	}

	public void deleteCours(String code) {
		daoCours.deleteById(code);	
	}

}
