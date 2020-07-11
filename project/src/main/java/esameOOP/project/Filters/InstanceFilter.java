package esameOOP.project.Filters;

import java.util.Calendar;

import esameOOP.project.Exceptions.InvalidFilterException;
import esameOOP.project.Model.Post.Politic;

public class InstanceFilter {

	public static MediaFilter createMediaFilter(String field, String type) throws InvalidFilterException {
		if (type.compareTo("status") == 0 || type.compareTo("photo") == 0 || type.compareTo("video") == 0
				|| type.compareTo("link") == 0)
			return new MediaFilter(field, type);
		else
			throw new InvalidFilterException("Not an allowed type of post");
	}

	public static PoliticFilter createPoliticFilter(String field, String category) throws InvalidFilterException {
		switch (category) {
		case "NON_POLITIC":
			return new PoliticFilter(field, Politic.NON_POLITIC);
		case "POLITIC":
			return new PoliticFilter(field, Politic.POLITIC);
		case "NATIONAL":
			return new PoliticFilter(field, Politic.NATIONAL);
		case "EU":
			return new PoliticFilter(field, Politic.EU);
		case "EXTRA_EU":
			return new PoliticFilter(field, Politic.EXTRA_EU);
		case "INTERNATIONAL":
			return new PoliticFilter(field, Politic.INTERNATIONAL);
		default:
			throw new InvalidFilterException("Not an allowed category of post");
		}
	}

	public static DateFilter createDateFilter(String field, String operator, String d) throws InvalidFilterException {
		try {
			switch (operator) {
			// I mesi partono da 0...
			case "before": {
				int y = Integer.parseInt(d.substring(0, 4));
				int m = Integer.parseInt(d.substring(5, 7));
				int day = Integer.parseInt(d.substring(8));
				Calendar date = Calendar.getInstance();
				date.set(y, m - 1, day);
				return new BeforeDateFilter(field, operator, date);
			}
			case "after": {
				int y = Integer.parseInt(d.substring(0, 4));
				int m = Integer.parseInt(d.substring(5, 7));
				int day = Integer.parseInt(d.substring(8));
				Calendar date = Calendar.getInstance();
				date.set(y, m - 1, day);
				return new AfterDateFilter(field, operator, date);
			}
			case "between": {
				int y1 = Integer.parseInt(d.substring(0, 4));
				int m1 = Integer.parseInt(d.substring(5, 7));
				int day1 = Integer.parseInt(d.substring(8, 10));
				int y2 = Integer.parseInt(d.substring(11, 15));
				int m2 = Integer.parseInt(d.substring(16, 18));
				int day2 = Integer.parseInt(d.substring(19, 21));
				Calendar Date1 = Calendar.getInstance();
				Calendar Date2 = Calendar.getInstance();
				Date1.set(y1, m1 - 1, day1);
				Date2.set(y2, m2 - 1, day2);
				return new BetweenDatesFilter(field, operator, Date1, Date2);
			}
			default:
				throw new InvalidFilterException("Invalid operator");
			}
		} catch (NumberFormatException e) {
			throw new InvalidFilterException("The date was badly written");
		}
	}

}