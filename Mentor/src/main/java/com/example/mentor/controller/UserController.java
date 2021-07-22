package com.example.mentor.controller;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.board.model.TestUser;
import com.project.board.repository.UserRepository;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepository;

	@Autowired
	HttpSession session;

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@PostMapping("/signup")
	public String signupPost(@ModelAttribute @Valid TestUser user, BindingResult result) {
		if( result.hasErrors() ) {
			// �뿉�윭瑜� List濡� ���옣
			List<ObjectError> list = result.getAllErrors();
			for( ObjectError error : list ) {
				System.out.println(error);
			}
			return "/signup";
		}
		userRepository.save(user);
		return "redirect:/";
	}

	@GetMapping("/signin")
	public String signin() {
		return "signin";
	}

	@PostMapping("/signin")
	public String signinPost(@ModelAttribute @Valid TestUser user, BindingResult result) {
		if( result.hasErrors() ) {
			// �뿉�윭瑜� List濡� ���옣
			List<ObjectError> list = result.getAllErrors();
			for( ObjectError error : list ) {
				System.out.println(error);
			}
			return "/signin";
		}
		TestUser dbUser = userRepository.findByEmailAndPwd(user.getEmail(), user.getPwd());
		if(dbUser != null) {
			session.setAttribute("user_info", dbUser);
		}
		return "redirect:/";
	}
	
	@GetMapping("/signout")
	public String signout() {
		session.invalidate();
		return "redirect:/";
	}
}