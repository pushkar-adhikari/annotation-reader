package info.pushkaradhikari.AnnotationReader.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileModificationUtils {
	
	private static final String CLASS_EXTENSION = ".class";
	private static final String PACKAGE_SEPERATOR = ".";
	
	public List<String> getClassesFromDirectory(File root, String packagePrefix) {
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
	
}
