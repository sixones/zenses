using System;
using System.Collections;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using System.Text;

using PortableDeviceApiLib;

using Zenses.Lib.Constants;
using Zenses.Lib.Objects;

namespace Zenses.Lib.Managers
{
	public class EntryManager
	{
		private PortableDeviceClass _hDeviceClass;
		private IPortableDeviceContent _hDeviceContent;
		private DeviceManager _hDeviceManager;
		private Device _hDevice;

		public EntryManager(Device device)
		{
			this._hDevice = device;
			this._hDeviceManager = new DeviceManager();
			this._hDeviceClass = this._hDeviceManager.GetDevice(device.Id);
		}

		public void BeginFetchContent()
		{
			this._hDeviceClass.Content(out this._hDeviceContent);

			this.EnumerateFetchContent("DEVICE", 0);
		}

		public delegate void FoundTrackEntryDelegate(EntryObject entryObject);
		public event FoundTrackEntryDelegate FoundNewMusicEntryEvent;

		public void EnumerateFetchContent(string parentId, int currentLevel)
		{
			EntryObject entryObject = new EntryObject();
			this.UpdateEntryProperties(parentId, ref entryObject);

			if (entryObject.Track < 0)
			{
				// playlist
			}

			if (entryObject.Level == 3)
			{
				// artist
			}

			if (entryObject.Track >= 0 || (entryObject.Filename != null && entryObject.Filename.EndsWith("zpl")))
			{
				// music entry object
				FoundNewMusicEntryEvent(entryObject);
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
					this.EnumerateFetchContent(objectId, currentLevel + 1);
				}

			} while (fetchedCount > 0);
		}

		public void UpdateEntryProperties(string objectId, ref EntryObject entry)
		{
			Hashtable properties = this._hDeviceManager.GetDeviceProperties(this._hDevice.Id, objectId);

			if (properties.Count != 0)
			{
				entry.Id = (string)properties[PropertyNames.OBJECT_ID];
				entry.PersistentId = (string)properties[PropertyNames.OBJECT_PERSISTENT_ID];
				entry.Name = (string)properties[PropertyNames.OBJECT_NAME];
				entry.Title = (string)properties[PropertyNames.OBJECT_TITLE];
				entry.Artist = (string)properties[PropertyNames.OBJECT_ARTIST];
				entry.Album = (string)properties[PropertyNames.OBJECT_ALBUM];

				if (properties[PropertyNames.OBJECT_FILENAME] != null)
				{
					entry.Filename = (string)properties[PropertyNames.OBJECT_FILENAME];
				}
				else
				{
					entry.Filename = "";
				}

				if (properties[PropertyNames.OBJECT_CONTENT_TYPE] != null)
				{
					entry.ContentType = (string)properties[PropertyNames.OBJECT_CONTENT_TYPE];
				}
				else
				{
					entry.ContentType = "";
				}

				if (properties[PropertyNames.OBJECT_DURATION] != null)
				{
					entry.Length = (long)(properties[PropertyNames.OBJECT_DURATION]);
				}
				else
				{
					entry.Length = 0;
				}

				if (properties[PropertyNames.OBJECT_FORMAT] != null)
				{
					entry.ObjectFormat = (string)properties[PropertyNames.OBJECT_FORMAT];
				}
				else
				{
					entry.ObjectFormat = "";
				}

				if (properties[PropertyNames.OBJECT_PLAY_COUNT] != null)
				{
					entry.PlayCount = (int)((uint)properties[PropertyNames.OBJECT_PLAY_COUNT]);
				}
				else
				{
					entry.PlayCount = 0;
				}

				if (properties[PropertyNames.OBJECT_TRACK] != null)
				{
					entry.Track = (int)((uint)properties[PropertyNames.OBJECT_TRACK]);
				}
				else
				{
					entry.Track = -1;
				}
			}
		}
	}
}
