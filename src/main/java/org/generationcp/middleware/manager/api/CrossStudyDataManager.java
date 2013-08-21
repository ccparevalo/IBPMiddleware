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

package org.generationcp.middleware.manager.api;

import java.util.List;

import org.generationcp.middleware.domain.dms.StudyReference;
import org.generationcp.middleware.domain.dms.TrialEnvironmentProperty;
import org.generationcp.middleware.domain.dms.TrialEnvironments;
import org.generationcp.middleware.domain.h2h.CategoricalTraitInfo;
import org.generationcp.middleware.domain.h2h.CharacterTraitInfo;
import org.generationcp.middleware.domain.h2h.NumericTraitInfo;
import org.generationcp.middleware.exceptions.MiddlewareQueryException;


public interface CrossStudyDataManager{

    /**
     * Retrieves a list of all trial environments.
     * 
     * @return TrialEnvironments
     * @throws MiddlewareQueryException
     */
    TrialEnvironments getAllTrialEnvironments() throws MiddlewareQueryException;
    
    /**
     * Get all environment properties given a list of environments.
     * 
     * @param trialEnvtIds
     * @return a List of Environment Properties
     * @throws MiddlewareQueryException
     */
    List<TrialEnvironmentProperty> getPropertiesForTrialEnvironments(List<Integer> trialEnvtIds) throws MiddlewareQueryException;
    
    /**
     * Get all studies given a list of environments.
     * 
     * @param environmentIds
     * @return a list of Study References
     * @throws MiddlewareQueryException
     */
    List<StudyReference> getStudiesForTrialEnvironments(List<Integer> environmentIds) throws MiddlewareQueryException;
    
    
    /**
     * Retrieves a set of standard variables (traits) used for the numeric variates observed in given list of environments. 
     * Numeric variates are those with type "Numeric variable" (cvterm ID = 1110) or type "Date variable" (cvterm ID = 1117). 
     * 
     * @param environmentIds
     * @return List of NumericTraitInfo
     * @throws MiddlewareQueryException
     */
    List<NumericTraitInfo> getTraitsForNumericVariates(List<Integer> environmentIds) throws MiddlewareQueryException;
    

    /**
     * Retrieves a set of standard variables (traits) used for the character
     * variates observed in given list of environments. Character variates are
     * those with type "Character variable" (cvterm ID = 1120).
     * 
     * @param environmentIds
     * @return List of CharacterTraitInfo
     * @throws MiddlewareQueryException
     */
    List<CharacterTraitInfo> getTraitsForCharacterVariates(List<Integer> environmentIds) throws MiddlewareQueryException;
    
    /**
     * Retrieve a set of standard variables (traits) used for the categorical
     * variables observed in given list of environments. Categorical variables
     * are those with type "Categorical variable" (cvterm ID = 1130).
     * 
     * @param environmentIds
     * @return List of CategoricalTraitInfo
     * @throws MiddlewareQueryException
     */
    List<CategoricalTraitInfo> getTraitsForCategoricalVariates(List<Integer> environmentIds) throws MiddlewareQueryException;

    
}