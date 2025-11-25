package org.isfce.pid.GradleDispenses2526.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.isfce.pid.GradleDispenses2526.model.Cours;
import org.isfce.pid.GradleDispenses2526.service.CoursService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/cours/")
@Slf4j
public class CoursController {

	private CoursService coursService;

	public CoursController(CoursService coursService) {
		this.coursService = coursService;
	}

	@GetMapping("info")
	String getCours(@RequestParam("code") String id, Model model) {
		log.info("cours param" + id);
		if (!model.containsAttribute("cours"))
			model.addAttribute("cours",
					coursService.getCours(id).orElseThrow(() -> new NoSuchElementException("Ce cours n'existe pas")));
		return "/cours/cours";
	}

	@GetMapping({ "info2/{code}", "info2" })
	String getCours2(@PathVariable(required = false, name = "code") Optional<String> id, Model model) {
		log.info("cours path" + id);
		String c = id.orElse("IPDB");
		model.addAttribute("cours", coursService.getCours(c).get());
		return "/cours/cours";
	}

	@GetMapping("liste")
	String getListeCours(Model model) {
		model.addAttribute("listeCours", coursService.getListe());
		return "/cours/listeCours";
	}

	@GetMapping("add")
	String addCoursGet(@ModelAttribute("cours") Cours cours) {
		return "/cours/addCours";
	}

	@PostMapping("add")
	String addCoursPost(@Valid @ModelAttribute("cours") Cours cours, BindingResult errors, Model model,
			RedirectAttributes rModel) {
		if (errors.hasErrors()) {
			log.error(errors.getAllErrors().toString());
			return "/cours/addCours";

		}
		if (coursService.existCours(cours.getCode())) {
			errors.rejectValue("code", "err.cours.doublon");
			return "/cours/addCours";
		}

		log.info("Ajout d'un cours: " + cours);
		coursService.addCours(cours);
		rModel.addFlashAttribute(cours);
		return "redirect:/cours/info?code=" + cours.getCode();
	}

}
