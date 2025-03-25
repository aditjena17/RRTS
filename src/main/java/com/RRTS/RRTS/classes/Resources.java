package com.RRTS.RRTS.classes;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "resources")
public class Resources {
	@Id
	private String city;
	
	private String manpower;
	private String materials;
	private String machines;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getManpower() {
		return manpower;
	}
	public void setManpower(String manpower) {
		this.manpower = manpower;
	}
	public String getMaterials() {
		return materials;
	}
	public void setMaterials(String materials) {
		this.materials = materials;
	}
	public String getMachines() {
		return machines;
	}
	public void setMachines(String machines) {
		this.machines = machines;
	}
	
	public Resources(String city, String manpower, String materials, String machines) {
		super();
		this.city = city;
		this.manpower = manpower;
		this.materials = materials;
		this.machines = machines;
	}
	
	public Resources() {}
}
