package com.RRTS.RRTS.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.RRTS.RRTS.classes.AuthService;
import com.RRTS.RRTS.classes.Todo;
import com.RRTS.RRTS.classes.TodoRepository;
import com.RRTS.RRTS.classes.User;
import com.RRTS.RRTS.classes.UserRepository;
import com.RRTS.RRTS.repositories.CityadminRepository;
import com.RRTS.RRTS.repositories.MayorRepository;
import com.RRTS.RRTS.repositories.ResidentRepository;
import com.RRTS.RRTS.repositories.SupervisorRepository;
import com.RRTS.RRTS.roles.CityAdmin;
import com.RRTS.RRTS.roles.Mayor;
import com.RRTS.RRTS.roles.Resident;
import com.RRTS.RRTS.roles.Supervisor;

import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class LoginController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthService authService;
	@Autowired
	private TodoRepository todoRepository;
	@Autowired
	private ResidentRepository residentRepository;
	@Autowired
	private SupervisorRepository supervisorRepository;
	@Autowired
	private CityadminRepository cityadminRepository;
	@Autowired
	private MayorRepository mayorRepository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String firstPage() {
		return "home";
	}
	
	@RequestMapping(value = "/pre-signup", method = RequestMethod.GET)
	public String preSignupPage() {
		return "pre_signup";
	}
	
	@RequestMapping(value = "/pre-signup", method = RequestMethod.POST)
	public String preSignupPage(@RequestParam(value = "role") String role, HttpSession session) {
		switch (role.toLowerCase()) {
	        case "resident":
	            Resident resident = new Resident();
	            resident.setRole(role);
	            session.setAttribute("user", resident);
	            break;
	        case "supervisor":
	            Supervisor supervisor = new Supervisor();
	            supervisor.setRole(role);
	            session.setAttribute("user", supervisor);
	            break;
	        case "city_admin":
	            CityAdmin cityAdmin = new CityAdmin();
	            cityAdmin.setRole(role);
	            session.setAttribute("user", cityAdmin);
	            break;
	        case "mayor":
	            Mayor mayor = new Mayor();
	            mayor.setRole(role);
	            session.setAttribute("user", mayor);
	            break;
	        default:
	            session.setAttribute("user", new User());
		}
		return "signup";
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
		List<Todo> todos = new ArrayList<>();
		
		String role = user.getRole();
		switch(role.toLowerCase()) {
			case "resident":
				Resident resident = new Resident(user);
				residentRepository.save(resident);
				break;
			case "supervisor":
				Supervisor supervisor = new Supervisor(user);
				supervisorRepository.save(supervisor);
				break;
			case "cityadmin":
				CityAdmin cityadmin = new CityAdmin(user);
				cityadminRepository.save(cityadmin);
				break;
			case "mayor":
				Mayor mayor = new Mayor(user);
				mayorRepository.save(mayor);
				break;
			default:
				userRepository.save(user);
		}
		userRepository.save(user);
		
		session.setAttribute("email", user.getEmail());
        session.setAttribute("role", user.getRole());
        session.setAttribute("city", user.getCity());
        session.setAttribute("firstName", user.getFirstName());
        session.setAttribute("lastName", user.getLastName());
        
        switch (user.getRole().toLowerCase()) {
            case "resident":
                return "home";
            case "supervisor":
                return "redirect:/supervisor";
            case "cityadmin":
                return "redirect:/city_admin";
            case "mayor":
                return "mayor";
            default:
                return "home";
        }
	}
	
	@RequestMapping(value="/pre-login", method=RequestMethod.GET)
	public String preLoginPage() {
		return "pre_login";
	}
	
	@RequestMapping(value="/pre-login", method=RequestMethod.POST)
	public String preLoginPage(@RequestParam(value="role")String role, ModelMap map) {
		switch (role.toLowerCase()) {
        case "resident":
            Resident resident = new Resident();
            resident.setRole(role);
            map.addAttribute("user", resident);
        case "supervisor":
            Supervisor supervisor = new Supervisor();
            supervisor.setRole(role);
            map.addAttribute("user", supervisor);
        case "city_admin":
            CityAdmin cityAdmin = new CityAdmin();
            cityAdmin.setRole(role);
            map.addAttribute("user", cityAdmin);
        case "mayor":
            Mayor mayor = new Mayor();
            mayor.setRole(role);
            map.addAttribute("user", mayor);
		}
		return "login";
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
        	User fullUser = userRepository.findByEmail(user.getEmail());
        	session.setAttribute("email", user.getEmail());
            session.setAttribute("role", user.getRole());
            session.setAttribute("city", fullUser.getCity());
            session.setAttribute("firstName", fullUser.getFirstName());
            session.setAttribute("lastName", fullUser.getLastName());
            
            switch (user.getRole().toLowerCase()) {
                case "resident":
                    return "home";
                case "supervisor":
                    return "redirect:/supervisor";
                case "cityadmin":
                    return "redirect:/city_admin";
                case "mayor":
                    return "mayor";
                default:
                    return "home";
            }
		}
    }
	
	@RequestMapping(value = "/report_issue", method = RequestMethod.GET)
    public String reportIssuePage(ModelMap map, HttpSession session) {
		if (session.getAttribute("email") == null) {
            return "redirect:/pre-login"; 
        }
		map.addAttribute("todo", new Todo());
        return "report_issue";
    }
	
	@RequestMapping(value = "/report_issue", method = RequestMethod.POST)
    public String reportIssuePage(@ModelAttribute Todo todo, HttpSession session) {
		String email = (String)session.getAttribute("email");
		if (email == null) {
            return "redirect:/pre-login"; 
        }
		User user = userRepository.findByEmail(email);
		todo.setEmail(email);
		todo.setUser(user);
		todo.setStatus("Pending");
		todo.setIssueDate(LocalDate.now());
		switch(todo.getSeverity()) {
			case "low":
				todo.setCompletionDate(LocalDate.now().plusDays(3));
				break;
			case "medium":
				todo.setCompletionDate(LocalDate.now().plusDays(7));
				break;
			case "high":
				todo.setCompletionDate(LocalDate.now().plusDays(15));
				break;
		}
		todoRepository.save(todo);
		session.setAttribute("email", user.getEmail());
        session.setAttribute("role", user.getRole());
        
        switch (user.getRole().toLowerCase()) {
            case "resident":
                return "home";
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
	
	@RequestMapping(value="/track_repair", method = RequestMethod.GET)
	public String getRepairPage(HttpSession session) {
		String email = (String)session.getAttribute("email");
		if (email == null) {
            return "redirect:/pre-login"; 
        }
		return "demo";
	}
	
	@RequestMapping("/contact")
    public String contactPage() {
        return "contact";
    }
	
	@RequestMapping("/city_admin" )
	public String getCityAdminPage() {
		return "city_admin";
	}
	
	@RequestMapping("/supervisor")
	public String getSupervisorPage() {
		return "supervisor";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteUser(HttpSession session) {
		String email = (String) session.getAttribute("email");
		User user = userRepository.findByEmail(email);
		String role = user.getRole();
		switch(role.toLowerCase()) {
			case "resident":
				residentRepository.deleteByEmail(email);
			case "supervisor":
				supervisorRepository.deleteByEmail(email);
			case "cityadmin":
				cityadminRepository.deleteByEmail(email);
			case "mayor":
				mayorRepository.deleteByEmail(email);
		}
		todoRepository.deleteByEmail(email);
		userRepository.delete(user);
		
		return "redirect:/logout";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String getProfile(HttpSession session) {
		return "profile";
	}
	
	@RequestMapping("/about-us")
    public String showAboutUsPage() {
        return "details/about_us";
    }
	
	@RequestMapping("/show_resources")
	public String showResources() {
		return "show_resources";
	}
}
