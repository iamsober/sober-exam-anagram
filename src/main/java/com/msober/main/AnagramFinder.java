package com.msober.main;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.msober.util.FileUtil;

/**
 * The Class AnagramFinder.
 */
public class AnagramFinder {
	
	
	private static final String regexEmptyStr = "\\s";
	
	private static final String emptyStr = "";

	
	/**
	 * Creates the anagram key.
	 *
	 * @param str input string/word
	 * @return generated key
	 */
	private static String createAnagramKey(String str){
		
		String strippedStr = str.replaceAll( AnagramFinder.regexEmptyStr, AnagramFinder.emptyStr);
		
		char[] anagramArray = strippedStr.toLowerCase().toCharArray();
		
		Arrays.sort(anagramArray);
		
		return new String(anagramArray);
	}
	
	/**
	 * Read each line of file. Group words by generated key
	 * 	Map< *generated key, *set of words>
	 *
	 * @param fileName the file location
	 * @return concurrent map contains group words by the generated anagram key
	 * @throws Exception the exception
	 */
	public static ConcurrentMap<String, Set<String>> readWordsFromFile(String fileName) throws Exception{		
		
		try (Stream<String> stream = Files.lines(Paths.get(FileUtil.getFileURI(fileName)), StandardCharsets.ISO_8859_1)) {
			
			// group all words by the generated key
			ConcurrentMap<String, Set<String>> anagramsGroup = stream.collect(
					Collectors.groupingByConcurrent(n -> createAnagramKey(n),
							//Collectors.mapping(n -> n, Collectors.toSet()))); // case sensitive grouping
							Collectors.mapping(n -> n.toString().toLowerCase(), Collectors.toSet()))); // case insensitive
			
			return anagramsGroup;
			
		} catch (Exception e) {
			throw e;
		}
	
	}
	
	/**
	 * Filter set of anagrams from the given map.
	 *
	 * @param anagramsGroup the anagrams group
	 * @return the concurrent hash map
	 */
	public static ConcurrentHashMap<String, Set<String>> filterAnagrams(ConcurrentMap<String, Set<String>> anagramsGroup){
		
		ConcurrentHashMap<String, Set<String>> anagrams = (ConcurrentHashMap<String, Set<String>>) anagramsGroup.entrySet().stream()
			.filter(n -> n.getValue().size() > 1)
			.collect(Collectors.toConcurrentMap(p -> p.getKey(), p -> p.getValue()));
		
		return anagrams;

	}

	public static void main(String[] args) {
		
		try{
			String fileName = "file/wordlist.txt";
			
			ConcurrentHashMap<String, Set<String>> anagrams = AnagramFinder.filterAnagrams(AnagramFinder.readWordsFromFile(fileName));
			
			int count = 0;
			for(String k : anagrams.keySet()){
				
				System.out.println(" " + anagrams.get(k).toString()
						.replace("[", "")
						.replace("]", "")
						.replace(",", " ")
						.trim());
				
				count += anagrams.get(k).size();
			}
			
			System.out.println("\n\nAnagram count: " + anagrams.size());
			System.out.println("Total words: " + count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
