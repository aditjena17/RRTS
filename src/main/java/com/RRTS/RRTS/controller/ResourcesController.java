package com.RRTS.RRTS.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.RRTS.RRTS.classes.Resources;
import com.RRTS.RRTS.classes.ResourcesRepository;
import com.RRTS.RRTS.classes.Todo;
import com.RRTS.RRTS.classes.TodoRepository;
import com.RRTS.RRTS.classes.User;
import com.RRTS.RRTS.classes.UserRepository;
import com.RRTS.RRTS.repositories.IssuesRepository;
import com.RRTS.RRTS.roles.Issues;

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
    
    @Autowired
    private IssuesRepository issuesRepository;

    @RequestMapping("/repairs")
    public List<Todo> getRepairRequests(HttpSession session) {
    	String email = (String)session.getAttribute("email");
    	String role = (String)session.getAttribute("role");
    	User user = userRepository.findByEmail(email);
    	switch (user.getRole().toLowerCase()) {
        case "resident":
            return todoRepository.findByEmail(email);
        default:
            return todoRepository.findAll();
    	}    	
    }    
    
    @DeleteMapping("/allocate/{requestId}/delete-resource/{resourceType}")
    public ResponseEntity<String> deleteResource(@PathVariable String requestId, @PathVariable String resourceType) {
        Todo allocation = todoRepository.findById(requestId);
        Issues issue = issuesRepository.findByRequestId(requestId);
        Map<String, Integer> resources1 = allocation.getResources();
        Map<String, Integer> resources2 = issue.getResources();

        if (resources1.containsKey(resourceType)) {
            resources1.remove(resourceType);
            resources2.remove(resourceType);
            todoRepository.save(allocation);
            issuesRepository.save(issue);
            return ResponseEntity.ok("Resource deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found.");
        }
    }
    
    @PostMapping("/approve/{id}")
    public RedirectView approveIssue(@PathVariable String id) {
        Todo todo = todoRepository.findById(id);
        todo.setApproved("Yes");
        todoRepository.save(todo);
        
		return new RedirectView("/city_admin");
    }

    @PostMapping("/reject/{id}")
    public RedirectView rejectIssue(@PathVariable String id, RedirectAttributes redirectAttributes) {
    	Todo todo = todoRepository.findById(id);
        todo.setApproved("Rejected");
        todoRepository.save(todo);
		
		return new RedirectView("/city_admin");
    }
    
    @PostMapping("/reset-approval/{id}")
    public ResponseEntity<String> resetApproval(@PathVariable String id) {
        Todo todo = todoRepository.findById(id);
        if (todo != null) {
            todo.setApproved("No");
            todoRepository.save(todo);
            return ResponseEntity.ok("Approval reset to No");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found");
        }
    }

    @RequestMapping("/resources")
    public List<Resources> getAllocatedResources() {
        return resourceRepository.findAll();
    }
    
    @RequestMapping("/todos")
    public List<Todo> getTodosByCity(HttpSession session) {
    	String city = (String)session.getAttribute("city");
        return todoRepository.findByCity(city);
    }
    
    
    
    @DeleteMapping("/deleteResource/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable String id) {
        if (resourceRepository.existsById(id)) {
            resourceRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
