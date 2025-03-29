package cookcloud.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RECIPETAG")
@IdClass(RecipeTagId.class)
public class RecipeTag implements Serializable {

	private static final long serialVersionUID = 472551661700793L;

	@Id
	@Column(name="RECIPE_ID")
	private Long recipeId;
	
	@ManyToOne
	@JoinColumn(name="RECIPE_ID", insertable = false, updatable = false)
	private Recipe recipe;

	@Id
	@Column(name="HASH_ID")
	private Long hashId;
	
	@ManyToOne
	@JoinColumn(name="HASH_ID", insertable = false, updatable = false)
	private Hashtag hashtag;

}