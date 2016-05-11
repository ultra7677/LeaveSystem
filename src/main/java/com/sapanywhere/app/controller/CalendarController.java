package com.sapanywhere.app.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sapanywhere.app.exception.BusinessException;
import com.sapanywhere.app.model.calendar.CalendarPage;
import com.sapanywhere.app.model.calendar.HolidayForm;
import com.sapanywhere.app.service.HolidayService;
import com.sapanywhere.app.service.user.UserInfo;

@Controller
public class CalendarController {

	private static Logger logger = Logger.getLogger(CalendarController.class);

	@Autowired
	private HolidayService holidayService;

	@RequestMapping(value = "/calendar.html", method = RequestMethod.GET)
	public String indexPage(Model model) {
		logger.info("load calendar.html");
		CalendarPage calendarPage = new CalendarPage(
				this.holidayService.findAllHolidaysThisYear());
		model.addAttribute(calendarPage);
		return "/calendar/index";
	}

	@RequestMapping(value = "/createHoliday.html", method = RequestMethod.GET)
	public String createPage(HolidayForm holidayForm) {
		logger.info("load createHoliday.html");
		return "/calendar/create";
	}

	@RequestMapping(value = "/createHoliday", method = RequestMethod.POST)
	public String createHoliday(HolidayForm holidayForm, BindingResult result) {
		if (result.hasErrors()) {
			return "/calendar/create";
		}
		try {
			this.holidayService.create(holidayForm.parse());
		} catch (BusinessException ex) {
			result.reject(ex.getCode());
			return "/calendar/create";
		}
		return "redirect:/calendar.html";
	}
	
	@RequestMapping(value = "/holiday({id:[0-9]+})", method = RequestMethod.DELETE)
	public void deleteHoliday(@PathVariable Long id,
			@AuthenticationPrincipal UserInfo userInfo){
		this.holidayService.delete(id, userInfo.getUser());
	}
}
