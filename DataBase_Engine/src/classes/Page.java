package classes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;
import java.lang.*;
import java.nio.charset.StandardCharsets;
@SuppressWarnings("unused")
public class Page implements Serializable {
private transient int max;
private transient int number_of_rows;
private transient int curr_page;
private transient String path;
private static Vector<Tuple> Tuples;
public Page(int max, String path) {
this.max = max;
this.path = path;
Tuples = new Vector<Tuple>();
this.Save();
}
public boolean isFull() {
	if(Tuples.size() == max) {
	return true;	
	}
	return false;
}
public boolean isEmpty() {
	if(Tuples.size() == 0) {
	return true;	
	}
	return false;
}
/*
 * Add Rows to the Pages
 */
public void addToPages(Tuple T) {
	if(!isFull()) 
	{
	Tuples.add(T);
	number_of_rows++;	
	}
}
/*
 * Save the page by Serializing it 
 */
public void Save() {
	try {
	FileOutputStream FOS = new FileOutputStream(path+".class");
	ObjectOutputStream oos = new ObjectOutputStream(FOS);
	oos.writeObject(this);
	oos.close();
	FOS.close();
	}
	catch(Exception e){	
	}
}
public int number_of_rows() {
return number_of_rows;	
}
public Vector<Tuple> getTuples(){
return Tuples;	
}
public void deserialize(String Path) throws IOException {
FileInputStream fis = new FileInputStream(Path+".class");
ObjectInputStream ois = new ObjectInputStream(fis);
try {
Page p = (Page)ois.readObject();
for(int i=0; i < p.Tuples.size();i++) {
System.out.println(p.Tuples.get(i));	
}
} catch (ClassNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
fis.close();
ois.close();
}
}
 
