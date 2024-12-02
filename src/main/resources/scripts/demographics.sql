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
                   pa.address4, 
                   pa.address5, 
                   pa.address6, 
                   p.dead, 
                   p.cause_of_death,
                   case 
						when c.concept_id = 16 then 142412
						when c.concept_id = 43 then 114100
						when c.concept_id = 58 then 112141
						when c.concept_id = 60 then 115835
						when c.concept_id = 84 then 84
						when c.concept_id = 86 then 86
						when c.concept_id = 102 then 102
						when c.concept_id = 123 then 116128
						when c.concept_id = 148 then 112234
						when c.concept_id = 507 then 507
						when c.concept_id = 903 then 117399
						when c.concept_id = 1067 then 1067
						when c.concept_id = 1107 then 1107
						-- when c.concept_id = 1548 then --
						when c.concept_id = 1571 then 125561
						-- when c.concept_id = 1572 then --
						when c.concept_id = 1593 then 159
						when c.concept_id = 2375 then 137296
						when c.concept_id = 5041 then 5041
						when c.concept_id = 5547 then 119975
						when c.concept_id = 5622 then 5622
						when c.concept_id = 6483 then 139444
						when c.concept_id = 7257 then 134612
						when c.concept_id = 7971 then 145717
						-- when c.concept_id = 10363 then 
						-- when c.concept_id = 10364 then 
						-- when c.concept_id = 10365 then 
						when c.concept_id = 10366 then 133814
						-- when c.concept_id = 10367 then 
						-- when c.concept_id = 10654 then
						when c.concept_id = 12038 then 155762 
						end as kmr_concept_id,
                   case
						when c.concept_id = 16 then '142412AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 43 then '114100AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 58 then '112141AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 60 then '115835AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 84 then '84AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 86 then '86AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 102 then '102AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 123 then '116128AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 148 then '112234AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 507 then '507AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 903 then '117399AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 1067 then '1067AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 1107 then '1107AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						-- when c.concept_id = 1548 then '' --
						when c.concept_id = 1571 then '125561AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						-- when c.concept_id = 1572 then '' --
						when c.concept_id = 1593 then '159AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 2375 then '137296AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 5041 then '5041AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 5547 then '119975AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 5622 then '5622AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 6483 then '139444AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 7257 then '134612AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						when c.concept_id = 7971 then '145717AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						-- when c.concept_id = 10363 then ''
						-- when c.concept_id = 10364 then ''
						-- when c.concept_id = 10365 then ''
						when c.concept_id = 10366 then '133814AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA' --
						-- when c.concept_id = 10367 then ''
						-- when c.concept_id = 10654 then ''
						when c.concept_id = 12038 then '155762AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
						end as kmr_concept_uuid ,    
                   p.birthdate_estimated, 
                   p.voided, 
                   l.location_id, 
                   l.name, 
                   pa.address1 county,  
                   pa.address2 sub_county, 
                   pa.city_village, 
                   pa.state_province,  
                   pa.county_district, 
                   pa.address3 landmark 
                   from amrs.encounter e  
                   inner join amrs.patient pt on e.patient_id =pt.patient_id 
                   inner join amrs.person p on p.person_id=pt.patient_id 
                   inner join amrs.person_name pn on pn.person_id=p.person_id 
                   inner join amrs.person_address pa on pa.person_id=p.person_id and pa.preferred=1 
                   inner join amrs.location l on e.location_id=l.location_id 
                   where l.uuid in ( 2   )  -- and p.person_id >  + pid +   
                   group by pt.patient_id 
                   order by e.patient_id asc limit 10