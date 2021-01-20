package classes;
import classes.Page;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
@SuppressWarnings({ "unused" })
public class Table implements Serializable {
private  String Table_Name;
private  Hashtable<String,Object> Table_Row_Values;
private  String Primary_Key;
private static final long serialVersionUID = 1L;
private int number_of_rows;
private transient String MetaData_Path = "data/metadata.csv";
private String Path;
private int curr_page=0;

public Table(String Table_Name, String Primary_Key, Hashtable<String,Object>Table_Row_Values,int number_of_rows) throws Exception
{
this.Table_Name  = Table_Name;
this.Primary_Key = Primary_Key;
this.Table_Row_Values = Table_Row_Values;
this.number_of_rows = number_of_rows;
this.Path = Table_Name + "/";
SaveinMetaData();
createDirectory();
createPage();
SaveTable();
}

/*
 * Create Directory where the Table and Pages are kept
 */
private void createDirectory() {
File TableDirectory = new File(Path);
TableDirectory.mkdir();
}

/*
 * Create a new Page which holds the Records
 */
private Page createPage() throws IOException {
	curr_page++;
	Page page = new Page(number_of_rows, Path+Table_Name+"_"+ curr_page+".class");
	SaveTable();
	return page;
	
}

/*Check if a File Already Exists if it's the first table to be created 
 * by the user a new csv file is created else it append the new table 
 */
@SuppressWarnings("resource")
public void SaveinMetaData() throws FileNotFoundException{
try {
Set<Entry<String,Object>>entries = Table_Row_Values.entrySet();
File DirTemp = new File(MetaData_Path);
boolean Exists = DirTemp.exists();
FileWriter FW;
 	if(Exists) {
 		FW = new FileWriter(MetaData_Path,true);
 	}
 	else {
 		FW  = new FileWriter(MetaData_Path);	 
 	}
 BufferedWriter BW = new BufferedWriter(FW);
 PrintWriter  PW = new PrintWriter(BW);
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
	 PW.append(Table_Name); PW.append(","); PW.append(ent.getKey()); PW.append(","); PW.append((String)ent.getValue()); PW.append(",");
	 if(ent.getKey().equals(Primary_Key))
	 {
	 	PW.append("True");
	 }
	 else{
	 	PW.append("False");    
	 }
	 PW.append(",");PW.append("False");
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
 * Save a new Table in a page and serialize it
 */
public void SaveTable() throws IOException
{		
File f = new File(Path+Table_Name+".class");
	if(!f.exists()) {
		f.createNewFile();
	}
FileOutputStream fos = new FileOutputStream(f);
ObjectOutputStream oos = new ObjectOutputStream(fos);
oos.writeObject(this);
oos.close();
fos.close();
}

/* 
 * check if a table already exists with this name
 */
public boolean checktablename(String name)throws IOException {
	try {
	@SuppressWarnings("resource")
	BufferedReader BR = new BufferedReader(new FileReader(MetaData_Path));
	String n = BR.readLine();
		while (BR.ready()){
			StringTokenizer st = new StringTokenizer(n,",");
			String TableName = st.nextToken();
			if(! TableName.equals("Name")){
				if(TableName.equals(name)) {
					return true;
				}	
			}
		n = BR.readLine();
		}
		}	
   catch (Exception e) 
 {
	   e.printStackTrace(); 
	   }
return false;
	}

/*
 * insert into the table with creating a new page and adding the rows in them till the maximum is reached
 */
public void insert_into_table(String Name, Hashtable<String,Object>Row_Values) throws IOException, ClassNotFoundException {
Tuple tuple = new Tuple(Row_Values);
Page page = addTuples(tuple);
}

public Page addTuples(Tuple tuple) throws FileNotFoundException, IOException, ClassNotFoundException {
	File f = new File(Path + Table_Name + "_" + curr_page+".class");
	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
	Page curPage = (Page) ois.readObject();
		if(curPage.isFull()) {
			curPage = createPage();
		}
	curPage.addtuple(tuple);
	ois.close();
	return curPage;
}

/*
 * Delete Pages from Table 
 * if Page Size is Zero then the whole Page is Deleted 
 */
public boolean Delete_From_table(String TableName, Hashtable<String,Object> htblColNameValue) throws FileNotFoundException, IOException, ClassNotFoundException {
boolean Isfound;
File Requested_Page;
File f = new File(Path);
int count_pages = f.list().length;
	for(int i=1; i <= count_pages-1;i++) {
		Requested_Page = new File(Path + Table_Name + "_" + i+".class");
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Requested_Page));
		Page current_page = (Page) ois.readObject();
		for(int j =0;j < current_page.size();j++) {
//System.out.println(current_page.size());
			if(ValueExists(htblColNameValue,current_page.getTuple().get(j))) {
				System.out.println("yes deleted");
				current_page.removeTuple(current_page.getTuple().get(j));
			}
			if(current_page.isEmpty()) {
				Requested_Page.delete();
			}
		}
		
		ois.close();
	}
this.SaveTable();
return true;
}

/*/
 * Check if the Value of the Tuple exists in the HashTable
 */
public boolean ValueExists(Hashtable<String,Object> htblColNameValue, Tuple t) {
boolean flag = false;
	for(Entry<String,Object> entry: htblColNameValue.entrySet()) {
		String ColumnType = entry.getKey();
		Object ColumnValue =  entry.getValue();
		if(t.getValues().contains(ColumnValue)) {
			flag = true;
		}
		else {
			flag = false;	
		}
}	
return flag;
}


/*
* 
* 
*/
public boolean Update_From_table(String strTableName, String strKey,Hashtable<String,Object> htblColNameValue) throws FileNotFoundException, IOException, ClassNotFoundException {
boolean Isfound;
int key = Integer.parseInt(strKey);
File Requested_Page;
File f = new File(Path);
int count_pages = f.list().length;
	for(int i=1; i <= count_pages-1;i++) {
		Requested_Page = new File(Path + Table_Name + "_" + i+".class");
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Requested_Page));
		Page current_page = (Page) ois.readObject();
		for(int j =0;j < current_page.size();) {
			System.out.println(current_page.size());
			if(KeyExists(key, current_page)) {
				j=key;
				current_page.removeTuple(current_page.getTuple().get(j));
				insert_into_table(strTableName, htblColNameValue);
				j=j+9999999;
			}
//			if(current_page.isEmpty()) {
//				Requested_Page.delete();
//			}
			else{
		System.out.print("tuple not found");
		}	
			
		}
		
		ois.close();
	}
this.SaveTable();
return true;
}
private boolean KeyExists(int strKey,Page current_page) {
	boolean flag = false;
	
	for(int i=0;i<current_page.size();i++) {
		//String ColumnType = entry.getKey();
		//Object ColumnValue =  entry.getValue();
		if(strKey==i) {
			flag = true;
		}
		else {
			flag = false;	
		}
}	
return flag;
}


public void setNumber_of_rows(int number_of_rows) 
{
	this.number_of_rows = number_of_rows;
}

public String getTable_Name() {
	return Table_Name;
}

public void setTable_Name(String table_Name) {
	Table_Name = table_Name;
}

public Hashtable<String, Object> getTable_Columns_Keys() {
	return Table_Row_Values;
}
public String getPath() {
return Path;	
}
}