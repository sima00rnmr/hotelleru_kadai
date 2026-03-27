package com.example.moattravel.event;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetEventListener {

    private final JavaMailSender mailSender;

    public PasswordResetEventListener(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @EventListener
    public void handleEvent(PasswordResetEvent event) {


        // メール送信
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(event.getUser().getEmail());
        mail.setSubject("パスワードリセット");
        mail.setText("以下のリンクからパスワードを再設定してください\n" + event.getUrl());

        mailSender.send(mail);
    }
}