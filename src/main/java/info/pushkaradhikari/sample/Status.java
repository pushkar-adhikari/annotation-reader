package info.pushkaradhikari.sample;

public enum Status {

	close(0, "CLOSE"), open(1, "OPEN"), progress(2, "Progress");

	private final int key;
	private final String status;

	private Status(int key, String status) {
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
