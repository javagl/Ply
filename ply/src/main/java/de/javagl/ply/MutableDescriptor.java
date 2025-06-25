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
 * A {@link Descriptor} where {@link ElementDescriptor} objects can be added.
 */
public interface MutableDescriptor extends Descriptor
{
    /**
     * Add the given comment to be part of the PLY file
     * 
     * @param comment The comment
     */
    void addComment(String comment);

    /**
     * Add the specified property to this descriptor.
     * 
     * This will automatically add the element descriptor for the specified
     * element name if necessary.
     * 
     * @param elementName The element name
     * @param propertyName The property name
     * @param propertyType The property type
     * @throws IllegalArgumentException If a property with the given name was
     *         already added for the specified element
     */
    void addProperty(String elementName, String propertyName,
        PlyType propertyType);

    /**
     * Add the specified list property to this descriptor.
     * 
     * This will automatically add the element descriptor for the specified
     * element name if necessary.
     * 
     * @param elementName The element name
     * @param propertyName The property name
     * @param propertySizeType The property size type
     * @param propertyType The property type
     * @throws IllegalArgumentException If a property with the given name was
     *         already added for the specified element, or if the size type is
     *         not an integer type
     */
    void addListProperty(String elementName, String propertyName,
        PlyType propertySizeType, PlyType propertyType);
}