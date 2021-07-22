package com.example.mentor.controller;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.board.model.TestBoard;
import com.project.board.model.TestBoard2;
import com.project.board.model.TestUser;
import com.project.board.repository.BoardRepository;
import com.project.board.repository.BoardRepository2;

@Controller
public class BoardController2 {
	
	@Autowired
	BoardRepository2 boardRepository2;
	
	@Autowired
	HttpSession session;
	
	@GetMapping("/board2/write")
	public String boardWrite() {
		return "board2/write";
	}
	@PostMapping("/board2/write")
	public String boardWritePost(@ModelAttribute @Valid TestBoard2 board2, BindingResult result) {
		if( result.hasErrors() ) {
			List<ObjectError> list = result.getAllErrors();
			for( ObjectError error : list ) {
				System.out.println(error);
			}
			return "/board2/write";
		}
		TestUser user = (TestUser) session.getAttribute("user_info");
		String userId = user.getEmail();
		board2.setUserId(userId);
		boardRepository2.save(board2);
		return "redirect:/board2/";
	}
	
	@GetMapping("/board2")
	public String board2(Model model) {
//		List<TestBoard> list = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//		model.addAttribute("list", list);
		return "board2/search";
	}
	
	@GetMapping("/board2/list")
	public String boardlist(Model model) {
		List<TestBoard2> list = boardRepository2.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("list", list);
		return "board2/list";
	}

	@GetMapping("/board2/search")
	public String search(@RequestParam(value="keyword") String keyword, Model model) {
		List<TestBoard2> list = boardRepository2.findByTitleContaining(keyword);
		model.addAttribute("list", list);
		return "/board2/list";
	}
	
	@GetMapping("/board2/{id}")
	public String boardView(Model model, @PathVariable("id") long id) {
		Optional<TestBoard2> data = boardRepository2.findById(id);
		TestBoard2 board2 = data.get();
		long board_views = board2.getViews();
		long bv_plus = board_views + 1;
		board2.setViews(bv_plus);
		boardRepository2.save(board2);
		model.addAttribute("board", board2);
		return "board2/view";
	}
	
	
	@GetMapping("/board2/update/{id}")
	public String boardUpdate(Model model, @PathVariable("id") long id) {
		Optional<TestBoard2> data = boardRepository2.findById(id);
		TestBoard2 board2 = data.get();
		model.addAttribute("board", board2);
		return "board/update";
	}
	@PostMapping("/board2/update/{id}")
	public String boardUpdatePost(@ModelAttribute @Valid TestBoard2 board2, @PathVariable("id") long id, BindingResult result) {
		if( result.hasErrors() ) {
			List<ObjectError> list = result.getAllErrors();
			for( ObjectError error : list ) {
				System.out.println(error);
			}
			return "/board2/update/{id}";
		}
		TestUser user = (TestUser) session.getAttribute("user_info");
		String userId = user.getEmail();
		board2.setUserId(userId);
		board2.setId(id);
		boardRepository2.save(board2);
		return "redirect:/board2/" + id;
	}
	
	@GetMapping("/board2/delete/{id}")
	public String boardDelete(@PathVariable("id") long id) {
		boardRepository2.deleteById(id);
		return "redirect:/board2";
	}
	
}