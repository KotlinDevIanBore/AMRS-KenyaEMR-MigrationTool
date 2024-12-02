
select  pa.person_id,
pt.person_attribute_type_id,
pt.name,
 pa.value,
case when pt.person_attribute_type_id=10 then 'b2c38640-2603-4629-aebd-3b54f33f1e3a' -- Telephone
when pt.person_attribute_type_id =12 then '830bef6d-b01f-449d-9f8d-ac0fede8dbd3'
when pt.person_attribute_type_id =25 then '342a1d39-c541-4b29-8818-930916f4c2dc' -- contact
when pt.person_attribute_type_id =0 then '7cf22bec-d90a-46ad-9f48-035952261294' -- Kin Address
 when pt.person_attribute_type_id =0 then '94614350-84c8-41e0-ac29-86bc107069be' -- alternative phone
else null end as kenyaemruuid
 from amrs.person_attribute pa
 inner join amrs.person_attribute_type pt on pa.person_attribute_type_id = pt.person_attribute_type_id
where pa.person_id  in (1220891) and pa.voided=0