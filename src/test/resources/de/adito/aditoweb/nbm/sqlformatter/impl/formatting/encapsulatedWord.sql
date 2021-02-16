/****** Some tesing script comment ******/
SELECT TOP (1000) [ZM_IMPORT_XMLID]
      ,[DATASET_ID],[FRAMENAME],[XML_DATA]
      ,[DATE_EDIT]    ,[DATE_NEW]
      ,[USER_EDIT]     ,[USER_NEW]
 ,[PROCESSED]
      ,[ERROR_DESCRIPTION]    FROM [ADITOTEST].[dbo].[ZM_IMPORT_XML]where DATASET_ID like '5901000031/1'
            order by DATE_NEW desc