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
 * Interface for an element of PLY data.
 * 
 * The methods in this interface may return references to the arrays that are
 * stored internally. Clients should be aware of possible side-effects when
 * modifying these arrays.
 * 
 * Implementations of this interface are not required to perform any type
 * checks. When requesting a property with the wrong type, implementations are
 * allowed to throw an unchecked exception (e.g. a {@link ClassCastException})
 */
public interface Element
{
    /**
     * Returns the specified property value, or <code>null</code> if this
     * element does not have this property.
     * 
     * @param name The name
     * @return The value
     */
    Byte getCharProperty(String name);

    /**
     * Returns the specified property value, or <code>null</code> if this
     * element does not have this property.
     * 
     * @param name The name
     * @return The value
     */
    Short getShortProperty(String name);

    /**
     * Returns the specified property value, or <code>null</code> if this
     * element does not have this property.
     * 
     * @param name The name
     * @return The value
     */
    Integer getIntProperty(String name);

    /**
     * Returns the specified property value, or <code>null</code> if this
     * element does not have this property.
     * 
     * @param name The name
     * @return The value
     */
    Float getFloatProperty(String name);

    /**
     * Returns the specified property value, or <code>null</code> if this
     * element does not have this property.
     * 
     * @param name The name
     * @return The value
     */
    Double getDoubleProperty(String name);

    /**
     * Returns the specified property value, or <code>null</code> if this
     * element does not have this property.
     * 
     * @param name The name
     * @return The value
     */
    byte[] getCharListProperty(String name);

    /**
     * Returns the specified property value, or <code>null</code> if this
     * element does not have this property.
     * 
     * @param name The name
     * @return The value
     */
    short[] getShortListProperty(String name);

    /**
     * Returns the specified property value, or <code>null</code> if this
     * element does not have this property.
     * 
     * @param name The name
     * @return The value
     */
    int[] getIntListProperty(String name);

    /**
     * Returns the specified property value, or <code>null</code> if this
     * element does not have this property.
     * 
     * @param name The name
     * @return The value
     */
    float[] getFloatListProperty(String name);

    /**
     * Returns the specified property value, or <code>null</code> if this
     * element does not have this property.
     * 
     * @param name The name
     * @return The value
     */
    double[] getDoubleListProperty(String name);

}