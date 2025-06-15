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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Default implementation of a {@link MutablePlySource}
 */
class DefaultPly implements MutablePlySource
{
    /**
     * The {@link Descriptor}
     */
    private Descriptor descriptor;

    /**
     * The mapping from element names to lists of elements for this name
     */
    private final Map<String, List<Element>> elementLists;

    /**
     * Default constructor
     * 
     * @param descriptor The {@link Descriptor}
     */
    DefaultPly(Descriptor descriptor)
    {
        this.descriptor = Objects.requireNonNull(descriptor,
            "The descriptor may not be null");
        this.elementLists = new LinkedHashMap<String, List<Element>>();
    }

    @Override
    public void addElement(String elementName, Element element)
    {
        Objects.requireNonNull(elementName, "The elementName may not be null");
        Objects.requireNonNull(element, "The element may not be null");

        List<Element> elementList = elementLists.get(elementName);
        if (elementList == null)
        {
            elementList = new ArrayList<Element>();
            elementLists.put(elementName, elementList);
        }
        elementList.add(element);
    }

    @Override
    public void addElements(String elementName,
        Collection<? extends Element> elements)
    {
        Objects.requireNonNull(elementName, "The elementName may not be null");
        Objects.requireNonNull(elements, "The elements may not be null");

        List<Element> elementList = elementLists.get(elementName);
        if (elementList == null)
        {
            elementList = new ArrayList<Element>();
            elementLists.put(elementName, elementList);
        }
        elementList.addAll(elements);
    }

    @Override
    public Descriptor getDescriptor()
    {
        return descriptor;
    }

    @Override
    public List<Element> getElementList(String elementName)
    {
        List<Element> list = elementLists.get(elementName);
        if (list == null)
        {
            return null;
        }
        return Collections.unmodifiableList(list);
    }

}