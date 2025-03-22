package cookcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cookcloud.entity.MemberAllergyFood;
import cookcloud.entity.MemberAllergyFoodId;

@Repository
public interface MemberAllergyFoodRepository extends JpaRepository<MemberAllergyFood, MemberAllergyFoodId>{

}
