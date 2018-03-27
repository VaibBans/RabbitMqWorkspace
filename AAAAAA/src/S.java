import java.util.HashMap;
import java.util.Map;

public class S {
	private String name;
	private String address ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
//	S a = new S();

	public static void main (String[] args){
		Map<Integer, S> map = new HashMap<>();
		S s = new S();
		s.setAddress("add");
		s.setName("a");
		map.put(1, s);
		System.out.println(s.toString());
		System.out.println(map.toString());
	}
	@Override
	public String toString() {
		return "S [name=" + name + ", address=" + address + "]";
	}
	
}
