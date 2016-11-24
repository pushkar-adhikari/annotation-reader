package info.pushkaradhikari.AnnotationReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringParameterizedEnumReader extends TypeAnnotationReader{
	
	public StringParameterizedEnumReader(Class<? extends Annotation> annotation) {
		super(annotation);
	}

	@Override
	protected void readValues(Class<?> clazz)
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
	
}
