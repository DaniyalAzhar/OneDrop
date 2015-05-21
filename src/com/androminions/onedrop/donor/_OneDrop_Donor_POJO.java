package com.androminions.onedrop.donor;

public class _OneDrop_Donor_POJO{
	String email;
	String fullName;
	String password;
	String area;
	String bloodGroup;
	String contact;
	Double longitude;
	Double latitude;
	int availability=0;
	
	
	public _OneDrop_Donor_POJO(){
		
	}
	public void parseDonor(String data){
		
	}
	@Override
    public String toString() {
        return "email=" + email + 
        			", fullName=" + fullName +
        			", password=" + password +
        			", area=" + area +
        			", bloodGroup=" + bloodGroup +
        			", contact=" + contact +        			
        			", availability=" +0;
    } 
	public _OneDrop_Donor_POJO(String username, String fullName,
			String password, String area, String bloodGroup, String gender,
			int age, String contact) {
		super();
		this.email = username;
		this.fullName = fullName;
		this.password = password;
		this.area = area;
		this.bloodGroup = bloodGroup;
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String username) {
		this.email = username;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public int getAvailability() {
		return availability;
	}
	public void setAvailability(int availability) {
		this.availability = availability;
	}
}
