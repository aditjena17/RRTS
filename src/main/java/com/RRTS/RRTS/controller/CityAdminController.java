package com.RRTS.RRTS.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@RequestMapping(value="/city-admin", method = RequestMethod.GET)
	public String getCityAdminPage() {
		return "city_admin";
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
	public String allocateResources(HttpSession session, @ModelAttribute Resources resources, ModelMap map,
			@RequestParam List<String> resourceName, @RequestParam List<Integer> resourceQuantity) {
		if (session.getAttribute("email") == null) {
            return "redirect:/login"; 
        }
		resources.setResources(resourceName, resourceQuantity);
		resourcesRepository.save(resources);
		return "city_admin";
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/allocate/{id}")
	public ResponseEntity<String> allocateResources(
	        @PathVariable String id,
	        @RequestBody Todo allocation) {
	    
	    Todo request = todoRepository.findById(id);
        request.setManpower(allocation.getManpower());
        
        Map<String, Integer> existingResources = request.getResources();
        if (existingResources == null) {
            existingResources = new HashMap<>();
        }

        if (allocation.getResources() != null) {
            for (Map.Entry<String, Integer> entry : allocation.getResources().entrySet()) {
                existingResources.put(entry.getKey(), entry.getValue());
            }
        }

        request.setResources(existingResources);
        
        request.setMachines(allocation.getMachines());

        todoRepository.save(request);
        return ResponseEntity.ok("Resources allocated successfully");
	    
	}
	
	@PostMapping("/api/updateStatus/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestBody Map<String, String> payload) {
	    Todo todo = todoRepository.findById(id);
        todo.setStatus(payload.get("status"));
        todoRepository.save(todo);
        return ResponseEntity.ok("Status updated successfully");
	}
	
	@PostMapping("/api/updateDates/{id}")
	public ResponseEntity<?> updateDates(@PathVariable String id, @RequestBody Map<String, String> payload) {
	    Todo todo = todoRepository.findById(id);
        todo.setIssueDate(LocalDate.parse(payload.get("issueDate"), DateTimeFormatter.ISO_LOCAL_DATE));
        todo.setCompletionDate(LocalDate.parse(payload.get("completionDate"), DateTimeFormatter.ISO_LOCAL_DATE));
        todoRepository.save(todo);
        return ResponseEntity.ok("Dates updated successfully");
	}
	
	
}
