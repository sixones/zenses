using System;
using System.Collections.Generic;
using System.Security.Cryptography;
using System.Text;

using Zenses.Lib.Objects;

namespace Zenses.Lib.Protocol
{
	public class Audioscrobbler
	{
		private string _sUsername;
		private string _sPassword;
		private string _sMD5Hash = "";

		public void Open()
		{
			// send hello request
			// look at response
			// its open :)
		}

		private void CalculateMD5Hash()
		{
			MD5 md5 = MD5CryptoServiceProvider.Create();
			UTF8Encoding encoding = new UTF8Encoding();

			byte[] buffer = encoding.GetBytes(this._sPassword);
			byte[] hash = md5.ComputeHash(buffer);

			for (int i = 0; i < hash.Length; i++)
			{
				this._sMD5Hash += hash[i].ToString("x2");
			}
		}

		public void SubmitSingleTrack(PlayedTrack playedTrack)
		{

		}
	}
}
