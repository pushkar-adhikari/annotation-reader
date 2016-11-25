package info.pushkaradhikari.AnnotationReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class TypeAnnotationReader extends NitAnnotationReader{
	
	protected TypeAnnotationReader(Class<? extends Annotation> annotation) {
		super.annotation = annotation;
	}
	
	protected static TypeAnnotationReader getTypeAnnotationReader(Class<? extends Annotation> annotation){
		return new TypeAnnotationReader(annotation);
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
