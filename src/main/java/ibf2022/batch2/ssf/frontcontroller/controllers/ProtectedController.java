package ibf2022.batch2.ssf.frontcontroller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProtectedController {

	// TODO Task 5
	// Write a controller to protect resources rooted under /protected

	@GetMapping("/protected/**")
	public String handleProtectedRequest() {

		return "view1";
    }
	
}
