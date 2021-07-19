package com.example.mentor.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.mentor.model.User;
import com.example.mentor.model.UserBoard;
import com.example.mentor.repository.UserBoardRepository;

@Controller
public class UserBoardController {

	@Autowired
	UserBoardRepository userboardRepository;
	@Autowired
	HttpSession session;

	@GetMapping("/userboard/write")
	public String userBoardWrite() {
		return "userboard/write";
	}

	@PostMapping("/userboard/write")
	public String userboardWritePost(@ModelAttribute UserBoard userboard){
		User user = (User) session.getAttribute("user_info");
		String userId = user.getEmail();
		userboard.setUserId(userId);
		userboardRepository.save(userboard);
		return "board/write";
	}

	@GetMapping("/userboard")
	public String userboard(Model model) {
		List<UserBoard> list = userboardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("list", list);
		return "userboard/list";
	}

	@GetMapping("/userboard/{id}")
	public String userboardView(Model model, @PathVariable("id") long id) {
		Optional<UserBoard> data = userboardRepository.findById(id);
		UserBoard userboard = data.get();
		model.addAttribute("userboard", userboard);
		return "userboard/view";
	}

	@GetMapping("/userboard/update/{id}")
	public String userboardUpdate(Model model, @PathVariable("id") long id) {
		Optional<UserBoard> data = userboardRepository.findById(id);
		UserBoard userboard = data.get();
		model.addAttribute("userboard", userboard);
		return "userboard/update";
	}

	@PostMapping("/userboard/update/{id}")
	public String userboardUpdatePost(@ModelAttribute UserBoard userboard, @PathVariable("id") long id) {
		User user = (User) session.getAttribute("user_info");
		String userId = user.getEmail();
		userboard.setUserId(userId);
		userboard.setId(id);
		userboardRepository.save(userboard);
		return "redirect:/userboard/" + id;
	}

	@GetMapping("/userboard/delete/{id}")
	public String userboardDelete(@PathVariable("id") long id) {
		userboardRepository.deleteById(id);
		return "redirect:/userboard";
	}

}