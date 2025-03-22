package com.RRTS.RRTS.classes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "resources")
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
