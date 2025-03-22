package cookcloud.entity;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberAllergyFoodId {

	private String memId;
    private Long allergyId;
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MemberAllergyFoodId memberAllergyFoodId = (MemberAllergyFoodId) obj;
        return Objects.equals(memId, memberAllergyFoodId.memId) &&
                Objects.equals(allergyId, memberAllergyFoodId.allergyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memId, allergyId);
    }
    
}
