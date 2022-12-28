package com.datastaxtutorials.livecodingdemo;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class HolidayPrimaryKey {

	@PrimaryKeyColumn(
			name="year_bucket",
			ordinal=0,
			type=PrimaryKeyType.PARTITIONED)
	private long yearBucket;
	
	@PrimaryKeyColumn(
			name="event_date",
			ordinal=1,
			type=PrimaryKeyType.CLUSTERED)
	private long eventDate;
	
	@PrimaryKeyColumn(
			name="id",
			ordinal=2,
			type=PrimaryKeyType.CLUSTERED)
	private java.util.UUID id;

	public long getYearBucket() {
		return yearBucket;
	}

	public void setYearBucket(long yearBucket) {
		this.yearBucket = yearBucket;
	}

	public long getEventDate() {
		return eventDate;
	}

	public void setEventDate(long eventDate) {
		this.eventDate = eventDate;
	}

	public java.util.UUID getId() {
		return id;
	}

	public void setId(java.util.UUID id) {
		this.id = id;
	}
	
}
