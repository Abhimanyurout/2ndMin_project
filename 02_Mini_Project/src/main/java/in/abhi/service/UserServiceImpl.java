package in.abhi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import in.abhi.binding.LoginForm;
import in.abhi.binding.SignUpForm;
import in.abhi.binding.UnlockForm;
import in.abhi.entity.UserDtlsEntity;
import in.abhi.repo.UserDtlsRepo;
import in.abhi.util.EmailUtils;
import in.abhi.util.PwdUtils;
import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public String login(LoginForm form) {
		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());
		
		if(entity == null) {
			return "Invalid Credentials";
		}
		if(entity.getAccStatus().equals("LOCKED")) {
			return "Your Account is Locked";
		}
		
		//create session and store user data in session
		
		session.setAttribute("userId", entity.getUserId());
		return "Success";
	}

	@Override
	public boolean signUp(SignUpForm form) {
		
		UserDtlsEntity user = userDtlsRepo.findByEmail(form.getEmail());
		
		if(user !=null) {
			return false;
		}
		
		//TODO:copy data form binding object to entity obj
		UserDtlsEntity entity=new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);
		
		
		// TODO: generate random pwd
		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);
		
		//TODO:Set Account status as Locked
		entity.setAccStatus("LOCKED");
		
		//TODO:Insert record
		userDtlsRepo.save(entity);
		
		//TODO:Send mail to user to  unlock Account
		
		String to=form.getEmail();
		String subject="Unlock your Account | Yours Avi?";
		
		StringBuffer body=new StringBuffer("");
		body.append("<h1>Use the below temporary password and unlock your Account</h1>");
		body.append("Temporary pwd : "+ tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email="+to+"\">Click here to unlock your Account</a>");
		
		
		emailUtils.sendEmail(to, subject, body.toString());
		
		return true;
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {
		
		UserDtlsEntity entity = userDtlsRepo.findByEmail(form.getEmail());
		
		if(entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus("Unlocked");
			userDtlsRepo.save(entity);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean forgotPwd(String email) {
		//check record present in db or not
		UserDtlsEntity entity = userDtlsRepo.findByEmail(email);
		
		//if record not available send invalid Email id
		if(entity==null) {
			return false; //"invalid Email Id"
		}
		
		//if record is available send pwd to mail and send succ Msg
		
		String Subject="Recover your Password";
		String body="Your PWD :: "+entity.getPwd();
		emailUtils.sendEmail(email, Subject, body);
		
		
		return true;//
	}

}





















