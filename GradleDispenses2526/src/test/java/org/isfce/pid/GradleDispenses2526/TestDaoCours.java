package org.isfce.pid.GradleDispenses2526;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.isfce.pid.GradleDispenses2526.dao.ICoursDao;
import org.isfce.pid.GradleDispenses2526.dao.IUeDao;
import org.isfce.pid.GradleDispenses2526.model.Acquis;
import org.isfce.pid.GradleDispenses2526.model.Cours;
import org.isfce.pid.GradleDispenses2526.model.UE;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import jakarta.transaction.Transactional;

@ActiveProfiles(value = "testU")
@Sql({ "/dataTestU.sql" })
@SpringBootTest
class TestDaoCours {
	@Autowired
	ICoursDao daoCours;
	@Autowired
	IUeDao daoUe;

	@Test
	@Transactional
	void countCours() {
		assertEquals(7, daoCours.count());
	}

	@Test
	@Transactional
	void getCours() {
		Optional<Cours> oipid = daoCours.findById("IPID");
		assertTrue(oipid.isPresent());

	}

	@Test
	@Transactional
	void getSaveUE() {
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

		daoUe.save(pap);
		var oPAP = daoUe.findById("IPAP");
		assertTrue(oPAP.isPresent());
		UE pap2 = oPAP.get();
		assertEquals(pap, pap2);
		assertEquals(4, pap2.getAcquis().size());
		// ajout d'un acquis
		pap2.getAcquis().add(new Acquis("Test", 5));

		daoUe.save(pap2);
		var oPAP3 = daoUe.findById("IPAP");
		assertTrue(oPAP3.isPresent());
		UE pap3 = oPAP.get();
		assertEquals(pap2, pap3);
		assertEquals(5, pap3.getAcquis().size());
		
		// suppression d'un acquis
		pap3.getAcquis().remove(0);
		daoUe.save(pap3);
		oPAP3 = daoUe.findById("IPAP");
		assertTrue(oPAP3.isPresent());
		pap3 = oPAP3.get();
		assertEquals(4, pap3.getAcquis().size());
		
		daoUe.delete(pap3);
		oPAP3 = daoUe.findById("IPAP");
		assertFalse(oPAP3.isPresent());
	}

}
