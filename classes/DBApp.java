package classes;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.Map.Entry;

import classes.Table;
import config.Config_Reader;
@SuppressWarnings("unused")
public class DBApp {
private static Vector<Table> tables;
private static String Path = "data/tables.ser";
private static int Max_Row;
private static String Column_Type;

public static void init(String strTableName , String ClusteringKey, Hashtable<String,Object> ColumnNameType) throws Exception{
	tables = de_serialize_table(tables);	
	Table t;
	if(tables == null) {
	t = new Table(strTableName, ClusteringKey, ColumnNameType,Max_Row);
	tables =new Vector<Table>();
	}
	else {
	t = new Table(strTableName, ClusteringKey, ColumnNameType,Max_Row);
	}
	tables.add(t);
	serialize_table();
}

public boolean CheckTableType(Hashtable<String,Object> ColNameType) {
Set<Entry<String,Object>>entries = ColNameType.entrySet();
boolean notAvailableDataType ;
for(Entry<String,Object> ent : entries) {
notAvailableDataType = false;

if(ent.getValue().equals("java.lang.String")) {
notAvailableDataType = true;	
}

if(ent.getValue().equals("java.lang.Integer")) {
notAvailableDataType = true;	
}

if(ent.getValue().equals("java.lang.Double")) {
notAvailableDataType = true;	
}

if(ent.getValue().equals("java.lang.Boolean")) {
notAvailableDataType = true;	
}

if(ent.getValue().equals("java.util.Date")) {
notAvailableDataType = true;	
}

if(ent.getValue().equals("java.awt.Polygon")){
notAvailableDataType = true;	
}

if(!notAvailableDataType) {
return false;
}
}
return true;
}

/*
 * Check if a tables exists 
 */
public boolean TableExists(String Table_Name) throws ClassNotFoundException, IOException {
tables = de_serialize_table(tables);	
Table t;
if(tables == null) {
return false;	
}

else {
for(int i=0; i < tables.size();i++) {
if(tables.get(i).getTable_Name().equals(Table_Name)) {
return true;	
}	
}	
}
return false;
}

public void createTable(String strTableName,String strClusteringKeyColumn,Hashtable<String,Object> htblColNameType) throws Exception {
//if(!CheckTableType(htblColNameType)) {
//throw new DBAppException("Data Type is not Found");	
//}
if(! TableExists(strTableName)){
init(strTableName, strClusteringKeyColumn,htblColNameType);
}
System.out.println("created");
}

public void createBTreeIndex(String strTableName,String strColName) 
{
}

public void createRTreeIndex(String strTableName,String strColName) 
{

}

/*
 * Check the Type of the Inputs Passed by Reading the CSV file and check if the row value is the same data-type with column inserted in metadata.csv
 */
public boolean CheckType(String TableName, Hashtable<String,Object>Row_Values) {
boolean flag = false;
try {
File f = new File("data/metadata.csv");
InputStream fr = new FileInputStream(f);
@SuppressWarnings("resource")
BufferedReader BR = new BufferedReader(new InputStreamReader(fr));
String n;

while((n = BR.readLine()) != null) {
StringTokenizer st = new StringTokenizer(n,",");
String Table_Name = st.nextToken();
String Column_Name = st.nextToken();
String Column_Type = st.nextToken();

if(TableName.equals(Table_Name)) {	

Object Column_DataType = Row_Values.get(Column_Name);

if(!CheckDataType(Column_DataType,Column_Type)) {
this.Column_Type = Column_Type;
return false;
}
}
}
}
catch(Exception e) {
e.printStackTrace();	
}
return true;
}

/*
 * Helper Method for CheckType 
 */
public static boolean CheckDataType(Object ColumnName, String ColumnType) {

	if(ColumnType.equals("java.lang.String")) {
		if((Object)ColumnName instanceof String) {
		return true;	
		}
		return false;	
	}
	
	if(ColumnType.equals("java.lang.Integer")) {

		if(ColumnName instanceof Integer){
		return true;	
		}
		return false;
	}
	
	if(ColumnType.equals("java.lang.Double")) {

	if(ColumnName instanceof Double){
		return true;	
		}
	return false;
	}
	
	if(ColumnType.equals("java.lang.Boolean")) {
		if(ColumnName instanceof Boolean){
			return true;	
			}
		return false;
		}

	if(ColumnType.equals("java.util.Date")) {
		if(ColumnName instanceof Date) {
		return true;	
		}
	return false;
	}
	
	if(ColumnType.equals("java.awt.Polygon")) {
		if(ColumnName instanceof Polygon) {
		return true;	
		}
		return false;
	}
	
return false;
}



public void insertIntoTable(String strTableName, Hashtable<String,Object> htblColNameValue)throws DBAppException, ClassNotFoundException, IOException
{
if(!TableExists(strTableName)) {
throw new DBAppException("Table Not Found Exception");
}
//if(!CheckType(strTableName,htblColNameValue)) {
//throw new DBAppException("In-Compatible Type Excpetion should be " + Column_Type);
//}
Table Current_Table;
tables = de_serialize_table(tables);	
for(int i=0; i < tables.size();i++) {
if(tables.get(i).getTable_Name().equals(strTableName)) {
tables.get(i).insert_into_table(strTableName,htblColNameValue);	
break;
}	
}
serialize_table();
System.out.println("inserted");
}

public void updateTable(String strTableName, String strKey,Hashtable<String,Object> htblColNameValue)throws DBAppException, ClassNotFoundException, IOException 
{
	if(getTable(strTableName) == null){
		throw new DBAppException("Table Not Found Exception");
		}
		Table table = getTable(strTableName);
		table.Update_From_table(strTableName, strKey, htblColNameValue);
		System.out.println("updated");

}


public void deleteFromTable(String strTableName, Hashtable<String,Object> htblColNameValue)throws DBAppException, ClassNotFoundException, IOException 
{
	
if(getTable(strTableName) == null){
throw new DBAppException("Table Not Found Exception");
}
Table table = getTable(strTableName);
table.Delete_From_table(strTableName, htblColNameValue);
System.out.println("deleted");
}


public Table getTable(String strTableName) throws ClassNotFoundException, IOException {
	tables = de_serialize_table(tables);	
	Table t;
	if(!tables.isEmpty()) {
	for(int i=0; i < tables.size();i++) {
	if(tables.get(i).getTable_Name().equals(strTableName)) {
	return tables.get(i);
	}
	}
	}
	serialize_table();	
return null;
}

@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
public static void main(String[]args) throws Exception 
{
	DBApp dbApp = new DBApp();
	Config_Reader cf = new Config_Reader(); 
	Max_Row = cf.getPropValues();
	String strTableName = "boom";
	
	Hashtable htblColNameType = new Hashtable(); 		
	htblColNameType.put("id", "java.lang.Integer");
	htblColNameType.put("name", "java.lang.String");
	htblColNameType.put("gpa", "java.lang.double");
	dbApp.createTable( strTableName, "id", htblColNameType );
	
	Hashtable htblColNameValue = new Hashtable(); 
	htblColNameValue.put("id", 123);
	htblColNameValue.put("name", "Ahmed Noor"  );
	htblColNameValue.put("gpa", 23.4);
	dbApp.insertIntoTable( strTableName , htblColNameValue );
	
	Hashtable htblColNameValue2 = new Hashtable( );
	htblColNameValue2.put("id", 55);
	htblColNameValue2.put("name", "Ahmed Noor2"  );
	htblColNameValue2.put("gpa", 0.95  );
	dbApp.insertIntoTable( strTableName , htblColNameValue2 );
	
	Hashtable hh = new Hashtable<String, Object>();
	hh.put("id",123);
	hh.put("name", "Ahmed Noor");
	hh.put("gpa", 23.4);
	dbApp.updateTable(strTableName, "1", hh);
	
	Hashtable htblColDelete = new Hashtable();	
	htblColDelete.put("id",123);
	htblColDelete.put("name", "Ahmed Noor");
	htblColDelete.put("gpa", 23.4);
	dbApp.deleteFromTable(strTableName,htblColDelete);

	
}
@SuppressWarnings("serial")
class DBAppException extends Exception {
public DBAppException(String Message) {
super(Message);	
}
}
public static void serialize_table() throws IOException {
FileOutputStream fos = new FileOutputStream(Path);	
@SuppressWarnings("resource")
ObjectOutputStream oos = new ObjectOutputStream(fos);
oos.writeObject(tables);
oos.close();
fos.close();
}
public static Vector<Table> de_serialize_table(Vector<Table>tables) throws IOException, ClassNotFoundException {
	File f = new File(Path);	

	if(!f.exists()) {
	f.createNewFile();
	}
	if(f.length() == 0) {
		return null;
	}
FileInputStream fis = new FileInputStream(Path);
@SuppressWarnings("resource")
ObjectInputStream ois = new ObjectInputStream(fis);
Vector<Table>tbs = (Vector<Table>)ois.readObject();
ois.close();
fis.close();
return tbs;	
}

}
