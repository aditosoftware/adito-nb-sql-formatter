/****** Some tesing script comment ******/
select
  top (
    1000
  ) [ZM_IMPORT_XMLID],
  [DATASET_ID],
  [FRAMENAME],
  [XML_DATA],
  [DATE_EDIT],
  [DATE_NEW],
  [USER_EDIT],
  [USER_NEW],
  [PROCESSED],
  [ERROR_DESCRIPTION]
from [ADITOTEST].[DBO].[ZM_IMPORT_XML]
where
  DATASET_ID like '5901000031/1'
order by
  DATE_NEW desc