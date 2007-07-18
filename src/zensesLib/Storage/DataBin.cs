using System;
using System.Collections;
using System.Collections.Generic;
using System.Data.Sql;
using System.Data.SqlClient;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;

using Zenses.Lib.Constants;

namespace Zenses.Lib.Storage
{
	public class DataBin
	{
		private Hashtable _tDataTable;
		private StoreType _eStoreType;
		private string _sDataLocation;

		// content_store
		// hashtable -->
		// - key: persidentId
		// - value: entryObject

		// history_store
		// hashtable -->
		// - key: persidentId
		// - value: struct (historyStruct)
		//			entryObject
		//			lastFmObject


		public DataBin(StoreType storeType)
		{
			this._eStoreType = storeType;

			// setup our storage location
			if (this._eStoreType == StoreType.DeviceContent) this._sDataLocation = FileLocations.DEVICE_CONTENT_FILENAME;
			else if (this._eStoreType == StoreType.PlayedHistory) this._sDataLocation = FileLocations.PLAYED_HISTORY_FILENAME;
		}

		public void Open()
		{
			FileStream fileStream = new FileStream(this._sDataLocation, FileMode.OpenOrCreate, FileAccess.Read);

			if (fileStream.Length != 0)
			{
				try
				{
					BinaryFormatter binaryFormatter = new BinaryFormatter();

					this._tDataTable = new Hashtable();
					this._tDataTable = binaryFormatter.Deserialize(fileStream);
				}
				finally
				{
					fileStream.Close();
				}
			}
		}

		public void Save()
		{
			FileStream fileStream = new FileStream(this._sDataLocation, FileMode.OpenOrCreate, FileAccess.Write);

			try
			{
				BinaryFormatter binaryFormatter = new BinaryFormatter();
				binaryFormatter.Serialize(fileStream, this._tDataTable);
			}
			finally
			{
				fileStream.Close();
			}
		}
	}
}
