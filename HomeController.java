package com.skplanet.pinky.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.skplanet.pinky.domain.Member;
import com.skplanet.pinky.service.MemberService;
import com.skplanet.pinky.validator.MemberValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpSession httpSession) {
		
		if(httpSession.getAttribute("userLogin") != null)
			return "redirect:/booklist";
		
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
			
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String addMember(@ModelAttribute("member") Member member,
			BindingResult result,
			HttpSession httpSession,
			Model model) {
		
		new MemberValidator().validate(member, result);
		if(result.hasErrors()) {
			System.out.println("error!!!!!");
			return "redirect:/login";
		}
		
		memberService.addMember(member);
		
		httpSession.setAttribute("userLogin", member);
		
		return "redirect:/";
	}
}
