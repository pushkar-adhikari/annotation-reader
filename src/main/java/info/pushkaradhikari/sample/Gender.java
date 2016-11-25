package info.pushkaradhikari.sample;

public enum Gender {
	
	male(0,"MALE"),
	female(1,"FEMALE");
	
	private final int key;
	private final String gender;	
	
	private Gender(int key, String gender) {
		this.key = key;
		this.gender = gender;
	}
	
	public int getKey() {
		return key;		
	}
	
	public String getGender() {
		return gender;		
	}
	
}
