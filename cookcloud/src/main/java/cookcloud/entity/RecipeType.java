package cookcloud.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "RECIPETYPE")
public class RecipeType implements Serializable {

	private static final long serialVersionUID = 261483928476605L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="RECIPETYPE_ID", nullable = false)
	private Long recipeTypeId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RECIPETYPE_INSERT_AT", nullable = false)
	private LocalDateTime recipeTypeInsertAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RECIPETYPE_UPDATE_AT")
	private LocalDateTime recipeTypeUpdateAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RECIPETYPE_DELETE_AT")
	private LocalDateTime recipeTypeDeleteAt;

	@Column(name="RECIPETYPE_IS_DELETED", columnDefinition = "CHAR(1)", nullable = false)
	private String recipeTypeIsDeleted;

	@Column(name="RECIPETYPE_CODE", nullable = false)
	private Long recipeTypeCode;

	@Column(name="RECIPE_ID", nullable = false)
	private Long recipeId;
	
	@ManyToOne
	@JoinColumn(name="RECIPE_ID", insertable = false, updatable = false)
	private Recipe recipe;
	
}
