package com.sapanywhere.app.service;

import java.util.Locale;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import com.sapanywhere.app.entity.Leave;
import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.properties.MailProperties;

@Service
public class MailService {

	private static final String LeaveRequestTemplatePath="/templates/leave/request";
	private static final String LeaveRequestSubject="email.leave.request.subject";
	
	private static Logger logger = Logger.getLogger(MailService.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailProperties mailProperties;

	@Autowired
	private ApproverService approverService;

	@Autowired
	private SpringTemplateEngine springTemplateEngine;
	
	@Autowired
	private LocaleService localeService;
	
	@Autowired
	private FormatterService formatterService;


	public void sendLeaveRequestMail(Leave leave) {
		try {
			
			User approver = this.approverService.getApprover(leave.getUser());
			Locale locale = LocaleContextHolder.getLocale();
			final Context context = new Context(locale);
			context.setVariable("leave", leave);
			context.setVariable("approver",approver);
			context.setVariable("totalHours", this.formatterService.formatTotalHours(leave.getTotal()));
			final String subject = localeService.getMessage(LeaveRequestSubject,leave.getUser().getFullName());
			final String htmlContent = this.springTemplateEngine.process(LeaveRequestTemplatePath, context);

			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setFrom(this.mailProperties.getFrom());
			message.setTo(approver.getEmail());
			message.setSubject(subject);
			message.setText(htmlContent, true);
			this.mailSender.send(mimeMessage);
			logger.info("send mail success.");
		} catch (Exception ex) {
			logger.error("Send mail failed.");
			logger.error(ex);
		}
	}
}
