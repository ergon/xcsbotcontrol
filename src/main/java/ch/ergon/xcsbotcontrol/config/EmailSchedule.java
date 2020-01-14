package ch.ergon.xcsbotcontrol.config;

import ch.ergon.xcsbotcontrol.api.model.EmailScheduleType;
import ch.ergon.xcsbotcontrol.api.model.WeekDay;

public class EmailSchedule {
	private EmailScheduleType type = EmailScheduleType.AFTER_EACH_INTEGRATION;
	private Integer hourOfDay;
	private Integer minutesAfterHour;
	private WeekDay dayOfWeek;

	public EmailScheduleType getType() {
		return type;
	}

	public void setType(EmailScheduleType type) {
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
