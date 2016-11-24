package info.pushkaradhikari.sample;

import info.pushkaradhikari.Annotations.FetchValue;

@FetchValue
public enum Country {

	INDIA(0, "India"), AUSTRALIA(1, "Australia");

	private final int key;
	private final String status;

	private Country(int key, String status) {
		this.key = key;
		this.status = status;
	}

	public int getKey() {
		return key;
	}

	public String getStatus() {
		return status;
	}

}
