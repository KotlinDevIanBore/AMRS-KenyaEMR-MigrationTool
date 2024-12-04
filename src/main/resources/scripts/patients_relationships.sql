
 SELECT r.person_a,r.relationship,rt.a_is_to_b,rt.b_is_to_a,rt.uuid,r.person_b FROM amrs.relationship r 
 inner join amrs.relationship_type rt on rt.relationship_type_id=r.relationship
 where voided =0 and person_a in (41447) and person_b in(42413)

-- select * from  openmrs.relationship_type;