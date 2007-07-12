using System;
using System.Collections.Generic;
using System.Text;

namespace Zenses.Lib.Objects
{
	public class EntryObject
	{
		private string _sId;
		private string _sPersistentId;
		private int _iLevel;
		private string _sTitle;
		private string _sArtist;
		private string _sAlbum;
		private int _iTrack;
		private int _iPlayCount;
		private int _iSize;
		private string _sName;
		private string _sFilename;
		private int _iLength;
		private string _sContentType;
		private string _sObjectFormat;

		public string Id
		{
			get { return this._sId; }
			set { this._sId = value; }
		}

		public string PersistentId
		{
			get { return this._sPersistentId; }
			set { this._sPersistentId = value; }
		}

		public int Level
		{
			get { return this._iLevel; }
			set { this._iLevel = value; }
		}

		public string Title
		{
			get { return this._sTitle; }
			set { this._sTitle = value; }
		}

		public string Artist
		{
			get { return this._sArtist; }
			set { this._sArtist = value; }
		}

		public string Album
		{
			get { return this._sAlbum; }
			set { this._sAlbum = value; }
		}

		public int Track
		{
			get { return this._iTrack; }
			set { this._iTrack = value; }
		}

		public int PlayCount
		{
			get { return this._iPlayCount; }
			set { this._iPlayCount = value; }
		}

		public int Size
		{
			get { return this._iSize; }
			set { this._iSize = value; }
		}

		public string Name
		{
			get { return this._sName; }
			set { this._sName = value; }
		}

		public string Filename
		{
			get { return this._sFilename; }
			set { this._sFilename = value; }
		}

		public int Length
		{
			get { return this._iLength; }
			set { this._iLength = value; }
		}

		public string ContentType
		{
			get { return this._sContentType; }
			set { this._sContentType = value; }
		}

		public string ObjectFormat
		{
			get { return this._sObjectFormat; }
			set { this._sObjectFormat = value; }
		}
	}
}
