package in.abhi.service;

import in.abhi.binding.LoginForm;
import in.abhi.binding.SignUpForm;
import in.abhi.binding.UnlockForm;

public interface UserService {
	public String login(LoginForm form);
	
	public boolean signUp(SignUpForm form);
	
	public boolean unlockAccount(UnlockForm form);
	
	public boolean forgotPwd(String email);
}
