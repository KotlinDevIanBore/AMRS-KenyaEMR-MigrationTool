WITH cte_vitals_concepts as (
SELECT 
    concept_id, uuid
FROM
    amrs.concept
WHERE
    uuid IN (
		'a8a65d5a-1350-11df-a1f1-0026b9348838', 
        'a8a65e36-1350-11df-a1f1-0026b9348838',
        'a8a65f12-1350-11df-a1f1-0026b9348838',
        'a8a6f71a-1350-11df-a1f1-0026b9348838',
        'a8a65fee-1350-11df-a1f1-0026b9348838',
        'a8a660ca-1350-11df-a1f1-0026b9348838',
        'a8a6619c-1350-11df-a1f1-0026b9348838',
        '5099d8a8-36c1-4574-a568-9bc49c15c08c',
        '507f48e7-26fc-490b-a521-35d7c5aa8e9f',
        'a8a66354-1350-11df-a1f1-0026b9348838',
        'a89c60c0-1350-11df-a1f1-0026b9348838',
        '9061e5d5-8478-4d16-be44-bfec05b6705a',
        'a89c6188-1350-11df-a1f1-0026b9348838')
)

SELECT 
o.person_id,
o.encounter_id, 
e.encounter_datetime,
e.visit_id,
o.location_id,
o.concept_id,
case  when o.concept_id=1342 then '1342AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
  when o.concept_id=1343 then '1343AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
  when o.concept_id=5085 then '5085AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
  when o.concept_id=5086 then '5086AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
  when o.concept_id=5087 then '5087AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
  when o.concept_id=5088 then '5088AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
  when o.concept_id=5089 then '5089AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
  when o.concept_id=5090 then '5090AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
  when o.concept_id=5092 then '5092AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
  when o.concept_id=5242 then '5242AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
 end  kenyaemr_uuid,
o.obs_datetime,
o.value_numeric
-- max(o.obs_datetime) as obs_datetime,
-- max(case when o.concept_id = 9816 then o.value_numeric end) as height_age_zscore,
-- max(case when o.concept_id = 8238 then o.value_numeric end) as weight_height_zscore,
-- max(case when o.concept_id = 8239 then o.value_numeric end) as bmi_age_zscore,
-- max(case when o.concept_id = 1342 then o.value_numeric end) as bmi,
-- max(case when o.concept_id = 1343 then o.value_numeric end) as muac_mm,
-- max(case when o.concept_id = 5085 then o.value_numeric end) as systolic_bp,
-- max(case when o.concept_id = 5086 then o.value_numeric end) as diastolic_bp,
-- max(case when o.concept_id = 5087 then o.value_numeric end) as pulse,
-- max(case when o.concept_id = 5088 then o.value_numeric end) as temperature,
-- max(case when o.concept_id = 5092 then o.value_numeric end) as spo2,
-- max(case when o.concept_id = 5242 then o.value_numeric end) as rr,
-- max(case when o.concept_id = 5089 then o.value_numeric end) as weight,
-- max(case when o.concept_id = 5090 then o.value_numeric end) as height 
FROM amrs.obs o 
INNER JOIN amrs.encounter e using(encounter_id)
WHERE o.concept_id IN (SELECT concept_id FROM cte_vitals_concepts) 
AND o.location_id IN(2, 339) 
AND o.person_id = 1225788
GROUP BY o.person_id, o.encounter_id,o.concept_id