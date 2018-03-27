package com.example.demo;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
* Created by aiacob on 28.08.2017.
*/
@Configuration
public class CacheConfiguration {

 @Bean
 public Cache<String, Object> cache() {
   return   CacheBuilder.newBuilder().softValues().build();
 }

}

public SalesOrg findSalesOrg(HttpServletRequest request, String salesOrgCode) {

   try {
     //noinspection unchecked
     return (SalesOrg) cache.get("SALES-ORG-"+salesOrgCode,
         () -> findSalesOrgViaApiCall(request, salesOrgCode)
     );
     
     public SalesOrg findSalesOrg(HttpServletRequest request, String salesOrgCode) {

    	   try {
    	     //noinspection unchecked
    	     return (SalesOrg) cache.get("SALES-ORG-"+salesOrgCode,
    	         () -> findSalesOrgViaApiCall(request, salesOrgCode)
    	     );
    	   } catch (ExecutionException e) {
    	     log.error("Error in cache communication", e);
    	     return findSalesOrgViaApiCall(request, salesOrgCode);
    	   }
    	 }