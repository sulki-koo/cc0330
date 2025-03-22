package cookcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cookcloud.entity.Allergy;

public interface AllergyRepository extends JpaRepository<Allergy, Long>{

}
