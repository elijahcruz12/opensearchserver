/**   
 * License Agreement for OpenSearchServer
 *
 * Copyright (C) 2012 Emmanuel Keller / Jaeksoft
 * 
 * http://www.open-search-server.com
 * 
 * This file is part of OpenSearchServer.
 *
 * OpenSearchServer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * OpenSearchServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenSearchServer. 
 *  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.jaeksoft.searchlib.logreport;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import com.jaeksoft.searchlib.Logging;
import com.jaeksoft.searchlib.SearchLibException;
import com.jaeksoft.searchlib.util.FormatUtils.ThreadSafeDateFormat;
import com.jaeksoft.searchlib.util.FormatUtils.ThreadSafeSimpleDateFormat;

public class ErrorParserLogger {

	private final static ThreadSafeDateFormat timeStampFormat = new ThreadSafeSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssZ");

	private static DailyLogger logger = null;

	public final static void init() {
		logger = new DailyLogger(Logging.getLogDirectory(), "parser.error",
				timeStampFormat);
	}

	public final static void close() {
		if (logger != null)
			logger.close();
	}

	public final static String getLocation(StackTraceElement[] stackTrace) {
		for (StackTraceElement element : stackTrace)
			if (element.getClassName().startsWith("com.jaeksoft"))
				return element.toString();
		return null;
	}

	public final static String getFirstLocation(StackTraceElement[] stackTrace) {
		for (StackTraceElement element : stackTrace) {
			String ele = element.toString();
			if (ele != null && ele.length() > 0)
				return ele;
		}
		return null;
	}

	public final static String getFullStackTrace(StackTraceElement[] stackTrace) {
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			for (StackTraceElement element : stackTrace)
				pw.println(element);
			return sw.toString();
		} finally {
			if (pw != null)
				IOUtils.closeQuietly(pw);
			if (sw != null)
				IOUtils.closeQuietly(sw);
		}
	}

	public final static void log(String url, String filename, String filePath,
			Throwable t) throws SearchLibException {
		StringBuffer sb = new StringBuffer('\t');
		if (url != null)
			sb.append(url);
		else if (filePath != null)
			sb.append(filePath);
		else if (filename != null)
			sb.append(filename);
		sb.append('\t');
		sb.append(t.getMessage());
		String codeLocation = null;
		String causeMessage = null;
		while (t != null) {
			causeMessage = t.getMessage();
			String cl = getLocation(t.getStackTrace());
			if (cl != null)
				codeLocation = cl;
			t = t.getCause();
		}
		if (causeMessage != null) {
			sb.append('\t');
			sb.append(causeMessage);
		}
		if (codeLocation != null) {
			sb.append('\t');
			sb.append(codeLocation);
		}
		logger.log(sb.toString());
	}
}
