package com.RRTS.RRTS.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.RRTS.RRTS.classes.Resources;
import com.RRTS.RRTS.classes.ResourcesRepository;
import com.RRTS.RRTS.classes.Todo;
import com.RRTS.RRTS.classes.TodoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CityAdminController {
	private ResourcesRepository resourcesRepository;
	private TodoRepository todoRepository;
	
	public CityAdminController(ResourcesRepository resourcesRepository, TodoRepository todoRepository) {
		super();
		this.resourcesRepository = resourcesRepository;
		this.todoRepository = todoRepository;
	}
	
	@RequestMapping(value="/allocate", method = RequestMethod.GET)
	public String allocateResources(ModelMap map) {		
		List<Todo> repairs = new ArrayList<>();
		repairs = todoRepository.findAll();
		map.addAttribute("todos", repairs);
		map.addAttribute("resources", new Resources());
		return "allocate_resources";
	}
	
	@RequestMapping(value="/allocate", method = RequestMethod.POST)
	public String allocateResources(HttpSession session, @ModelAttribute Resources resources, ModelMap map) {
		if (session.getAttribute("email") == null) {
            return "redirect:/login"; 
        }
		resourcesRepository.save(resources);
		return "city_admin";
	}
}
