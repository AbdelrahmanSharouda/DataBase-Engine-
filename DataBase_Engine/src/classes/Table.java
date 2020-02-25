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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class Table implements Serializable {
private String Table_Name;
private Hashtable<String,Object> Table_Row_Values;
private Hashtable<String,Object> Table_Columns_Values;
private String Primary_Key;
private int number_of_columns;
private String Path = "/Users/mohanedmashaly/eclipse-workspace/DataBase_Engine/src/data/metadata.csv.txt";
private BufferedReader BR;
private PrintWriter    PW;
private BufferedWriter BW;
private FileWriter     FW;
private File 		   FF;
private Vector<Object>Foreign_Keys;
private Set<Entry<String,Object>>entries;
public String getTable_Name() {
	return Table_Name;
}
public void setTable_Name(String table_Name) {
	Table_Name = table_Name;
}
public Hashtable<String, Object> getTable_Columns_Keys() {
	return Table_Row_Values;
}
public Table(String Table_Name, String Primary_Key, Hashtable<String,Object>Table_Row_Values) throws FileNotFoundException
{
this.Table_Name  = Table_Name;
this.Primary_Key = Primary_Key;
this.Table_Row_Values = Table_Row_Values;
SaveinMetaData();
insert_into_table();
}
//Check if a File Already Exists if it's the first table to be created 
//by the user a new csv file is created else it append the new table 
public void SaveinMetaData() throws FileNotFoundException{
try {
entries = Table_Row_Values.entrySet();
File DirTemp = new File(Path);
boolean Exists = DirTemp.exists();
//if(!DirTemp.exists()) 
//{
 if(Exists) {
 FW = new FileWriter(Path,true);
 }
 else {
 FW = new FileWriter(Path);	 
 }
 BW = new BufferedWriter(FW);
 PW = new PrintWriter(BW);
 if(Exists) {
 PW.println();
 PW.println();
 }
 PW.append("Table Name");
 PW.append(","); 
 PW.append("Column Name");
 PW.append(",");
 PW.append("Column Type");
 PW.append(",");
 PW.append("Key");
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
catch (Exception e) {
	e.printStackTrace();
}
}

public void insert_into_table() {
	
}
}
