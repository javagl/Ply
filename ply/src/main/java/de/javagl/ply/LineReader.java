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
import java.util.Objects;

/**
 * Utility class for reading lines from an InputStream.
 * 
 * This will read the data from the input stream byte by byte, until a "line"
 * (indicated by a LF or CRLF) has been read.
 * 
 * This is mainly used for reading the header of PLY data, which may be followed
 * by ASCII- or binary PLY data.
 */
class LineReader
{
    /**
     * The InputStream to read from
     */
    private final InputStream inputStream;

    /**
     * Create a LineReader that reads from the given InputStream.
     * 
     * @param inputStream The InputStream to read from
     */
    LineReader(InputStream inputStream)
    {
        this.inputStream = Objects.requireNonNull(inputStream,
            "The inputStream may not be null");
    }

    /**
     * Read a single line from the underlying InputStream.
     * 
     * @return The line, or <code>null</code> if the end of the stream was
     *         reached.
     * @throws IOException If an IO error occurs
     */
    String readLine() throws IOException
    {
        StringBuffer sb = new StringBuffer();
        while (true)
        {
            int i = inputStream.read();
            if (i == -1)
            {
                if (sb.length() == 0)
                {
                    return null;
                }
                return sb.toString();
            }
            else if (i == '\r')
            {
                continue;
            }
            else if (i == '\n')
            {
                return sb.toString();
            }
            sb.append((char) i);
        }
    }

}