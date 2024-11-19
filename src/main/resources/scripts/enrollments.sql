select e.c from encounter e
inner join encounter_type et on e.encounter_type=et.encounter_type_id
inner join obs o on e.encounter_id =o.encounter_id
where patient_id='1097287' and et.encounter_type_id in ( 1,3,105,265,266,32) order by e.encounter_id asc;
 
 -- select * from  encounter_type where name like'%INITIAL%'
 -- 1,3,105,265,266,32