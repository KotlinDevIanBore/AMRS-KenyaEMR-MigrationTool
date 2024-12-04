-- SELECT * FROM amrs.form where name like "%%" and published = 1; -- 157, 116
SELECT
    o.concept_id,
    o.value_datetime,
    -- o.value_text,
    l.name AS location_name,
    CASE
        WHEN o.value_text IS NOT NULL THEN o.value_text
        WHEN o.value_datetime IS NOT NULL THEN 'No reason indicated'
        ELSE 'No Value'
    END AS reason_for_discontinuation
FROM
    amrs.obs o
INNER JOIN
    amrs.encounter e ON o.encounter_id = e.encounter_id
INNER JOIN
    location l ON o.location_id = l.location_id
WHERE
    e.encounter_type IN (157, 116)
	AND l.location_id in (339)
    And e.voided = 0
LIMIT 100;


 -- select * from amrs.encounter_type where encounter_type_id in (157, 116);