select  -- distinct(concept_id)
-- *,
 distinct(o.concept_id),cn.name from orders o
inner join encounter e on e.encounter_id = o.encounter_id
inner join concept_name cn on cn.concept_id =o.concept_id and cn.locale='en' and locale_preferred=1
 -- where  e.location_id in (2,339,379)