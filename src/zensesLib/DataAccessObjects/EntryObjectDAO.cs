using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using System.Text;

using Zenses.Lib.Objects;
using Zenses.Lib.Storage;

namespace Zenses.Lib.DataAccessObjects
{
	public class EntryObjectDAO
	{
		private Database _hDatabase;

		public EntryObjectDAO()
		{
			this._hDatabase = new Database();
		}

		public List<EntryObject> FetchNotSubmitted()
		{
			return this.FetchNotSubmitted(false);
		}

		public List<EntryObject> FetchNotSubmitted(bool repeatPlayed)
		{
			List<EntryObject> entries = this.FetchPlayed();

			List<EntryObject> recentlyPlayed = new List<EntryObject>();

			foreach (EntryObject entry in entries)
			{
				DataSet dataSet = this._hDatabase.Fetch("SELECT * FROM played_history WHERE track_persistent_id = @persistentId",
															new SQLiteParameter("@persistentId", entry.Id)
														);

				int notSubmittedCount = entry.PlayCount - dataSet.Tables[0].Rows.Count;

				for (int i = 0; i < notSubmittedCount; i++)
				{
					recentlyPlayed.Add(entry);

					if (!repeatPlayed) break;
				}
			}

			return recentlyPlayed;
		}

		public List<EntryObject> FetchPlayed()
		{
			return this.FetchAll("WHERE play_count > 0");
		}

		public List<string> FetchAllArtists()
		{
			DataSet dataSet = this._hDatabase.Fetch("SELECT artist FROM device_tracks GROUP BY artist");

			if (dataSet.Tables[0].Rows.Count > 0)
			{
				List<string> artists = new List<string>();

				foreach (DataRow row in dataSet.Tables[0].Rows)
				{
					artists.Add(row["artist"] as string);
				}

				return artists;
			}

			return null;
		}

		public List<string> FetchAllAlbums()
		{
			DataSet dataSet = this._hDatabase.Fetch("SELECT album FROM device_tracks GROUP BY album");

			if (dataSet.Tables[0].Rows.Count > 0)
			{
				List<string> albums = new List<string>();

				foreach (DataRow row in dataSet.Tables[0].Rows)
				{
					albums.Add(row["artist"] as string);
				}

				return albums;
			}

			return null;
		}

		public List<EntryObject> FetchByArtist(string artist)
		{
			return this.FetchAll("WHERE artist = @artist", new SQLiteParameter("@artist", artist));
		}

		public List<EntryObject> FetchByAlbum(string album)
		{
			return this.FetchAll("WHERE album = @album", new SQLiteParameter("@album", album));
		}

		public List<EntryObject> FetchAll()
		{
			return this.FetchAll(null);
		}

		public List<EntryObject> FetchAll(string conditionStatement, params SQLiteParameter[] parameters)
		{
			DataSet dataSet;

			if (conditionStatement == null)
			{
				dataSet = this._hDatabase.Fetch("SELECT * FROM device_tracks ORDER BY artist");
			}
			else
			{
				dataSet = this._hDatabase.Fetch("SELECT * FROM device_tracks " + conditionStatement + " ORDER BY artist", parameters);
			}

			if (dataSet.Tables[0].Rows.Count > 0)
			{
				List<EntryObject> entries = new List<EntryObject>();

				foreach (DataRow row in dataSet.Tables[0].Rows)
				{
					EntryObject entryObject = new EntryObject();
					this.SetDatabaseFields(ref entryObject, row);

					entries.Add(entryObject);
				}

				return entries;
			}

			return null;
		}

		public EntryObject LoadObject(string persistentId)
		{
			DataSet dataSet = this._hDatabase.Fetch("SELECT * FROM device_tracks WHERE persistent_id = '" + persistentId + "' LIMIT 1");

			if (dataSet.Tables[0].Rows.Count > 0)
			{
				EntryObject entryObject = new EntryObject();
				this.SetDatabaseFields(ref entryObject, dataSet.Tables[0].Rows[0]);

				return entryObject;
			}

			return null;
		}

		private bool ObjectExists(string persistentId)
		{
			string sqlQuery = "SELECT * FROM device_tracks WHERE persistent_id = @persistentId";

			DataSet dataSet = this._hDatabase.Fetch(sqlQuery, new SQLiteParameter("@persistentId", persistentId));

			if (dataSet.Tables[0].Rows.Count == 0)
			{
				return false;
			}

			return true;
		}

		public void SaveObject(EntryObject entryObject)
		{
			if (!this.ObjectExists(entryObject.PersistentId))
			{
				string sqlQuery = "INSERT INTO device_tracks ("
						+ "[id], "
						+ "[persistent_id], "
						+ "[name], "
						+ "[artist], "
						+ "[album], "
						+ "[length], "
						+ "[size], "
						+ "[play_count], "
						+ "[filename] "
						+ ") VALUES ("
						+ "@id, "
						+ "@persistentId, "
						+ "@name, "
						+ "@artist, "
						+ "@album, "
						+ "@length, "
						+ "@size, "
						+ "@playCount, "
						+ "@filename"
						+ ")";

				this._hDatabase.Execute(sqlQuery, 
					new SQLiteParameter("@id", entryObject.Id), 
					new SQLiteParameter("@persistentId", entryObject.PersistentId), 
					new SQLiteParameter("@name", entryObject.Name), 
					new SQLiteParameter("@artist", entryObject.Artist),
					new SQLiteParameter("@album", entryObject.Album), 
					new SQLiteParameter("@length", entryObject.Length), 
					new SQLiteParameter("@size", entryObject.Size), 
					new SQLiteParameter("@playCount", entryObject.PlayCount), 
					new SQLiteParameter("@filename", entryObject.Filename)
				);
			}
			else
			{
				string sqlQuery = "UPDATE device_tracks SET ("
						+ "[id] = @id, "
						+ "[name] = @name, "
						+ "[artist] = @artist, "
						+ "[album] = @album, "
						+ "[length] = @length, "
						+ "[size] = @size, "
						+ "[play_count] @playCount, "
						+ "[filename] = @filename "
						+ ") WHERE persistent_id = @persistentId";

				this._hDatabase.Execute(sqlQuery,
					new SQLiteParameter("@id", entryObject.Id),
					new SQLiteParameter("@persistentId", entryObject.PersistentId),
					new SQLiteParameter("@name", entryObject.Name),
					new SQLiteParameter("@artist", entryObject.Artist),
					new SQLiteParameter("@album", entryObject.Album),
					new SQLiteParameter("@length", entryObject.Length),
					new SQLiteParameter("@size", entryObject.Size),
					new SQLiteParameter("@playCount", entryObject.PlayCount),
					new SQLiteParameter("@filename", entryObject.Filename)
				);
			}
		}

		public void SetDatabaseFields(ref EntryObject entryObject, DataRow row)
		{
			entryObject.Id = row["id"] as string;
			entryObject.PersistentId = row["persistent_id"] as string;
			entryObject.Name = row["name"] as string;
			entryObject.Artist = row["artist"] as string;
			entryObject.Album = row["album"] as string;
			entryObject.Length = (int)((long)row["length"]);
			entryObject.Size = (int)((long)row["size"]);
			entryObject.PlayCount = (int)((long)row["play_count"]);
			entryObject.Filename = row["filename"] as string;
		}
	}
}
