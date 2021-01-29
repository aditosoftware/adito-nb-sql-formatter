select
  *
from CUSTOMERS
where
  ID in (
    select
      ID
    from (
      select
        *
      from CUSTOMERS
      where
        ID in (
          select
            ID
          from CUSTOMERS
          where
            SALARY > 4500
        )
    )
    where
      SALARY > 4500
  );

select
  *
from CUSTOMERS
where
  ID in (
    select
      ID
    from CUSTOMERS
    where
      SALARY > 4500
  );