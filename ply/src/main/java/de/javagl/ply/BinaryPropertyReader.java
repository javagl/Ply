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
import java.util.function.Function;

/**
 * Interface for classes that can read information about a single PLY property
 * from an input stream.
 * 
 * Instances of this class are created by the {@link BinaryPlyContentReader} via
 * method references, to read the property values of an {@link Element}.
 */
interface BinaryPropertyReader
{
    /**
     * Handle the specified property by reading the information from the given
     * input stream and passing the result to the given {@link PlyTarget}
     * 
     * @param inputStream The input stream
     * @param elementTypeIndex The element type index
     * @param elementIndex The element index
     * @param propertyIndex The property index
     * @param sizeReader A function for reading the size of lists
     * @param plyTarget The {@link PlyTarget}
     * @throws IOException if an IO error occurs
     */
    void read(InputStream inputStream, int elementTypeIndex, int elementIndex,
        int propertyIndex, Function<InputStream, Number> sizeReader,
        PlyTarget plyTarget) throws IOException;
}