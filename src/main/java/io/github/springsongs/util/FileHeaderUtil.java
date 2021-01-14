package io.github.springsongs.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class FileHeaderUtil {
	private static FileHeaderUtil me;
	private static List<String> headerList;

	private FileHeaderUtil() {
	}

	public static FileHeaderUtil getInstance() {
		if (me == null) {
			me = new FileHeaderUtil();
		}
		return me;
	}

	public List<String> getHeaderList() {
		if (headerList == null) {
			headerList = new ArrayList<String>();

			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			String classpathResource = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/checkFileHeader.properties";
			Properties p = new Properties();
			try {
				Resource[] res = resolver.getResources(classpathResource);
				for (Resource re : res) {
					p.load(re.getInputStream());
					break;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			for (Map.Entry<Object, Object> item : p.entrySet()) {
				headerList.add(item.getValue().toString());
			}
		}

		return headerList;
	}

}
