select person_id,pii.identifier
,address1 county 
,address2 sub_county
,city_village
,state_province 
,county_district
,address3 landmark
from amrs.person_address  pa
inner join amrs.patient_identifier pii on pii.patient_id=pa.person_id
where pa.person_id >=1200891 and pa.preferred=1 and state_province is not null    limit 50