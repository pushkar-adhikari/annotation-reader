package info.pushkaradhikari.AnnotationReader;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import info.pushkaradhikari.AnnotationReader.utils.FileModificationUtils;

public abstract class NitAnnotationReader {
	
	private static final String CLASSPATH_ROOT = "";
	
	protected Class<? extends Annotation> annotation;
	private FileModificationUtils fileModifier = new FileModificationUtils();
	protected List<String> valuesList = new ArrayList<String>();
	
	public List<String> read() {
		List<String> classes = getClasses();
		try {
			for (String clazz : classes) {
				try {
					readValues(Class.forName(clazz));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return valuesList;
	}
	
	private List<String> getClasses() {
		List<String> classes = new ArrayList<String>();
		try {
			Enumeration<URL> roots = getResources();
			while (roots.hasMoreElements()) {
				URL url = roots.nextElement();
				classes = getClassesRecursivelyFromURLs(url);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classes;
	}

	private Enumeration<URL> getResources() throws IOException{
		return Thread.currentThread().getContextClassLoader().getResources(CLASSPATH_ROOT);
	}
	
	private List<String> getClassesRecursivelyFromURLs(URL url) {
		File root = new File(url.getPath());
		List<String> classes = fileModifier.getClassesFromDirectory(root, CLASSPATH_ROOT);
		return classes;
	}
	
	protected abstract void readValues(Class<?> clazz)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException;
	
}
