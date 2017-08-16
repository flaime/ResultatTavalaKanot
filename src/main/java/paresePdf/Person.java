package paresePdf;

public class Person {

	private String forName;
	private String sureName;
	private String sex;
	private String yearOfBirth;
	private String licensNumber;
	private String klub;
	
	public Person(String forName, String sureName, String sex, String yearOfBirth, String licensNumber, String klub) {
		super();
		this.forName = forName;
		this.sureName = sureName;
		this.sex = sex;
		this.yearOfBirth = yearOfBirth;
		this.licensNumber = licensNumber;
		this.klub = klub;
	}

	public String getForName() {
		return forName;
	}

	public void setForName(String forName) {
		this.forName = forName;
	}

	public String getSureName() {
		return sureName;
	}

	public void setSureName(String sureName) {
		this.sureName = sureName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(String yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public String getLicensNumber() {
		return licensNumber;
	}

	public void setLicensNumber(String licensNumber) {
		this.licensNumber = licensNumber;
	}

	public String getKlub() {
		return klub;
	}

	public void setKlub(String klub) {
		this.klub = klub;
	}
	
	
	
}
