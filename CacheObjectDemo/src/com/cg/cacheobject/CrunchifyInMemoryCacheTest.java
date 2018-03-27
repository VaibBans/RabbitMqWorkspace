package com.cg.cacheobject;

public class CrunchifyInMemoryCacheTest {

	public static void main(String[] args) throws InterruptedException{
		CrunchifyInMemoryCacheTest crunchifyCache = new CrunchifyInMemoryCacheTest();
		/*System.out.println("\n\n===============Test1: crunchifyTestAddRemoveObjects =============");
        crunchifyCache.crunchifyTestAddRemoveObjects();
		System.out.println("\n\n===============Test2: crunchifyTestExpiredCacheObjects ============");
        crunchifyCache.crunchifyTestExpiredCacheObjects();
		System.out.println("\n\n===============Test3: crunchifyTestObjectsCleanUpTime ============");
        crunchifyCache.crunchifyTestObjectsCleanupTime();*/
        System.out.println("Accessing the objects already present");

        crunchifyCache.crunchifyAccessObjects();
        
	}
	
	private void crunchifyTestAddRemoveObjects(){
		CrunchifyInMemoryCache<String , String> cache = new CrunchifyInMemoryCache<String,String>(200,500, 6);
		cache.put("eBay","eBay");
		cache.put("Paypal","Paypal");
		cache.put("Google","Google");
		cache.put("Microsoft","Microsoft");
		cache.put("IBM","IBM");
		cache.put("Facebook","Facebook");
	
		System.out.println("6 Cache Object Added.. cache.size(): "+ cache.size());
		cache.remove("eBay");
		System.out.println("One Cache Object removed.. cache.size(): "+ cache.size());
		
		System.out.println(cache.toString());
		
		cache.put("Twitter", "Twitter");
		cache.put("SAP", "SAP");
		System.out.println("Two Objects Added but reached maxItems.. cache.size(): "+ cache.size());
		
		System.out.println(cache.toString());

	}
	
	 private void crunchifyTestExpiredCacheObjects() throws InterruptedException {

	        CrunchifyInMemoryCache<String, String> cache = new CrunchifyInMemoryCache<String, String>(1, 1, 10);
	        cache.put("eBay", "eBay");
	        cache.put("Paypal", "Paypal");
	        Thread.sleep(3000);	 
	        System.out.println("Two objects are added but reached timeToLive. cache.size(): " + cache.size());	 
	    }
	 
	private void crunchifyTestObjectsCleanupTime() throws InterruptedException{
		int size = 500000;
		
		CrunchifyInMemoryCache<String, String> cache = new CrunchifyInMemoryCache<>(100,100,500000);
		
		for(int i=0;i<size;i++){
			String value = Integer.toString(i);
			cache.put(value, value);
		}
        Thread.sleep(200);
        
        long start = System.currentTimeMillis();
        cache.cleanup();
        double finish = (double) (System.currentTimeMillis() - start) / 1000.0;
 
        System.out.println("Cleanup times for " + size + " objects are " + finish + " s");
	}
	private void crunchifyAccessObjects(){
        CrunchifyInMemoryCache<String, String> cache = new CrunchifyInMemoryCache<String, String>(1, 1, 10);
        cache.put("1", "Hello");
        System.out.println(cache.toString());

	}

}
