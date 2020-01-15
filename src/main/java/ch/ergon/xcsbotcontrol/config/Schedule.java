package ch.ergon.xcsbotcontrol.config;

import ch.ergon.xcsbotcontrol.api.model.WeekDay;

public class Schedule {
	private ScheduleType type;
	private Integer hourOfDay;
	private Integer minutesAfterHour;
	private WeekDay dayOfWeek;

	public ScheduleType getType() {
		return type;
	}

	public void setType(ScheduleType type) {
		this.type = type;
	}

	public Integer getHourOfDay() {
		return hourOfDay;
	}

	public void setHourOfDay(Integer hourOfDay) {
		this.hourOfDay = hourOfDay;
	}

	public Integer getMinutesAfterHour() {
		return minutesAfterHour;
	}

	public void setMinutesAfterHour(Integer minutesAfterHour) {
		this.minutesAfterHour = minutesAfterHour;
	}

	public WeekDay getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(WeekDay dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
}
