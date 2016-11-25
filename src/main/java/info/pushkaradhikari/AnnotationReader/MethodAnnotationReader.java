package info.pushkaradhikari.AnnotationReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodAnnotationReader extends NitAnnotationReader{
	
	protected MethodAnnotationReader(Class<? extends Annotation> annotation) {
		super.annotation = annotation;
	}
	
	protected static MethodAnnotationReader getMethodAnnotationReader(Class<? extends Annotation> annotation){
		return new MethodAnnotationReader(annotation);
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
