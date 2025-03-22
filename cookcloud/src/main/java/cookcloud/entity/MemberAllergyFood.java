package cookcloud.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEMBER_ALLERGY_FOOD")
@IdClass(MemberAllergyFoodId.class)
public class MemberAllergyFood implements Serializable {

	private static final long serialVersionUID = 655743065440798L;

	@Id
	@Column(name="MEM_ID", columnDefinition = "VARCHAR2(20)", nullable = false)
	private String memId;
	
	@ManyToOne
	@JoinColumn(name="MEM_ID", insertable = false, updatable = false)
	private Member member;

	@Id
	@Column(name="ALLERGY_ID", nullable = false)
	private Long allergyId;
	
	@ManyToOne
	@JoinColumn(name="ALLERGY_ID", insertable = false, updatable = false)
	private Allergy allergy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MEM_ALLERGY_INSERT_AT", nullable = false)
	private LocalDateTime memAllergyInsertAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MEM_ALLERGY_UPDATE_AT")
	private LocalDateTime memAllergyUpdateAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MEM_ALLERGY_DELETE_AT")
	private LocalDateTime memAllergyDeleteAt;

	@Column(name="MEM_ALLERGY_IS_DELETED", columnDefinition = "CHAR(1)", nullable = false)
	private String memAllergyIsDeleted;

}
