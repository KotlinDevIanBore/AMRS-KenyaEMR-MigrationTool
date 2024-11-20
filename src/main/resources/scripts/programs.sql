select  pp.patient_id,  
		p.name, 
                   pp.uuid program_uuid, 
				   pp.location_id, 
                       l.uuid location_uuid, 
                       p.concept_id, 
                       pp.date_enrolled, 
                       pp.date_completed 
                       from amrs.patient_program pp 
                       inner join amrs.encounter e on e.patient_id=pp.patient_id 
                       inner join amrs.program p on p.program_id=pp.program_id 
                       inner join amrs.location l on l.location_id = e.location_id 
                       -- and l.uuid in ("08feb14c-1352-11df-a1f1-0026b9348838, 8cad59c8-7f88-4964-aa9e-908f417f70b2") 
                       group by  pp.patient_id,p.concept_id limit 5