package com.datastaxtutorials.livecodingdemo;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("nerd_holidays")
public class HolidayEntity {
	@PrimaryKey
	private HolidayPrimaryKey key;

	private String name;

	public HolidayPrimaryKey getKey() {
		return key;
	}

	public void setKey(HolidayPrimaryKey key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
