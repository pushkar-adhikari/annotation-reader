package info.pushkaradhikari.AnnotationReader.old;

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

@Deprecated
public class Reader {
	
	private static final String CLASS_EXTENSION = ".class";
	private static final String CLASSPATH_ROOT = "";
	private static final String PACKAGE_SEPERATOR = ".";
	private static final String LINE_SEPERATOR = "\n";
	private static final String RETURN_TYPE_STRING = "String";
	private static final String DEFAULT_INHERITED_METHOD_NAME = "value";
	private static final String DEFAULT_FILE_NAME = "fetchedValue.txt";
	
	private Class<? extends Annotation> annotation;
	private FileWriter writer;
	
	public Reader(Class<? extends Annotation> annotation) {
		this.annotation = annotation;
	}
	
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
			Enumeration<URL> roots = Thread.currentThread().getContextClassLoader().getResources(CLASSPATH_ROOT);
			while (roots.hasMoreElements()) {
				URL url = roots.nextElement();
				File root = new File(url.getPath());
				classes = getClassesFromDirectory(root, CLASSPATH_ROOT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classes;
	}

	private List<String> getClassesFromDirectory(File root, String packagePrefix) {
		List<String> classes = new ArrayList<String>();
		for (File file : root.listFiles()) {
			if (file.isDirectory()) {
				String constructedPrefix = addPackagePrefix(packagePrefix, file.getName(), false);
				classes.addAll(getClassesFromDirectory(file, constructedPrefix));
			} else {
				String fileName = file.getName();
				if (fileName.contains(CLASS_EXTENSION)) {
					String simpleClassName = removeExtensionFromFileName(fileName);
					String fullyQualifiedName = addPackagePrefix(packagePrefix, simpleClassName, true);
					classes.add(fullyQualifiedName);
				}
			}
		}
		return classes;
	}

	private String removeExtensionFromFileName(String fileName) {
		int extensionEndIndex = fileName.lastIndexOf('.');
		String simpleClassName = fileName.substring(0, extensionEndIndex);
		return simpleClassName;
	}

	private String addPackagePrefix(String packagePrefix, String fileName, boolean isFile) {
		if (isFile) {
			return packagePrefix.concat(fileName);
		} else {
			return packagePrefix.concat(fileName).concat(PACKAGE_SEPERATOR);
		}
	}
	
	private void readValues(Class<?> clazz)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		if (isAnnotationPresent(clazz)) {
			Object[] enumArray = clazz.getEnumConstants();
			for (Object object : enumArray) {
				Method[] methods = clazz.getDeclaredMethods();
				for (Method method : methods) {
					if (filter(method)) {
						String invocationResult = (String) method.invoke(object);
						writer.write(invocationResult);
						writer.write(LINE_SEPERATOR);
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

	private boolean isAnnotationPresent(Class<?> classObject) {
		return classObject.isAnnotationPresent(this.annotation);
	}
	
}
