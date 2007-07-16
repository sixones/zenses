using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

using Zenses.Lib.Constants;
using Zenses.Lib.Managers;
using Zenses.Lib.Objects;

namespace Zenses.Lib.Collections
{
	public class TrackEntries : IEnumerable<EntryObject>, IEnumerable
	{
		private Device _hDevice;

		public TrackEntries(Device device)
		{
			this._hDevice = device;
		}

		public IEnumerator<EntryObject> GetEnumerator()
		{
			return new EntryObjectEnumerator(this._hDevice);
		}

		IEnumerator IEnumerable.GetEnumerator()
		{
			return new EntryObjectEnumerator(this._hDevice);
		}
	}
}
