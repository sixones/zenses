using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using System.Text;

using Zenses.Lib.Objects;
using Zenses.Lib.Storage;

namespace Zenses.Lib.DataAccessObjects
{
	public class PlayedTrackDAO
	{
		private Database _hDatabase;

		public PlayedTrackDAO()
		{
			this._hDatabase = new Database();
		}

		public List<PlayedTrack> FetchAll()
		{
			return this.FetchAll(null);
		}

		public List<PlayedTrack> FetchAll(string conditionStatement, params SQLiteParameter[] parameters)
		{
			DataSet dataSet;

			if (conditionStatement == null)
			{
				dataSet = this._hDatabase.Fetch("SELECT * FROM played_history INNER JOIN device_tracks ON track_persistent_id = persistent_id ORDER BY artist");
			}
			else
			{
				dataSet = this._hDatabase.Fetch("SELECT * FROM played_history INNER JOIN device_tracks ON track_persistent_id = persistent_id " + conditionStatement + " ORDER BY artist", parameters);
			}

			if (dataSet.Tables[0].Rows.Count > 0)
			{
				List<PlayedTrack> playedTracks = new List<PlayedTrack>();

				foreach (DataRow row in dataSet.Tables[0].Rows)
				{
					PlayedTrack playedTrack = new PlayedTrack();
					this.SetDatabaseFields(ref playedTrack, row);

					playedTracks.Add(playedTrack);
				}

				return playedTracks;
			}

			return null;
		}

		public void SaveObject(PlayedTrack playedTrack)
		{
			//if (!this.ObjectExists(entryObject.PersistentId))
			//{
				string sqlQuery = "INSERT INTO played_history ("
						+ "[track_persistent_id], "
						+ "[date_submitted] "
						+ ") VALUES ("
						+ "@persistentId, "
						+ "@dateSubmitted"
						+ ")";

				this._hDatabase.Execute(sqlQuery,
					new SQLiteParameter("@persistentId", playedTrack.TrackPersistentId),
					new SQLiteParameter("@dateSubmitted", playedTrack.DateSubmitted)
				);
			//}
			//else
			//{
			//    string sqlQuery = "UPDATE device_tracks SET ("
			//            + "[id] = @id, "
			//            + "[name] = @name, "
			//            + "[artist] = @artist, "
			//            + "[album] = @album, "
			//            + "[length] = @length, "
			//            + "[size] = @size, "
			//            + "[play_count] @playCount, "
			//            + "[filename] = @filename "
			//            + ") WHERE persistent_id = @persistentId";

			//    this._hDatabase.Execute(sqlQuery,
			//        new SQLiteParameter("@id", entryObject.Id),
			//        new SQLiteParameter("@persistentId", entryObject.PersistentId),
			//        new SQLiteParameter("@name", entryObject.Name),
			//        new SQLiteParameter("@artist", entryObject.Artist),
			//        new SQLiteParameter("@album", entryObject.Album),
			//        new SQLiteParameter("@length", entryObject.Length),
			//        new SQLiteParameter("@size", entryObject.Size),
			//        new SQLiteParameter("@playCount", entryObject.PlayCount),
			//        new SQLiteParameter("@filename", entryObject.Filename)
			//    );
			//}
		}

		public void SetDatabaseFields(ref PlayedTrack playedTrack, DataRow row)
		{
			playedTrack.TrackPersistentId = row["track_persistent_id"] as string;
			playedTrack.DateSubmitted = (DateTime)row["date_submitted"];

			playedTrack.EntryObject = new EntryObject();

			playedTrack.EntryObject.Id = row["id"] as string;
			playedTrack.EntryObject.PersistentId = row["persistent_id"] as string;
			playedTrack.EntryObject.Name = row["name"] as string;
			playedTrack.EntryObject.Artist = row["artist"] as string;
			playedTrack.EntryObject.Album = row["album"] as string;
			playedTrack.EntryObject.Length = (int)((long)row["length"]);
			playedTrack.EntryObject.Size = (int)((long)row["size"]);
			playedTrack.EntryObject.PlayCount = (int)((long)row["play_count"]);
			playedTrack.EntryObject.Filename = row["filename"] as string;
		}
	}
}
