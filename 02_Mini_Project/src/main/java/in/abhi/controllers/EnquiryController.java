package in.abhi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.abhi.binding.DashboardResponse;
import in.abhi.binding.EnquiryForm;
import in.abhi.binding.EnquirySearchCriteria;
import in.abhi.entity.StudentEnqEntity;
import in.abhi.service.EnquiryService;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private EnquiryService enqService;
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		
		Integer userId= (Integer) session.getAttribute("userId");
		DashboardResponse dashboardData = enqService.getDashboardData(userId);
		
		model.addAttribute("dashboardData", dashboardData);
		
		
		return "dashbord";
	}
	
	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj,Model model) {
//		System.out.println(formObj);
		//save the data
		boolean status = enqService.saveEnquriry(formObj);
		
		if(status) {
			model.addAttribute("succMsg", "Enquiry Added");
		}else {
			model.addAttribute("errMsg", "Problem Occured");
		}
		
		return "add-enquiry";
	}
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		
		//get courses data from the dropdown
		List<String> courses = enqService.getCourses();
		
		//get enq status data from drop down
		List<String> enqStatuses = enqService.getEnqStatuses();
		//create binding cls obj
		EnquiryForm formObj=new EnquiryForm();
		
		//set data in model obj
		model.addAttribute("coursesNames", courses);
		model.addAttribute("statusNames", enqStatuses);
		model.addAttribute("formObj", formObj);
		
		return "add-enquiry";
	}
	private void initForm(Model model) {
		//get course from for down
		List<String> courses = enqService.getCourses();
		
		//get status from for down
		List<String> enqStatuses = enqService.getEnqStatuses();
		
		//creating binding cls obj
		EnquiryForm formObj=new EnquiryForm();
		
		//Set data in model obj

		model.addAttribute("courseName", courses);
		model.addAttribute("statusName", enqStatuses);
	
		
		model.addAttribute("formObj", formObj);
		
	}
	
	@GetMapping("/enquiries")
	public String viewEnquiriesPage(Model model) {
		initForm(model);
		List<StudentEnqEntity> enquiries = enqService.getEnquries();
		model.addAttribute("enquiries", enquiries);
		return "view-enquiries";
	}
	
	@GetMapping("/filter-enquiries")
	public String getFilteredEnqs( @RequestParam String cname, 
			@RequestParam String status,
			@RequestParam String mode,Model model) {
			
		EnquirySearchCriteria criteria=new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setEnqStatus(mode);
		criteria.setClassMode(status);
		System.out.println(criteria);
		
		Integer userId= (Integer) session.getAttribute("userId");
		List<StudentEnqEntity> filteredEnqs = enqService.getFilteredEnqs(criteria, userId);

		model.addAttribute("enquiries", filteredEnqs);
		
		return "filter-enquiries";
	}

}













