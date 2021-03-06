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
import java.util.Set;

import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.manager.Database;
import org.generationcp.middleware.manager.GdmsTable;
import org.generationcp.middleware.manager.SetOperation;
import org.generationcp.middleware.pojos.Name;
import org.generationcp.middleware.pojos.gdms.AccMetadataSet;
import org.generationcp.middleware.pojos.gdms.AccMetadataSetPK;
import org.generationcp.middleware.pojos.gdms.AlleleValues;
import org.generationcp.middleware.pojos.gdms.AllelicValueElement;
import org.generationcp.middleware.pojos.gdms.AllelicValueWithMarkerIdElement;
import org.generationcp.middleware.pojos.gdms.CharValues;
import org.generationcp.middleware.pojos.gdms.DartValues;
import org.generationcp.middleware.pojos.gdms.Dataset;
import org.generationcp.middleware.pojos.gdms.DatasetElement;
import org.generationcp.middleware.pojos.gdms.DatasetUsers;
import org.generationcp.middleware.pojos.gdms.GermplasmMarkerElement;
import org.generationcp.middleware.pojos.gdms.Map;
import org.generationcp.middleware.pojos.gdms.MapDetailElement;
import org.generationcp.middleware.pojos.gdms.MapInfo;
import org.generationcp.middleware.pojos.gdms.MappingPop;
import org.generationcp.middleware.pojos.gdms.MappingPopValues;
import org.generationcp.middleware.pojos.gdms.MappingValueElement;
import org.generationcp.middleware.pojos.gdms.Marker;
import org.generationcp.middleware.pojos.gdms.MarkerAlias;
import org.generationcp.middleware.pojos.gdms.MarkerDetails;
import org.generationcp.middleware.pojos.gdms.MarkerIdMarkerNameElement;
import org.generationcp.middleware.pojos.gdms.MarkerInfo;
import org.generationcp.middleware.pojos.gdms.MarkerMetadataSet;
import org.generationcp.middleware.pojos.gdms.MarkerMetadataSetPK;
import org.generationcp.middleware.pojos.gdms.MarkerNameElement;
import org.generationcp.middleware.pojos.gdms.MarkerOnMap;
import org.generationcp.middleware.pojos.gdms.MarkerUserInfo;
import org.generationcp.middleware.pojos.gdms.Mta;
import org.generationcp.middleware.pojos.gdms.ParentElement;
import org.generationcp.middleware.pojos.gdms.Qtl;
import org.generationcp.middleware.pojos.gdms.QtlDataElement;
import org.generationcp.middleware.pojos.gdms.QtlDetailElement;
import org.generationcp.middleware.pojos.gdms.QtlDetails;
import org.generationcp.middleware.pojos.gdms.QtlDetailsPK;

/**
 * This is the API for retrieving and storing genotypic data.
 * 
 */
public interface GenotypicDataManager{

    /**
     * Gets the name ids by germplasm ids. 
     * Searches the acc_metadataset table by giving the list of germplasm ids.
     *
     * @param gIds the list germplasm ids
     * @return the name ids matching the given germplasm ids
     * @throws MiddlewareQueryException 
     */
    public List<Integer> getNameIdsByGermplasmIds(List<Integer> gIds) throws MiddlewareQueryException;

    /**
     * Gets the Name records matching the given name ids.
     * This method is based on GMS_getNameRecord. 
     *
     * @param nIds the list of Name ids to match
     * @return the name records corresponding to the list of name ids
     * @throws MiddlewareQueryException
     */
    public List<Name> getNamesByNameIds(List<Integer> nIds) throws MiddlewareQueryException;

    /**
     * Gets the Name record by the given name id.
     *
     * @param nId the name id to match
     * @return the Name record corresponding to the name id
     * @throws MiddlewareQueryException
     */
    public Name getNameByNameId(Integer nId) throws MiddlewareQueryException;

    /**
     * Counts all Map records.
     *
     * @param instance 
     *          - specifies whether the data should be retrieved 
     *          from either the Central or the Local IBDB instance
     * @return the number of Map records found in the given instance
     * @throws MiddlewareQueryException
     */
    public long countAllMaps(Database instance) throws MiddlewareQueryException;

    /**
     * Gets all the Map records in the given range from the given database instance.
     *
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @param instance 
     *          - specifies whether the data should be retrieved 
     *          from either the Central or the Local IBDB instance
     * @return List of all the maps on the given range from the given database instance
     * @throws MiddlewareQueryException
     */
    public List<Map> getAllMaps(int start, int numOfRows, Database instance) throws MiddlewareQueryException;

    /**
     * Gets map information (marker_name, linkage_group, start_position) 
     * from mapping_data view by the given map name.
     *
     * @param mapName 
     *          - the name of the map to retrieve
     * @param instance 
     *          - specifies whether the data should be retrieved 
     *          from either the Central or the Local IBDB instance
     * @return the map info corresponding to the given map name
     * @throws MiddlewareQueryException
     */
    public List<MapInfo> getMapInfoByMapName(String mapName, Database instance) throws MiddlewareQueryException;

    /**
     * Counts all the dataset names.
     *
     * @param instance 
     *          - specifies whether the data should be retrieved 
     *          from either the Central or the Local IBDB instance
     * @return the number of dataset names found in the given instance
     * @throws MiddlewareQueryException
     */
    public long countDatasetNames(Database instance) throws MiddlewareQueryException;

    /**
     * Gets the dataset names from the dataset table. 
     * Data is filtered by ignoring dataset  type = 'qtl'.
     * 
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @param instance 
     *          - specifies whether the data should be retrieved 
     *          from either the Central or the Local IBDB instance
     * @return List of the dataset names based on the given range 
     *          from the given database instance
     * @throws MiddlewareQueryException
     */
    public List<String> getDatasetNames(int start, int numOfRows, Database instance) throws MiddlewareQueryException;


    /**
     * Gets the dataset names from the dataset table based on the given qtl id. 
     * Retrieves from both local and central database instances.
     * 
     * @param qtlId 
     *          - the QTL ID to match
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of the dataset names based on the given qtl Id 
     *          from both local and central database instances
     * @throws MiddlewareQueryException
     */
    public List<String> getDatasetNamesByQtlId(Integer qtlId, int start, int numOfRows) throws MiddlewareQueryException;


    /**
     * Counts the dataset names from the dataset table based on the given qtl id. 
     * Counts from both local and central database instances.
     * 
     * @param qtlId 
     *          - the QTL ID to match
     * @return the number of dataset names based on the given qtl Id 
     *          from both local and central database instances
     * @throws MiddlewareQueryException
     */
    public long countDatasetNamesByQtlId(Integer qtlId) throws MiddlewareQueryException;

    /**
     * Gets the dataset details (dataset id, dataset type) 
     * from the dataset table by dataset name.
     *
     * @param datasetName 
     *          - the dataset name to match
     * @param instance 
     *          - specifies whether the data should be retrieved 
     *          from either the Central or the Local IBDB instance
     * @return the dataset details by dataset name
     * @throws MiddlewareQueryException
     */
    public List<DatasetElement> getDatasetDetailsByDatasetName(String datasetName, Database instance) throws MiddlewareQueryException;

    /**
     * Retrieves a list of matching marker ids from the marker table based on
     * the specified list of Marker Names.
     *
     * @param markerNames 
     *          - List of marker names to search for the corresponding marker ids
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @param instance 
     *          - specifies whether the data should be retrieved 
     *          from either the Central or the Local IBDB instance
     * @return List of matching marker ids based on the specified marker names
     * @throws MiddlewareQueryException
     */
    public List<Integer> getMarkerIdsByMarkerNames(List<String> markerNames, int start, int numOfRows, Database instance)
            throws MiddlewareQueryException;

