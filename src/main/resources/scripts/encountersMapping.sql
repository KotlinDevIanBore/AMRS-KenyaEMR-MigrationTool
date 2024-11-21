select patient_id, 
encounter_id,
et.name,
encounter_datetime,
encounter_type,
form_id,
 visit_id,
 e.voided,
 case 
 when encounter_type =110 then 6
 when encounter_type = 270 then 8
 when encounter_type = 23 then 303
 when encounter_type = 22 then 303
 when encounter_type = 280 then 302
 when encounter_type = 1 then 7
 when encounter_type = 14 then 21
 when encounter_type = 14 then 8
 when encounter_type = 16 then 6
 when encounter_type = 16 then 252
 when encounter_type = 2 then 8
 when encounter_type = 265 then 14
 when encounter_type = 264 then 15
  when encounter_type = 32 then 14
 when encounter_type = 33 then 15
 when encounter_type = 242 then 21
 when encounter_type = 294 then 73
 when encounter_type = 13 then 300
 when encounter_type = 138 then 8
 when encounter_type = 252 then 243
 when encounter_type = 209 then 243
 when encounter_type = 208 then 243
 when encounter_type = 234 then 10
 when encounter_type = 233 then 9
 when encounter_type = 31 then 2
 when encounter_type = 127 then 8
 when encounter_type = 144 then 8
 when encounter_type = 153 then 8
 when encounter_type = 181 then 21
 when encounter_type = 182 then 21
 when encounter_type = 248 then 24
 when encounter_type = 249 then 29
 when encounter_type = 203 then 24
 when encounter_type = 186 then 21
 when encounter_type = 19 then 21
 when encounter_type = 20 then 21
 when encounter_type = 26 then 21
 when encounter_type = 17 then 21
 when encounter_type = 18 then 21
 when encounter_type = 279 then 303
 when encounter_type = 157 then 2
 when encounter_type = 250 then 28
 when encounter_type = 243 then 22
 when encounter_type = 43 then 8
 when encounter_type = 132 then 22
 when encounter_type = 115 then 11
  end kenyaem_encounter_id
 ,
  case 
  when encounter_type =110 then 'd1059fb9-a079-4feb-a749-eedd709ae542'
  when encounter_type = 270 then 'a0034eee-1940-4e35-847f-97537a35d05e'
  when encounter_type = 23 then '54df6991-13de-4efc-a1a9-2d5ac1b72ff8'
  when encounter_type = 22 then '54df6991-13de-4efc-a1a9-2d5ac1b72ff8'
  when encounter_type = 280 then 'ec2a91e5-444a-4ca0-87f1-f71ddfaf57eb'
  when encounter_type = 1 then 'de78a6be-bfc5-4634-adc3-5f1a280455cc'
  when encounter_type = 14 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'
  when encounter_type = 14 then 'a0034eee-1940-4e35-847f-97537a35d05e'
  when encounter_type = 16 then 'd1059fb9-a079-4feb-a749-eedd709ae542'
  when encounter_type = 16 then '160fcc03-4ff5-413f-b582-7e944a770bed'
  when encounter_type = 2 then 'a0034eee-1940-4e35-847f-97537a35d05e'
  when encounter_type = 265 then '3ee036d8-7c13-4393-b5d6-036f2fe45126'
  when encounter_type = 264 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'
    when encounter_type = 32 then '3ee036d8-7c13-4393-b5d6-036f2fe45126'
  when encounter_type = 33 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'
  when encounter_type = 242 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'
  when encounter_type - 294 then '5021b1a1-e7f6-44b4-ba02-da2f2bcf8718'
  when encounter_type = 13 then 'e360f35f-e496-4f01-843b-e2894e278b5b'
  when encounter_type = 138 then 'a0034eee-1940-4e35-847f-97537a35d05e'
  when encounter_type = 252 then '86709cfc-1490-11ec-82a8-0242ac130003'
  when encounter_type = 209 then '86709cfc-1490-11ec-82a8-0242ac130003'
  when encounter_type = 208 then '86709cfc-1490-11ec-82a8-0242ac130003'
  when encounter_type = 234 then 'bcc6da85-72f2-4291-b206-789b8186a021'
  when encounter_type = 233 then '415f5136-ca4a-49a8-8db3-f994187c3af6'
  when encouter_type = 31 then '2bdada65-4c72-4a48-8730-859890e25cee'
  when encounter_type = 127 then 'a0034eee-1940-4e35-847f-97537a35d05e'
  when encounter_type = 144 then 'a0034eee-1940-4e35-847f-97537a35d05e'
  when encounter_type = 153 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'
  when encounter_type = 181 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'
  when encounter_type = 182 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'
  when encounter_type = 248 then '7df67b83-1b84-4fe2-b1b7-794b4e9bfcc3'
  when encounter_type = 249 then '7dffc392-13e7-11e9-ab14-d663bd873d93'
  when encounter_type = 203 then '7df67b83-1b84-4fe2-b1b7-794b4e9bfcc3'
  when encounter_type = 186 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'
  when encounter_type = 19 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'
  when encounter_type = 20 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'
  when encounter_type = 26 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'
  when encounter_type = 17 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'
  when encounter_type = 18 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'
  when encounter_type = 279 then '54df6991-13de-4efc-a1a9-2d5ac1b72ff8'
  when encounter_type = 157 then '2bdada65-4c72-4a48-8730-859890e25cee'
  when encounter_type = 250 then '9bc15e94-2794-11e8-b467-0ed5f89f718b'
  when encounter_type = 243 then '975ae894-7660-4224-b777-468c2e710a2a'
  when encounter_type = 43 then 'a0034eee-1940-4e35-847f-97537a35d05e'
  when encounter_type = 132 then '975ae894-7660-4224-b777-468c2e710a2a'
  when encounter_type = 115 then '01894f88-dc73-42d4-97a3-0929118403fb'
  end kenyaem_encounter_uuid
 from  amrs.encounter_type et 
 where e.voided =0
 order by e.encounter_id desc