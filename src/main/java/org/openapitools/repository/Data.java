package org.openapitools.repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The data for a single resource retrieved from the repository.
 */
@XmlRootElement(name = "Data")
class Data {

	private String id;
	private String name;
	private String description;

	public String getId() {
		return id;
	}

	@XmlElement
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Data [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
}
