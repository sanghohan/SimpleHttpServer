package com.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * Created by 1002074 on 2016. 5. 01..
 */
public class LogUtil {
	public static String getStackTrace(Exception e)
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pinrtStream = new PrintStream(out);
		e.printStackTrace(pinrtStream);
		String stackTraceString = out.toString();
		
		return stackTraceString;
	}
}
