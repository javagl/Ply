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
import java.io.OutputStream;
import java.util.function.BiConsumer;

/**
 * Interface for classes that can write information about a single PLY property.
 * 
 * Instances of this class are created by the {@link BinaryPlyWriter} via method
 * references, to write the property values of an {@link Element}.
 */
interface BinaryPropertyWriter
{
    /**
     * Write the specified property value into the given output stream.
     * 
     * @param element The element
     * @param propertyName The name of the property
     * @param sizeWriter A consumer that writes a size into the output stream
     *        for list properties
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    void write(Element element, String propertyName,
        BiConsumer<Integer, OutputStream> sizeWriter, OutputStream outputStream)
        throws IOException;
}