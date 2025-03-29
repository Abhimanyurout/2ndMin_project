package in.abhi.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "AIT_Statuses")
@Data
public class EnqStatusEntity {
	
	 @Id
	 @GeneratedValue
	    private Integer id;

	    

//	    @Column(name = "status_name", nullable = false, unique = true)
	    private String statusName;

//	    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
//	    private List<CourseEntity> courses;

}









