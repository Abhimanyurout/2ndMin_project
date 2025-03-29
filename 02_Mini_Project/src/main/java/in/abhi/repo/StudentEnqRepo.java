package in.abhi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.abhi.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer>{

}
