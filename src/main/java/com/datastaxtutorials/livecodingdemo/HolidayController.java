package com.datastaxtutorials.livecodingdemo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(
		methods = {POST, GET, OPTIONS, PUT, DELETE, PATCH},
		maxAge = 3600,
		allowedHeaders = {"x-requested-with", "origin", "content-type", "accept"},
		origins = "*" )
public class HolidayController {

	private HolidayRepository holidayRepo;
	
	public HolidayController(HolidayRepository hRepo) {
		this.holidayRepo = hRepo;
	}
	
	@GetMapping("/nerdholidays/year/{year}")
	public ResponseEntity<Stream<HolidayEntity>> findHolidaysByYear(
			@PathVariable(value="year") long year) {
	
		return ResponseEntity.ok(getHolidaysByYear(year).stream());
	}
	
	@PostMapping("/nerdholidays/create")
	public ResponseEntity<HolidayEntity> saveHoliday(
			HttpServletRequest req,
			@RequestBody HolidayPojo nerdHoliday) {
		
		HolidayEntity savedHoliday = saveNewHoliday(nerdHoliday);
		
		return ResponseEntity.ok(savedHoliday);
	}
	
	public List<HolidayEntity> getHolidaysByYear(long year) {
		return holidayRepo.findByKeyYearBucket(year);
	}
	
	public HolidayEntity saveNewHoliday(HolidayPojo holidayIncoming) {
		
		LocalDate lDate = holidayIncoming.getEventDate();
		String strDate = lDate.toString().replace("-","");
		
		int year = lDate.getYear();
		UUID id = UUID.randomUUID();
		String name = holidayIncoming.getName();
				
		HolidayPrimaryKey key = new HolidayPrimaryKey();
		key.setYearBucket(year);
		key.setEventDate(Long.parseLong(strDate));
		key.setId(id);
		
		HolidayEntity holiday = new HolidayEntity();
		holiday.setKey(key);
		holiday.setName(name);
		
		holidayRepo.save(holiday);
		
		return holiday;
	}
 }
