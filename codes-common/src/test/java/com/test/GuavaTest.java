package com.test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;
import com.test.com.codes.common.util.test.Person1;

public class GuavaTest {
	
	@Test
	public void test(){
		Person1 p1 = new Person1();
		p1.setFirstName("abcd");
		p1.setLastName("bcd");
		 
		Person1 p2 = new Person1();
		p2.setFirstName("abcd");
		p2.setLastName("abcd");
		
		int compareTo = p1.compareTo(p2);
		System.out.println(compareTo);
		
	}
	@Test
	public void test1(){
		Person1 p1 = new Person1();
		p1.setFirstName("abcd");
		p1.setLastName("bcd");
		
		System.out.println(p1);
	}
	
	@Test
	public void testPreconditions(){
		String reference = "a";
		String checkNotNull = Preconditions.checkNotNull(reference);
		System.out.println(checkNotNull);
	}
	
	@Test
	public void testOrdering(){
		// 最值字符 case insensitive 不区分大小写
		List<String> strs = Lists.newArrayList("a", "d", "C", "b");
		String min = Ordering
			.from(String.CASE_INSENSITIVE_ORDER)
			.min(strs);
		Assert.assertEquals("a", min);
		
		// 最值数字
		List<Integer> nums = Lists.newArrayList(11,2,5,22,44,23,5,6,7);
		int max = Ordering.natural().max(nums);
		Assert.assertEquals(44, max);
		
		// 通过字符长度排序
		Ordering<String> byLength = new Ordering<String>() {
			@Override
			public int compare(String arg0, String arg1) {
				return Ints.compare(arg0.length(), arg1.length());
			}
		};
		
		strs = Lists.newArrayList("1", "22", "55555", null, "333", "4444");
		Collections.sort(strs, byLength.nullsFirst());
		Assert.assertEquals("55555", strs.get(strs.size()-1));
		
		// 对容器里面的元素 前多少个 进行从小到大的排序
		nums = Lists.newArrayList(323,43,23,55,11,66,44,98);
		List<Integer> list = Ordering.natural().leastOf(nums, nums.size()/2);
		System.out.println(list);
		
	}
	
	@Test
	public void testRange(){
		Range<Integer> openClosed = Range.openClosed(3, 10);
		Assert.assertTrue(openClosed.contains(5));
	}
	
	@Test
	public void testCache(){
		for (int i = 0; i < 10; i++) {
			
		}
		
		CacheBuilder.newBuilder()
			.maximumSize(10)
			.expireAfterWrite(30, TimeUnit.SECONDS)
			.build(new CacheLoader<Integer, Person1>() {
				@Override
				public Person1 load(Integer key) throws Exception {
					// TODO Auto-generated method stub
					return null;
				}
			});
	}
	
	@Test
	public void testFiles(){
		Path path = Paths.get("D", "Downloads", "Google", "apache-maven-3.2.5-bin.zip");
		System.out.println( path.getFileName() );
		System.out.println( path.getNameCount() );
		System.out.println( path.getParent() );
		
		// File from = new File("D:/Downloads/迅雷下载/apache-tomcat-7.0.64-windows-x86.zip");
		// File to = new File("D:/Downloads/Software/apache-tomcat-7.0.64-windows-x86.zip");
		try {
			
			//Files.copy(from, to);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
