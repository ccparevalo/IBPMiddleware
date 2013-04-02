/*******************************************************************************
 * Copyright (c) 2012, All Rights Reserved.
 * 
 * Generation Challenge Programme (GCP)
 * 
 * 
 * This software is licensed for use under the terms of the GNU General Public
 * License (http://bit.ly/8Ztv8M) and the provisions of Part F of the Generation
 * Challenge Programme Amended Consortium Agreement (http://bit.ly/KQX1nL)
 * 
 *******************************************************************************/

package org.generationcp.middleware.pojos.dms;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * http://gmod.org/wiki/Chado_Tables#Table:_cvtermsynonym
 * 
 * A cvterm actually represents a distinct class or concept. A concept can be referred to 
 * by different phrases or names. In addition to the primary name (cvterm.name) there can 
 * be a number of alternative aliases or synonyms. 
 * For example, "T cell" as a synonym for "T lymphocyte".
 * 
 * @author Darla Ani
 *
 */
@Entity
@Table( name = "cvtermsynonym",
		uniqueConstraints = {
		@UniqueConstraint(columnNames = { "cvterm_id", "synonym" }) })
public class CVTermSynonym implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Basic(optional = false)
	@Column(name = "cvtermsynonym_id")
	private Long cvTermSynonymId;
	
	
	/**
	 * Related CVTerm entity.
	 */
	@OneToOne
	@JoinColumn(name = "cvterm_id")
	private CVTerm cvTerm;
	
	/**
	 * Alias or synonym for related CV Term
	 */
	@Basic(optional = false)
	@Column(name = "synonym")
	private String synonym;

	/**
	 * Related CVTerm type. A synonym can be exact, narrower, or broader than.
	 */
	@OneToOne
	@JoinColumn(name = "type_id")
	private CVTerm type;
	
	public CVTermSynonym(){
		
	}
	
	public CVTermSynonym(Long id){
		this.cvTermSynonymId = id;
	}

	public Long getCvTermSynonymId() {
		return cvTermSynonymId;
	}

	public void setCvTermSynonymId(Long id) {
		this.cvTermSynonymId = id;
	}

	public CVTerm getCvTerm() {
		return cvTerm;
	}

	public void setCvTerm(CVTerm cvTerm) {
		this.cvTerm = cvTerm;
	}

	public String getSynonym() {
		return synonym;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	public CVTerm getType() {
		return type;
	}

	public void setType(CVTerm type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cvTermSynonymId == null) ? 0 : cvTermSynonymId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CVTermSynonym))
			return false;
		
		CVTermSynonym other = (CVTermSynonym) obj;
		if (cvTermSynonymId == null) {
			if (other.cvTermSynonymId != null)
				return false;
		} else if (!cvTermSynonymId.equals(other.cvTermSynonymId))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CVTermSynonym [cvTermSynonymId=");
		builder.append(cvTermSynonymId);
		builder.append(", cvTerm=");
		builder.append(cvTerm);
		builder.append(", synonym=");
		builder.append(synonym);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}
	

}