using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

using Zenses.Lib.Constants;

namespace Zenses.Lib.Managers
{
	public class PropertyManager
	{
		public static string GetKeyName(uint pid, Guid fmtid)
		{
			Hashtable knownKeys = new Hashtable();
			knownKeys.Add(PropertyNames.OBJECT_PLAY_COUNT, PropertyKeys.WPD_MEDIA_USE_COUNT);
			knownKeys.Add(PropertyNames.OBJECT_GENRE, PropertyKeys.WPD_MEDIA_GENRE);
			knownKeys.Add(PropertyNames.OBJECT_STAR_RATING, PropertyKeys.WPD_MEDIA_STAR_RATING);
			knownKeys.Add(PropertyNames.OBJECT_ARTIST, PropertyKeys.WPD_MEDIA_ARTIST);
			knownKeys.Add(PropertyNames.OBJECT_ALBUM_ARTIST, PropertyKeys.WPD_MEDIA_ALBUM_ARTIST);
			knownKeys.Add(PropertyNames.OBJECT_SUBSCRIPTION_CONTENT_ID, PropertyKeys.WPD_MEDIA_SUBSCRIPTION_CONTENT_ID);
			knownKeys.Add(PropertyNames.OBJECT_EFFECTIVE_RATING, PropertyKeys.WPD_MEDIA_USER_EFFECTIVE_RATING);
			knownKeys.Add(PropertyNames.OBJECT_ALBUM, PropertyKeys.WPD_MUSIC_ALBUM);
			knownKeys.Add(PropertyNames.OBJECT_TRACK, PropertyKeys.WPD_MUSIC_TRACK);
			knownKeys.Add(PropertyNames.OBJECT_ID, PropertyKeys.WPD_OBJECT_ID);
			knownKeys.Add(PropertyNames.OBJECT_SIZE, PropertyKeys.WPD_OBJECT_SIZE);
			knownKeys.Add(PropertyNames.OBJECT_DURATION, PropertyKeys.WPD_MEDIA_DURATION);
			knownKeys.Add(PropertyNames.OBJECT_TITLE, PropertyKeys.WPD_MEDIA_TITLE);
			knownKeys.Add(PropertyNames.OBJECT_DATE_AUTHORED, PropertyKeys.WPD_OBJECT_DATE_AUTHORED);
			knownKeys.Add(PropertyNames.OBJECT_DATE_CREATED, PropertyKeys.WPD_OBJECT_DATE_CREATED);
			knownKeys.Add(PropertyNames.OBJECT_MODIFIED, PropertyKeys.WPD_OBJECT_DATE_MODIFIED);
			knownKeys.Add(PropertyNames.OBJECT_FORMAT, PropertyKeys.WPD_OBJECT_FORMAT);
			knownKeys.Add(PropertyNames.OBJECT_DRM_PROTECTED, PropertyKeys.WPD_OBJECT_IS_DRM_PROTECTED);
			knownKeys.Add(PropertyNames.OBJECT_FILENAME, PropertyKeys.WPD_OBJECT_ORIGINAL_FILE_NAME);
			knownKeys.Add(PropertyNames.OBJECT_NAME, PropertyKeys.WPD_OBJECT_NAME);
			knownKeys.Add(PropertyNames.OBJECT_PERSISTENT_ID, PropertyKeys.WPD_OBJECT_PERSISTENT_UNIQUE_ID);
			knownKeys.Add(PropertyNames.OBJECT_TOTAL_BITRATE, PropertyKeys.WPD_MEDIA_TOTAL_BITRATE);
			knownKeys.Add(PropertyNames.OBJECT_SKIP_COUNT, PropertyKeys.WPD_MEDIA_SKIP_COUNT);
			knownKeys.Add(PropertyNames.OBJECT_SAMPLE_RATE, PropertyKeys.WPD_MEDIA_SAMPLE_RATE);
			knownKeys.Add(PropertyNames.OBJECT_RELEASE_DATE, PropertyKeys.WPD_MEDIA_RELEASE_DATE);
			knownKeys.Add(PropertyNames.OBJECT_LAST_BUILD_DATE, PropertyKeys.WPD_MEDIA_LAST_BUILD_DATE);
			knownKeys.Add(PropertyNames.OBJECT_LAST_ACCESSED_TIME, PropertyKeys.WPD_MEDIA_LAST_ACCESSED_TIME);
			knownKeys.Add(PropertyNames.OBJECT_BITRATE_TYPE, PropertyKeys.WPD_MEDIA_BITRATE_TYPE);
			knownKeys.Add(PropertyNames.OBJECT_ALBUM_ART, PropertyKeys.WPD_RESOURCE_ALBUM_ART);
			knownKeys.Add(PropertyNames.DEVICE_FIRMWARE_VERSION, PropertyKeys.WPD_DEVICE_FIRMWARE_VERSION);
			knownKeys.Add(PropertyNames.DEVICE_FRIENDLY_NAME, PropertyKeys.WPD_DEVICE_FRIENDLY_NAME);
			knownKeys.Add(PropertyNames.DEVICE_MANUFACTURER, PropertyKeys.WPD_DEVICE_MANUFACTURER);
			knownKeys.Add(PropertyNames.DEVICE_MODEL, PropertyKeys.WPD_DEVICE_MODEL);
			knownKeys.Add(PropertyNames.DEVICE_BATTERY_LEVEL, PropertyKeys.WPD_DEVICE_POWER_LEVEL);
			knownKeys.Add(PropertyNames.DEVICE_POWER_SOURCE, PropertyKeys.WPD_DEVICE_POWER_SOURCE);
			knownKeys.Add(PropertyNames.DEVICE_SERIAL_NUMBER, PropertyKeys.WPD_DEVICE_SERIAL_NUMBER);
			knownKeys.Add(PropertyNames.OBJECT_CONTENT_TYPE, PropertyKeys.WPD_OBJECT_CONTENT_TYPE);

			foreach (DictionaryEntry entry in knownKeys)
			{
				PortableDeviceApiLib._tagpropertykey propertyKey = (PortableDeviceApiLib._tagpropertykey)entry.Value;

				if (propertyKey.pid == pid && propertyKey.fmtid == fmtid)
				{
					return (string)entry.Key;
				}
			}

			return pid.ToString() + " " + fmtid.ToString();
		}
	}
}