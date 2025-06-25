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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Methods to create {@link Descriptor} instances
 */
public class Descriptors
{
    /**
     * Creates a new {@link MutableDescriptor}
     * 
     * @return The {@link MutableDescriptor}
     */
    public static MutableDescriptor create()
    {
        return new DefaultDescriptor();
    }

    /**
     * Compute a mapping from element names to the indices that they have in the
     * given {@link Descriptor}
     * 
     * @param descriptor The {@link Descriptor}
     * @return The indices
     */
    static Map<String, Integer> computeElementTypeIndices(Descriptor descriptor)
    {
        Map<String, Integer> elementTypeIndices =
            new LinkedHashMap<String, Integer>();
        List<ElementDescriptor> elementDescriptors =
            descriptor.getElementDescriptors();
        for (int e = 0; e < elementDescriptors.size(); e++)
        {
            ElementDescriptor elementDescriptor = elementDescriptors.get(e);
            String name = elementDescriptor.getName();
            elementTypeIndices.put(name, e);
        }
        return elementTypeIndices;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Descriptors()
    {
        // Private constructor to prevent instantiation
    }

}