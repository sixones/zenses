using System;
using System.Collections.Generic;
using System.Text;

using Zenses.Lib.Objects;

namespace Zenses.Lib.Managers
{
	public class PlayedTrackManager
	{
		public List<PlayedTrack> GenerateScrobbleTimes(List<EntryObject> entries, DateTime startDateTime)
		{
			List<PlayedTrack> playedTracks = new List<PlayedTrack>();

			if (entries == null) return null;

			foreach (EntryObject entry in entries)
			{
				PlayedTrack playedTrack = new PlayedTrack();

				startDateTime = startDateTime.AddSeconds(5);
				playedTrack.DateSubmitted = startDateTime;

				playedTrack.EntryObject = entry;
				playedTrack.TrackPersistentId = entry.PersistentId;

				playedTracks.Add(playedTrack);

				startDateTime = startDateTime.AddSeconds(playedTrack.EntryObject.LengthSeconds);
			}

			return playedTracks;
		}

		public static void SubmitTrack(PlayedTrack playedTrack)
		{
			// submit the track to last.fm
		}
	}
}
