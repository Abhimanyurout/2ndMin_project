package in.abhi.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "AIT_USER_DTLS")
@Data
public class UserDtlsEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String name;
	private String email;
	private String pwd;
	private Long phn;
	private String accStatus;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<StudentEnqEntity> enquiries;
}














