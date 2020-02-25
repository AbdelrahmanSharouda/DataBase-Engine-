package classes;
import java.io.FileNotFoundException;
import java.util.*;
public class DBApp {
public void init(){
	
} 
// this does whatever initialization you would like // or leave it empty if there is no code you want to
// execute at application startup
public static void createTable(String strTableName,String strClusteringKeyColumn,Hashtable<String,Object> htblColNameType) throws FileNotFoundException {
//Table T = new Table(strTableName, strClusteringKeyColumn, htblColNameType);
}
//throws DBAppException
public void createBTreeIndex(String strTableName,String strColName) 
{
}
//throws DBAppException
public void createRTreeIndex(String strTableName,String strColName) 
{

}
//throws DBAppException
public void insertIntoTable(String strTableName, Hashtable<String,Object> htblColNameValue) 
{
	
}
//                                    throws DBAppException
public void updateTable(String strTableName, String strKey,Hashtable<String,Object> htblColNameValue) 
{
	} 
//throws DBAppException
public void deleteFromTable(String strTableName, Hashtable<String,Object> htblColNameValue) 
{
	
}
//throws DBAppException
//public Iterator selectFromTable(SQLTerm[] arrSQLTerms, String[] strarrOperators) {
//	
//}
//throws DBAppException
public static void main(String[]args) throws FileNotFoundException 
{
	DBApp dbApp = new DBApp();
String TA = "TA";	
Hashtable htblColNameType_1 = new Hashtable();
htblColNameType_1.put("id", "java.lang.Integer");
htblColNameType_1.put("name", "java.lang.String");
htblColNameType_1.put("Job-Type", "java.lang.double");



dbApp.createTable(TA,"SSN",htblColNameType_1);
//dbApp.createTable(strTableName_2,"id",  htblColNameType_1);

}
}
