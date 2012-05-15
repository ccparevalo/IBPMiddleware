package org.generationcp.middleware.pojos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "level_c")
public class CharacterLevel implements Serializable {

	private static final long serialVersionUID = -7870779107873158520L;

	public static final String GET_BY_OUNIT_ID_LIST = "select oi.ounitid, oi.factorid, f.fname, lc.lvalue " +
		"from oindex oi join obsunit ou on oi.ounitid = ou.ounitid " +
		"join level_c lc on lc.factorid = oi.factorid and lc.levelno = oi.levelno " +
		"join factor f on f.labelid = lc.labelid " +
		"where oi.ounitid in (:ounitIdList)";
	
	@EmbeddedId
	protected CharacterLevelPK id;
	
	@Column(name = "lvalue")
	private String value;
	
	public CharacterLevel() {
	}

	public CharacterLevel(CharacterLevelPK id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	public CharacterLevelPK getId() {
		return id;
	}

	public void setId(CharacterLevelPK id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
	   if (obj == null)
		   return false;
	   if (obj == this) 
		   return true; 
	   if (!(obj instanceof CharacterLevel)) 
		   return false;
	
	   CharacterLevel rhs = (CharacterLevel) obj;
	   return new EqualsBuilder()
	                 .appendSuper(super.equals(obj))
	                 .append(id, rhs.id)
	                 .isEquals();
	}
	
	@Override
	public int hashCode() 
	{
	     return new HashCodeBuilder(31, 77).
	       append(id).
	       toHashCode();
	}
	
	@Override
	public String toString() {
		return "CharacterLevel [id=" + id + ", value=" + value + "]";
	}

}