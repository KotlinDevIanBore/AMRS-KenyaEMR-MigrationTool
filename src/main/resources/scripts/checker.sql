select * from openmrs.encounter e
inner join openmrs.person p  on p.person_id=e.patient_id
where p.uuid='8c24402c-7fb2-417f-b1e8-599cad616fa1';
 update openmrs.encounter set form_id =65  where encounter_id =1475;

select * from openmrs.form order by name;


select * from amrs_migration.identifier where identifier='1522908922';