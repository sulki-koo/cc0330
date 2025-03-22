package cookcloud.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ALLERGY")
public class Allergy implements Serializable {

	private static final long serialVersionUID = 766982821103295L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ALLERGY_ID", nullable = false)
	private Long allergyId;

	@Column(name = "ALLERGY_NAME",  columnDefinition = "NVARCHAR2(20)", nullable = false)
	private String allergyName;

	@Column(name = "ALLERGY_DESCRIPTION",  columnDefinition = "NVARCHAR2(200)")
	private String allergyDescription;

}
