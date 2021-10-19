package com.germanium.lms.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
*
* @author Arjun Krishna Shenoy
*/

@Entity
@Table(name = "substitution")
@ApiModel(value = "Substitution", description = "This class holds the substitute user details")
@Getter
@Setter
public class Substitution {

	@EmbeddedId
	private SubstitutionId id;
	
}
