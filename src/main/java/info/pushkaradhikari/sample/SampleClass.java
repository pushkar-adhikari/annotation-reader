package info.pushkaradhikari.sample;

import info.pushkaradhikari.AnnotationReader.MasterReader;
import info.pushkaradhikari.AnnotationReader.StringParameterizedEnumReader;
import info.pushkaradhikari.AnnotationReader.old.Reader;
import info.pushkaradhikari.Annotations.FetchValue;

public class SampleClass {
	
	public static void main(String[] args) {
		//Reader reader = new Reader(FetchValue.class);
		//reader.read();
		
		MasterReader reader = new StringParameterizedEnumReader(FetchValue.class);
		reader.read();
	}
	
}
