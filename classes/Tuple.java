package classes;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;
public class Tuple implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private Vector<Object> values;
		

	public Tuple( Hashtable<String,Object>Row_Values)
	{
		values = new Vector<Object>();
		addValue(Row_Values);
	}
	
	public void addValue(Hashtable<String,Object>Row_Values)
	{
	Set<Entry<String,Object>>entries = Row_Values.entrySet();
	for(Entry<String,Object> ent : entries) {
	values.add(ent.getValue());	
	}
	}
	
	public String printtuple()
	{
		String ret = "";
		for(Object o: values)
			ret += o.toString() + ", ";
		return ret;
	}
	
	public Vector<Object>getValues() {
	return values;	
	}
}