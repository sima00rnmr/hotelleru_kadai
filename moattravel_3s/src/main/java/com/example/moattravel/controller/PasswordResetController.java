package com.example.moattravel.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.moattravel.entity.PasswordResetToken;
import com.example.moattravel.entity.User;
import com.example.moattravel.repository.UserRepository;
import com.example.moattravel.service.PasswordResetService;

@Controller
public class PasswordResetController {
	private final UserRepository userRepository;
	private final PasswordResetService passwordResetService;
	private final PasswordEncoder passwordEncoder;
	private PasswordResetToken getValidToken(String token) {
	    PasswordResetToken t = passwordResetService.findByToken(token);
	    return passwordResetService.isValidToken(t) ? t : null;
	}

	public PasswordResetController(UserRepository userRepository, PasswordResetService passwordResetService,
			PasswordEncoder passwordEncoder) {
		this.passwordResetService = passwordResetService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/forgot-password")
	public String sendResetMail(@RequestParam String email) {

		User user = userRepository.findByEmail(email);

		if (user != null) {
			String token = passwordResetService.createToken(user);
			String url = passwordResetService.createResetUrl(token);

			System.out.println(url); // ← 仮入力
		}

		return "redirect:/login?resetRequested";
	}
	//パスワード変更画面
		@GetMapping("/forgot-password")
		public String showForgotPasswordForm() {
		    return "forgot-password";
		}
		
	//トークンのリンク先
	@GetMapping("/reset-password")
	public String showResetForm(@RequestParam String token, Model model) {

	    PasswordResetToken resetToken = getValidToken(token);

	    if (resetToken == null) {
	        model.addAttribute("errorMessage", "トークンが無効または期限切れです");
	        return "reset-password";
	    }

	    model.addAttribute("token", token);
	    return "reset-password";
	}

	//パスワードの更新について
	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam String token,
	        @RequestParam String password,
	        @RequestParam String confirmPassword,
	        Model model) {

	    if (!password.equals(confirmPassword)) {
	        model.addAttribute("token", token);
	        model.addAttribute("errorMessage", "パスワードが一致しません");
	        return "reset-password";
	    }

	    PasswordResetToken resetToken = getValidToken(token);

	    if (resetToken == null) {
	        return "redirect:/login?invalidToken";
	    }

	    User user = resetToken.getUser();
	    user.setPassword(passwordEncoder.encode(password));
	    userRepository.save(user);

	    passwordResetService.deleteToken(resetToken);

	    return "redirect:/login?resetSuccess";
	}
	

}
