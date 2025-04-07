package com.RRTS.RRTS.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.RRTS.RRTS.repositories.IssuesRepository;
import com.RRTS.RRTS.roles.Issues;

import jakarta.servlet.http.HttpSession;

@Controller
public class CityAdminController {
	private ResourcesRepository resourcesRepository;
	private TodoRepository todoRepository;
	
	@Autowired
	private IssuesRepository issuesRepository;
	
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
		return "redirect:/allocate_resources";
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
	
	@GetMapping("/city_admin")
	public String getIssues(ModelMap map) {
	    List<Issues> issues = issuesRepository.findAll(); // or filter only pending issues
	    List<Todo> todos = todoRepository.findByApproved("Waiting");
	    map.addAttribute("issues", todos);
	    return "city_admin"; // Thymeleaf template name
	}

	@ResponseBody
	@PostMapping(value = "/api/allocate/{id}")
	public ResponseEntity<String> allocateResources(
	        @PathVariable String id,
	        @RequestBody Todo todo,
	        HttpSession session) {
	    
	    Todo request = todoRepository.findById(id);
        request.setManpower(todo.getManpower());
        request.setEstimated_cost(todo.getEstimated_cost());
        Map<String, Integer> existingResources = request.getResources();
        if (existingResources == null) {
            existingResources = new HashMap<>();
        }

        if (todo.getResources() != null) {
            for (Map.Entry<String, Integer> entry : todo.getResources().entrySet()) {
                existingResources.put(entry.getKey(), entry.getValue());
            }
        }
        
        request.setResources(existingResources);
        
        request.setMachines(todo.getMachines());

        todoRepository.save(request);
        Issues issue = issuesRepository.findByRequestId(id);
        if(issue == null) issue = new Issues();
        issue.setRequestId(id);
        issue.setTitle(request.getTitle());
        issue.setDescription(request.getDescription());
        issue.setLocation(request.getLocation());
        issue.setCity(request.getCity());
        issue.setState(request.getState());
        issue.setIssueType(request.getIssueType());
        issue.setSeverity(request.getSeverity());
        issue.setIssueDate(request.getIssueDate());
        issue.setCompletionDate(request.getCompletionDate());
        issue.setImages(request.getImages());
        issue.setEmail(request.getEmail());
        issue.setUser(request.getUser());
        issue.setStatus(request.getStatus());

        issue.setManpower(request.getManpower());
        issue.setMachines(request.getMachines());
        issue.setResources(request.getResources());
        issue.setEstimated_cost(request.getEstimated_cost());
        issue.setSupervisor_email((String)session.getAttribute("email"));
        issue.setSupervisor_name((String)session.getAttribute("firstName")+" "+(String)session.getAttribute("lastName"));
        issue.setMaterials_used(request.getMaterials_used());
		issuesRepository.save(issue);
        
		request.setApproved("No");
		todoRepository.save(request);
        return ResponseEntity.ok("Resources allocated successfully");
	}
	
	@ResponseBody
	@PostMapping(value = "/api/approval/{id}")
	public ResponseEntity<String> getApproval(ModelMap map,
	        @PathVariable String id,
	        @RequestBody Todo todo,
	        HttpSession session) {
	    
	    Todo request = todoRepository.findById(id);
        request.setManpower(todo.getManpower());
        
        Map<String, Integer> existingResources = request.getResources();
        if (existingResources == null) {
            existingResources = new HashMap<>();
        }

        if (todo.getResources() != null) {
            for (Map.Entry<String, Integer> entry : todo.getResources().entrySet()) {
                existingResources.put(entry.getKey(), entry.getValue());
            }
        }
        List<String> materials_used = new ArrayList<>(existingResources.keySet());
        request.setResources(existingResources);
        request.setSupervisor_email((String)session.getAttribute("email"));
        request.setEstimated_cost(todo.getEstimated_cost());
        request.setSupervisor_name((String)session.getAttribute("firstName")+(String)session.getAttribute("lastName"));
        request.setMaterials_used(materials_used);
        request.setApproved("Waiting");
        request.setMachines(todo.getMachines());

        todoRepository.save(request);
        return ResponseEntity.ok("Approval Sent!");
	    
	}
	
	@ResponseBody
	@PostMapping("/api/updateStatus/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestBody Map<String, String> payload) {
	    Todo todo = todoRepository.findById(id);
        todo.setStatus(payload.get("status"));
        todoRepository.save(todo);
        return ResponseEntity.ok("Status updated successfully");
	}
	
	@ResponseBody
	@PostMapping("/api/updateDates/{id}")
	public ResponseEntity<?> updateDates(@PathVariable String id, @RequestBody Map<String, String> payload) {
	    Todo todo = todoRepository.findById(id);
        todo.setIssueDate(LocalDate.parse(payload.get("issueDate"), DateTimeFormatter.ISO_LOCAL_DATE));
        todo.setCompletionDate(LocalDate.parse(payload.get("completionDate"), DateTimeFormatter.ISO_LOCAL_DATE));
        todoRepository.save(todo);
        return ResponseEntity.ok("Dates updated successfully");
	}
	
	@GetMapping("/allocate_resources")
    public String showResourcePage(ModelMap model) {
        List<Resources> resources = resourcesRepository.findAll(); // Or filtered
        model.addAttribute("resources", resources);
        return "allocate_resources";
    }
	
	@PostMapping("/allocate_resources")
	public String allocateResources(@RequestParam String state,
						            @RequestParam String city,
						            @RequestParam(required = false, defaultValue = "0") Integer manpower,
						            @RequestParam(required = false, defaultValue = "0") Integer machines,
						            @RequestParam("resourceTypes[]") List<String> resourceTypes,
						            @RequestParam("resourceQuantities[]") List<Integer> resourceQuantities) 
	{		
		Map<String, Integer> resourceMap = new HashMap<>();
        for (int i = 0; i < resourceTypes.size(); i++) {
            String type = resourceTypes.get(i).trim();
            int quantity = resourceQuantities.get(i);
            if (!type.isEmpty()) {
                resourceMap.put(type, quantity);
            }
        }
		
		Resources resources = new Resources();
        resources.setState(state);
        resources.setCity(city);
        resources.setManpower(manpower);
        resources.setMachines(machines);
        resources.setResources(resourceMap);

        // Save to MongoDB
        resourcesRepository.save(resources);
		return "redirect:/city_admin";
	}
}
