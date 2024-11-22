SELECT patient_id,MIN(regimen_data.enc_id) AS Encounter_ID, 
concept_id,value_coded,Encounter_Date,GROUP_CONCAT(concept_name SEPARATOR ",") as Regimen,Reason_for_Change FROM 
(
	SELECT o.person_id as patient_id,o.encounter_id as enc_id,o.concept_id,o.value_coded,o.voided,e.encounter_datetime AS Encounter_Date, cn.name as concept_name  
		from amrs.obs o
		INNER JOIN amrs.concept_name cn ON o.value_coded=cn.concept_id and cn.locale='en' and cn.concept_name_type='FULLY_SPECIFIED' 
		INNER JOIN amrs.encounter e ON e.encounter_id=o.encounter_id and e.voided=0 
	where o.concept_id=1088 and o.voided=0 
    and o.location_id in (339)
    and o.person_id in(704258)
	GROUP BY patient_id, o.value_coded 
)
 as regimen_data
 LEFT OUTER JOIN (
	 SELECT encounter_id,cn.name AS Reason_for_Change
		 FROM amrs.obs o 
		 INNER JOIN amrs.concept_name cn ON o.value_coded=cn.concept_id and o.voided=0 
	 AND o.concept_id in (1252, 1262, 1266,1269) and cn.locale='en'  and cn.concept_name_type='FULLY_SPECIFIED' 
	 GROUP BY encounter_id 
 ) as reg_fail 
 ON regimen_data.enc_id=reg_fail.encounter_id
 
 GROUP BY enc_id ORDER BY patient_id ASC,Encounter_Date ASC 
 