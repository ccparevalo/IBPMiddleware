package org.generationcp.middleware.v2.pojos;

public enum CVTermId {

	//Standard Variable
	STANDARD_VARIABLE(1070)
	, STUDY_INFORMATION(1010)
	, VARIABLE_DESCRIPTION(1060)
	, GERMPLASM_ENTRY(1040)
	, TRIAL_DESIGN_INFO(1030)
	, TRIAL_ENVIRONMENT_INFO(1020)
	
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
	, RELEASE_DATE(8130)
	
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
	, IS_STUDY(1145) //TODO For confirmation
	
	//Properties
	, SEASON(2452)
	
	;
	
	private final Integer id;
	
	private CVTermId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
}
