package info.pushkaradhikari.AnnotationReader.old;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

//import info.pushkaradhikari.Annotations.FetchValue;
@Deprecated
public class FetchValuesReader {
	
	public static final String CLASS_EXTENSION = ".class";
	public static final String CLASSPATH_ROOT = "";
	public static final String PACKAGE_SEPERATOR = ".";
	public static final String RETURN_TYPE_STRING = "String";
	public static final String DEFAULT_INHERITED_METHOD_NAME = "value";
	
	private Class<? extends Annotation> clazz;
	
	public FetchValuesReader() {
		//this.clazz = FetchValue.class;
	}
	
	private void readValues(Class<?> clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (isFetchValuePresent(clazz)) {
			Object[] enumArray = clazz.getEnumConstants();
			for (Object object : enumArray) {
				Method[] methods = clazz.getDeclaredMethods();
				for (Method method : methods) {
					if (filter(method)) {
						System.out.println(method.invoke(object, null));
					}
				}
			}
		}
	}

	private boolean filter(Method method) {
		boolean name = !method.getName().contains(DEFAULT_INHERITED_METHOD_NAME);
		boolean returnType = method.getReturnType().getSimpleName().contentEquals(RETURN_TYPE_STRING);
		return name && returnType;
	}

	private boolean isFetchValuePresent(Class<?> classObject) {
		return classObject.isAnnotationPresent(this.clazz);
	}

	public static void main(String[] args) {
		try {
			
			FetchValuesReader reader = new FetchValuesReader();
			List<String> classes = reader.getClasses();
			for (String string : classes) {
				reader.readValues(Class.forName(string));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<String> getClasses() {
		List<String> classes = new ArrayList<String>();
		try {
			Enumeration<URL> roots = Thread.currentThread().getContextClassLoader().getResources(CLASSPATH_ROOT);
			while (roots.hasMoreElements()) {
				URL url = roots.nextElement();
				File root  = new File(url.getPath());
				classes = getClassesFromDirectory(root, CLASSPATH_ROOT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classes;
	}
	
	private List<String> getClassesFromDirectory(File root, String packagePrefix){
		List<String> classes = new ArrayList<String>();
		for(File file : root.listFiles()){
			if (file.isDirectory()) {
				String constructedPrefix = addPackagePrefix(packagePrefix, file.getName(), false);
				classes.addAll(getClassesFromDirectory(file, constructedPrefix));
			} else {
				String fileName  = file.getName();
				if(fileName.contains(CLASS_EXTENSION)){
					String simpleClassName = removeExtensionFromFileName(fileName);
					String fullyQualifiedName = addPackagePrefix(packagePrefix, simpleClassName, true);
					classes.add(fullyQualifiedName);
				}
			}
		}
		return classes;
	}
	
	private String removeExtensionFromFileName(String fileName){
		int extensionEndIndex = fileName.lastIndexOf('.');
		String simpleClassName = fileName.substring(0, extensionEndIndex);
		return simpleClassName;
	}
	
	private String addPackagePrefix(String packagePrefix, String fileName, boolean isFile){
		if (isFile) {
			return packagePrefix.concat(fileName);
		}else{
			return packagePrefix.concat(fileName).concat(PACKAGE_SEPERATOR);
		}
	}
}
