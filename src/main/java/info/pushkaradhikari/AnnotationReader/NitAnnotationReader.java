package info.pushkaradhikari.AnnotationReader;

import java.lang.annotation.Annotation;

import info.pushkaradhikari.AnnotationReader.impl.AbstractReader;
import info.pushkaradhikari.AnnotationReader.impl.MethodReader;
import info.pushkaradhikari.AnnotationReader.impl.StringParameterizedEnumReader;
import info.pushkaradhikari.AnnotationReader.impl.TypeReader;
import info.pushkaradhikari.Annotations.FetchValue;

public class NitAnnotationReader {
	
	public AbstractReader aReader;
	
	public NitAnnotationReader() {
		aReader = configureReader(FetchValue.class, ReaderType.STRING_PARAMETERIZED_ENUM);
	}

	public AbstractReader configureReader(Class<? extends Annotation> annotation, ReaderType readerType) {
		switch (readerType) {
		case ENUM:
			return new TypeReader(annotation);
		case METHOD:
			return new MethodReader(annotation);
		case STRING_PARAMETERIZED_ENUM:
			return new StringParameterizedEnumReader(annotation);
		default:
			throw (new UnsupportedOperationException("Unsupported readerType!"));
		}
	}

}
