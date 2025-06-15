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

/**
 * An {@link ElementDescriptor} where properties can be added
 */
public interface MutableElementDescriptor extends ElementDescriptor
{
    /**
     * Add the specified property to this descriptor
     * 
     * @param name The name of the property
     * @param type The type of the property
     * @throws IllegalArgumentException If the type is not valid
     * @throws IllegalArgumentException If a property with the same name as the
     *         given one already exists
     */
    void addProperty(String name, PlyType type);

    /**
     * Add the specified property to this descriptor
     * 
     * @param name The name of the property
     * @param sizeType The size type
     * @param type The type of the property
     * @throws IllegalArgumentException If the type or the size type is not
     *         valid
     * @throws IllegalArgumentException If a property with the same name as the
     *         given one already exists
     */
    void addListProperty(String name, PlyType sizeType, PlyType type);

}