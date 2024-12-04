select
distinct(cause_of_death),
c.concept_id,
cn.name,
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
end as kmr_concept_uuid
from amrs.person p
inner join amrs.concept c on p.cause_of_death =c.concept_id
inner join amrs.concept_name cn on c.concept_id = cn.concept_id and cn.locale_preferred=1