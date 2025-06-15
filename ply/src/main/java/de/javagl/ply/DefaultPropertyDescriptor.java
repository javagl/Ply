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

import java.util.Objects;

/**
 * Default implementation of a {@link PropertyDescriptor}
 */
class DefaultPropertyDescriptor implements PropertyDescriptor
{
    /**
     * The name of the property
     */
    private final String name;

    /**
     * The type of the property
     */
    private final PlyType type;

    /**
     * The list size type, or <code>null</code> if this is no list property.
     */
    private final PlyType sizeType;

    /**
     * Creates a new property descriptor
     * 
     * @param name The name of the property
     * @param type The type of the property
     * @param sizeType The list size type. If this is <code>null</code>, then
     *        this property is NO list property.
     * @throws IllegalArgumentException If the type or the size type is not
     *         valid
     */
    DefaultPropertyDescriptor(String name, PlyType type, PlyType sizeType)
    {
        this.name = Objects.requireNonNull(name, "The name may not be null");
        this.type = Objects.requireNonNull(type, "The type may not be null");
        this.sizeType = sizeType;
        if (sizeType != null)
        {
            if (!PlyType.isValidSize(sizeType))
            {
                throw new IllegalArgumentException(
                    "The size type '" + sizeType + "' is not valid");
            }
        }
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public PlyType getType()
    {
        return type;
    }

    @Override
    public boolean isList()
    {
        return sizeType != null;
    }

    @Override
    public PlyType getSizeType()
    {
        return sizeType;
    }

    @Override
    public String toString()
    {
        if (isList())
        {
            return "Property[" + name + ", type=" + type + ", sizeType="
                + sizeType + "]";
        }
        return "Property[" + name + ", type=" + type + "]";
    }

}