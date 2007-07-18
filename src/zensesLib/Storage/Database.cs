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
		private SQLiteConnection _hConnection;

		public void Connect()
		{
			if (this._hConnection == null || this._hConnection.State == ConnectionState.Closed)
			{
				if (!File.Exists(FileLocations.DATABASE_FILENAME))
				{
					SQLiteConnection.CreateFile(FileLocations.DATABASE_FILENAME);
				}

				try
				{
					using (this._hConnection = new SQLiteConnection())
					{
						this._hConnection.ConnectionString = "Data Source=" + FileLocations.DATABASE_FILENAME + "; Version=3;";
						this._hConnection.Open();
					}
				}
				catch (SQLiteException)
				{
					// handle exception
				}
			}
		}

		public DataSet Fetch(string sqlQuery)
		{
			this.Connect();

			DataSet fetchedData = new DataSet();

			using (SQLiteTransaction transaction = this._hConnection.BeginTransaction())
			{
				using (SQLiteDataAdapter dataAdapter = new SQLiteDataAdapter(sqlQuery, this._hConnection))
				{
					using (SQLiteCommandBuilder commandBuilder = new SQLiteCommandBuilder(dataAdapter))
					{
						dataAdapter.FillSchema(fetchedData, SchemaType.Mapped);
						transaction.Commit();
					}
				}
			}

			return fetchedData;
		}

		public int Execute(string sqlQuery)
		{
			this.Connect();

			int affectedRows = 0;

			using (SQLiteTransaction transaction = this._hConnection.BeginTransaction())
			{
				using (SQLiteCommand command = new SQLiteCommand())
				{
					command.CommandText = sqlQuery;
					command.Connection = this._hConnection;

					affectedRows = command.ExecuteNonQuery();
					transaction.Commit();
				}
			}

			return affectedRows;
		}

		public void Close()
		{
			this._hConnection.Close();
		}

		~Database()
		{
			this.Close();
		}
	}
}
