package com.sapanywhere.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.sapanywhere.app.properties.MailProperties;

@Configuration
public class MailConfiguration {

	@Autowired
	private MailProperties mailProperties;

	@Bean
	public JavaMailSender mailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(this.mailProperties.getHost());
		mailSender.setPort(this.mailProperties.getPort());
		mailSender.setUsername(this.mailProperties.getUsername());
		mailSender.setPassword(this.mailProperties.getPassword());
		mailSender.setProtocol(this.mailProperties.getProtocol());
		return mailSender;
	}

}
