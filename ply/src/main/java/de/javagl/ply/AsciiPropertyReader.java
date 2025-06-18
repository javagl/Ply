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
import java.util.List;

/**
 * Interface for classes that can parse information about a single PLY property
 * from a list of tokens.
 * 
 * Instances of this class are created by the {@link AsciiPlyContentReader} via
 * method references, to read the property values of an {@link Element}.
 */
interface AsciiPropertyReader
{
    /**
     * Read the specified property by parsing the specified tokens and passing
     * the result to the given {@link PlyTarget}.
     * 
     * @param tokens The list of tokens
     * @param tokenIndex The token index where to start reading
     * @param elementTypeIndex The element type index
     * @param elementIndex The element index
     * @param propertyIndex The property index
     * @param plyTarget The {@link PlyTarget}
     * @return How many tokens have been processed
     * @throws IOException If an IO error occurs
     */
    int read(List<String> tokens, int tokenIndex, int elementTypeIndex,
        int elementIndex, int propertyIndex, PlyTarget plyTarget)
        throws IOException;
}