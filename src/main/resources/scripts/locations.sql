  --   select location_id,name,parent_location from amrs.location l where   l.uuid in ("08feb14c-1352-11df-a1f1-0026b9348838",
-- "8cad59c8-7f88-4964-aa9e-908f417f70b2")

 -- select location_id,name,description,parent_location from amrs.location where location_id in(select distinct(parent_location)
 -- from amrs.location l where l.parent_location is not null)
 -- order by name asc
 --  l.uuid in ("08feb14c-1352-11df-a1f1-0026b9348838",
-- "8cad59c8-7f88-4964-aa9e-908f417f70b2")

select location_id,name,description,parent_location
                  from amrs.location where location_id in(select distinct(parent_location)
                  from amrs.location l where l.parent_location is not null)
                  order by name asc