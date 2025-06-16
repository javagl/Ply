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
    private final List<ElementDescriptor> elementDescriptors;

    /**
     * Default constructor
     */
    DefaultDescriptor()
    {
        this.comments = new ArrayList<String>();
        this.elementDescriptors = new ArrayList<ElementDescriptor>();
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

    @Override
    public void addElementDescriptor(ElementDescriptor elementDescriptor)
    {
        for (int i = 0; i < elementDescriptors.size(); i++)
        {
            ElementDescriptor existing = elementDescriptors.get(i);
            if (existing.getName().equals(elementDescriptor.getName()))
            {
                throw new IllegalArgumentException("ELement with name '"
                    + elementDescriptor.getName() + "' already exists");
            }
        }
        elementDescriptors.add(elementDescriptor);
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

}