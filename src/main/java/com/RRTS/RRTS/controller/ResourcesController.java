package com.RRTS.RRTS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RRTS.RRTS.classes.Resources;
import com.RRTS.RRTS.classes.ResourcesRepository;
import com.RRTS.RRTS.classes.Todo;
import com.RRTS.RRTS.classes.TodoRepository;

@RestController
@RequestMapping("/api")
public class ResourcesController {
	
	@Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ResourcesRepository resourceRepository;

    @RequestMapping("/repairs")
    public List<Todo> getRepairRequests() {
        return todoRepository.findAll();
    }

    @RequestMapping("/resources")
    public List<Resources> getAllocatedResources() {
        return resourceRepository.findAll();
    }
}
