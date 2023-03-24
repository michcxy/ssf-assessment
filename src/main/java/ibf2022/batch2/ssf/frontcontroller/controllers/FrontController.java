package ibf2022.batch2.ssf.frontcontroller.controllers;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import ibf2022.batch2.ssf.frontcontroller.services.AuthenticationService;
import ibf2022.batch2.ssf.model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("attempts")
public class FrontController {

	@Autowired
    private AuthenticationService authSvc;

	// TODO: Task 2, Task 3, Task 4, Task 6

    @GetMapping(path={"/", "/index.html"})
    public String getIndex(Model m, HttpSession sess){
        sess.invalidate(); 
        m.addAttribute("user", new User());
        return "view0";
		
    }
	@PostMapping(path="/login")
    public String postUser(Model model, HttpSession sess, @Valid User user, BindingResult bindings) throws Exception{

		//count login attempts
		Integer attempts = (Integer) sess.getAttribute("attempts");
		if (attempts == null) {
			attempts = 0;
		}

		//User u = (User)sess.getAttribute("user");
		//model.addAttribute(user);
        if(bindings.hasErrors()){
            return "view0";
        }

        List<ObjectError> errors = authSvc.authenticate(user.getUsername(), user.getPassword());
        if(!errors.isEmpty()){
            //add individual errors into binding result
            for(ObjectError e: errors){
                bindings.addError(e);
            }
			attempts++;
			user.attempted = true;
			sess.setAttribute("attempts", attempts);
			if (attempts >= 3) {
				authSvc.disableUser(user.getUsername());
				sess.setAttribute("attempts", 0);
				return "view2";
			}
			model.addAttribute("errorMessage", "Invalid username or password");
            return "view0";
        }

		//reset login count
		sess.setAttribute("attempts", 0);
        //sess.setAttribute("user", user);
        model.addAttribute("user", new User());
		return "redirect:/protected/";
	}

	//generate random captcha
	public void generateCaptcha(){

		double answer;
		String captcha = "";
        SecureRandom rand = new SecureRandom();
        int num1 = rand.nextInt(50)+1;
        int num2 = rand.nextInt(50)+1;
        int operation = rand.nextInt(3)+1;

			switch(operation){
				case 1:
					answer = num1+num2;
					captcha = Integer.toString(num1) + "+" + Integer.toString(num2) + "?";
				case 2:
					answer = num1-num2;
					captcha = Integer.toString(num1) + "-" + Integer.toString(num2) + "?";
				case 3:
					answer = num1*num2;
					captcha = Integer.toString(num1) + "*" + Integer.toString(num2) + "?";
				case 4: 
					answer = num1/num2;
					captcha = Integer.toString(num1) + "/" + Integer.toString(num2) + "?";
			}
		
    }

}
