public class ExampleSQL
{
	public static final String TEST = "[]  \nselect* from TESt";
	public static final String BASIC = "select* from TESt";
	public static final String SIMPLE = "select*,abs(ddd)from   SOME_TABLE JOIN teST on BLLL =22";
	public static final String COMMENTS = "select\r\n--Test Comment   \r\n   *,*,* /*from TEST*/from BLA";
	public static final String STRINGS = "select * from TEST where 'asd' =\"sdfsdf\" or 1= 'dd'";
	public static final String COMPLEX = "selecT sum( tesT), (case TEST when ff then 'sad' when  df then 'asd' else " +
		"dfdfdfdffdfsd ) , count(te.DFF), (select * from test where aBB = bCC),  Abb.TeSt from  " +
		"xD.tet\t\nwhere\n ORGANISATION.CONTACT_ID=  " +
		"PERSON.CONTACT_ID anD 'hello world'=123.4or'abc'='de'select *join  " +
		"tABlesxx\n\t\ton12='cddd'and'abc'=TEST.TeSt; dRop DatABAse TEStDB " +
		"inseRT inTO SOME_tablE (CustomerName, ContactName, Address, City, PostalCode, Country)\n" +
		"VALUES ('Cardinal','Tom B. Erichsen','Skagen 21','Stavanger','4006' ,  'Norway'),\n\n" +
		"\t\t\t('Cardinal','Tom B. Erichsen','Skagen 21', 'Stavanger',  '4006', 'Norway')";
}
