package com.datastaxtutorials.livecodingdemo;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface HolidayRepository extends CassandraRepository<HolidayEntity,HolidayPrimaryKey> {
	
	List<HolidayEntity> findByKeyYearBucket(long yearBucket);
}
