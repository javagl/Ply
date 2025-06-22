/*
 * www.javagl.de - Ply
 *
 * Copyright (c) 2011-2025 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package de.javagl.ply;

import java.io.IOException;
import java.io.InputStream;

/**
 * IO utility methods
 */
class IO
{
    /**
     * Read the specified number of bytes into the given byte array
     * 
     * @param inputStream The input stream
     * @param target The target array
     * @param bytesToRead The number of bytes to read
     * @throws IOException If an IO error occurs
     */
    static void read(InputStream inputStream, byte target[], int bytesToRead)
        throws IOException
    {
        int bytesRead = 0;
        while (bytesRead < bytesToRead)
        {
            int read = inputStream.read(target, bytesRead, bytesToRead);
            if (read == -1)
            {
                throw new IOException("Unexpected end of input");
            }
            bytesRead += read;
        }
    }

    /**
     * Private constructor to prevent instantiation
     */
    private IO()
    {
        // Private constructor to prevent instantiation
    }

}
