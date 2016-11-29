package info.pushkaradhikari.AnnotationReader.impl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class TypeReader extends AbstractReader{
	
	public TypeReader(Class<? extends Annotation> annotation) {
		super.annotation = annotation;
	}
	
	@Override
	protected void readValues(Class<?> clazz)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		if (isAnnotationPresent(clazz)) {
			Object[] enumArray = clazz.getEnumConstants();
			for (Object object : enumArray) {
				valuesList.add(object.toString());
			}
		}
	}

	protected boolean isAnnotationPresent(Class<?> clazz) {
		return clazz.isAnnotationPresent(annotation);
	}
	 

}
