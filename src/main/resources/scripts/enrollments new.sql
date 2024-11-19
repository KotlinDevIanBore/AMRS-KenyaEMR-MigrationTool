select person_id,encounter_id,enrollment_date,hiv_start_date,death_date,transfer_out,transfer_out_date from etl.flat_hiv_summary_v15b  
where is_clinical_encounter = 1 and next_clinical_datetime_hiv is null
and location_id in (339,2,379)
-- and person_id=1177153
--  limit 10