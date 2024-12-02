SELECT 
    pa.person_id,
    pt.person_attribute_type_id,
    case when pt.person_attribute_type_id =5 then '1054'
    when pt.person_attribute_type_id =42 then '1542'
    when pt.person_attribute_type_id =73 then '1712'
    end kenyaemr_concept,
    pt.name,
    pa.value
    
FROM
    amrs.person_attribute pa
        INNER JOIN
    amrs.person_attribute_type pt ON pa.person_attribute_type_id = pt.person_attribute_type_id and pt.person_attribute_type_id in(42,73,5)
    inner join amrs.con
WHERE
     pa.person_id IN (1220891,1191232, 1199830, 1170791, 1174464, 1206185)
        AND 
        pa.voided = 0