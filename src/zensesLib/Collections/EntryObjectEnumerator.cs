using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

using PortableDeviceApiLib;

using Zenses.Lib.Constants;
using Zenses.Lib.Managers;
using Zenses.Lib.Objects;

namespace Zenses.Lib.Collections
{
	public class EntryObjectEnumerator : IEnumerator<EntryObject>
	{
		private IPortableDeviceContent _hDeviceContent;
		private EntryManager _hEntryManager;
		private EntryObject _oCurrentEntryObject;
		private string _sCurrentObjectId;
		private int _iCurrentLevel;
		private string _sCurrentParentId;
		private List<EntryObject> _lEntryObjects;
		private int _iLastPosition = 0;
		private bool _bUpdateAquired = false;

		public EntryObjectEnumerator(Device device)
		{
			this.Reset();

			this._hEntryManager = new EntryManager(device);
			PortableDeviceClass deviceClass = new PortableDeviceClass();
			deviceClass.Open(device.Id, DeviceManager.ClientValues);
			deviceClass.Content(out this._hDeviceContent);

			this._lEntryObjects = new List<EntryObject>();

			this.FindChild(this._sCurrentObjectId, this._iCurrentLevel);
		}

		public EntryObject Current
		{
			get
			{
				return this._oCurrentEntryObject;
			}
		}

		public void Dispose()
		{
			this.Reset();

			this._hEntryManager = null;
			this._hDeviceContent = null;
			this._oCurrentEntryObject = null;
		}

		object IEnumerator.Current
		{
			get
			{
				return this._oCurrentEntryObject as object;
			}
		}

		public bool MoveNext()
		{
			//try
			//{
			//    this.FindChild(this._sCurrentObjectId, this._iCurrentLevel);
			//}
			//catch (UnauthorizedAccessException)
			//{
			//    return true;
			//}

			while (this._lEntryObjects.Count < this._iLastPosition + 1) { }
			
			this._oCurrentEntryObject = this._lEntryObjects[this._iLastPosition];
			this._iLastPosition++;
			
			return true;
		}
		


		public void FindChild(string parentId, int currentLevel)
		{
			EntryObject entry = new EntryObject();
			this._hEntryManager.UpdateEntryProperties(parentId, ref entry);

			if (entry.Track >= 0 || (entry.Filename != null && entry.Filename.EndsWith("zpl")))
			{
				if (this._sCurrentObjectId != parentId)
				{
					// music object
					//this._oCurrentEntryObject = entry;
					this._sCurrentObjectId = parentId;
					this._iCurrentLevel = currentLevel;
					//throw new UnauthorizedAccessException();

					this._lEntryObjects.Add(entry);
				}
			}

			IEnumPortableDeviceObjectIDs deviceObjectIds;
			this._hDeviceContent.EnumObjects(0, parentId, null, out deviceObjectIds);

			uint fetchedCount = 0;

			do
			{
				string objectId;
				deviceObjectIds.Next(1, out objectId, ref fetchedCount);

				if (fetchedCount > 0)
				{
					this.FindChild(objectId, currentLevel + 1);
				}
			}
			while (fetchedCount > 0);
		}

		public void Reset()
		{
			this._iCurrentLevel = 0;
			this._sCurrentObjectId = "DEVICE";
		}
	}
}
