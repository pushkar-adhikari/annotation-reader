package info.pushkaradhikari.AnnotationReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class TypeReader extends NitAnnotationReader{
	
	protected TypeReader(Class<? extends Annotation> annotation) {
		super.annotation = annotation;
	}
	
	protected static TypeReader getTypeAnnotationReader(Class<? extends Annotation> annotation){
		return new TypeReader(annotation);
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