    /**
     * Gets the list of marker ids from the marker_metadataset table 
     * based on the given dataset id.
     *
     * @param datasetId the dataset id to match
     * @return the markerIds by datasetId
     * @throws MiddlewareQueryException
     */
    public List<Integer> getMarkerIdsByDatasetId(Integer datasetId) throws MiddlewareQueryException;

    /**
     * Gets the germplasm id of parents and the mapping type 
     * from the mapping_pop table based on the given dataset id.
     *
     * @param datasetId the dataset id to match
     * @return the parents and mapping type corresponding to the dataset id
     * @throws MiddlewareQueryException
     */
    public List<ParentElement> getParentsByDatasetId(Integer datasetId) throws MiddlewareQueryException;

    /**
     * Gets the marker type from the marker table based on the given marker ids.
     *
     * @param markerIds the marker ids to match
     * @return the marker type corresponding to the given marker ids
     * @throws MiddlewareQueryException
     */
    public List<String> getMarkerTypesByMarkerIds(List<Integer> markerIds) throws MiddlewareQueryException;

    /**
     * Gets the marker names by germplasm ids. 
     * This method searches the tables allele_values, char_values and mapping_pop_values 
     * for the existence of the given gids. Then gets the marker ids from allele_values, 
     * char_values, mapping_pop_values by the gids. And finally, gets the marker name 
     * from marker table by marker ids.
     *
     * @param gIds the germplasm ids to search for
     * @return the marker names corresponding to the given germplasm ids
     * @throws MiddlewareQueryException
     */
    public List<MarkerNameElement> getMarkerNamesByGIds(List<Integer> gIds) throws MiddlewareQueryException;

    /**
     * Gets the germplasm names by marker names.
     * This method searches the allele_values, char_values, mapping_pop_values tables 
     * for the existence of marker ids. Then gets gids from allele_values, char_values, 
     * mapping_pop_values by marker ids. And finally, returns the germplasm names 
     * matching the marker names.
     *
     * @param markerNames 
     *          - the marker names to match      
     * @param instance 
     *          - specifies whether the data should be retrieved 
     *          from either the Central or the Local IBDB instance
     * @return the GermplasmMarkerElement list that contains the germplasm name 
     *          and the corresponding marker names
     * @throws MiddlewareQueryException
     */
    public List<GermplasmMarkerElement> getGermplasmNamesByMarkerNames(List<String> markerNames, Database instance) throws MiddlewareQueryException;
    
