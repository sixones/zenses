using System;
using System.Runtime.InteropServices;

namespace Zenses.Lib
{
	[StructLayout(LayoutKind.Explicit, Size = 16)]
	public struct PropVariant
	{
		[FieldOffset(0)]
		public short variantType;

		[FieldOffset(8)]
		public IntPtr pointerValue;

		[FieldOffset(8)]
		public byte byteValue;

		[FieldOffset(8)]
		public long longValue;

		[FieldOffset(8)]
		public double dateValue;
	}
}
