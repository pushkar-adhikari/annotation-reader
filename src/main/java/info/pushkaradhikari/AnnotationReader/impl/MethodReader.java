package info.pushkaradhikari.AnnotationReader.impl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodReader extends AbstractReader{
	
	public MethodReader(Class<? extends Annotation> annotation) {
		super.annotation = annotation;
	}
	
	@Override
	protected void readValues(Class<?> clazz)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		Method[] methodArray = clazz.getDeclaredMethods();
		for (Method method : methodArray) {
			if (isAnnotationPresent(method))
				valuesList.add(method.getName());
		}
	}

	private boolean isAnnotationPresent(Method method) {
		return method.isAnnotationPresent(annotation);
	}
	
}
