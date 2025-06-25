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
 * Default implementation of a {@link Descriptor}
 */
class DefaultDescriptor implements MutableDescriptor
{
    /**
     * The comments that appeared in the PLY file
     */
    private final List<String> comments;

    /**
     * The {@link ElementDescriptor} objects, one for each element defined in
     * the PLY file
     */
    private final List<DefaultElementDescriptor> elementDescriptors;

    /**
     * Default constructor
     */
    DefaultDescriptor()
    {
        this.comments = new ArrayList<String>();
        this.elementDescriptors = new ArrayList<DefaultElementDescriptor>();
    }

    @Override
    public void addComment(String comment)
    {
        Objects.requireNonNull(comment, "The comment may not be null");
        comments.add(comment);
    }

    @Override
    public List<String> getComments()
    {
        return Collections.unmodifiableList(comments);
    }

    /**
     * Add the given {@link ElementDescriptor}
     * 
     * @param elementDescriptor The {@link ElementDescriptor}
     * @throws IllegalArgumentException If a descriptor for the elements with
     *         the same name as the given one was already added
     */
    void addElementDescriptor(DefaultElementDescriptor elementDescriptor)
    {
        ElementDescriptor existing = find(elementDescriptor.getName());
        if (existing != null)
        {
            throw new IllegalArgumentException("Element with name '"
                + elementDescriptor.getName() + "' already exists");
        }
        elementDescriptors.add(elementDescriptor);
    }

    @Override
    public String getElementName(int elementTypeIndex)
    {
        return elementDescriptors.get(elementTypeIndex).getName();
    }

    @Override
    public String getPropertyName(int elementTypeIndex, int propertyIndex)
    {
        ElementDescriptor elementDescriptor =
            elementDescriptors.get(elementTypeIndex);
        return elementDescriptor.getPropertyName(propertyIndex);
    }

    @Override
    public PlyType getPropertyType(int elementTypeIndex, int propertyIndex)
    {
        ElementDescriptor elementDescriptor =
            elementDescriptors.get(elementTypeIndex);
        return elementDescriptor.getPropertyType(propertyIndex);
    }

    @Override
    public PlyType getPropertySizeType(int elementTypeIndex, int propertyIndex)
    {
        ElementDescriptor elementDescriptor =
            elementDescriptors.get(elementTypeIndex);
        return elementDescriptor.getPropertySizeType(propertyIndex);
    }

    @Override
    public List<ElementDescriptor> getElementDescriptors()
    {
        return Collections.unmodifiableList(elementDescriptors);
    }

    @Override
    public String toString()
    {
        return "Descriptor[" + elementDescriptors + "]";
    }

    /**
     * Find the {@link ElementDescriptor} for the given name, and return
     * <code>null</code> if it does not exist yet.
     * 
     * @param elementName The element name
     * @return The {@link ElementDescriptor}
     */
    private DefaultElementDescriptor find(String elementName)
    {
        for (int i = 0; i < elementDescriptors.size(); i++)
        {
            DefaultElementDescriptor elementDescriptor =
                elementDescriptors.get(i);
            if (elementDescriptor.getName().equals(elementName))
            {
                return elementDescriptor;
            }
        }
        return null;
    }

    /**
     * Obtain the {@link ElementDescriptor} for the given name, creating it if
     * it does not exist yet.
     * 
     * @param elementName The element name
     * @return The {@link ElementDescriptor}
     */
    private DefaultElementDescriptor obtain(String elementName)
    {
        DefaultElementDescriptor elementDescriptor = find(elementName);
        if (elementDescriptor == null)
        {
            elementDescriptor = new DefaultElementDescriptor(elementName);
            elementDescriptors.add(elementDescriptor);
        }
        return elementDescriptor;
    }

    @Override
    public void addProperty(String elementName, String propertyName,
        PlyType propertyType)
    {
        DefaultElementDescriptor elementDescriptor = obtain(elementName);
        elementDescriptor.addProperty(propertyName, propertyType);
    }

    @Override
    public void addListProperty(String elementName, String propertyName,
        PlyType propertySizeType, PlyType propertyType)
    {
        DefaultElementDescriptor elementDescriptor = obtain(elementName);
        elementDescriptor.addListProperty(propertyName, propertySizeType,
            propertyType);
    }

}