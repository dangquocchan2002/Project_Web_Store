package poly.store.controller;

import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import poly.store.dao.AccountDAO;
import poly.store.entity.Account;
import poly.store.service.EmailService;

@Controller
public class AccountController {
	@Autowired
	AccountDAO dao;
	
	
	
	@Autowired
	EmailService emailService;
	
	@RequestMapping("/security/sign-up")
	public String signup(Model model) {
		return "security/sign-up";
	}
	
	@RequestMapping("signup")
	public String dangKy(Model model, Account account ) {
		
		
		dao.save(account);
		model.addAttribute("message", "Dang ki thanh cong!");
		
		
		
		return "redirect:/security/login/form";
	}
	
	@RequestMapping("/security/forgot")
	public String forgot(Model model) {
		
		return "security/forgot";
		
	}
	
	@RequestMapping("send")
	public String sendCode(Model model, @RequestParam("username") String username, 
			@RequestParam("email") String email) {
		Account account = dao.findById(username).get();
		try {
			if (account.getUsername().isEmpty()) {
				model.addAttribute("message", "Tên người dùng không tồn tại !!!");
			} else {
				emailService.send(email, "Mật khẩu của bạn là:", account.getPassword());
				model.addAttribute("message", "Mật khẩu của bạn đã được gửi về email !!!");
			}
			model.addAttribute("message", "Mật khẩu của bạn đã được gửi về email !!!");
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		return "redirect:/security/forgot";
	}

}
