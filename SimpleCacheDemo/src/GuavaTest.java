
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class GuavaTest {

	String name = "a";
	String password = "a";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "GuavaTest [name=" + name + ", password=" + password + ", cache=" + cache + "]";
	}

	private static GuavaTest gt = new GuavaTest();

	public static GuavaTest getInstance() {
		return gt;
	}

	private LoadingCache<String, GuavaTest> cache;

	private GuavaTest() {

		cache = CacheBuilder.newBuilder().refreshAfterWrite(2, TimeUnit.SECONDS)
				.build(new CacheLoader<String, GuavaTest>() {

					@Override
					public GuavaTest load(String arg0) throws Exception {
						// TODO Auto-generated method stub
						return addcache(arg0);
					}
				});
	}
	
		private GuavaTest addcache(String arg0) {
		System.out.println("Adding Cache");
		GuavaTest g=new GuavaTest();
		return  g;
	}

	public LoadingCache<String, GuavaTest>  addEntry(){
		//cache.put("1",new GuavaTest());
		cache.put("2",new GuavaTest());
		return cache;
	}
	public GuavaTest getEntry(String args) throws ExecutionException {
		System.out.println("Size of cache " + cache.size());
		return cache.get(args);
	}

	public static void main(String[] args) {
		GuavaTest gt = GuavaTest.getInstance();
		GuavaTest gt1 = GuavaTest.getInstance();
		
		gt1.setName("b");
		gt1.setPassword("b");
		try {
			System.out.println("res one="+gt.addcache("1").toString());
			System.out.println("res two="+gt1.addcache("2").toString());
			
			System.out.println(gt.getEntry("java"));
			System.out.println(gt.getEntry("Hello World"));
			System.out.println(gt.getEntry("java"));
			/*Thread.sleep(2100);
			System.out.println(gt.getEntry("java"));
			System.out.println(gt.getEntry("java"));

			System.out.println("-----------------");
			System.out.println(gt.getEntry("java"));
			System.out.println(gt.getEntry("java"));

			System.out.println("-----------------");
			System.out.println(gt.getEntry("java"));
			System.out.println(gt.getEntry("java is object oriented programming language"));

			System.out.println("-----------------");
			System.out.println(gt.getEntry("java is platform independent"));
			System.out.println(gt.getEntry("java has classes"));

			System.out.println("-----------------");
			System.out.println(gt.getEntry("java is modular"));
			System.out.println(gt.getEntry("java"));

			System.out.println("-----------------");
			System.out.println(gt.getEntry("java"));
			System.out.println(gt.getEntry("java"));

			System.out.println("-----------------");
			System.out.println(gt.getEntry("java"));
			System.out.println(gt.getEntry("java"));*/

		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
