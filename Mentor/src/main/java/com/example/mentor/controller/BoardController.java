package com.example.mentor.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.mentor.model.TestBoard;
import com.example.mentor.model.TestBoard2;
import com.example.mentor.model.TestUser;
import com.example.mentor.repository.BoardRepository;

@Controller
public class BoardController {
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	HttpSession session;
	
	@GetMapping("/board/write")
	public String boardWrite() {
		return "board/write";
	}
	@PostMapping("/board/write")
	public String boardWritePost(@ModelAttribute @Valid TestBoard board, BindingResult result) {
		if( result.hasErrors() ) {
			List<ObjectError> list = result.getAllErrors();
			for( ObjectError error : list ) {
				System.out.println(error);
			}
			return "/board/write";
		}
		TestUser user = (TestUser) session.getAttribute("user_info");
		String userId = user.getEmail();
		board.setUserId(userId);
		boardRepository.save(board);
		return "redirect:/board";
	}
	
	@GetMapping("/board")
	public String board(Model model) {
//		List<TestBoard> list = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//		model.addAttribute("list", list);
		return "board/search";
	}
	
	@GetMapping("/board/search")
	public String search(@RequestParam(value="keyword") String keyword, Model model) {
		List<TestBoard> list = boardRepository.findByTitleContaining(keyword);
		model.addAttribute("list", list);
		return "board/list";
	}	
	
	@GetMapping("/board/{id}")
	public String boardView(Model model, @PathVariable("id") long id) {
		Optional<TestBoard> data = boardRepository.findById(id);
		TestBoard board = data.get();
		long board_views = board.getViews();
		long bv_plus = board_views + 1;
		board.setViews(bv_plus);
		boardRepository.save(board);
		model.addAttribute("board", board);
		return "board/view";
	}
	
	
	@GetMapping("/board/update/{id}")
	public String boardUpdate(Model model, @PathVariable("id") long id) {
		Optional<TestBoard> data = boardRepository.findById(id);
		TestBoard board = data.get();
		model.addAttribute("board", board);
		return "board/update";
	}
	@PostMapping("/board/update/{id}")
	public String boardUpdatePost(@ModelAttribute @Valid TestBoard board, @PathVariable("id") long id, BindingResult result) {
		if( result.hasErrors() ) {
			List<ObjectError> list = result.getAllErrors();
			for( ObjectError error : list ) {
				System.out.println(error);
			}
			return "/board/update/{id}";
		}
		TestUser user = (TestUser) session.getAttribute("user_info");
		String userId = user.getEmail();
		board.setUserId(userId);
		board.setId(id);
		boardRepository.save(board);
		return "redirect:/board/" + id;
	}
	
	@GetMapping("/board/delete/{id}")
	public String boardDelete(@PathVariable("id") long id) {
		boardRepository.deleteById(id);
		return "redirect:/board";
	}
	
	@GetMapping("/board/list")
	public String boardlist(Model model) {
		List<TestBoard> list = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "views"));
		model.addAttribute("list", list);
		return "board/list";
	}
}