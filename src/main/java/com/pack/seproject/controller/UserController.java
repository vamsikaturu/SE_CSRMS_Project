package com.pack.seproject.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.pack.seproject.model.Category;
import com.pack.seproject.model.User;
import com.pack.seproject.repository.CategoryRepository;
import com.pack.seproject.repository.ReminderRepository;
import com.pack.seproject.repository.UserRespository;



@Controller
public class UserController {
    
	@Autowired
	UserRespository userRespository;

	@Autowired
	ReminderRepository reminderRepository;

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	JavaMailSender javaMailSender;

	@GetMapping("/login" )
	public String loginPage (User user, Model m) {
		m.addAttribute( "user", new User());
		return "login";
	}
	
	@GetMapping("/signin")
	public String signIn(Model m) {
		System.out.println("signin");
		m.addAttribute("signinData", new User());
		return "signin";
	}

	@GetMapping("/login_error")
	public String loginError(Model m) {
		m.addAttribute("error", "error");
		return "redirect:/login";
	}
	

	@PostMapping("/signin_process")
	public String signInProcess(User user, Model m){
		System.out.println(user.getEmail());

		userRespository.save(user);

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("vamsikaturu008@gmail.com");
		mailMessage.setTo(user.getEmail());
		mailMessage.setText("You have Successfully Registered");
		mailMessage.setSubject("Centralized Reminder Management App");
		javaMailSender.send(mailMessage);
		
		return "login";
	}
	
	@GetMapping("/userhome")
	public String userHome(Model m){

		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		Object userdata = authentication.getPrincipal();
		String[] userdetails = userdata.toString().split(" ");
	
		m.addAttribute("username", userdetails[0]);
		m.addAttribute("id", userdetails[1]);

		List<Category> category = categoryRepository.findByUserId(Integer.parseInt(userdetails[1]));
		m.addAttribute("category", category);
		return "homepage";
	}


	
}
