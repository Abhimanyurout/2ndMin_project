package in.abhi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import in.abhi.binding.DashboardResponse;
import in.abhi.binding.EnquiryForm;
import in.abhi.binding.EnquirySearchCriteria;
import in.abhi.entity.CourseEntity;
import in.abhi.entity.EnqStatusEntity;
import in.abhi.entity.StudentEnqEntity;
import in.abhi.entity.UserDtlsEntity;
import in.abhi.repo.CourseRepo;
import in.abhi.repo.EnqStatusRepo;
import in.abhi.repo.StudentEnqRepo;
import in.abhi.repo.UserDtlsRepo;
import jakarta.servlet.http.HttpSession;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private CourseRepo coursesRepo;
	
	@Autowired
	private StudentEnqRepo enqRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public DashboardResponse getDashboardData(Integer userId) {
		
		DashboardResponse response=new DashboardResponse();
		
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		
		if(findById.isPresent()) {
			UserDtlsEntity userEntity = findById.get();
			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();
			
			Integer total = enquiries.size();
			
			Integer enrolledCnt = enquiries.stream()
					.filter(e -> e.getEnqStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();
			
			Integer lostCnt = enquiries.stream()
					.filter(e -> e.getEnqStatus().equals("Lost"))
					.collect(Collectors.toList()).size();
			
			response.setTotalEnquriesCnt(total);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);
			
			
		}
		
		return response;
	}
	
	@Override
	public List<String> getCourses() {
		List<CourseEntity> findAll = coursesRepo.findAll();
		
		List<String> names=new ArrayList<String>();
		
		for(CourseEntity entity: findAll) {
			names.add(entity.getCourseName());			
		}		
		
		return names;
	}

	@Override
	public List<String> getEnqStatuses() {
		List<EnqStatusEntity> findAll = statusRepo.findAll();
		
		List<String> statusList=new ArrayList<String>();
		
		for(EnqStatusEntity entity:findAll) {
			statusList.add(entity.getStatusName());
		}
		return statusList;
	}

	

	@Override
	public boolean saveEnquriry(EnquiryForm form) {
		// TODO Auto-generated method stub
		StudentEnqEntity enqEntity=new StudentEnqEntity();
		BeanUtils.copyProperties(form, enqEntity);
		
		Integer userId= (Integer) session.getAttribute("userId");
		
		UserDtlsEntity userEntity = userDtlsRepo.findById(userId).get();
		enqEntity.setUser(userEntity);
		
		enqRepo.save(enqEntity);
		
		return true;
	}
	
	
	@Override
	public List<StudentEnqEntity> getEnquries() {
		
		Integer userId =(Integer) session.getAttribute("userId");
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		
		if(findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			return enquiries;
		}
		
		return null;
	}

	@Override
	public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria,Integer userId) {
		// TODO Auto-generated method stub
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
				
				if(findById.isPresent()) {
					UserDtlsEntity userDtlsEntity = findById.get();
					List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();

					if(null !=criteria.getCourseName() && ! "".equals(criteria.getCourseName())) {
						enquiries=enquiries.stream()
								.filter(e -> e.getCourseName().equals(criteria.getCourseName()))
								.collect(Collectors.toList());
					}
					
					if (criteria.getEnqStatus() != null && !criteria.getEnqStatus().isEmpty()) {
					    enquiries = enquiries.stream()
					            .filter(e -> e.getEnqStatus().equals(criteria.getEnqStatus()))
					            .collect(Collectors.toList());
					}

					if (criteria.getClassMode() != null && !criteria.getClassMode().isEmpty()) {
					    enquiries = enquiries.stream()
					            .filter(e -> e.getClassMode().equals(criteria.getClassMode()))
					            .collect(Collectors.toList());
					}

					
					return enquiries;
				}
				
				
				
		return null;
	}

}









