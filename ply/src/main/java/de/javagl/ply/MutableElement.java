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
 * An {@link Element} where property values may be modified
 */
public interface MutableElement extends Element
{
    /**
     * Set the given value for the property with the given name
     * 
     * @param name The name of the property
     * @param value The value for the property
     */
    void setCharProperty(String name, Byte value);

    /**
     * Set the given value for the property with the given name
     * 
     * @param name The name of the property
     * @param value The value for the property
     */
    void setShortProperty(String name, Short value);

    /**
     * Set the given value for the property with the given name
     * 
     * @param name The name of the property
     * @param value The value for the property
     */
    void setIntProperty(String name, Integer value);

    /**
     * Set the given value for the property with the given name
     * 
     * @param name The name of the property
     * @param value The value for the property
     */
    void setFloatProperty(String name, Float value);

    /**
     * Set the given value for the property with the given name
     * 
     * @param name The name of the property
     * @param value The value for the property
     */
    void setDoubleProperty(String name, Double value);

    /**
     * Set the given value for the property with the given name
     * 
     * @param name The name of the property
     * @param value The value for the property
     */
    void setCharListProperty(String name, byte[] value);

    /**
     * Set the given value for the property with the given name
     * 
     * @param name The name of the property
     * @param value The value for the property
     */
    void setShortListProperty(String name, short[] value);

    /**
     * Set the given value for the property with the given name
     * 
     * @param name The name of the property
     * @param value The value for the property
     */
    void setIntListProperty(String name, int[] value);

    /**
     * Set the given value for the property with the given name
     * 
     * @param name The name of the property
     * @param value The value for the property
     */
    void setFloatListProperty(String name, float[] value);

    /**
     * Set the given value for the property with the given name
     * 
     * @param name The name of the property
     * @param value The value for the property
     */
    void setDoubleListProperty(String name, double[] value);
}
