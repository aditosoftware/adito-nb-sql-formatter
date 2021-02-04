SELECT *
   FROM CUSTOMERS
   WHERE ID IN (SELECT ID
         FROM (SELECT *
                                                 FROM CUSTOMERS
                                                 WHERE ID IN (SELECT ID
                                                       FROM CUSTOMERS
                                                       WHERE SALARY > 4500))
         WHERE SALARY > 4500) ;SELECT *
                                  FROM CUSTOMERS
                                  WHERE ID IN (SELECT ID
                                        FROM CUSTOMERS
                                        WHERE SALARY > 4500) ;