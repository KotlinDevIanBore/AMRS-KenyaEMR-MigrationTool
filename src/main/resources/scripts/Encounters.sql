-- use amrs;
select *,e.patient_id, o.concept_id,o.obs_datetime,o.value_coded, '423c034e-14ac-4243-ae75-80d1daddce55' kenyaemr_uuid, '' kenyaemr_value from amrs.obs  o
inner join amrs.encounter e on e.encounter_id = o.encounter_id
inner join amrs.concept c 
inner join amrs_migration.patients p on p.person_id=e.patient_id
 where o.concept_id in (1724)