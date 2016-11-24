package info.pushkaradhikari.AnnotationReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class TypeAnnotationReader extends MasterReader{
	
	public TypeAnnotationReader(Class<? extends Annotation> annotation) {
		super.annotation = annotation;
	}
	
	@Override
	protected void readValues(Class<?> clazz)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		if (isAnnotationPresent(clazz)) {
			Object[] enumArray = clazz.getEnumConstants();
			for (Object object : enumArray) {
				writer.write(object.toString());
				writer.write(LINE_SEPERATOR);
			}
		}
	}

}
