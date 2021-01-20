package classes;
import java.io.*;
import java.util.Vector;
public class Page implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int maxSize, nElements;
	private Vector<Tuple>Tuples;
	private String path;

	public Page(int maxSize, String path) throws IOException
	{
		this.path = path;
		this.maxSize = maxSize;
		this.Tuples = new Vector<Tuple>();
		this.save();
	}
	
	public boolean isFull()
	{
		return nElements == maxSize;
	}
	
	public boolean addtuple(Tuple tuple) throws IOException
	{
		if(isFull()) {
			return false;
		}
		Tuples.add(tuple);
		nElements++;
		save();
		return true;
	}
		
	
	
	public void save() throws IOException
	{
		File f = new File(path);
		if(!f.exists())
			f.delete();
		f.createNewFile();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
		oos.writeObject(this);
		oos.close();
	}
	public int size()
	{
		return nElements;
	}
	
	public boolean isEmpty() {
		return nElements == 0;
	}
	
	public Vector<Tuple> getTuple() {
		return Tuples;
	}
	
	public boolean removeTuple(Tuple t) throws IOException {
		if(Tuples.contains(t)) {
		Tuples.remove(t);
		nElements--;
		save();
		return true;
		}
		else {
		return false;	
		}
	}
}