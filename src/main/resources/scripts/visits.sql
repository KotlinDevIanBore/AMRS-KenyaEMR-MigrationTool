select v.visit_id,e.patient_id,visit_type_id,date_started,e.location_id,date_started,encounter_type,v.voided from amrs.visit v
inner join  amrs.encounter e on e.visit_id = v.visit_id
where e.location_id in (2,379,339)
group by  v.visit_id