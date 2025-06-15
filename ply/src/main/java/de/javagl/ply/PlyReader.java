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
 * Interface for classes that can read PLY data
 */
public interface PlyReader
{
    /**
     * Read the data from the given input stream and return it as a
     * {@link PlySource}
     * 
     * The caller is responsible for closing the given stream.
     * 
     * @param inputStream The input stream
     * @return The resulting data as a {@link PlySource}
     * @throws IOException If an IO error occurs
     */
    PlySource read(InputStream inputStream) throws IOException;

    /**
     * Read the {@link Descriptor} from the given input stream.
     * 
     * This will read the stream, byte by byte, until the header is complete and
     * the {@link Descriptor} can be returned.
     * 
     * The caller will usually not close the input stream after this method
     * returns. Instead, the caller will pass the remaining stream to
     * {@link #readContent(InputStream, PlyTarget)} and close the input stream
     * afterwards.
     * 
     * @param inputStream The input stream
     * @return The {@link Descriptor}
     * @throws IOException If an IO error occurs
     */
    Descriptor readDescriptor(InputStream inputStream) throws IOException;

    /**
     * Read the main content from the given input stream and pass it to the
     * given {@link PlyTarget}.
     * 
     * This assumes that the {@link Descriptor} was already read from the given
     * input stream, and the stream only contains the remaining data.
     * 
     * The caller is responsible for closing the given stream.
     * 
     * @param inputStream The input stream
     * @param plyTarget The {@link PlyTarget}
     * @throws IOException If an IO error occurs
     */
    void readContent(InputStream inputStream, PlyTarget plyTarget)
        throws IOException;

}
