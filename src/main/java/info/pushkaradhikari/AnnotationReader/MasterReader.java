package info.pushkaradhikari.AnnotationReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import info.pushkaradhikari.AnnotationReader.utils.FileModificationUtils;

public abstract class MasterReader {
	
	public static final String LINE_SEPERATOR = "\n";
	public static final String RETURN_TYPE_STRING = "String";
	public static final String DEFAULT_INHERITED_METHOD_NAME = "value";
	private static final String CLASSPATH_ROOT = "";
	private static final String DEFAULT_FILE_NAME = "fetchedValue.txt";
	
	protected Class<? extends Annotation> annotation;
	private FileModificationUtils fileModifier = new FileModificationUtils();
	
	public FileWriter writer;
	
	public void read() {
		List<String> classes = getClasses();
		try {
			writer = new FileWriter(DEFAULT_FILE_NAME);
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
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public boolean filter(Method method) {
		boolean name = !method.getName().contains(DEFAULT_INHERITED_METHOD_NAME);
		boolean returnType = method.getReturnType().getSimpleName().contentEquals(RETURN_TYPE_STRING);
		return name && returnType;
	}
	
	public boolean isAnnotationPresent(Class<?> classObject) {
		return classObject.isAnnotationPresent(annotation);
	}
	
}
