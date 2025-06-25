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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of an {@link ElementDescriptor}
 */
class DefaultElementDescriptor implements ElementDescriptor
{
    /**
     * THe name of the element
     */
    private final String name;

    /**
     * The {@link PropertyDescriptor}s for the properties of the element
     */
    private final List<PropertyDescriptor> propertyDescriptors;

    /**
     * Creates a new descriptor for an element of a PLY file
     * 
     * @param name The name of the element
     */
    DefaultElementDescriptor(String name)
    {
        this.name = Objects.requireNonNull(name, "The name may not be null");
        this.propertyDescriptors = new ArrayList<PropertyDescriptor>();
    }

    /**
     * Add the given {@link PropertyDescriptor} to this descriptor
     * 
     * @param propertyDescriptor The {@link PropertyDescriptor} to add
     * @throws IllegalArgumentException If a property with the same name as the
     *         given one already exists
     */
    private void addPropertyDescriptor(PropertyDescriptor propertyDescriptor)
    {
        for (int i = 0; i < propertyDescriptors.size(); i++)
        {
            PropertyDescriptor existing = propertyDescriptors.get(i);
            if (existing.getName().equals(propertyDescriptor.getName()))
            {
                throw new IllegalArgumentException("Property with name '"
                    + propertyDescriptor.getName() + "' already exists");
            }
        }
        propertyDescriptors.add(propertyDescriptor);
    }

    /**
     * Add the specified property
     * 
     * @param name The name
     * @param type The type
     * @throws IllegalArgumentException If a property with the given name was
     *         already added
     */
    void addProperty(String name, PlyType type)
    {
        addPropertyDescriptor(new DefaultPropertyDescriptor(name, type, null));
    }

    /**
     * Add the specified property
     * 
     * @param name The name
     * @param sizeType The size type
     * @param type The type
     * @throws IllegalArgumentException If a property with the given name was
     *         already added
     */
    void addListProperty(String name, PlyType sizeType, PlyType type)
    {
        addPropertyDescriptor(
            new DefaultPropertyDescriptor(name, type, sizeType));
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getPropertyName(int propertyIndex)
    {
        return propertyDescriptors.get(propertyIndex).getName();
    }

    @Override
    public PlyType getPropertyType(int propertyIndex)
    {
        return propertyDescriptors.get(propertyIndex).getType();
    }

    @Override
    public PlyType getPropertySizeType(int propertyIndex)
    {
        return propertyDescriptors.get(propertyIndex).getSizeType();
    }

    @Override
    public List<PropertyDescriptor> getPropertyDescriptors()
    {
        return Collections.unmodifiableList(propertyDescriptors);
    }

    @Override
    public String toString()
    {
        return "Element[" + name + ", " + propertyDescriptors + "]";
    }

}