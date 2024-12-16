SELECT   
                                              o.person_id,  
                                              e.encounter_id,  
                                              e.visit_id,  
                                              e.encounter_datetime,  
                                              e.encounter_type,  
                                              l.uuid AS location_uuid,  
                                              o.concept_id,  
                                              cn.name AS concept_name,  
                                             -- max(o.obs_datetime),  
                                              COALESCE(o.value_coded,  
                                                      o.value_datetime,  
                                                      o.value_numeric,  
                                                      o.value_text) AS value,  
                                              cd.name AS value_type,  
                                              c.datatype_id,  
                                              et.name AS encounterName,  
                                              e.creator AS provider_id,  
                                              'HIV Enrollment' AS Category  
                                          FROM  
                                              amrs.obs o  
                                                  INNER JOIN  
                                              amrs.encounter e ON (o.encounter_id = e.encounter_id)  
                                                  INNER JOIN  
                                              amrs.encounter_type et ON et.encounter_type_id = e.encounter_type  
                                                  INNER JOIN  
                                              amrs.concept c ON c.concept_id = o.concept_id  
                                                  INNER JOIN  
                                              amrs.concept_name cn ON o.concept_id = cn.concept_id  and cn.locale_preferred = 1 
                                                  INNER JOIN  
                                              amrs.concept_datatype cd ON cd.concept_datatype_id = c.datatype_id  
                                                  INNER JOIN  
                                              amrs.location l ON e.location_id = l.location_id  
                                          WHERE  
                                                  o.concept_id IN(6749,10747,10748,7013,1499,9203,6748,1633,2155,5356,966,1088,5419,10804,6032,6176,5272,5629,1174,7013,1499,9203,6748) -- 5356 
                                                  and e.patient_id in (1171851)  
                                                  AND e.voided = 0 
                                                 group by o.concept_id,e.patient_id
                                          ORDER BY o.encounter_id   ASC 
                                           