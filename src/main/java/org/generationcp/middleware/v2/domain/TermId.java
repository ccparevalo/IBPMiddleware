package org.generationcp.middleware.v2.domain;

public enum TermId {

	//Standard Variable
	STANDARD_VARIABLE(1070)
	, STUDY_INFORMATION(1010)
	, VARIABLE_DESCRIPTION(1060)
	, IBDB_STRUCTURE(1000)
	
	//CV Term Relationship
	, HAS_METHOD(1210)
	, HAS_PROPERTY(1200)
	, HAS_SCALE(1220)
	, HAS_TYPE(1105)
	, HAS_VALUE(1190)
	, IS_A(1225)
	, STORED_IN(1044)
	
	//Study Fields
	, STUDY_NAME(8005)
	, PM_KEY(8040)
	, STUDY_TITLE(8007)
	, STUDY_OBJECTIVE(8030)
	, PI_ID(8110)
	, STUDY_TYPE(8070)
	, START_DATE(8050)
	, END_DATE(8060)
	, STUDY_UID(8020)
	, STUDY_IP(8120)
	, CREATION_DATE(8048)
	
	// Numeric Data Fields
	, NUMERIC_VARIABLE(1110)
	, DATE_VARIABLE(1117)
	, NUMERIC_DBID_VARIABLE(1118)
	, CHARACTER_DBID_VARIABLE(1128)
	, CHARACTER_VARIABLE(1120)
	
	//Variate Types
	, OBSERVATION_VARIATE(1043)
	, CATEGORICAL_VARIATE(1048)
	
	//Folder, Study, Dataset Nodes
	, HAS_PARENT_FOLDER(1140)
	, BELONGS_TO_STUDY(1150) 
	, IS_STUDY(1145)
	
	//Properties
	, SEASON(2452)
	, GID(8240)
	
	// Experiment Types
	, STUDY_EXPERIMENT(1010)
	, DATASET_EXPERIMENT(1015)
	, TRIAL_ENVIRONMENT_EXPERIMENT(1020)
	, PLOT_EXPERIMENT(1155)
	
	// Location storage
	, TRIAL_ENVIRONMENT_INFO_STORAGE(1020)
    , TRIAL_INSTANCE_STORAGE(1021)
    , LATITUDE_STORAGE(1022)
    , LONGITUDE_STORAGE(1023)
    , DATUM_STORAGE(1024)
    , ALTITUDE_STORAGE(1025)
    
    // Germplasm storage
    , GERMPLASM_ENTRY_STORAGE(1040)
    , ENTRY_NUMBER_STORAGE(1041)
    , ENTRY_GID_STORAGE(1042)
    , ENTRY_DESIGNATION_STORAGE(1046)
    , ENTRY_CODE_STORAGE(1047)
	
    // Experiment storage
    , TRIAL_DESIGN_INFO_STORAGE(1030)
    	
	// Other
    , ORDER(1420)
	, MIN_VALUE(1113)
	, MAX_VALUE(1115)

	// Stock Type
	, ENTRY_CODE(8300)
	;
	;
	
	private final Integer id;
	
	private TermId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
}