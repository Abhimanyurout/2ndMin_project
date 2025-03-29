package in.abhi.service;

import java.util.List;

import in.abhi.binding.DashboardResponse;
import in.abhi.binding.EnquiryForm;
import in.abhi.binding.EnquirySearchCriteria;
import in.abhi.entity.StudentEnqEntity;

public interface EnquiryService {
	
	public DashboardResponse getDashboardData(Integer userId);
	
	public List<String> getCourses();
	
	public List<String> getEnqStatuses();
	
	
	public boolean saveEnquriry(EnquiryForm form);
	
	public List<StudentEnqEntity> getEnquries();
	
	public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria,Integer userId);
}
