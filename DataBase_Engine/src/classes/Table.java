package classes;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.lang.Integer;
import java.lang.Boolean;
import java.lang.Double;
import java.lang.String;
import java.io.Serializable;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
public class Table implements Serializable {
private  String Table_Name;
private  Hashtable<String,Object> Table_Row_Values;
private  Hashtable<String,Object> Table_Columns_Values;
private  String Primary_Key;
private  int number_of_columns;
private transient String MetaData_Path = "/Users/mohanedmashaly/eclipse-workspace/DataBase_Engine/src/data/metadata.csv";
private transient BufferedReader BR;
private transient PrintWriter    PW;
private transient BufferedWriter BW;
private transient FileWriter     FW;
private transient File 		   FF;
private transient String Path;
private int number_of_Primary_Keys;
private Vector<Object>Foreign_Keys;
private transient Set<Entry<String,Object>>entries;
private transient boolean Is_Exists;
private transient int curr_page;
public String getTable_Name() {
	return Table_Name;
}
public void setTable_Name(String table_Name) {
	Table_Name = table_Name;
}
public Hashtable<String, Object> getTable_Columns_Keys() {
	return Table_Row_Values;
}
public Table(String Table_Name, String Primary_Key, Hashtable<String,Object>Table_Row_Values) throws Exception
{
this.Table_Name  = Table_Name;
this.Primary_Key = Primary_Key;
this.Table_Row_Values = Table_Row_Values;
SaveinMetaData();
SaveTable();
}
/*Check if a File Already Exists if it's the first table to be created 
 * by the user a new csv file is created else it append the new table 
 */
public void SaveinMetaData() throws FileNotFoundException{
try {
entries = Table_Row_Values.entrySet();
File DirTemp = new File(MetaData_Path);
boolean Exists = DirTemp.exists();
 if(Exists) {
 FW = new FileWriter(MetaData_Path,true);
 }
 else {
 FW = new FileWriter(MetaData_Path);	 
 }
 BW = new BufferedWriter(FW);
 PW = new PrintWriter(BW);
 if(!checktablename(Table_Name)) {
 if(Exists) 
 {
 PW.println();
 }
 PW.append("Table Name");
 PW.append(","); 
 PW.append("Column Name");
 PW.append(",");
 PW.append("Column Type");
 PW.append(",");
 PW.append("ClusteringKey");
 PW.append(",");
 PW.append("Indexed");
 
 for(Entry<String,Object> ent : entries)
 {
	 PW.println();
	 PW.append(Table_Name);
	 PW.append(",");
	 PW.append(ent.getKey());
	 PW.append(",");
	 PW.append((String)ent.getValue());
	 PW.append(",");
	 	if(ent.getKey().equals(Primary_Key))
	 	{
	 		PW.append("True");
	 	}
	 	else{
	 		PW.append("False");    
	 	}
	 PW.append(",");
	 PW.append("False");
	 	}
PW.flush();
PW.close();
}
}
catch (Exception e) {
	e.printStackTrace();
}
}
/* 
 * check if a table already exists with this name
 */
public boolean checktablename(String name)throws IOException {
try {
	BR = new BufferedReader(new FileReader("/Users/mohanedmashaly/eclipse-workspace/DataBase_Engine/src/data/metadata.csv"));
	String n = BR.readLine();
	while (BR.ready()){
	StringTokenizer st = new StringTokenizer(n,",");
	String TableName = st.nextToken();
	if(! TableName.equals("Table Name")) {
	if(TableName.equals(name)) {
	return true;
	}	
	}
	n = BR.readLine();
	}
	}	
	catch (Exception e) {
		e.printStackTrace();
	}
	return false;
	
}
/*
 * Save a Table in a new File and Serialize it 
 */
public void SaveTable() throws IOException
{	
Path = Table_Name;	
File f = new File(Path+".class");
if(!f.exists()) {
FileOutputStream fos = new FileOutputStream(f);
ObjectOutputStream oos = new ObjectOutputStream(fos); 
oos.writeObject(this);
oos.close();
fos.close();
}
}
/*
 * insert into the table with creating a new page and adding the rows in them till the maximum is reached
 */
public void insert_into_table(String Name, Hashtable<String,Object>Row_Values) throws IOException {
Page new_page = new Page(200,Table_Name+curr_page);
Tuple t = new Tuple(Row_Values);
new_page.addToPages(t);	
System.out.println(new_page.number_of_rows());
}
}
