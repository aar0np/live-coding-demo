package com.datastaxtutorials.livecodingdemo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = -4305152363115052956L;

	private TextField eventName = new TextField("Event name");
	private TextField eventDate = new TextField("Event date");
	
	private TextField year = new TextField("Year");
	private Grid<HolidayPojo> grid = new Grid<>(HolidayPojo.class);
	
	HolidayController holidayController;
	
	public MainView(HolidayRepository holidayRepo) {
		holidayController = new HolidayController(holidayRepo);
		grid.setColumns("name","eventDate");
		
		add(getForm(),getQuery(),grid);
	}
	
	private Component getForm() {
		var layout = new HorizontalLayout();
		layout.setAlignItems(Alignment.BASELINE);
		
		var addButton = new Button("Add");
		addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		layout.add(eventName,eventDate,addButton);
		
		addButton.addClickListener(click ->{
			
			HolidayPojo holiday = new HolidayPojo();
			holiday.setName(eventName.getValue());
			holiday.setEventDate(LocalDate.parse(eventDate.getValue()));
			
			holidayController.saveNewHoliday(holiday);
			clearFields();
			refreshGrid();
		});
		
		return layout;
	}
	
	public Component getQuery() {
		var layout = new HorizontalLayout();
		layout.setAlignItems(Alignment.BASELINE);
		
		var queryButton = new Button("Query");
		queryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		layout.add(year, queryButton);
		
		queryButton.addClickListener(click -> {
			refreshGrid();
		});
		
		return layout;
	}
	
	private void refreshGrid() {
		int intYear = Integer.parseInt(year.getValue());
		List<HolidayEntity> rows = holidayController.getHolidaysByYear(intYear);
		
		grid.setItems(convertHolidayList(rows));
	}
	
	private List<HolidayPojo> convertHolidayList(List<HolidayEntity> holidays) {
		List<HolidayPojo> returnVal = new ArrayList<>();
		
		
		for (HolidayEntity holiday : holidays) {
			long lngDate = holiday.getKey().getEventDate();
			LocalDate eventDate = convertLongToLocalDate(lngDate);
			
			HolidayPojo hJson = new HolidayPojo();
			hJson.setEventDate(eventDate);
			hJson.setName(holiday.getName());
			
			returnVal.add(hJson);
		}
		
		return returnVal;
	}
	
	private LocalDate convertLongToLocalDate(long date) {
		String strDate = Long.toString(date);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyMMdd");
		return LocalDate.parse(strDate,dtf);
	}
	
	private void clearFields() {
		eventName.clear();
		eventDate.clear();
	}
}
