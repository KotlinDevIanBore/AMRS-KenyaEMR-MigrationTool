select 
-- e.*,
e.patient_id,
e.encounter_id,
e.encounter_datetime,
-- e.form_id,
-- f.name,
(case f.form_id when '15' then 164144 -- New
     when o.concept_id=10194  then 160563  --  'Transfer-In'
     when f.form_id then 164931  -- Transit
     else NULL end) as Patient_Type,
    
  
     (case  when o.concept_id=2051 and o.value_coded =2047   then 160539  -- 'VCT'
     when  o.concept_id=2051 and o.value_coded =2240 then 162050  -- 'ccc'
      when  o.concept_id=2051 and o.value_coded =2177 then 162050 -- 'PITC Mappend to CCC'
     when 'Medical out patient' then 160542
     when o.concept_id=2051 and o.value_coded =1965 then 160542 -- 'OPD'
     when 'Inpatient Adult' then 160536
     when 'Inpatient Child' then 160537
     when o.concept_id=2051 and o.value_coded =1776 then 160536 -- 'PMTCT'
     when 'Mother Child Health' then 159937
     when 'TB Clinic' then 160541
     when 'Unknown' then 162050
     when 'Other' then 5622 else o.value_coded end) as Entry_point ,
	 -- null as TI_Facility,
	case  when o.concept_id=7013  then o.value_datetime else null end  Date_first_enrolled_in_care
/*
    enr.Transfer_in_date,
    enr.Date_started_art_at_transferring_facility,
    enr.Date_confirmed_hiv_positive,
    enr.Facility_confirmed_hiv_positive,
    enr.Baseline_arv_use,
    (case enr.Purpose_of_baseline_arv_use when 'PMTCT' then 1148
     when 'PEP' then 1691
     when 'ART' then 1181 else NULL end) as Purpose_of_baseline_arv_use,
    (case enr.Baseline_arv_regimen when 'AF2D (TDF + 3TC + ATV/r)' then 164512
     when 'AF2A (TDF + 3TC + NVP)' then 162565
     when 'AF2B (TDF + 3TC + EFV)' then 164505
     when 'AF1A (AZT + 3TC + NVP' then 1652
     when 'AF1B (AZT + 3TC + EFV)' then 160124
     when 'AF4B (ABC + 3TC + EFV)' then 162563
     when 'AF4A (ABC + 3TC + NVP)' then 162199
     when 'CF2A (ABC + 3TC + NVP)' then 162199
     when 'CF2D (ABC + 3TC + LPV/r)' then 162200
     when 'CF2B (ABC + 3TC + EFV)' then 162563 else NULL end) as Baseline_arv_regimen,
    enr.Baseline_arv_regimen_line,
    enr.Baseline_arv_date_last_used,
    (case enr.Baseline_who_stage when 'Stage1' then 1204
     when 'Stage2' then 1205
     when 'Stage3' then 1206
     when 'Stage4' then 1207
     when 'Unknown' then 1067 else NULL end) as Baseline_who_stage,
    enr.Baseline_cd4_results,
    enr.Baseline_cd4_date,
    enr.Baseline_vl_results,
    enr.Baseline_vl_date,
    enr.Baseline_vl_ldl_results,
    enr.Baseline_vl_ldl_date,
    enr.Baseline_HBV_Infected,
    enr.Baseline_TB_Infected,
    enr.Baseline_Pregnant,
    enr.Baseline_Breastfeeding,
    enr.Baseline_Weight,
    enr.Baseline_Height,
    enr.Baseline_BMI,
    enr.Name_of_treatment_supporter,
    enr.Relationship_of_treatment_supporter,
    enr.Treatment_supporter_telephone,
    enr.Treatment_supporter_address,
	*/
from 
amrs.encounter e  
inner join amrs.obs o on e.encounter_id=o.encounter_id
inner join amrs.form f on f.form_id=e.form_id
where e.encounter_type in (1 , 3, 24, 32, 105, 137, 135, 136, 265, 266)
and e.location_id in (2,336,98) and e.voided=0
and o.concept_id in (2051,7013)
group by e.encounter_id