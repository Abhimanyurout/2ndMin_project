package in.abhi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.abhi.binding.LoginForm;
import in.abhi.binding.SignUpForm;
import in.abhi.binding.UnlockForm;
import in.abhi.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String handelSignUp(@ModelAttribute("user") SignUpForm form, Model model) {
		
		boolean status = userService.signUp(form);
		
		if (status) {
			model.addAttribute("succMsg", "Check your Email");
		} else {
			model.addAttribute("errMsg","Choose Unique Email ");
		}
		
		return "signup";
	}
	
	
	
	
	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {
		
		UnlockForm unlockFormObj=new UnlockForm();
		unlockFormObj.setEmail(email);
		
		model.addAttribute("unlock", unlockFormObj);
		
		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock,Model model) {
		
		System.out.println(unlock);
		if(unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			
			boolean status = userService.unlockAccount(unlock);
			if(status) {
				model.addAttribute("succMsg", "Your Account unlocked successfuly");
			}else {
				model.addAttribute("errMsg", "Given Temporary pwd is incorrect, check your email..");

			}
		}else {
			model.addAttribute("errMsg", "New pwd and confirm pwd should be matched");
		}
		
		
		return "unlock";
	}
	
	
	
	
	
	
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
		
		String status = userService.login(loginForm);
		
		if(status.contains("Success")) {
			//Display Dashboard
			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg",status);
		
		
		return "login";
	}
	
	
	
	
	
	
	@GetMapping("/forgot")
	public String forgotPwdPage() {
		return "forgotPwd";
	}
	
	@PostMapping("/forgotPwd")
	public String forgotPwd(@RequestParam("email") String email,Model model) {
		System.out.println(email);
		
		boolean status = userService.forgotPwd(email);
		
		if(status) {
			//send succMsg
			model.addAttribute("succMsg", "Password sent to your mail");
		}else {
			//send error msg
			model.addAttribute("errMsg", "invalid Email Id");
			
		}
		
		
		return "forgotPwd";
	}
	
	
}



















