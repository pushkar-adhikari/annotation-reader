package info.pushkaradhikari.AnnotationReader;

import java.lang.annotation.Annotation;

public class AnnotationReaderFactory {
	public static NitAnnotationReader configureReader(Class<? extends Annotation> annotation, AnnotationReaderType readerType){
		switch(readerType){
		case ANNOTATION:
			throw(new UnsupportedOperationException("Not yet Implemented!"));
		case CLASS:
			throw(new UnsupportedOperationException("Not yet Implemented!"));
		case CONSTRUCTOR:
			throw(new UnsupportedOperationException("Not yet Implemented!"));
		case ENUM:
			return TypeAnnotationReader.getTypeAnnotationReader(annotation);
		case FIELD:
			throw(new UnsupportedOperationException("Not yet Implemented!"));
		case INTERFACE:
			throw(new UnsupportedOperationException("Not yet Implemented!"));
		case METHOD:
			return MethodAnnotationReader.getMethodAnnotationReader(annotation);
		case PACKAGE:
			throw(new UnsupportedOperationException("Not yet Implemented!"));
		case PARAMETER:
			throw(new UnsupportedOperationException("Not yet Implemented!"));
		case VARIABLE:
			throw(new UnsupportedOperationException("Not yet Implemented!"));
		case STRING_PARAMETERIZED_ENUM:
			return StringParameterizedEnumReader.getTypeAnnotationReader(annotation);
		default:
			throw(new UnsupportedOperationException("Unsupported readerType!"));
		}
	}
	
	public static NitAnnotationReader configureReader(String fullyQualifiedAnnotationName, AnnotationReaderType readerType){
		Class<? extends Annotation> annotationClass = null;
		try {
			annotationClass = createAnnotationClass(fullyQualifiedAnnotationName);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return configureReader(annotationClass, readerType);
	}
	
	private static Class<? extends Annotation> createAnnotationClass(String annotationName) throws NoSuchFieldException, ClassNotFoundException{
		Class<?> clazz = Class.forName(annotationName);
		if(clazz.isAnnotation())
			return (Class<? extends Annotation>) clazz;
		else
			throw(new NoSuchFieldException("Annotation "+annotationName+" does not Exist"));
	}
}
