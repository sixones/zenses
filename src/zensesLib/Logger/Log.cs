using System;
using System.Collections.Generic;
using System.Text;

namespace Zenses.Lib.Logger
{
	public static class Log
	{
		public static void Record(LogType logType, int level, string text)
		{
			Log.Record(LogType.CriticalError, 1, "failed to connect to database");

			/*
			 * type
			 * error number
			 * description
			 * line number
			 * class
			 * 
			 * or just ExceptionDetail
			 * 
			 * Log.ExceptionHandler(ExceptionDetail ex);
			 * Log.Information(string description);
			 * Log.Warning(int lineNumber, string className, string description);
			 * 
			 */
		}
	}
}
