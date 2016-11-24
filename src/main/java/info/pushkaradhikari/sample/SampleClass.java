package info.pushkaradhikari.sample;

import info.pushkaradhikari.AnnotationReader.Reader;
import info.pushkaradhikari.Annotations.FetchValue;

public class SampleClass {
	
	public static void main(String[] args) {
		Reader reader = new Reader(FetchValue.class);
		reader.read();
	}
	
}
