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
 * Methods related to {@link ElementDescriptor} instances
 */
class ElementDescriptors
{
    /**
     * Compute a mapping from property names to the indices that the properties
     * have in the given {@link ElementDescriptor}
     * 
     * @param elementDescriptor The {@link ElementDescriptor}
     * @return The indices
     */
    static Map<String, Integer>
        computePropertyIndices(ElementDescriptor elementDescriptor)
    {
        Map<String, Integer> propertyIndices =
            new LinkedHashMap<String, Integer>();
        List<PropertyDescriptor> propertyDescriptors =
            elementDescriptor.getPropertyDescriptors();
        for (int p = 0; p < propertyDescriptors.size(); p++)
        {
            PropertyDescriptor propertyDescriptor = propertyDescriptors.get(p);
            String name = propertyDescriptor.getName();
            propertyIndices.put(name, p);
        }
        return propertyIndices;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private ElementDescriptors()
    {
        // Private constructor to prevent instantiation
    }

}