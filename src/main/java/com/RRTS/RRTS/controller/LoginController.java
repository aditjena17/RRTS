package com.RRTS.RRTS.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.RRTS.RRTS.classes.AuthService;
import com.RRTS.RRTS.classes.Todo;
import com.RRTS.RRTS.classes.TodoRepository;
import com.RRTS.RRTS.classes.User;
import com.RRTS.RRTS.classes.UserRepository;

import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {
	public LoginController(UserRepository userRepository, TodoRepository todoRepository, AuthService authService) {
		super();
		this.userRepository = userRepository;
		this.todoRepository = todoRepository;
		this.authService = authService;
	}
	private UserRepository userRepository;
	private AuthService authService;
	private TodoRepository todoRepository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String firstPage() {
		return "home";
	}
	
	@RequestMapping("/resident")
	public String residentPage(HttpSession session) {
		if (session.getAttribute("email") == null) {
            return "redirect:/login"; 
        }
		return "resident";
	}
	
	@RequestMapping(value = "/report_issue", method = RequestMethod.GET)
    public String reportIssuePage(ModelMap map, HttpSession session) {
		if (session.getAttribute("email") == null) {
            return "redirect:/login"; 
        }
		map.addAttribute("todo", new Todo());
        return "report_issue";
    }
	
	@RequestMapping(value = "/report_issue", method = RequestMethod.POST)
    public String reportIssuePage(@ModelAttribute Todo todo, HttpSession session) {
		String email = (String)session.getAttribute("email");
		User user = userRepository.findByEmail(email);
		if (email == null) {
            return "redirect:/login"; 
        }
		todo.setUser(user);
		todo.setStatus("pending");
		todo.setIssueDate(LocalDate.now());
		switch(todo.getSeverity()) {
			case "low":
				todo.setCompletionDate(LocalDate.now().plusDays(3));
			case "medium":
				todo.setCompletionDate(LocalDate.now().plusDays(7));
			case "high":
				todo.setCompletionDate(LocalDate.now().plusDays(15));
		}
		todoRepository.save(todo);
		session.setAttribute("email", user.getEmail());
        session.setAttribute("role", user.getRole());
        
        switch (user.getRole().toLowerCase()) {
            case "resident":
                return "resident";
            case "supervisor":
                return "supervisor";
            case "city_admin":
                return "city_admin";
            case "mayor":
                return "mayor";
            default:
                return "home";
        }
    }
	
	@RequestMapping("/track_repair")
    public String trackRepairPage(ModelMap map, HttpSession session) {
		if (session.getAttribute("email") == null) {
            return "redirect:/login"; 
        }
		String email = (String) session.getAttribute("email");
		String role = (String) session.getAttribute("role");
		User user = userRepository.findByEmail(email);
		
		List<Todo> repairs = new ArrayList<>();
		if(role.equalsIgnoreCase("resident")) {
			repairs = todoRepository.findByUser(user);
		}else if(role.equalsIgnoreCase("supervisor")) {
			repairs = todoRepository.findAll();
		}
		
        map.addAttribute("todos", repairs);
        return "track_repair";
    }
	
	@RequestMapping("/contact")
    public String contactPage() {
        return "contact";
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(ModelMap map) {
		map.addAttribute("login", new User());
        return "login";
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPage(@ModelAttribute("loginDetails") User user, HttpSession session) {
		String result = authService.authenticateUser(user.getEmail(), user.getPassword(), user.getRole());
		switch (result) {
        case "Email not found":
            return "loginFail";
        case "Incorrect password":
        	return "loginFail";
        case "Incorrect role":
        	return "loginFail";
        default:
        	session.setAttribute("email", user.getEmail());
            session.setAttribute("role", user.getRole());
            
            switch (user.getRole().toLowerCase()) {
                case "resident":
                    return "resident";
                case "supervisor":
                    return "supervisor";
                case "city_admin":
                    return "city_admin";
                case "mayor":
                    return "mayor";
                default:
                    return "home";
            }
		}
    }
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupPage(ModelMap map) {
		map.addAttribute("user", new User());
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupPage(ModelMap map, @ModelAttribute User user, HttpSession session) {
		if (userRepository.existsByEmail(user.getEmail())) {
	        map.addAttribute("error", "Email already exists. Please use a different email.");
	        return "signup"; 
	    }
		
		map.addAttribute("username", user.getEmail());
		userRepository.save(user);
		session.setAttribute("email", user.getEmail());
        session.setAttribute("role", user.getRole());
        
        switch (user.getRole().toLowerCase()) {
            case "resident":
                return "resident";
            case "supervisor":
                return "supervisor";
            case "city_admin":
                return "city_admin";
            case "mayor":
                return "mayor";
            default:
                return "home";
        }
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteUser(HttpSession session) {
		String email = (String) session.getAttribute("email");
		User user = userRepository.findByEmail(email);
		userRepository.delete(user);
		
		return "redirect:/logout";
	}
	
	@RequestMapping("/about-us")
    public String showAboutUsPage() {
        return "details/about_us";
    }
}
