SELECT
    o.obs_id,
    o.concept_id,
    o.person_id,
    o.obs_datetime,
    o.encounter_id,
         CASE
        WHEN o.value_numeric IS NOT NULL THEN 'Value Numeric'
        WHEN o.value_coded IS NOT NULL THEN 'Value Coded'
        WHEN o.value_text IS NOT NULL THEN 'Value Text'
        ELSE null
    END AS value_type,
       CASE
        WHEN o.value_numeric IS NOT NULL THEN o.value_numeric
        WHEN o.value_coded IS NOT NULL THEN o.value_coded
        WHEN o.value_text IS NOT NULL THEN o.value_text
         WHEN o.value_datetime IS NOT NULL THEN o.value_datetime
        ELSE null
    END AS value,
    e.encounter_datetime,
    e.uuid AS amrs_encounter_uuid,
    et.name AS encounter_type,
    COALESCE(cn_answer.name, '') as value_coded_name,
    COALESCE(drug.name, '') as drug_name
FROM
    amrs.obs o
    INNER JOIN amrs.concept_name cn ON o.concept_id = cn.concept_id
        AND cn.locale = 'en'
        -- AND cn.concept_name_type = 'FULLY_SPECIFIED'
    LEFT JOIN amrs.encounter e ON o.encounter_id = e.encounter_id
    LEFT JOIN amrs.encounter_type et ON e.encounter_type = et.encounter_type_id
    LEFT JOIN amrs.location l ON e.location_id = l.location_id
    LEFT JOIN amrs.concept_name cn_answer ON o.value_coded = cn_answer.concept_id
        AND cn_answer.locale = 'en'
        -- AND cn_answer.concept_name_type = 'FULLY_SPECIFIED'
    LEFT JOIN amrs.drug ON o.value_drug = drug.drug_id
WHERE
     o.person_id = ?
    AND o.voided = 0
ORDER BY
    o.obs_datetime DESC LIMIT 10;