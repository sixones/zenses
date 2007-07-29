using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using System.IO;
using System.Text;

using Zenses.Lib.Constants;

namespace Zenses.Lib.Storage
{
	public class Database
	{
		private static SQLiteConnection __hConnection;

		public Database()
		{
			this.Connect();
		}

		public void Connect()
		{
			this.CreateDatabase();

			if (__hConnection == null || __hConnection.State == ConnectionState.Closed)
			{
				try
				{
					__hConnection = new SQLiteConnection();
					
					__hConnection.ConnectionString = "Data Source=" + FileLocations.DATABASE_FILENAME + "; Version=3;";
					__hConnection.Open();
					
				}
				catch (SQLiteException)
				{
					// handle exception
				}

				this.CreateStructure();
			}
		}

		public void CreateDatabase()
		{
			if (!Directory.Exists("data"))
			{
				Directory.CreateDirectory("data");
			}

			if (!File.Exists(FileLocations.DATABASE_FILENAME))
			{
				SQLiteConnection.CreateFile(FileLocations.DATABASE_FILENAME);
			}
		}

		public void CreateStructure()
		{
			if (__hConnection.GetSchema("Tables").Rows.Count == 0)
			{
				// create the two tables in the database
				this.Execute("CREATE TABLE device_tracks ([id] NVARCHAR(30), [persistent_id] NVARCHAR(30) PRIMARY KEY, [name] NVARCHAR(256), [artist] NVARCHAR(256), [album] NVARCHAR(256), [length] INTEGER, [size] INTEGER, [play_count] INTEGER, [filename] TEXT)");
				this.Execute("CREATE TABLE played_history ([track_persistent_id] NVARCHAR(30), [date_submitted] NVARCHAR(20))");
			}
		}

		public DataSet Fetch(string sqlQuery, params SQLiteParameter[] parameters)
		{
			this.Connect();

			DataSet fetchedData = new DataSet();

			using (SQLiteTransaction transaction = __hConnection.BeginTransaction())
			{
				using (SQLiteCommand command = new SQLiteCommand())
				{
					command.Parameters.AddRange(parameters);
					command.CommandText = sqlQuery;
					command.Connection = __hConnection;

					using (SQLiteDataAdapter dataAdapter = new SQLiteDataAdapter(command))
					{
						using (SQLiteCommandBuilder commandBuilder = new SQLiteCommandBuilder(dataAdapter))
						{
							dataAdapter.Fill(fetchedData);
							transaction.Commit();
						}
					}
				}
			}

			return fetchedData;
		}

		public int Execute(string sqlQuery, params SQLiteParameter[] parameters)
		{
			this.Connect();

			int affectedRows = 0;
			try
			{
				using (SQLiteTransaction transaction = __hConnection.BeginTransaction())
				{
					using (SQLiteCommand command = new SQLiteCommand())
					{
						command.Parameters.AddRange(parameters);

						command.CommandText = sqlQuery;
						command.Connection = __hConnection;

						affectedRows = command.ExecuteNonQuery();
						transaction.Commit();
					}
				}
			}
			catch (SQLiteException ex)
			{
				throw ex;
			}

			return affectedRows;
		}

		public void Close()
		{
			__hConnection.Close();
		}

		~Database()
		{
			this.Close();
		}
	}
}
