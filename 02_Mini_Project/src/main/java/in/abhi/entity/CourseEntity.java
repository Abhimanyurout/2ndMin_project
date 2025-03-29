package in.abhi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "AIT_COURSES")
@Data
public class CourseEntity {

    @Id
    @GeneratedValue
    private Integer id;

//    @Column(name = "course_name", nullable = false)
    private String courseName;

//    @ManyToOne
//    @JoinColumn(name = "id", referencedColumnName = "status_id", nullable = false)
//    private EnqStatusEntity status;

}
