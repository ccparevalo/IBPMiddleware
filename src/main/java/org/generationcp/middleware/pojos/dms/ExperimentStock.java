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
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * http://gmod.org/wiki/Chado_Natural_Diversity_Module#Table:_nd_experiment_stock
 * 
 * Part of a stock or a clone of a stock that is used in an experiment.
 * 
 * @author tippsgo
 *
 */
@Entity
@Table(name = "nd_experiment_stock")
public class ExperimentStock implements Serializable {

	private static final long serialVersionUID = -3361812145612014083L;

	@Id
	@Basic(optional = false)
	@Column(name = "nd_experiment_stock_id")
	private Integer experimentStockId;
	
	/**
	 * Stock used in the extraction or the corresponding stock for the clone.
	 */
	@Basic(optional = false)
	@Column(name = "stock_id")
	private Integer stockId;
	
	/**
	 * The Experiment object.
	 */
	@Basic(optional = false)
	@Column(name = "nd_experiment_id")
	private Integer experimentId;

	/**
	 * The Type object.
	 */
	@Basic(optional = false)
	@Column(name = "type_id")
	private Integer typeId;

	public Integer getExperimentStockId() {
		return experimentStockId;
	}

	public void setExperimentStockId(Integer experimentStockId) {
		this.experimentStockId = experimentStockId;
	}

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(Integer experimentId) {
		this.experimentId = experimentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((experimentStockId == null) ? 0 : experimentStockId
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExperimentStock other = (ExperimentStock) obj;
		if (experimentStockId == null) {
			if (other.experimentStockId != null)
				return false;
		} else if (!experimentStockId.equals(other.experimentStockId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExperimentStock [experimentStockId=" + experimentStockId
				+ "stockId=" + stockId
				+ ", typeId=" + typeId + "]";
	}

}
