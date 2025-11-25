package org.isfce.pid.GradleDispenses2526.controller.error;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

//@ControllerAdvice
public class MonAdviceController {
	
	@ExceptionHandler(NoSuchElementException.class)
	public ModelAndView gestionErreur(NoSuchElementException exc, HttpServletRequest req) {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("error");
		mv.addObject("info", exc.getMessage());
		mv.addObject("path",req.getRequestURL());
		return mv;
	}

}
