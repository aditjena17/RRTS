package com.RRTS.RRTS.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "resources")
public class Resources {
	@Id
	private String city;	
	private Integer manpower;
	private Map<String,Integer> resources = new HashMap<>();
	private Integer machines;
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getManpower() {
		return manpower;
	}

	public void setManpower(Integer manpower) {
		this.manpower = manpower;
	}

	public Map<String, Integer> getResources() {
		return resources;
	}

	public void setResources(List<String> resourceNames, List<Integer> resourceQuantities) {
        for (int i = 0; i < resourceNames.size(); i++) {
            resources.put(resourceNames.get(i), resourceQuantities.get(i));
        }
    }

	public Integer getMachines() {
		return machines;
	}

	public void setMachines(Integer machines) {
		this.machines = machines;
	}

	public Resources() {}
}
