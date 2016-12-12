package com.msober.main;

import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnagramFinderTest {
	
	private ConcurrentHashMap<String, Set<String>> anagrams = new ConcurrentHashMap<String, Set<String>>();

	@Test
	public void readFileTest(){
		
		String fileName = "file/example.txt";
		String testStrKey = "abeorst";
		String testStr = "boaters";
		
		String test2StrKey = "aefst";
		String test2Str = "feast";
		
		try {
			ConcurrentMap<String, Set<String>> test = AnagramFinder.readWordsFromFile(fileName);
			
			Assert.assertNotNull(test);
			Assert.assertEquals(test, anagrams);
			
			// anagram word
			Assert.assertNotNull(test.get(testStrKey));
			Assert.assertNotNull(test.get(testStrKey).contains(testStr));
			
			Assert.assertNotNull(test.get(test2StrKey));
			Assert.assertNotNull(test.get(test2StrKey).contains(test2Str));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception encountered! ");
		}
	}
	
	@Test
	public void filterAnagramsTest(){
		
		ConcurrentHashMap<String, Set<String>> testData = new ConcurrentHashMap<String, Set<String>>();
		testData.put("abeorst", Stream.of("boaster", "boaters", "borates").collect(Collectors.toCollection(HashSet::new)));

		
		ConcurrentHashMap<String, Set<String>> test = AnagramFinder.filterAnagrams(anagrams);
		
		Assert.assertEquals(testData, test);
		
	}
	
	@Before
	public void setUp() {
		anagrams.put("abeorst", Stream.of("boaster", "boaters", "borates").collect(Collectors.toCollection(HashSet::new)));
		anagrams.put("estt", Stream.of("test").collect(Collectors.toCollection(HashSet::new)));
		anagrams.put("elst", Stream.of("lest").collect(Collectors.toCollection(HashSet::new)));
		anagrams.put("aefst", Stream.of("feast").collect(Collectors.toCollection(HashSet::new)));

	}
}
