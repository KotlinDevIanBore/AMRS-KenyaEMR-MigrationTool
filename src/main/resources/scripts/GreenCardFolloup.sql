SELECT o.person_id as patient_id,e.form_id,o.concept_id,o.encounter_id,e.encounter_datetime,
 cn.name question,
case when o.value_datetime is not null then o.value_datetime
when o.value_coded is not null then o.value_coded
when o.value_numeric is not null then o.value_numeric
when o.value_text is not null then o.value_text end 
as value  
                  FROM amrs.obs o  
                  INNER JOIN amrs.concept c ON o.concept_id=c.concept_id  
				INNER JOIN amrs.concept_name cn ON o.concept_id = cn.concept_id
                 and cn.locale_preferred=1
              -- LEFT JOIN amrs.concept_name cn_answer ON o.value_coded = cn_answer.concept_id and cn_answer.locale_preferred=1
			   AND o.person_id IN(59807)  
                  AND c.concept_id in (1246,1412,10653,5242,
                  10805,1343,9782,6578,12258,5356,6048,5219,10893,10727, 
                  6176,9742,10591,6174,1271,12,1272,10761,2028,10676,7502,10677, 
                  7637,1113,1111,8292,11308,8293,10679,10785,10786,10787, 
                  10788,1266,10681,6793,2031,6968,10706,10239,6137,11679,1065, 
                  1066,1664,1193,7897,1198,1915,10707,1836,2061,5272,9736,10814, 
                  5596,12253,10708,7947,5624,5632,8355,374,6687,1119,10987,1120, 
                  10821,7343,11705,10845,1123,9467,1124,1125,1129,1126,1128,6042, 
                  7222,1109,10831,10832,10833,10834,8288,6287,6259,10726,2312, 
                  10400,9611,9609,9070,5096,1835,9605,10988,1724,10102,10103, 
                  10104,10105,10106,10107,10108,10109,5616,10381,12384,1629, 
                  6748,10984,11930,7656) -- 5088,5087,5085,5086,5089,5090,5092 
                  INNER JOIN amrs.encounter e ON o.encounter_id=e.encounter_id and e.voided=0 and o.voided=0  
                  and e.encounter_type in(2,4,106,176) and e.encounter_id in (14763811)
                  ORDER BY patient_id ASC,encounter_id DESC