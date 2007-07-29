using System;
using System.Collections.Generic;
using System.Text;

namespace Zenses.Lib.Objects
{
	public class PlayedTrack
	{
		private string _sTrackPersistentId;
		private DateTime _dDateSubmitted;
		private EntryObject _oEntryObject;

		public string TrackPersistentId
		{
			get { return this._sTrackPersistentId; }
			set { this._sTrackPersistentId = value; }
		}

		public DateTime DateSubmitted
		{
			get { return this._dDateSubmitted; }
			set { this._dDateSubmitted = value; }
		}

		public EntryObject EntryObject
		{
			get { return this._oEntryObject; }
			set { this._oEntryObject = value; }
		}
	}
}
