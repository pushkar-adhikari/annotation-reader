package info.pushkaradhikari.AnnotationReader;

import java.lang.annotation.Annotation;

import info.pushkaradhikari.Annotations.FetchValue;

public class AnnotationReaderFactory {
	
	public static NitAnnotationReader configureReader(Class<? extends Annotation> annotation, ReaderType readerType){
		switch(readerType){
		case ENUM:
			return TypeReader.getTypeAnnotationReader(annotation);
		case METHOD:
			return MethodReader.getMethodAnnotationReader(annotation);
		case STRING_PARAMETERIZED_ENUM:
			return StringParameterizedEnumReader.getTypeAnnotationReader(annotation);
		default:
			throw(new UnsupportedOperationException("Unsupported readerType!"));
		}
	}
	
	public static NitAnnotationReader configureReader(){
		return configureReader(FetchValue.class, ReaderType.STRING_PARAMETERIZED_ENUM);
	}
	
	public static NitAnnotationReader configureReader(String fullyQualifiedAnnotationName, ReaderType readerType){
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
