package com.javaeesamples.tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

	public static Date getDate(String date, String format) {
		Date dt = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		try {
			dt = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;
	}

}
