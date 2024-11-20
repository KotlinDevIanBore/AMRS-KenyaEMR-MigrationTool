 select  
                 p.uuid, 
                 p.person_id, 
                 pn.given_name, 
                 pn.family_name, 
                 pn.middle_name, 
                 p.gender, 
                 p.birthdate, 
                 pa.address1, 
                 pa.county_district, 
                 pa.address2, 
                 pa.address5, 
                 pa.address6, 
                 p.dead, 
                 p.birthdate_estimated, 
                 p.voided, 
                 l.uuid,
                 l.location_id, 
                 l.name
                 from amrs.encounter e  
                 inner join amrs.patient pt on e.patient_id =pt.patient_id 
                 inner join amrs.person p on p.person_id=pt.patient_id 
                 inner join amrs.location l on e.location_id=l.location_id 
                 -- inner join amrs.patient_identifier pii on pii.patient_id=e.patient_id and l.location_id=pii.location_id
                 inner join amrs.person_name pn on pn.person_id=p.person_id 
                 inner join amrs.person_address pa on pa.person_id=p.person_id 
                 where l.uuid in("08feb14c-1352-11df-a1f1-0026b9348838","8cad59c8-7f88-4964-aa9e-908f417f70b2") -- and  pii.identifier='15229-08081' -- and e.patient_id<1098247  
                 group by pt.patient_id 
                 order by e.patient_id desc limit 10