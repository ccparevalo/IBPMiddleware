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
package org.generationcp.middleware.util;

import java.util.ArrayList;
import java.util.List;

import org.generationcp.middleware.domain.oms.TermId;

public class PlotUtil {

	private static List<TermId> plotTypes;
	
	static {
	    plotTypes = new ArrayList<TermId>();
        plotTypes.add(TermId.TRIAL_ENVIRONMENT_EXPERIMENT);
        plotTypes.add(TermId.PLOT_EXPERIMENT);
        plotTypes.add(TermId.SAMPLE_EXPERIMENT);
        plotTypes.add(TermId.AVERAGE_EXPERIMENT);
        plotTypes.add(TermId.SUMMARY_EXPERIMENT);
	}
	
	public static List<TermId> getAllPlotTypes() {
		return plotTypes;
	}

	public static String getSqlTypeIds() {
		String sql = new String();
		sql += "(";
		sql += getAllPlotTypes().get(0).getId();
		for (int i = 1; i < getAllPlotTypes().size(); i++) {
			sql += "," + getAllPlotTypes().get(i).getId();
		}
		sql += ") ";
		return sql;
	}
}
