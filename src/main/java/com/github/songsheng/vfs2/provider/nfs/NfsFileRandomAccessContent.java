/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.songsheng.vfs2.provider.nfs;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.provider.AbstractRandomAccessContent;
import org.apache.commons.vfs2.util.RandomAccessMode;

import com.sun.xfile.XFile;
import com.sun.xfile.XRandomAccessFile;

/**
 * RandomAccess for Nfs files
 */
class NfsFileRandomAccessContent extends AbstractRandomAccessContent {
	private final XRandomAccessFile xRandomAccessFile;
	private final InputStream inputStream;

	public NfsFileRandomAccessContent(final XFile NfsFile, final RandomAccessMode mode) throws FileSystemException {
		super(mode);
		try {
			xRandomAccessFile = new XRandomAccessFile(NfsFile, mode.getModeString());
			inputStream = new InputStream() {
				@Override
				public int available() throws IOException {
					final long available = xRandomAccessFile.length() - xRandomAccessFile.getFilePointer();
					if (available > Integer.MAX_VALUE) {
						return Integer.MAX_VALUE;
					}

					return (int) available;
				}

				@Override
				public void close() throws IOException {
					xRandomAccessFile.close();
				}

				@Override
				public int read() throws IOException {
					return xRandomAccessFile.readByte();
				}

				@Override
				public int read(final byte[] b) throws IOException {
					return xRandomAccessFile.read(b);
				}

				@Override
				public int read(final byte[] b, final int off, final int len) throws IOException {
					return xRandomAccessFile.read(b, off, len);
				}

				@Override
				public long skip(final long n) throws IOException {
					xRandomAccessFile.seek(xRandomAccessFile.getFilePointer() + n);
					return n;
				}
			};
		} catch (final IOException e) {
			throw new FileSystemException("vfs.provider/random-access-open-failed.error", NfsFile, e);
		}
	}

	@Override
	public void close() throws IOException {
		xRandomAccessFile.close();
	}

	@Override
	public long getFilePointer() throws IOException {
		return xRandomAccessFile.getFilePointer();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return inputStream;
	}

	@Override
	public long length() throws IOException {
		return xRandomAccessFile.length();
	}

	@Override
	public boolean readBoolean() throws IOException {
		return xRandomAccessFile.readBoolean();
	}

	@Override
	public byte readByte() throws IOException {
		return xRandomAccessFile.readByte();
	}

	@Override
	public char readChar() throws IOException {
		return xRandomAccessFile.readChar();
	}

	@Override
	public double readDouble() throws IOException {
		return xRandomAccessFile.readDouble();
	}

	@Override
	public float readFloat() throws IOException {
		return xRandomAccessFile.readFloat();
	}

	@Override
	public void readFully(final byte[] b) throws IOException {
		xRandomAccessFile.readFully(b);
	}

	@Override
	public void readFully(final byte[] b, final int off, final int len) throws IOException {
		xRandomAccessFile.readFully(b, off, len);
	}

	@Override
	public int readInt() throws IOException {
		return xRandomAccessFile.readInt();
	}

	@Override
	public long readLong() throws IOException {
		return xRandomAccessFile.readLong();
	}

	@Override
	public short readShort() throws IOException {
		return xRandomAccessFile.readShort();
	}

	@Override
	public int readUnsignedByte() throws IOException {
		return xRandomAccessFile.readUnsignedByte();
	}

	@Override
	public int readUnsignedShort() throws IOException {
		return xRandomAccessFile.readUnsignedShort();
	}

	@Override
	public String readUTF() throws IOException {
		return xRandomAccessFile.readUTF();
	}

	@Override
	public void seek(final long pos) throws IOException {
		xRandomAccessFile.seek(pos);
	}

	@Override
	public void setLength(final long newLength) throws IOException {
		throw new IOException("set length for nfs is not supported");
	}

	@Override
	public int skipBytes(final int n) throws IOException {
		return xRandomAccessFile.skipBytes(n);
	}

	@Override
	public void write(final byte[] b) throws IOException {
		xRandomAccessFile.write(b);
	}

	@Override
	public void write(final byte[] b, final int off, final int len) throws IOException {
		xRandomAccessFile.write(b, off, len);
	}

	@Override
	public void write(final int b) throws IOException {
		xRandomAccessFile.write(b);
	}

	@Override
	public void writeBoolean(final boolean v) throws IOException {
		xRandomAccessFile.writeBoolean(v);
	}

	@Override
	public void writeByte(final int v) throws IOException {
		xRandomAccessFile.writeByte(v);
	}

	@Override
	public void writeBytes(final String s) throws IOException {
		xRandomAccessFile.writeBytes(s);
	}

	@Override
	public void writeChar(final int v) throws IOException {
		xRandomAccessFile.writeChar(v);
	}

	@Override
	public void writeChars(final String s) throws IOException {
		xRandomAccessFile.writeChars(s);
	}

	@Override
	public void writeDouble(final double v) throws IOException {
		xRandomAccessFile.writeDouble(v);
	}

	@Override
	public void writeFloat(final float v) throws IOException {
		xRandomAccessFile.writeFloat(v);
	}

	@Override
	public void writeInt(final int v) throws IOException {
		xRandomAccessFile.writeInt(v);
	}

	@Override
	public void writeLong(final long v) throws IOException {
		xRandomAccessFile.writeLong(v);
	}

	@Override
	public void writeShort(final int v) throws IOException {
		xRandomAccessFile.writeShort(v);
	}

	@Override
	public void writeUTF(final String str) throws IOException {
		xRandomAccessFile.writeUTF(str);
	}

}
