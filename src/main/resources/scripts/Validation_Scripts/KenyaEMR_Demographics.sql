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
pa.`Landmark`,
pattr.`Nearest Health Center`,
demographics2.marital_status AS 'Marital status',
demographics2.education_level AS 'Education',
demographics2.occupation AS 'Occupation',
pattr.`Next of Kin Name (optional)`,
pattr.`Next of Kin Relationship (optional)`,
pattr.`Next of Kin Phone Number (optional)`,
pattr.`Next of Kin Postal Address (optional)`

 FROM openmrs.person_name pn
 INNER JOIN person p ON pn.person_id=p.person_id
 INNER JOIN (
 SELECT
patient_id,
CASE WHEN pit.name='National Unique patient identifier' THEN pi.identifier END AS 'UPI',
CASE WHEN pit.name='Universal Identifier' THEN pi.identifier END AS 'Universal Identifier',
CASE WHEN pit.name='Unique Patient Number' THEN pi.identifier END AS 'CCC Number',
CASE WHEN pit.name='PREP Unique Number' THEN pi.identifier END AS 'PrEP Number',
CASE WHEN pit.name='HEI ID Number' THEN pi.identifier END AS 'HEI Number',
CASE WHEN pit.name='CPIMS Number' THEN pi.identifier END AS 'CPMIS ID',
CASE WHEN pit.name='Birth Certificate Number' THEN pi.identifier END AS 'Birth Certificate Number (optional)',
CASE WHEN pit.name='Driving License Number' THEN pi.identifier END AS 'Driving License Number (optional)',
CASE WHEN pit.name='Huduma Number' THEN pi.identifier END AS 'Huduma Number (optional)',
CASE WHEN pit.name='National ID' THEN pi.identifier END AS 'National ID (optional)',
CASE WHEN pit.name='Passport Number' THEN pi.identifier END AS 'Passport Number (optional)',
CASE WHEN pit.name='Patient Clinic Number' THEN pi.identifier END AS 'Patient Clinic Number (optional)',
CASE WHEN pit.name='Social Health Authority Identification Number' THEN pi.identifier END AS 'Social Health Authority Identification Number (optional)',
CASE WHEN pit.name='Social Health Insurance Number' THEN pi.identifier END AS 'Social Health Insurance Number (optional)',
CASE WHEN pit.name='AMRS ID' THEN pi.identifier END AS 'AMRS ID',
CASE WHEN pit.name='NHIF Number' THEN pi.identifier END AS 'NHIF Number (optional)'
FROM openmrs.patient_identifier_type pit
INNER JOIN patient_identifier pi ON pit.patient_identifier_type_id=pi.identifier_type
GROUP BY patient_id
 ) pis ON pn.person_id=pis.patient_id

 LEFT OUTER JOIN (
 SELECT
person_id ,
county_district as 'County (optional)',
state_province AS 'Sub County (optional)',
address1 AS 'Postal Address (optional)',
address4 AS 'Ward (optional)',
address6  AS 'Location',
address5 AS 'Sub-location',
city_village AS 'Village',
address2 AS 'Landmark'
 FROM openmrs.person_address
 group by person_id
 ) pa ON pn.person_id=pa.person_id

 LEFT OUTER JOIN (

 SELECT
person_id,
MAX(CASE WHEN pat.name='Telephone contact' THEN pa.value END) AS 'Telephone contact',
MAX(CASE WHEN pat.name='Alternate Phone Number' THEN pa.value END) AS 'Alternate phone number (optional)',
MAX(CASE WHEN pat.name='Email address' THEN pa.value END) AS 'Email address (optional)',
MAX(CASE WHEN pat.name='Nearest Health Facility' THEN pa.value END) AS 'Nearest Health Center',

MAX(CASE WHEN pat.name='Next of kin name' THEN pa.value END) AS 'Next of Kin Name (optional)',
MAX(CASE WHEN pat.name='Next of kin relationship' THEN pa.value END) AS 'Next of Kin Relationship (optional)',
MAX(CASE WHEN pat.name='Next of kin contact' THEN pa.value END) AS 'Next of Kin Phone Number (optional)',
MAX(CASE WHEN pat.name='Next of kin address' THEN pa.value END) AS 'Next of Kin Postal Address (optional)'


 FROM person_attribute pa
 INNER JOIN person_attribute_type pat ON pa.person_attribute_type_id=pat.person_attribute_type_id
 group by person_id
 ) pattr ON pn.person_id=pattr.person_id


 LEFT OUTER JOIN (
 select o.person_id as patient_id,
             max(if(o.concept_id in(1054),cn.name,null))  as marital_status,
             max(if(o.concept_id in(1712),cn.name,null))  as education_level,
             max(if(o.concept_id in(1542),cn.name,null))  as occupation,
             max(o.date_created) as date_created
                   from obs o
             join concept_name cn on cn.concept_id=o.value_coded and cn.concept_name_type='FULLY_SPECIFIED'
                                       and cn.locale='en'
      where o.concept_id in (1054,1712,1542) and o.voided=0
      group by person_id
 ) demographics2 ON pn.person_id=demographics2.patient_id
 ;