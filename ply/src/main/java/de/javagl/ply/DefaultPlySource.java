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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Default implementation of a {@link MutablePlySource}
 */
class DefaultPlySource implements MutablePlySource
{
    /**
     * The {@link Descriptor}
     */
    private Descriptor descriptor;

    /**
     * The list of lists of elements for this name
     */
    private final List<List<Element>> elementLists;

    /**
     * The mapping from names to element type indices
     */
    private final Map<String, Integer> elementTypeIndices;

    /**
     * Default constructor
     * 
     * @param descriptor The {@link Descriptor}
     */
    DefaultPlySource(Descriptor descriptor)
    {
        this.descriptor = Objects.requireNonNull(descriptor,
            "The descriptor may not be null");
        this.elementTypeIndices =
            Descriptors.computeElementTypeIndices(descriptor);

        this.elementLists = new ArrayList<List<Element>>();
        int n = descriptor.getElementDescriptors().size();
        for (int i = 0; i < n; i++)
        {
            this.elementLists.add(null);
        }
    }

    @Override
    public void addElement(int elementTypeIndex, Element element)
    {
        Objects.requireNonNull(element, "The element may not be null");
        List<Element> elementList = elementLists.get(elementTypeIndex);
        if (elementList == null)
        {
            elementList = new ArrayList<Element>();
            elementLists.set(elementTypeIndex, elementList);
        }
        elementList.add(element);
    }

    @Override
    public void addElement(String elementName, Element element)
    {
        int elementTypeIndex = elementTypeIndices.get(elementName);
        addElement(elementTypeIndex, element);
    }

    @Override
    public void addElements(int elementTypeIndex,
        Collection<? extends Element> elements)
    {
        Objects.requireNonNull(elements, "The elements may not be null");

        List<Element> elementList = elementLists.get(elementTypeIndex);
        if (elementList == null)
        {
            elementList = new ArrayList<Element>();
            elementLists.set(elementTypeIndex, elementList);
        }
        elementList.addAll(elements);
    }

    @Override
    public void addElements(String elementName,
        Collection<? extends Element> elements)
    {
        int elementTypeIndex = elementTypeIndices.get(elementName);
        addElements(elementTypeIndex, elements);
    }

    @Override
    public Descriptor getDescriptor()
    {
        return descriptor;
    }

    @Override
    public List<Element> getElementList(String elementName)
    {
        int elementTypeIndex = elementTypeIndices.get(elementName);
        return getElementList(elementTypeIndex);
    }

    @Override
    public List<Element> getElementList(int elementTypeIndex)
    {
        List<Element> list = elementLists.get(elementTypeIndex);
        if (list == null)
        {
            return null;
        }
        return Collections.unmodifiableList(list);
    }

}