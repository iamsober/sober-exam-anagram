package com.msober.util;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * The Class FileUtil.
 */
public class FileUtil {
	
	/**
	 * Gets the file URI.
	 *
	 * @param str file path
	 * @return the file URI
	 * @throws URISyntaxException the URI syntax exception
	 */
	public static URI getFileURI(String str) throws URISyntaxException{
		return new FileUtil().getClass().getClassLoader().getResource(str).toURI();
	}

}
