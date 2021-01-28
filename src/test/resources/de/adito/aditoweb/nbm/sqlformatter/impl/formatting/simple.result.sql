select
  *,
  count(
    *
  )
from ACTIVITY
where
  ACTIVITYID = '000-123-456'
  and "Max Maier" = name
join PERSON
  on ACTIVITY.ID = PERSON.PERSID