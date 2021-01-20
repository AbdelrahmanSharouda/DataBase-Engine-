package classes;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;

public class Tuple implements Serializable{
private  Vector<String> rows_names;
private  Vector<Object> rows_values;
public Tuple(Hashtable<String,Object> tuples){
rows_names =  new Vector<String>();
rows_values = new Vector<Object>();
this.add(tuples);
}
public void add (Hashtable<String,Object>tuples) {
Set<Entry<String,Object>>entries = tuples.entrySet();
for(Entry<String,Object> ent : entries)
{
rows_names.add(ent.getKey());
rows_values.add(ent.getValue());
}
}
public int size() {
return rows_names.size();	
}
public String toString() {
for(int i=0; i < rows_values.size();i++) {
System.out.println(rows_values.get(i).toString());	
}
return "";
}
}
