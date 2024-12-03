SELECT
pn.given_name AS 'First Name',
pn.middle_name 'Middle Name (optional)',
pn.family_name AS 'Family Name',
p.gender as 'Sex',
p.birthdate as 'Date of Birth ',
p.birthdate_estimated AS 'Birth date estimated',
p.dead AS 'Dead',
p.death_date AS 'Date of death',
pis.`UPI`,
pis.`Universal Identifier`,
pis.`CCC Number`,
pis.`PrEP Number`,
pis.`HEI Number`,
pis.`CPMIS ID`,
pis.`Birth Certificate Number (optional)`,
pis.`Driving License Number (optional)`,
pis.`Huduma Number (optional)`,
pis.`National ID (optional)`,
pis.`Passport Number (optional)`,
pis.`Patient Clinic Number (optional)`,
pis.`Social Health Authority Identification Number (optional)`,
pis.`Social Health Insurance Number (optional)`,
pis.`AMRS ID`,
pis.`NHIF Number (optional)`,
pa.`County (optional)`,
pa.`Sub County (optional)`,
pa.`Ward (optional)`,
pattr.`Telephone contact`,
pattr.`Alternate phone number (optional)`,
pa.`Postal Address (optional)`,
pattr.`Email address (optional)`,
pa.`Location`,
pa.`Sub-location`,
pa.`Village`,
pattr.`Landmark`,
pattr.`Nearest Health Center`,
demographics2.marital_status AS 'Marital status',
demographics2.education_level AS 'Education',
demographics2.occupation AS 'Occupation',
pattr.`Next of Kin Name (optional)`,
pattr.`Next of Kin Relationship (optional)`,
pattr.`Next of Kin Phone Number (optional)`,
pattr.`Next of Kin Postal Address (optional)`

 FROM person_name pn
 INNER JOIN person p ON pn.person_id=p.person_id
 INNER JOIN ( -- FILTER PERSONS FOR THIS LOCATION WITH ENCOUNTERS
 SELECT Distinct(patient_id) as person_id FROM encounter e where e.voided=0 AND location_id in (2,339)
	UNION
	SELECT person_id FROM users u
 ) ps ON pn.person_id=ps.person_id
 INNER JOIN (
 SELECT
patient_id,
MAX(CASE WHEN pit.name='UPI Number' THEN pi.identifier END) AS 'UPI',
MAX(CASE WHEN pit.name='AMRS Universal ID' THEN pi.identifier END) AS 'Universal Identifier',
MAX(CASE WHEN pit.name IN ('CCC Number') THEN pi.identifier END) AS 'CCC Number',
MAX(CASE WHEN pit.name='PrEP' THEN pi.identifier END) AS 'PrEP Number',
MAX(CASE WHEN pit.name='HEI ID Number' THEN pi.identifier END) AS 'HEI Number',
MAX(CASE WHEN pit.name='CPIMS Number' THEN pi.identifier END) AS 'CPMIS ID',
MAX(CASE WHEN pit.name='Birth Certificate Number' THEN pi.identifier END) AS 'Birth Certificate Number (optional)',
MAX(CASE WHEN pit.name='Driving License Number' THEN pi.identifier END) AS 'Driving License Number (optional)',
MAX(CASE WHEN pit.name='Huduma Number' THEN pi.identifier END) AS 'Huduma Number (optional)',
MAX(CASE WHEN pit.name='Kenya National ID Number' THEN pi.identifier END) AS 'National ID (optional)',
MAX(CASE WHEN pit.name='Passport Number' THEN pi.identifier END) AS 'Passport Number (optional)',
MAX(CASE WHEN pit.name='Patient Clinic Number' THEN pi.identifier END) AS 'Patient Clinic Number (optional)',
MAX(CASE WHEN pit.name='Social Health Authority Identification Number' THEN pi.identifier END) AS 'Social Health Authority Identification Number (optional)',
MAX(CASE WHEN pit.name='Social Health Insurance Number' THEN pi.identifier END) AS 'Social Health Insurance Number (optional)',
MAX(CASE WHEN pit.name IN ('AMRS Medical Record Number','Old AMPATH Medical Record Number') THEN pi.identifier END) AS 'AMRS ID',
MAX(CASE WHEN pit.name='NHIF Number' THEN pi.identifier END) AS 'NHIF Number (optional)'
FROM patient_identifier_type pit
INNER JOIN patient_identifier pi ON pit.patient_identifier_type_id=pi.identifier_type
AND pi.voided=0 AND location_id in(2,339)
GROUP BY patient_id
 ) pis ON pn.person_id=pis.patient_id

 LEFT OUTER JOIN (
 SELECT
person_id ,
address1 as 'County (optional)',
address2 AS 'Sub County (optional)',
null AS 'Postal Address (optional)',--
address4 AS 'Ward (optional)',
address6  AS 'Location',
address5 AS 'Sub-location',
city_village AS 'Village'
 FROM person_address where voided=0
 group by person_id
 ) pa ON pn.person_id=pa.person_id

 LEFT OUTER JOIN (

 SELECT
person_id,

MAX(CASE WHEN pat.name='Landmark' THEN pa.value END) AS 'Landmark',


MAX(CASE WHEN pat.name='Contact Phone Number' THEN pa.value END) AS 'Telephone contact',
MAX(CASE WHEN pat.name='Alternative contact phone number' THEN pa.value END) AS 'Alternate phone number (optional)',
MAX(CASE WHEN pat.name like '%Email%' THEN pa.value END) AS 'Email address (optional)',
null AS 'Nearest Health Center',

MAX(CASE WHEN pat.name='Next of Kin name' THEN pa.value END) AS 'Next of Kin Name (optional)',
MAX(CASE WHEN pat.name='Relationship to Next of Kin' THEN pa.value END) AS 'Next of Kin Relationship (optional)',
MAX(CASE WHEN pat.name='Next of Kin Contact Phone Number' THEN pa.value END) AS 'Next of Kin Phone Number (optional)',
MAX(CASE WHEN pat.name in ('Next of kin rural landmark','Next of Kin Urban Landmark') THEN pa.value END) AS 'Next of Kin Postal Address (optional)'
 FROM person_attribute pa
 INNER JOIN person_attribute_type pat ON pa.person_attribute_type_id=pat.person_attribute_type_id and pa.voided=0
 group by person_id
 ) pattr ON pn.person_id=pattr.person_id
  LEFT OUTER JOIN (
SELECT person_id as patient_id,
MAX(CASE WHEN o.concept_id=1605 THEN cn_edu.name END) AS education_level,
MAX(CASE WHEN o.concept_id=1972 THEN cn_occ.name END) AS occupation,
MAX(CASE WHEN o.concept_id=1054 THEN cn_mar.name END) AS marital_status

FROM obs o
LEFT JOIN concept_name cn_edu ON o.value_coded=cn_edu.concept_id AND cn_edu.locale='en'
LEFT JOIN concept_name cn_occ ON o.value_coded=cn_occ.concept_id AND cn_occ.locale='en'
LEFT JOIN concept_name cn_mar ON o.value_coded=cn_mar.concept_id AND cn_mar.locale='en'
WHERE o.concept_id in (1972,1605,1054) and o.voided=0 and location_id in (2,339)
Group by person_id
 ) demographics2 ON pn.person_id=demographics2.patient_id
 GROUP BY pn.person_id
 ;