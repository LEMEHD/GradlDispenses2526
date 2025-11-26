package org.isfce.pid.GradleDispenses2526.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.isfce.pid.GradleDispenses2526.controller.error.DuplicateException;
import org.isfce.pid.GradleDispenses2526.dao.IUeDao;
import org.isfce.pid.GradleDispenses2526.model.Acquis;
import org.isfce.pid.GradleDispenses2526.model.Cours;
import org.isfce.pid.GradleDispenses2526.model.UE;
import org.isfce.pid.GradleDispenses2526.service.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api/cours/", produces = "application/json")
@Slf4j
public class CoursControllerRest {

	private CoursService coursService;
	@Autowired
	private MessageSource bundle;

	public CoursControllerRest(CoursService coursService) {
		this.coursService = coursService;
	}

	@GetMapping({ "info/{code}", "info2" })
	ResponseEntity<Cours> getCours(@PathVariable(name = "code") String id) {
		log.info("cours path" + id);

		Optional<Cours> oCours = coursService.getCours(id);

		if (oCours.isPresent()) {
		    return ResponseEntity.ok(oCours.get());
		} else {
		    // .build() crée la réponse finale sans corps (body)
		    return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("liste")
	ResponseEntity<List<Cours>> getListeCours() {
		return ResponseEntity.ok(coursService.getListe());
	}

	@PostMapping(path = "add", consumes = "application/json")
	ResponseEntity<Cours> addCoursPost(@Valid @RequestBody Cours cours, Locale locale) {
		if (coursService.existCours(cours.getCode()))
			throw new DuplicateException(bundle.getMessage("err.cours.doublon", null, locale), "code");

		log.info("Ajout d'un cours: " + cours);
		coursService.addCours(cours);
		return new ResponseEntity<>(cours, HttpStatus.CREATED);
	}

	@DeleteMapping(path = "{code}/delete")
	ResponseEntity<String> deleteCours(@PathVariable(value = "code") String code) {
		if (!coursService.existCours(code))
			return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
		coursService.deleteCours(code);
		return ResponseEntity.ok(code);
	}
	@Autowired
	IUeDao daoUe;
	@GetMapping("listeUE")
	ResponseEntity<List<UE>> getListeUE() {
		daoUe.deleteById("IPAP");
		daoUe.save(creePAP());
		
		return ResponseEntity.ok(daoUe.findAll());
	}
	@PostMapping(path = "addUE", consumes = "application/json")
	ResponseEntity<UE> addUEPost(@Valid @RequestBody UE cours, Locale locale) {
		daoUe.deleteById(cours.getCode());
		
		log.info("Ajout d'une UE: " + cours);
		cours=daoUe.save(cours);
		return new ResponseEntity<>(cours, HttpStatus.CREATED);
	}
	
	UE creePAP() {
		String code = "7521 05 U32 D3";
		Acquis[] acquis = { new Acquis("mettre en oeuvre une représentation algorithmique du problème posé", 30),
				new Acquis("de développer au moins un programme en respectant les spécificités du langage choisi", 30),
				new Acquis("de mettre en oeuvre des procédures de test", 20),
				new Acquis("de justifier la démarche mise en oeuvre dans l’élaboration du (ou des) programme(s)", 20) };

		String prgm = """
				* d'identifier différents langages de programmation existants ;
				* de mettre en oeuvre une méthodologie de résolution de problème (observation,
				résolution, expérimentation, validation) et de la justifier en fonction de l’objectif
				poursuivi ;
				* de concevoir, construire et représenter des algorithmes, en utilisant :
					o les types de données élémentaires,
					o les figures algorithmiques de base (séquence, alternative et répétitive),
					o les instructions,
					o les portées des variables,
					o les fonctions et procédures,
					o la récursivité,
					o les entrées/sorties,
					o les fichiers,
					o les structures de données de base (tableaux et enregistrements) ;
				* de traduire de manière adéquate des algorithmes en respectant les spécificités du
				langage utilisé (JAVA, PYTHON);
				* de documenter de manière complète et précise les programmes développés ;
				* de produire des tests pour valider les programmes développés.
								""";
		List<Acquis> liste = new ArrayList<Acquis>(Arrays.asList(acquis));
		UE pap = UE.builder().code("IPAP").ects(8).nbPeriodes(120).nom("PRINCIPES ALGORITHMIQUES ET PROGRAMMATION")
				.prgm(prgm).ref(code).acquis(liste).build();

		return pap;
	}
	

}
