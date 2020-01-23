package com.NowakArtur97.WorldOfManga.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class MainController {

	@GetMapping
	public String showMainPage() {
		
		return "views/main";
	}
}
