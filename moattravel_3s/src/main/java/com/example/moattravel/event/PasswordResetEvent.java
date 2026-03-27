package com.example.moattravel.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.moattravel.entity.User;

import lombok.Getter;

//Data使おうと思ったけどsetter使わないならGetterを使えばいいんじゃない？と回答あった
@Getter
public class PasswordResetEvent extends ApplicationEvent {

	private final User user;
	private final String url;

	public PasswordResetEvent(Object source, User user, String url) {
		super(source);
		this.user = user;
		this.url = url;
	}

	@Component
	public class PasswordResetEventListener {

		private final JavaMailSender mailSender;

		public PasswordResetEventListener(JavaMailSender mailSender) {
			this.mailSender = mailSender;
		}
	}
}