package esameOOP.project.Util;

import java.lang.Math;
import java.util.Vector;
import java.util.Calendar;

import esameOOP.project.Model.Post;

public class Calculate {

	public static int calcMin(Vector<Post> feed) {
		int min = feed.elementAt(0).getNumChar(), support;
		for (int i = 1; i < feed.size(); i++) {
			support = feed.elementAt(i).getNumChar();
			if (support < min)
				min = support;
		}
		return min;
	}

	public static int calcMax(Vector<Post> feed) {
		int max = feed.elementAt(0).getNumChar(), support;
		for (int i = 1; i < feed.size(); i++) {
			support = feed.elementAt(i).getNumChar();
			if (support > max)
				max = support;
		}
		return max;
	}

	public static double calcAvg(Vector<Post> feed) {
		int sum = 0, Avg;
		for (int i = 0; i < feed.size(); i++) {
			sum += feed.elementAt(i).getNumChar();
		}
		Avg = sum / feed.size();
		return Avg;
	}

	public static double calcStdDev(Vector<Post> feed) {
		double sum = 0, Avg, StdDev;
		Avg = calcAvg(feed);
		for (int i = 0; i < feed.size(); i++)
			sum += Math.pow(feed.elementAt(i).getNumChar() - Avg, 2);
		StdDev = Math.sqrt(sum / feed.size());
		return StdDev;
	}

	public static double[] calcPostPerHour(Vector<Post> feed) {
		double slot[] = new double[24];
		Calendar date;
		int time;
		for (int i = 0; i < 24; i++)
			slot[i] = 0;
		for (int i = 0; i < feed.size(); i++) {
			date = feed.elementAt(i).getCreated_time();
			time = date.get(Calendar.HOUR_OF_DAY);
			slot[time] += 1;
		}
		for (int j = 0; j < slot.length; j++) {
			slot[j] = (slot[j] / feed.size())*100;
		}
		return slot;

	}

	public static double[] countPolitic(Vector<Post> feed) {
		double cont[] = new double[6];
		for (int i = 0; i < 6; i++)
			cont[i] = 0;
		for (int j = 0; j < feed.size(); j++) {
			switch (feed.elementAt(j).getPolitic()) {
			case NON_POLITIC:
				cont[0] += 1;
				break;
			case POLITIC:
				cont[1] += 1;
				break;
			case NATIONAL:
				cont[2] += 1;
				break;
			case EU:
				cont[3] += 1;
				break;
			case EXTRA_EU:
				cont[4] += 1;
				break;
			case INTERNATIONAL:
				cont[5] += 1;
				break;
			}
		}
		for (int i = 0; i < 6; i++)
			cont[i] = (cont[i] / feed.size()) * 100;
		return cont;
	}

}
