package classes;
import java.io.FileNotFoundException;
import java.util.*;
import classes.Table;
@SuppressWarnings("unused")
public class DBApp {
static Table Current_Table;
public static void init(String strTableName , String ClusteringKey, Hashtable<String,Object> ColumnNameType) throws Exception{
Current_Table = new Table(strTableName, ClusteringKey, ColumnNameType);	
} 
// this does whatever initialization you would like // or leave it empty if there is no code you want to
// execute at application startup
public static void createTable(String strTableName,String strClusteringKeyColumn,Hashtable<String,Object> htblColNameType) throws Exception {
init(strTableName, strClusteringKeyColumn,htblColNameType);
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
public void insertIntoTable(String strTableName, Hashtable<String,Object> htblColNameValue)throws Exception
{
Current_Table.insert_into_table(strTableName,htblColNameValue);
}
//throws DBAppException
public void updateTable(String strTableName, String strKey,Hashtable<String,Object> htblColNameValue) 
{
	} 
//throws DBAppException
public void deleteFromTable(String strTableName, Hashtable<String,Object> htblColNameValue) 
{
	
}
//throws DBAppException
//throws DBAppException

@SuppressWarnings({ "unchecked", "static-access" })
public static void main(String[]args) throws Exception 
{
	DBApp dbApp = new DBApp( );
	String strTableName = "student";
	Hashtable htblColNameType = new Hashtable( ); 
	Hashtable htblColNameValue = new Hashtable( );
	htblColNameType.put("id", "java.lang.Integer");
	htblColNameType.put("name", "java.lang.String");
	htblColNameType.put("gpa", "java.lang.double"); 
	dbApp.createTable( strTableName, "id", htblColNameType );	
	htblColNameValue.put("id",  2343432 ); 
	htblColNameValue.put("name", new String("Nour Ahmed" ) );
	htblColNameValue.put("gpa",  0.15 ); 
	
	htblColNameValue.put("id",  2343432 ); 
	htblColNameValue.put("name", new String("Nour Ahmed" ) );
	htblColNameValue.put("gpa",  0.15 ); 
	dbApp.insertIntoTable( strTableName , htblColNameValue );
	
}
@SuppressWarnings("serial")
class DBAppException extends Exception {
public DBAppException(String Message) {
super("DBApp Exception is thrown");	
}
}
}

