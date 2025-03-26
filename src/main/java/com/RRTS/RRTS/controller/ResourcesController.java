package com.RRTS.RRTS.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RRTS.RRTS.classes.Resources;
import com.RRTS.RRTS.classes.ResourcesRepository;
import com.RRTS.RRTS.classes.Todo;
import com.RRTS.RRTS.classes.TodoRepository;
import com.RRTS.RRTS.classes.User;
import com.RRTS.RRTS.classes.UserRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class ResourcesController {
	
	@Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ResourcesRepository resourceRepository;
    
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/repairs")
    public List<Todo> getRepairRequests(HttpSession session) {
    	String email = (String)session.getAttribute("email");
    	String role = (String)session.getAttribute("role");
    	User user = userRepository.findByEmail(email);
    	switch (user.getRole().toLowerCase()) {
        case "resident":
            return todoRepository.findByUser(user);
        default:
            return todoRepository.findAll();
    	}    	
    }    

    @RequestMapping("/resources")
    public List<Resources> getAllocatedResources() {
        return resourceRepository.findAll();
    }
    
    @RequestMapping("/mayor")
    public Map<String, Object> getMayorStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalRequests", 120);  // Example value
        stats.put("totalResources", 45);  // Example value

        stats.put("repairDistribution", List.of(
            Map.of("name", "Potholes", "value", 50),
            Map.of("name", "Cracks", "value", 30),
            Map.of("name", "Others", "value", 40)
        ));

        stats.put("resourceDistribution", List.of(
            Map.of("name", "Concrete", "value", 25),
            Map.of("name", "Asphalt", "value", 20)
        ));

        return stats;
    }
}