    /**
     * Retrieves a list of mapping values based on the specified germplasm ids and marker names.
     * This method gets mapping values (dataset_id, mapping_type, parent_a_gid, parent_b_gid, marker_type) 
     * from mapping_pop and marker tables by the given gids and marker ids.

     * @param gids 
     *          - list of germplasm ids to match
     * @param markerNames 
     *          - list of marker names
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of mapping values based on the specified germplasm ids and marker names
     * @throws MiddlewareQueryException
     */
    public List<MappingValueElement> getMappingValuesByGidsAndMarkerNames(List<Integer> gids, 
            List<String> markerNames, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Retrieves a list of allelic values (germplasm id, map_char_value, marker name) 
     * based on the specified germplasm ids and marker names.
     * Results are retrieved from 3 separate sources: allele_values, char_values, 
     * and mapping_pop_values.
     *
     * @param gids list of germplasm ids
     * @param markerNames list of marker names
     * @return List of allelic values based on the specified germplasm ids and marker names
     * @throws MiddlewareQueryException
     */
    public List<AllelicValueElement> getAllelicValuesByGidsAndMarkerNames(List<Integer> gids, List<String> markerNames)
            throws MiddlewareQueryException;

    /**
     * Retrieves a list of allelic values (germplasm id, char_value, marker id) 
     * based on the specified dataset id from the char_values table.
     *
     * @param datasetId 
     *          - the dataset id matching the allelic values
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of allelic values based on the specified dataset id
     * @throws MiddlewareQueryException
     */
    public List<AllelicValueWithMarkerIdElement> getAllelicValuesFromCharValuesByDatasetId(Integer datasetId, int start, int numOfRows)
            throws MiddlewareQueryException;

    /**
     * Counts the allelic values based on the specified dataset id 
     * from the char_values table.
     *
     * @param datasetId the dataset id matching the allelic values
     * @return the number of allelic values from char_values table based on the specified dataset id
     * @throws MiddlewareQueryException
     */
    public long countAllelicValuesFromCharValuesByDatasetId(Integer datasetId) throws MiddlewareQueryException;

    /**
     * Retrieves a list of allelic values (germplasm id, allele_bin_value, marker id) 
     * based on the specified dataset id from the allele_values table.
     *
     * @param datasetId 
     *          - the dataset id matching the allelic values
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of allelic values based on the specified dataset id
     * @throws MiddlewareQueryException
     */
    public List<AllelicValueWithMarkerIdElement> getAllelicValuesFromAlleleValuesByDatasetId(Integer datasetId, int start, int numOfRows)
            throws MiddlewareQueryException;

    /**
     * Counts the allelic values based on the specified datasetId 
     * from the allele_values table.
     *
     * @param datasetId the dataset id matching the allelic values
     * @return the number of allelic values from allele_values table based on the specified dataset id
     * @throws MiddlewareQueryException
     */
    public long countAllelicValuesFromAlleleValuesByDatasetId(Integer datasetId) throws MiddlewareQueryException;

    /**
     * Retrieves a list of allelic values (germplasm id, map_char_value, marker id) 
     * based on the specified dataset id from the mapping_pop_values table.
     *
     * @param datasetId 
     *          - the dataset id matching the allelic values
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of allelic values based on the specified dataset id
     * @throws MiddlewareQueryException
     */
    public List<AllelicValueWithMarkerIdElement> getAllelicValuesFromMappingPopValuesByDatasetId(Integer datasetId, int start, int numOfRows)
            throws MiddlewareQueryException;

    /**
     * Counts the allelic values based on the specified dataset id from the mapping_pop_values table.
     *
     * @param datasetId the dataset id matching the allelic values
     * @return the number of allelic values from mapping_pop_values table based on the specified dataset id
     * @throws MiddlewareQueryException
     */
    public long countAllelicValuesFromMappingPopValuesByDatasetId(Integer datasetId) throws MiddlewareQueryException;

    /**
     * Retrieves a list of matching marker names from the marker table based on
     * the specified list of marker ids.
     *
     * @param markerIds List of marker ids to search for the corresponding marker names
     * @return List of matching marker names and marker ids 
     *          based on the specified marker ids
     * @throws MiddlewareQueryException
     */
    public List<MarkerIdMarkerNameElement> getMarkerNamesByMarkerIds(List<Integer> markerIds) throws MiddlewareQueryException;

    /**
     * Gets all marker types.
     *
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of all marker types
     * @throws MiddlewareQueryException
     */
    public List<String> getAllMarkerTypes(int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Gets the number of marker types.
     *
     * @param instance 
     *          - specifies whether the data should be retrieved 
     *          from either the Central or the Local IBDB instance
     * @return the number of all marker types on the specified database instance
     * @throws MiddlewareQueryException
     */
    public long countAllMarkerTypes(Database instance) throws MiddlewareQueryException;

    /**
     * Retrieves the names of the the markers which have the specified marker type.
     *
     * @param markerType 
     *          - the marker type to match
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of marker names based on the specified marker type
     * @throws MiddlewareQueryException
     */
    public List<String> getMarkerNamesByMarkerType(String markerType, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Count the number of marker names matching the given marker type.
     *
     * @param markerType the marker type to match
     * @return the number of marker names corresponding to the given marker type
     * @throws MiddlewareQueryException
     */
    public long countMarkerNamesByMarkerType(String markerType) throws MiddlewareQueryException;

    /**
     * Retrieves all the associated germplasm ids matching the given marker id 
     * from the char_values table.
     * 
     * @param markerId 
     *          - the marker id to match
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of germplasm ids from char_values based on the given marker id
     * @throws MiddlewareQueryException
     */
    public List<Integer> getGIDsFromCharValuesByMarkerId(Integer markerId, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Retrieves a list of marker info based on the specified marker name 
     * from the marker_retrieval_info table.
     * 
     * @param markerName 
     *          - the markerName to match
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of MarkerInfo based on the specified marker name
     * @throws MiddlewareQueryException
     */
    public List<MarkerInfo> getMarkerInfoByMarkerName(String markerName, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Counts the marker info entries corresponding to the given marker name.
     * 
     * @param markerName 
     * @return the number of marker info entries
     * @throws MiddlewareQueryException
     */
    public long countMarkerInfoByMarkerName(String markerName) throws MiddlewareQueryException;

    /**
     * Retrieves a list of MarkerInfo based on the specified genotype from the marker_retrieval_info table.
     * 
     * @param genotype 
     *          - the genotype to match
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of MarkerInfo based on the specified genotype
     * @throws MiddlewareQueryException
     */
    public List<MarkerInfo> getMarkerInfoByGenotype(String genotype, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Counts the marker info entries corresponding to the given genotype.
     * 
     * @param genotype 
     * @return the number of marker info entries 
     * @throws MiddlewareQueryException
     */
    public long countMarkerInfoByGenotype(String genotype) throws MiddlewareQueryException;

    /**
     * Retrieves a list of marker info entries based on the specified db accession id 
     * from the marker_retrieval_info table.
     * 
     * @param dbAccessionId 
     *          - the db accession id  to match
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of MarkerInfo based on the specified db accession id 
     * @throws MiddlewareQueryException
     */
    public List<MarkerInfo> getMarkerInfoByDbAccessionId(String dbAccessionId, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Counts the marker info entries corresponding to the given db accession id.
     * 
     * @param dbAccessionId  
     * @return the number of marker info entries
     * @throws MiddlewareQueryException
     */
    public long countMarkerInfoByDbAccessionId(String dbAccessionId) throws MiddlewareQueryException;

    /**
     * Counts the number of germplasm ids matching the given marker id
     * from the char_values table.
     *
     * @param markerId the marker id
     * @return the count of germplasm ids corresponding to the given marker id 
     *          from char_values
     * @throws MiddlewareQueryException
     */
    public long countGIDsFromCharValuesByMarkerId(Integer markerId) throws MiddlewareQueryException;

    /**
     * Retrieves all the associated germplasm ids matching the given marker id 
     * from the allele_values table.
     *
     * @param markerId the marker id
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of germplasm ids from allele values based on the given marker id
     * @throws MiddlewareQueryException
     */
    public List<Integer> getGIDsFromAlleleValuesByMarkerId(Integer markerId, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Counts the number of germplasm ids matching the given marker id 
     * from the allele_values table.
     *
     * @param markerId the marker id
     * @return the count of germplasm ids corresponding to the given marker id 
     *          from allele_values
     * @throws MiddlewareQueryException
     */
    public long countGIDsFromAlleleValuesByMarkerId(Integer markerId) throws MiddlewareQueryException;

    /**
     * Retrieves all the associated germplasm ids matching the given marker id 
     * from the mapping_pop_values table.
     *
     * @param markerId 
     *          - the marker id to match
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of germplasm ids from mapping_pop_values table 
     *          based on the given marker id
     * @throws MiddlewareQueryException
     */
    public List<Integer> getGIDsFromMappingPopValuesByMarkerId(Integer markerId, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Counts the number of germplasm ids matching the given marker id 
     * from the mapping_pop_values table.
     *
     * @param markerId the marker id to match
     * @return the count of germplasm ids corresponding to the given marker id 
     *          from mapping_pop_values
     * @throws MiddlewareQueryException
     */
    public long countGIDsFromMappingPopValuesByMarkerId(Integer markerId) throws MiddlewareQueryException;

    /**
     * Gets the all db accession ids from marker.
     *
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of all non-empty db_accession IDs from Marker
     * @throws MiddlewareQueryException
     */
    public List<String> getAllDbAccessionIdsFromMarker(int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Count all db accession ids from marker.
     *
     * @return the number of non-empty db accession ids from marker
     * @throws MiddlewareQueryException
     */
    public long countAllDbAccessionIdsFromMarker() throws MiddlewareQueryException;

    /**
     * Gets the nids from acc metadataset by dataset ids.
     *
     * @param datasetIds 
     *          - the dataset ids to match
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of all name ids from acc_metadataset table 
     *          based on the given list of dataset ids
     * @throws MiddlewareQueryException
     */
    public List<Integer> getNidsFromAccMetadatasetByDatasetIds(List<Integer> datasetIds, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Gets the nids from acc metadataset by dataset ids filtered by gids.
     *
     * @param datasetIds 
     *          - the dataset ids to match
     * @param gids 
     *          - the gids to match
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of name ids from acc_metadataset based on the given list of dataset ids
     * @throws MiddlewareQueryException
     */
    public List<Integer> getNidsFromAccMetadatasetByDatasetIds(List<Integer> datasetIds, List<Integer> gids, int start, int numOfRows)
            throws MiddlewareQueryException;

    /**
     * Gets all the dataset Ids for Fingerprinting.
     * Retrieves data from both central and local database instances.
     *
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of all dataset Ids where type is not equal to 'mapping' or 'QTL'
     * @throws MiddlewareQueryException
     */
    public List<Integer> getDatasetIdsForFingerPrinting(int start, int numOfRows) throws MiddlewareQueryException;
    
    /**
     * Count the dataset Ids for Fingerprinting. 
     * Counts occurrences on both central and local database instances.
     *
     * @return the number of dataset Ids where type is not equal to 'mapping' or 'QTL'
     * @throws MiddlewareQueryException
     */
    public long countDatasetIdsForFingerPrinting() throws MiddlewareQueryException;


    /**
     * Gets all the dataset Ids for Mapping.
     * Retrieves data from both central and local database instances.
     *
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of all dataset Ids where type is equal to 'mapping' and not equal to 'QTL'
     * @throws MiddlewareQueryException
     */
    public List<Integer> getDatasetIdsForMapping(int start, int numOfRows) throws MiddlewareQueryException;
    
    /**
     * Count the dataset Ids for Mapping.
     * Counts occurrences on both central and local database instances.
     *
     * @return the number of dataset Ids where type is equal to 'mapping' and not equal to 'QTL'
     * @throws MiddlewareQueryException
     */
    public long countDatasetIdsForMapping() throws MiddlewareQueryException;


    /**
     * Gets the details of gdms_acc_metadataset given a set of Germplasm IDs.
     * Retrieves from either local (negative gid) or both local and central (positive gid).
     * Discards duplicates.
     *
     * @param gids
     *          - list of GIDs to match
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of the corresponding details of entries in gdms_acc_metadataset given a set of GIDs
     * @throws MiddlewareQueryException
     */
    public List<AccMetadataSetPK> getGdmsAccMetadatasetByGid(List<Integer> gids, int start, int numOfRows) throws MiddlewareQueryException;
    
    /**
     * Count the entries in gdms_acc_metadataset given a set of Germplasm IDs.
     * Counts from either local (negative gid) or both local and central (positive gid).
     * Includes duplicates in the count.
     *
     * @return the number of entries in gdms_acc_metadataset given a set of Germplasm IDs
     * @throws MiddlewareQueryException
     */
    public long countGdmsAccMetadatasetByGid(List<Integer> gids) throws MiddlewareQueryException;

    /**
     * Gets the marker ids matching the given GID and Dataset Ids
     * Retrieves data from both central and local database instances.
     *
     * @param gid
     *          - the GID to match
     * @param datasetIds 
     *          - the datasetIds to match
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of marker ids matching the given GID and dataset ids
     * @throws MiddlewareQueryException
     */
    public List<Integer> getMarkersByGidAndDatasetIds(Integer gid, List<Integer> datasetIds, int start, int numOfRows) throws MiddlewareQueryException;
    
    /**
     * Gets the number of marker ids matching the given GID and Dataset Ids
     * Counts occurrences on both central and local database instances.
     *
     * @param gid
     *          - the GID to match
     * @param datasetIds 
     *          - the datasetIds to match
     * @return the number of marker ids matching the given GID and dataset ids
     * @throws MiddlewareQueryException
     */
    public long countMarkersByGidAndDatasetIds(Integer gid, List<Integer> datasetIds) throws MiddlewareQueryException;


    
    /**
     * Gets the number of alleles given a set of GIDs
     *
     * @param gids
     *          - the GIDs to match
     * @return the number of alleles matching the given GIDs
     * @throws MiddlewareQueryException
     */
    public long countAlleleValuesByGids(List<Integer> gids) throws MiddlewareQueryException;


    /**
     * Gets the number of char values given a set of GIDs
     *
     * @param gids
     *          - the GIDs to match
     * @return the number of char values matching the given GIDs
     * @throws MiddlewareQueryException
     */
    public long countCharValuesByGids(List<Integer> gids) throws MiddlewareQueryException;


    /**
     * Gets int alleleValues for polymorphic markers retrieval given a list of GIDs
     * Retrieves data from both central and local database instances.
     *
     * @param gids
     *          - the GIDs to match
     * @return List of int alleleValues for polymorphic markers retrieval 
     * @throws MiddlewareQueryException
     */
    public List<AllelicValueElement> getIntAlleleValuesForPolymorphicMarkersRetrieval(List<Integer> gids, int start, int numOfRows) throws MiddlewareQueryException;

    
    /**
     * Gets the number of int alleleValues for polymorphic markers retrieval given a list of GIDs
     *
     * @param gids
     *          - the GIDs to match
     * @return the number of int alleleValues for polymorphic markers retrieval 
     * @throws MiddlewareQueryException
     */
    public long countIntAlleleValuesForPolymorphicMarkersRetrieval(List<Integer> gids) throws MiddlewareQueryException;


    /**
     * Gets char alleleValues for polymorphic markers retrieval given a list of GIDs
     * Retrieves data from both central and local database instances.
     *
     * @param gids
     *          - the GIDs to match
     * @return List of char alleleValues for polymorphic markers retrieval 
     * @throws MiddlewareQueryException
     */
    public List<AllelicValueElement> getCharAlleleValuesForPolymorphicMarkersRetrieval(List<Integer> gids, int start, int numOfRows) throws MiddlewareQueryException;

    
    /**
     * Gets the number of char alleleValues for polymorphic markers retrieval given a list of GIDs
     *
     * @param gids
     *          - the GIDs to match
     * @return the number of char alleleValues for polymorphic markers retrieval 
     * @throws MiddlewareQueryException
     */
    public long countCharAlleleValuesForPolymorphicMarkersRetrieval(List<Integer> gids) throws MiddlewareQueryException;

    
    /**
     * Gets the list og nids by dataset ids and marker ids and not by gids.
     *
     * @param datasetIds 
     *          - the dataset ids to match
     * @param markerIds 
     *          - the marker ids to match
     * @param gIds 
     *          - the gids not to match          
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return Set of name ids based on the given list of dataset ids, list of marker ids and a list of germplasm ids
     * @throws MiddlewareQueryException
     */
    public List<Integer> getNIdsByMarkerIdsAndDatasetIdsAndNotGIds(
        List<Integer> datasetIds, List<Integer> gIds,
        List<Integer> markerIds, int start, int numOfRows)
        throws MiddlewareQueryException;

    /**
     * Gets the list of nids by dataset ids and marker ids.
     *
     * @param datasetIds 
     *          - the dataset ids to match
     * @param markerIds 
     *          - the marker ids to match       
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return Set of name ids based on the given list of dataset ids, list of marker ids
     * @throws MiddlewareQueryException
     */
    public List<Integer> getNIdsByMarkerIdsAndDatasetIds(List<Integer> datasetIds,
        List<Integer> markerIds, int start, int numOfRows)
        throws MiddlewareQueryException;

    /**
     * Gets the count of nids by dataset ids and marker ids and not by gids.
     *
     * @param datasetIds 
     *          - the dataset ids to match
     * @param markerIds 
     *          - the marker ids to match
     * @param gIds 
     *          - the gids not to match          
     * @return count of name ids based on the given list of dataset ids, list of marker ids and a list of germplasm ids
     * @throws MiddlewareQueryException
     */
    public int countNIdsByMarkerIdsAndDatasetIdsAndNotGIds(List<Integer> datasetIds,
        List<Integer> markerIds, List<Integer> gIds)
        throws MiddlewareQueryException;
    
    /**
     * Gets the count of nids by dataset ids and marker ids.
     *
     * @param datasetIds 
     *          - the dataset ids to match
     * @param markerIds 
     *          - the marker ids to match       
     * @return count of name ids based on the given list of dataset ids, list of marker ids
     * @throws MiddlewareQueryException
     */
    int countNIdsByMarkerIdsAndDatasetIds(List<Integer> datasetIds,
        List<Integer> markerIds)
        throws MiddlewareQueryException;

    
    /**
     * Gets mapping alleleValues for polymorphic markers retrieval given a list of GIDs
     * Retrieves data from both central and local database instances.
     *
     * @param gids
     *          - the GIDs to match
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of mapping alleleValues for polymorphic markers retrieval 
     * @throws MiddlewareQueryException
     */
    public List<AllelicValueElement> getMappingAlleleValuesForPolymorphicMarkersRetrieval(List<Integer> gids, int start, int numOfRows) throws MiddlewareQueryException;
    

    /**
     * Gets the number of mapping alleleValues for polymorphic markers retrieval given a list of GIDs
     *
     * @param gids
     *          - the GIDs to match
     * @return the number of mapping alleleValues for polymorphic markers retrieval 
     * @throws MiddlewareQueryException
     */
    public long countMappingAlleleValuesForPolymorphicMarkersRetrieval(List<Integer> gids) throws MiddlewareQueryException;

    /**
     * Retrieves all QTL entries from the gdms_qtl table
     * 
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     *          
     * @return List of all QTL entries
     * @throws MiddlewareQueryException
     */
    public List<Qtl> getAllQtl(int start, int numOfRows) throws MiddlewareQueryException;
    

    /**
     * Returns the number of QTL entries from the gdms_qtl table
     * 
     * @return Count of QTL entries
     * @throws MiddlewareQueryException
     */
    public long countAllQtl() throws MiddlewareQueryException;
    
    /**
     * Retrieves QTL Ids from the gdms_qtl table matching the given name
     * 
     * @param name - the QTL name to match 
     * @param start - the starting index of the sublist of results to be returned
     * @param numOfRows - the number of rows to be included in the sublist of results to be returned
     * @return List of QTL Ids
     * @throws MiddlewareQueryException
     */
    public List<Integer> getQtlIdByName(String name, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Returns the number of QTL Ids from the gdms_qtl table matching the given name
     * 
     * @param name - the QTL name to match
     * @return Count of QTL Ids
     * @throws MiddlewareQueryException
     */
    public long countQtlIdByName(String name) throws MiddlewareQueryException;
    
    /**
     * Retrieves QTL entries from the gdms_qtl table matching the given name
     * 
     * @param name 
     *          - the name to match       
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned

     * @return List of QTL entries
     * @throws MiddlewareQueryException
     */
    public List<QtlDetailElement> getQtlByName(String name, int start, int numOfRows) throws MiddlewareQueryException;
    
    /**
     * Returns the number of QTL entries from the gdms_qtl table matching the given name
     * 
     * @param name - name of QTL
     * 
     * @return Count of QTL entries
     * @throws MiddlewareQueryException
     */
    public long countQtlByName(String name) throws MiddlewareQueryException;
    

    /**
     * Retrieves QTL IDs from the gdms_qtl table matching the given trait id
     * 
     * @param trait 
     *          - the trait id to match       
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned

     * @return List of QTL IDs
     * @throws MiddlewareQueryException
     */
    public List<Integer> getQtlByTrait(Integer trait, int start, int numOfRows) throws MiddlewareQueryException;
    
    /**
     * Returns the number of QTL entries from the gdms_qtl table matching the given trait id
     * 
     * @param trait - trait id of QTL
     * 
     * @return Count of QTL entries
     * @throws MiddlewareQueryException
     */
    public long countQtlByTrait(Integer trait) throws MiddlewareQueryException;

    /**
     * Retrieves the QTL trait ids from gdms_qtl_details table matching the given the dataset ID.
     * If the dataset ID is positive, the traits are retrieved from central, otherwise they are retrieved from local.
     * 
     * @param datasetId 
     *          - the datasetId to match       
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned

     * @return List of QTL trait ids
     * @throws MiddlewareQueryException
     */
    public List<Integer> getQtlTraitsByDatasetId(Integer datasetId, int start, int numOfRows) throws MiddlewareQueryException;
    
    /**
     * Returns the number of QTL traits from the gdms_qtl_details table matching the given dataset ID
     * 
     * @param datasetId 
     *          - the datasetId to match       
     * 
     * @return Count of QTL traits
     * @throws MiddlewareQueryException
     */
    public long countQtlTraitsByDatasetId(Integer datasetId) throws MiddlewareQueryException;

    /**
     * Returns all the parents from mapping population
     * 
     * @param start
     *             	- the starting index of the sublist of results to be returned
     * @param numOfRows
     *             	- the number of rows to be included in the sublist of results
     *            to be returned
     * @return List of parent_a_gid and parent_b_gid
     * 				- List of Parent A GId and Parent B GId 
     * @throws MiddlewareQueryException
     */
    public List<ParentElement> getAllParentsFromMappingPopulation(int start, int numOfRows) throws MiddlewareQueryException;
    
    
    /**
     * Returns the number of parent GIds (a and b)
     * 
     * @return BigInteger - number of parent GIds
     * @throws MiddlewareQueryException
     */
    public Long countAllParentsFromMappingPopulation() throws MiddlewareQueryException;
    
    /**
     * Returns map details given the name/part of the name of a map
     * @param nameLike
     *          - search query, name or part of name (non case-sensitive), add % for wildcard
     * @param start
     *          - the starting index of the sublist of results to be returned 
     * @param numOfRows
     *          - the number of rows to be included in the sublist of results to be returned
     * @return
     *          - List of Maps
     * @throws MiddlewareQueryException
     */
    public List<MapDetailElement> getMapDetailsByName(String nameLike, int start, int numOfRows) throws MiddlewareQueryException;
    
    /**
     * Returns count of map details given the name/part of the name of a map
     * @param nameLike
     *          - search query, name or part of name (non case-sensitive), add % for wildcard
     * @return
     *          - count of map details
     * @throws MiddlewareQueryException
     */
    public Long countMapDetailsByName(String nameLike) throws MiddlewareQueryException;
    

    /**
     * Gets all the Map details.
     *
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return List of all the map details
     * @throws MiddlewareQueryException
     */
    public List<MapDetailElement> getAllMapDetails(int start, int numOfRows) throws MiddlewareQueryException;
       

    /**
     * Retrieves the number of map details.
     *
     * @return Count of the map details
     * @throws MiddlewareQueryException
     */
    public long countAllMapDetails() throws MiddlewareQueryException;
    
    
    /**
     * Returns the map ids matching the given QTL name 
     * 
     * @param qtlName
     *          - the name of the QTL to match       
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return the map ids matching the given parameters
     * @throws MiddlewareQueryException
     */
    public List<Integer> getMapIdsByQtlName(String qtlName, int start, int numOfRows) throws MiddlewareQueryException;
    

    /**
     * Returns count of map ids given the qtl name
     * @param qtlName
     *          - name of the qtl to match
     * @return
     *          - count of map ids
     * @throws MiddlewareQueryException
     */
    public long countMapIdsByQtlName(String qtlName) throws MiddlewareQueryException;
    

    
    /**
     * Returns the marker ids matching the given QTL name, chromosome, min start position and max start position values 
     * 
     * @param qtlName
     *          - the name of the QTL to match       
     * @param chromosome
     *          - the value to match the linkage group of markers_onmap
     * @param min
     *          - the minimum start position 
     * @param max
     *          - the maximum start position 
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return the marker ids matching the given parameters
     * @throws MiddlewareQueryException
     */
    public List<Integer> getMarkerIdsByQtl(String qtlName, String chromosome, int min, int max, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Returns the number of marker ids matching the given QTL name, chromosome, min start position and max start position values 
     * 
     * @param qtlName
     *          - the name of the QTL to match       
     * @param chromosome
     *          - the value to match the linkage group of markers_onmap
     * @param min
     *          - the minimum start position 
     * @param max
     *          - the maximum start position 
     * @return Count of marker id entries
     * @throws MiddlewareQueryException
     */
    public long countMarkerIdsByQtl(String qtlName, String chromosome, int min, int max) throws MiddlewareQueryException;
    

    /**
     * Returns the markers matching the given marker ids 
     * 
     * @param markerIds
     *          - the Ids of the markers to retrieve
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * @return the markers matching the given ids
     * @throws MiddlewareQueryException
     */
    public List<Marker> getMarkersByIds(List<Integer> markerIds, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Adds a QtlDetails entry to the database
     * 
     * @param qtlDetails - the object to add
     * 
     * @return the id of the item added
     * @throws MiddlewareQueryException
     */
    public QtlDetailsPK addQtlDetails(QtlDetails qtlDetails) throws MiddlewareQueryException;
    

    /**
     * Adds a MarkerDetails entry to the database
     * 
     * @param markerDetails - the object to add
     * 
     * @return the id of the item added
     * @throws MiddlewareQueryException
     */
    public Integer addMarkerDetails(MarkerDetails markerDetails) throws MiddlewareQueryException;
    
    /**
     * Adds a MarkerUserInfo entry to the database
     * 
     * @param markerUserInfo - the object to add
     * 
     * @return the id of the item added
     * @throws MiddlewareQueryException
     */
    public Integer addMarkerUserInfo(MarkerUserInfo markerUserInfo) throws MiddlewareQueryException;
    
    /**
     * Adds a AccMetadataSet entry to the database
     * 
     * @param accMetadataSet - the object to add
     * 
     * @return the id of the item added
     * @throws MiddlewareQueryException
     */
    public AccMetadataSetPK addAccMetadataSet(AccMetadataSet accMetadataSet) throws MiddlewareQueryException;

    /**
     * Adds a MarkerMetadataSet entry to the database
     * 
     * @param markerMetadataSet - the object to add
     * 
     * @return the id of the item added
     * @throws MiddlewareQueryException
     */
    public MarkerMetadataSetPK addMarkerMetadataSet(MarkerMetadataSet markerMetadataSet) throws MiddlewareQueryException;

    /**
     * Adds a Dataset entry to the database
     * 
     * @param dataset - the object to add
     * 
     * @return the id of the item added
     * @throws MiddlewareQueryException
     */
    public Integer addDataset(Dataset dataset) throws MiddlewareQueryException;

    /**
     * Adds a GDMS marker given a Marker
     * @param marker
     * @return markerId - markerId of the inserted record
     * @throws MiddlewareQueryException
     */
    public Integer addGDMSMarker(Marker marker) throws MiddlewareQueryException;
    
    /**
     * Adds a GDMS marker alias given a MarkerAlias
     * @param markerAlias
     * @return markerId - markerId of the inserted record
     * @throws MiddlewareQueryException
     */
    public Integer addGDMSMarkerAlias(MarkerAlias markerAlias) throws MiddlewareQueryException;
    
    /**
     * Adds a dataset user given a DatasetUser
     * @param datasetUser
     * @return userId - userId of the inserted record
     * @throws MiddlewareQueryException
     */
    public Integer addDatasetUser(DatasetUsers datasetUser) throws MiddlewareQueryException;    
    /**
     * Adds an AlleleValues entry to the database
     * 
     * @param alleleValues - the object to add
     * 
     * @return the id of the item added
     * @throws MiddlewareQueryException
     */
    public Integer addAlleleValues(AlleleValues alleleValues) throws MiddlewareQueryException;

    
    /**
     * Adds an CharValues entry to the database
     * 
     * @param charValues - the object to add
     * 
     * @return the id of the item added
     * @throws MiddlewareQueryException
     */
    public Integer addCharValues(CharValues charValues) throws MiddlewareQueryException;

    /**
     * Adds a mapping pop given a MappingPop
     * @param mappingPop
     * @return DatasetId - datasetId of the inserted record
     * @throws MiddlewareQueryException
     */
    public Integer addMappingPop(MappingPop mappingPop) throws MiddlewareQueryException;
    
    /**
     * Adds a mapping pop given a MappingPopValue
     * @param mappingPopValue
     * @return mpId - mpId of the inserted record
     * @throws MiddlewareQueryException
     */
    public Integer addMappingPopValue(MappingPopValues mappingPopValue) throws MiddlewareQueryException;    
    
    /**
     * Adds a marker on map given a MarkerOnMap
     * @param markerOnMap
     * @return mapId - mapId of the inserted record
     * @throws MiddlewareQueryException
     */
    public Integer addMarkerOnMap(MarkerOnMap markerOnMap) throws MiddlewareQueryException;
    
    /**
     * Adds a dart value given a DartValue
     * @param dartValue
     * @return adId - adId of the inserted record
     * @throws MiddlewareQueryException
     */
    public Integer addDartValue(DartValues dartValue) throws MiddlewareQueryException;    
    
    /**
     * Adds a Qtl given a Qtl
     * @param qtl 
     * @return qtlId - qltId of the inserted record
     * @throws MiddlewareQueryException
     */
    public Integer addQtl(Qtl qtl) throws MiddlewareQueryException;

    /**
     * Adds a Map entry to the database
     * 
     * @param map - the object to add
     * 
     * @return the id of the item added
     * @throws MiddlewareQueryException
     */
    public Integer addMap(Map map) throws MiddlewareQueryException;

    
    /**
     * Sets SSR Markers
     * @param marker (Marker) marker_type will be set to/overridden by "SSR"
     * @param markerAlias (MarkerAlias) (marker_id will automatically be set to inserted marker's ID)
     * @param markerDetails (MarkerDetails) (marker_id will automatically be set to inserted marker's ID)
     * @param markerUserInfo (MarkerUserInfo) (marker_id will automatically be set to inserted marker's ID)
     * @return (boolean) - true if successful, exception or false if failed
     * @throws MiddlewareQueryException
     */
    public Boolean setSSRMarkers(Marker marker, MarkerAlias markerAlias, MarkerDetails markerDetails, MarkerUserInfo markerUserInfo) throws MiddlewareQueryException;
    
    /**
     * Sets SNP Markers
     * @param marker (Marker) marker_type will be set to/overridden by "SNP"
     * @param markerAlias (MarkerAlias) (marker_id will automatically be set to inserted marker's ID)
     * @param markerDetails (MarkerDetails) (marker_id will automatically be set to inserted marker's ID)
     * @param markerUserInfo (MarkerUserInfo) (marker_id will automatically be set to inserted marker's ID)
     * @return (boolean) - true if successful, exception or false if failed
     * @throws MiddlewareQueryException
     */
    public Boolean setSNPMarkers(Marker marker, MarkerAlias markerAlias, MarkerDetails markerDetails, MarkerUserInfo markerUserInfo) throws MiddlewareQueryException;    
    
    /**
     * Sets CAP Markers
     * @param marker (Marker) marker_type will be set to/overridden by "CAP"
     * @param markerAlias (MarkerAlias) (marker_id will automatically be set to inserted marker's ID)
     * @param markerDetails (MarkerDetails) (marker_id will automatically be set to inserted marker's ID)
     * @param markerUserInfo (MarkerUserInfo) (marker_id will automatically be set to inserted marker's ID)
     * @return (boolean) - true if successful, exception or false if failed
     * @throws MiddlewareQueryException
     */
    public Boolean setCAPMarkers(Marker marker, MarkerAlias markerAlias, MarkerDetails markerDetails, MarkerUserInfo markerUserInfo) throws MiddlewareQueryException;
    
    /**
     * Sets CISR Markers
     * @param marker (Marker) marker_type will be set to/overridden by "CISR"
     * @param markerAlias (MarkerAlias) (marker_id will automatically be set to inserted marker's ID)
     * @param markerDetails (MarkerDetails) (marker_id will automatically be set to inserted marker's ID)
     * @param markerUserInfo (MarkerUserInfo) (marker_id will automatically be set to inserted marker's ID)
     * @return (boolean) - true if successful, exception or false if failed
     * @throws MiddlewareQueryException
     */
    public Boolean setCISRMarkers(Marker marker, MarkerAlias markerAlias, MarkerDetails markerDetails, MarkerUserInfo markerUserInfo) throws MiddlewareQueryException;

    /**
     * Sets QTL
     * @param datasetUser - (DatasetUser)
     * @param dataset - (Dataset) dataset_type will be set to/overridden by "QTL"
     * @param qtlDetails - (QtlDetails) (qtl_id will be automatically set to inserted Qtl's ID)
     * @param qtl - (Qtl) (dataset_id will be automatically set to inserted dataset's ID) 
     * @return (boolean) - true if successful, exception or false if failed
     * @throws MiddlewareQueryException
     */
    public Boolean setQTL(DatasetUsers datasetUser, Dataset dataset, QtlDetails qtlDetails, Qtl qtl) throws MiddlewareQueryException;

    /**
     * Sets DArT
     * @param accMetadataSet - (AccMetadataSet)
     * @param markerMetadataSet - (MarkerMetadataSet)
     * @param datasetUser - (DatasetUser)
     * @param alleleValues - (AlleleValues)
     * @param dataset - (Dataset) dataset_type = "DArT", datatype = "int" 
     * @param dartValues - (DartValues)
     * @return (boolean) - true if successful, exception or false if failed
     * @throws MiddlewareQueryException
     */
    public Boolean setDart(AccMetadataSet accMetadataSet, MarkerMetadataSet markerMetadataSet, DatasetUsers datasetUser, 
            AlleleValues alleleValues, Dataset dataset, DartValues dartValues) throws MiddlewareQueryException;
    
    /**
     * Sets SSR
     * @param accMetadataSet - (AccMetadataSet)
     * @param markerMetadataSet - (MarkerMetadataSet)
     * @param datasetUser - (DatasetUser)
     * @param alleleValues - (AlleleValues)
     * @param dataset - (Dataset) dataset_type = "SSR", datatype = "int" 
     * @return (boolean) - true if successful, exception or false if failed
     * @throws MiddlewareQueryException
     */

    public Boolean setSSR(AccMetadataSet accMetadataSet, MarkerMetadataSet markerMetadataSet, DatasetUsers datasetUser, 
            AlleleValues alleleValues, Dataset dataset) throws MiddlewareQueryException;

    
    /**
     * Sets SNP
     * @param accMetadataSet - (AccMetadataSet)
     * @param markerMetadataSet - (MarkerMetadataSet)
     * @param datasetUser - (DatasetUser)
     * @param charValues - (CharValues)
     * @param dataset - (Dataset) dataset_type = "SNP", datatype = "int" 
     * @return (boolean) - true if successful, exception or false if failed
     * @throws MiddlewareQueryException
     */
    public Boolean setSNP(AccMetadataSet accMetadataSet, MarkerMetadataSet markerMetadataSet, DatasetUsers datasetUser, 
            CharValues charValues, Dataset dataset) throws MiddlewareQueryException;
    
    /**
     * Sets Mapping Data.
     * 
     * @param accMetadataSet - Accession Metadataset
     * @param markerMetadataSet - Marker Metadataset
     * @param datasetUser - Dataset Users
     * @param mappingPop - Mapping Population
     * @param mappingPopValues - Mapping population Values
     * @param dataset - Dataset
     * @return true if values were successfully saved in the database, false otherwise
     * @throws MiddlewareQueryException
     */
    public Boolean setMappingData(AccMetadataSet accMetadataSet, MarkerMetadataSet markerMetadataSet, DatasetUsers datasetUser,
            MappingPop mappingPop, MappingPopValues mappingPopValues, Dataset dataset) throws MiddlewareQueryException;
    
    /**
     * Sets Maps/
     * 
     * @param marker - GDMS Marker
     * @param markerOnMap - GDMS Marker On Map
     * @param map - GDMS Map
     * @return true if values were successfully saved in the database, false otherwise
     * @throws MiddlewareQueryException
     */
    public Boolean setMaps(Marker marker, MarkerOnMap markerOnMap, Map map) throws MiddlewareQueryException;

    /**
     * Gets Map ID from QTL Name
     * @param qtlName - name of qtl
     * @param start - starting record to retrieve
     * @param numOfRows - number of records to retrieve from start record
     * @return (List<Integer>) list of Map IDs
     * @throws MiddlewareQueryException
     */
    public List<Integer> getMapIDsByQTLName(String qtlName, int start, int numOfRows) throws MiddlewareQueryException;
    
    /**
     * Counts Map ID from QTL Name
     * @param qtlName - name of qtl
     * @return (long)count of Map IDs
     * @throws MiddlewareQueryException
     */
    public long countMapIDsByQTLName(String qtlName)  throws MiddlewareQueryException;
    
    /**
     * Gets Marker IDs from Map ID, Linkage Group and Between start position values
     * @param mapID - ID of map
     * @param linkageGroup - chromosome 
     * @param startPos - map starting position value
     * @param endPos - map ending position value
     * @param start - starting record to retrieve
     * @param numOfRows - number of records to retrieve from start record
     * @return (Set<Integer>) set of Marker IDs
     * @throws MiddlewareQueryException
     */
    public Set<Integer> getMarkerIDsByMapIDAndLinkageBetweenStartPosition(int mapID,
        String linkageGroup, double startPos, double endPos, int start,
        int numOfRows) throws MiddlewareQueryException;

    /**
     * Counts Marker IDs from Map ID, Linkage Group and Between start position values
     * @param mapId - ID of map
     * @param linkageGroup - chromosome 
     * @param startPos - map starting position value
     * @param endPos - map ending position value
     * @return (long) count of Marker IDs
     * @throws MiddlewareQueryException
     */
    public long countMarkerIDsByMapIDAndLinkageBetweenStartPosition(int mapId,
        String linkageGroup, double startPos, double endPos)
        throws MiddlewareQueryException;

    /**
     * Gets Markers by Marker IDs
     * @param markerIds - IDs of markers
     * @param start - starting record to retrieve
     * @param numOfRows - number of records to retrieve from start record
     * @return (List<Marker>) List of Markers
     * @throws MiddlewareQueryException
     */
    public List<Marker> getMarkersByMarkerIds(List<Integer> markerIds, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Counts Markers by Marker IDs
     * @param markerIDs - IDs of markers
     * @return Count of Markers
     * @throws MiddlewareQueryException
     */
    public long countMarkersByMarkerIds(List<Integer> markerIDs) throws MiddlewareQueryException;

    /**
     * Retrieves QTL entries from the gdms_qtl table matching the given list of qtl ids
     * 
     * @param qtls 
     *          - the list of qtl ids to match       
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned

     * @return List of QTL entries
     * @throws MiddlewareQueryException
     */
    public List<QtlDetailElement> getQtlByQtlIds(List<Integer> qtls, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Returns the number of QTL entries from the gdms_qtl table matching the given list of qtl ids
     * 
     * @param qtls - list of QTL IDs
     * 
     * @return Count of QTL entries
     * @throws MiddlewareQueryException
     */
    public long countQtlByQtlIds(List<Integer> qtls) throws MiddlewareQueryException;

    
    /**
     * Returns the QTL data entries from the gdms_qtl and gdms_qtl_details tables matching the given list of trait ids.
     * Retrieves values from both local and central.
     * 
     * @param qtlTraitIds 
     * 			- list of QTL trait ids to match
     * @param start 
     *          - the starting index of the sublist of results to be returned
     * @param numOfRows 
     *          - the number of rows to be included in the sublist of results 
     *          to be returned
     * 
     * @return List of QTL data entries
     * @throws MiddlewareQueryException
     */
    public List<QtlDataElement> getQtlDataByQtlTraits(List<Integer> qtlTraitIds, int start, int numOfRows) throws MiddlewareQueryException;

    /**
     * Returns the number of QTL data entries from the gdms_qtl and gdms_qtl_details tables matching the given list of trait ids.
     * Counts from both local and central instances.
     * 
     * @param qtlTraitIds - list of QTL trait ids to match
     * 
     * @return Count of QTL data entries
     * @throws MiddlewareQueryException
     */
    public long countQtlDataByQtlTraits(List<Integer> qtlTraitIds) throws MiddlewareQueryException;

    /**
     * Returns the number of nIds from AccMetaDataSet given a list of dataset Ids so that the GDMS UI can integrate to the GDMS database
     * @param datasetIds
     * @return Count of NIDs
     * @throws MiddlewareQueryException
     */
    public long countNidsFromAccMetadatasetByDatasetIds(List<Integer> datasetIds) throws MiddlewareQueryException;
    
    /**
     * Returns the Map ID given the map name. 
     * @param mapName
     * @return the Map ID
     * @throws MiddlewareQueryException
     */
    public Integer getMapIdByName(String mapName) throws MiddlewareQueryException;
    
    /**
     * Returns the number of mapping pop values from gdms_mapping_pop_values given a list of germplasm IDs
     * @param gIDs
     * @return count of mapping pop values
     * @throws MiddlewareQueryException
     */
    public long countMappingPopValuesByGids(List<Integer> gIds) throws MiddlewareQueryException;
    
    /**
     * Returns the number of mapping allele values gdms_allele_values given a list of germplasm IDs.
     * @param gIds
     * @return
     * @throws MiddlewareQueryException
     */
    public long countMappingAlleleValuesByGids(List<Integer> gIds) throws MiddlewareQueryException;
    
    /**
     * Returns the list of MarkerMetadataSet from gdms_marker_metadataset matching the given marker ID.
     * @param markerId
     * @return
     * @throws MiddlewareQueryException
     */
    public List<MarkerMetadataSet> getAllFromMarkerMetadatasetByMarker(Integer markerId) throws MiddlewareQueryException;
    
    /**
     * Returns the Dataset details given a list of dataset IDs
     * @param datasetIds
     * @return List Dataset entries matching the given dataset IDs
     * @throws MiddlewareQueryException
     */
    public List<Dataset> getDatasetDetailsByDatasetIds(List<Integer> datasetIds) throws MiddlewareQueryException;
    
    /**
     * Returns the QTL IDs given a list of dataset IDs 
     * @param datasetIds
     * @return QTL IDs
     * @throws MiddlewareQueryException
     */
    public List<Integer> getQTLIdsByDatasetIds(List<Integer> datasetIds) throws MiddlewareQueryException;

    /**
     * Returns the list of AccMetadataSet from gdms_acc_metadataset matching given list of GIDs applied for given Set Operation
     * and matching dataset ID
     * @param gIds - list of germplasm IDs
     * @param datasetId
     * @param operation - operation to be applied for list of GIDs. Either IN or NOT IN.
     * @return
     * @throws MiddlewareQueryException
     */
    public List<AccMetadataSetPK> getAllFromAccMetadataset(List<Integer> gIds, Integer datasetId, SetOperation operation) throws MiddlewareQueryException;
    
    /**
     * Returns the map name and marker count given a list of marker ids.
     * @param markerIds
     * @return map name and marker count as a list of MapDetailElements
     * @throws MiddlewareQueryException
     */
    public List<MapDetailElement> getMapAndMarkerCountByMarkers(List<Integer> markerIds) throws MiddlewareQueryException;
    
    /**
     * Returns all MTA data from both central and local databases.
     */
    public List<Mta> getAllMTAs() throws MiddlewareQueryException;
    
    /**
     * Counts all MTA data from both central and local databases.
     */
    public long countAllMTAs() throws MiddlewareQueryException;
    
    /**
     * Returns all MTAs matching the given Trait ID
     * @param traitId
     * @return
     * @throws MiddlewareQueryException
     */
    public List<Mta> getMTAsByTrait(Integer traitId) throws MiddlewareQueryException;

    /**
     * Delete QTLs given a dataset id and a qtl id.
     * @param datasetId
     * @param qtlId
     * @throws MiddlewareQueryException
     */
    public void deleteQTLs(List<Integer> qtlIds, Integer datasetId) throws MiddlewareQueryException;
    
    /**
     * Delete SSRGenotypingDatasets by dataset id.
     * @param datasetId
     * @throws MiddlewareQueryException
     */
    public void deleteSSRGenotypingDatasets(Integer datasetId) throws MiddlewareQueryException;
    
    /**
     * Delete SNPGenotypingDatasets by dataset id.
     * @param datasetId
     * @throws MiddlewareQueryException
     */
    public void deleteSNPGenotypingDatasets(Integer datasetId) throws MiddlewareQueryException;

    /**
     * Delete DArTGenotypingDatasets by dataset id.
     * @param datasetId
     * @throws MiddlewareQueryException
     */
    public void deleteDArTGenotypingDatasets(Integer datasetId) throws MiddlewareQueryException;
    
    /**
     * Delete MappingPopulationDatasets by dataset id.
     * @param datasetId
     * @throws MiddlewareQueryException
     */
    public void deleteMappingPopulationDatasets(Integer datasetId) throws MiddlewareQueryException;
    
    /**
     * Retrieves the QTL details (including QTL ID and Trait ID/TID) given a Map ID.
     * @param mapId
     * @return list of QTL Details
     * @throws MiddlewareQueryException
     */
    public List<QtlDetails> getQtlDetailsByMapId(Integer mapId) throws MiddlewareQueryException;

    /**
     * Counts the QTL details (including QTL ID and Trait ID/TID) given a Map ID.
     * @param mapId
     * @return list of QTL Details
     * @throws MiddlewareQueryException
     */
    public long countQtlDetailsByMapId(Integer mapId) throws MiddlewareQueryException;
       
    /**
     * Delete Map by map id.
     * @param mapId
     * @throws MiddlewareQueryException
     */
    public void deleteMaps(Integer mapId) throws MiddlewareQueryException;
    
    /**
     * Retrieve the list of Marker IDs from CharValues matching list of GIDs.
     * @param gIds
     * @return
     */
    public List<Integer> getMarkerFromCharValuesByGids(List<Integer> gIds) throws MiddlewareQueryException;
    
    /**
     * Retrieve the list of Marker IDs from AlleleValues matching list of GIDs.
     * @param gIds
     * @return
     */
    public List<Integer> getMarkerFromAlleleValuesByGids(List<Integer> gIds) throws MiddlewareQueryException;
    
    /**
     * Retrieve the list of Marker IDs from MappingPop matching list of GIDs.
     * @param gIds
     * @return
     */
    public List<Integer> getMarkerFromMappingPopByGids(List<Integer> gIds) throws MiddlewareQueryException;

    /**
     * Retrieves the last ID of a given GDMS table. 
     * For the local instance, this will be the MIN value.
     * For the central instance, this will be the MAX value.
     * @param gdmsTable
     * @return the last ID of the GDMS table
     * @throws MiddlewareQueryException
     */
    public long getLastId(Database instance, GdmsTable gdmsTable) throws MiddlewareQueryException;
    
    /**
     * Adds MTA, Dataset, and DatasetUsers records to the database.
     * @param dataset
     * @param mta
     * @param users
     * @throws MiddlewareQueryException
     */
    public void addMTA(Dataset dataset, Mta mta, DatasetUsers users) throws MiddlewareQueryException;
}
