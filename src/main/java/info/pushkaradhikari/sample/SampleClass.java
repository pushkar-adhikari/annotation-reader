package info.pushkaradhikari.sample;

import java.util.List;

import info.pushkaradhikari.AnnotationReader.AnnotationReaderFactory;
import info.pushkaradhikari.AnnotationReader.AnnotationReaderType;
import info.pushkaradhikari.AnnotationReader.NitAnnotationReader;

public class SampleClass {
	
	public static void main(String[] args) {
		//Reader reader = new Reader(FetchValue.class);
		//reader.read();
		
		NitAnnotationReader reader = AnnotationReaderFactory.configureReader(Deprecated.class, AnnotationReaderType.METHOD);
		List<String> values = reader.read();
		for (String val : values) {
			System.out.println(val);
		}
	}
	
}
