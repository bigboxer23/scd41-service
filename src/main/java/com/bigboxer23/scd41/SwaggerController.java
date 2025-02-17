package com.bigboxer23.scd41;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Hidden
public class SwaggerController {

	/** Catch swagger requests and redirect */
	@GetMapping({"/swagger", "/swagger/"})
	public String swagger() {
		return "redirect:/swagger-ui.html";
	}
}
