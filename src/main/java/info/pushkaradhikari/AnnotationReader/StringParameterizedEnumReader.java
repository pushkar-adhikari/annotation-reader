package info.pushkaradhikari.AnnotationReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringParameterizedEnumReader extends TypeAnnotationReader{
	
	private static final String RETURN_TYPE_STRING = "String";
	private static final String DEFAULT_INHERITED_METHOD_NAME = "value";
	
	protected StringParameterizedEnumReader(Class<? extends Annotation> annotation) {
		super(annotation);
	}
	
	protected StringParameterizedEnumReader getStringParameterizedEnumReader(Class<? extends Annotation> annotation){
		return (StringParameterizedEnumReader) getTypeAnnotationReader(annotation);
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
						valuesList.add(invocationResult);
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
	
}
