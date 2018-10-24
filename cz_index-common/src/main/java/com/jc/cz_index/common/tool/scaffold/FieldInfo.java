package com.jc.cz_index.common.tool.scaffold;

public class FieldInfo {
	private String type;
	private String name;
	private final String comments;

	public FieldInfo(String type, String name, String comments) {
		this.type = type;
		this.name = name;
		this.comments = comments;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComments() {
		return comments;
	}

}
