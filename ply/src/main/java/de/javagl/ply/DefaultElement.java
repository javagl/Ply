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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Default implementation of an {@link Element}
 */
class DefaultElement implements IndexedElement, MutableElement
{
    /**
     * The mapping from property names to indices
     */
    private final Map<String, Integer> propertyIndices;

    /**
     * The properties
     */
    private final Object[] properties;

    /**
     * Default constructor
     * 
     * @param propertyIndices The mapping from property names to indices
     */
    DefaultElement(Map<String, Integer> propertyIndices)
    {
        this.propertyIndices = Objects.requireNonNull(propertyIndices,
            "The propertyIndices may not be null");
        this.properties = new Object[propertyIndices.size()];
    }

    @Override
    public Byte getCharProperty(String name)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return null;
        }
        return getCharProperty(index);
    }

    @Override
    public Short getShortProperty(String name)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return null;
        }
        return getShortProperty(index);
    }

    @Override
    public Integer getIntProperty(String name)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return null;
        }
        return getIntProperty(index);
    }

    @Override
    public Float getFloatProperty(String name)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return null;
        }
        return getFloatProperty(index);
    }

    @Override
    public Double getDoubleProperty(String name)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return null;
        }
        return getDoubleProperty(index);
    }

    @Override
    public byte[] getCharListProperty(String name)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return null;
        }
        return getCharListProperty(index);
    }

    @Override
    public short[] getShortListProperty(String name)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return null;
        }
        return getShortListProperty(index);
    }

    @Override
    public int[] getIntListProperty(String name)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return null;
        }
        return getIntListProperty(index);
    }

    @Override
    public float[] getFloatListProperty(String name)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return null;
        }
        return getFloatListProperty(index);
    }

    @Override
    public double[] getDoubleListProperty(String name)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return null;
        }
        return getDoubleListProperty(index);
    }

    @Override
    public Byte getCharProperty(int index)
    {
        return (Byte) properties[index];
    }

    @Override
    public Short getShortProperty(int index)
    {
        return (Short) properties[index];
    }

    @Override
    public Integer getIntProperty(int index)
    {
        return (Integer) properties[index];
    }

    @Override
    public Float getFloatProperty(int index)
    {
        return (Float) properties[index];
    }

    @Override
    public Double getDoubleProperty(int index)
    {
        return (Double) properties[index];
    }

    @Override
    public byte[] getCharListProperty(int index)
    {
        return (byte[]) properties[index];
    }

    @Override
    public short[] getShortListProperty(int index)
    {
        return (short[]) properties[index];
    }

    @Override
    public int[] getIntListProperty(int index)
    {
        return (int[]) properties[index];
    }

    @Override
    public float[] getFloatListProperty(int index)
    {
        return (float[]) properties[index];
    }

    @Override
    public double[] getDoubleListProperty(int index)
    {
        return (double[]) properties[index];
    }

    @Override
    public void setCharProperty(String name, Byte value)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return;
        }
        setCharProperty(index, value);
    }

    @Override
    public void setShortProperty(String name, Short value)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return;
        }
        setShortProperty(index, value);
    }

    @Override
    public void setIntProperty(String name, Integer value)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return;
        }
        setIntProperty(index, value);
    }

    @Override
    public void setFloatProperty(String name, Float value)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return;
        }
        setFloatProperty(index, value);
    }

    @Override
    public void setDoubleProperty(String name, Double value)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return;
        }
        setDoubleProperty(index, value);
    }

    @Override
    public void setCharListProperty(String name, byte[] value)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return;
        }
        setCharListProperty(index, value);
    }

    @Override
    public void setShortListProperty(String name, short[] value)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return;
        }
        setShortListProperty(index, value);
    }

    @Override
    public void setIntListProperty(String name, int[] value)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return;
        }
        setIntListProperty(index, value);
    }

    @Override
    public void setFloatListProperty(String name, float[] value)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return;
        }
        setFloatListProperty(index, value);
    }

    @Override
    public void setDoubleListProperty(String name, double[] value)
    {
        Integer index = propertyIndices.get(name);
        if (index == null)
        {
            return;
        }
        setDoubleListProperty(index, value);
    }

    /**
     * Set the specified property
     * 
     * @param index The property index
     * @param value The value
     */
    void setCharProperty(int index, Byte value)
    {
        properties[index] = value;
    }

    /**
     * Set the specified property
     * 
     * @param index The property index
     * @param value The value
     */
    void setShortProperty(int index, Short value)
    {
        properties[index] = value;
    }

    /**
     * Set the specified property
     * 
     * @param index The property index
     * @param value The value
     */
    void setIntProperty(int index, Integer value)
    {
        properties[index] = value;
    }

    /**
     * Set the specified property
     * 
     * @param index The property index
     * @param value The value
     */
    void setFloatProperty(int index, Float value)
    {
        properties[index] = value;
    }

    /**
     * Set the specified property
     * 
     * @param index The property index
     * @param value The value
     */
    void setDoubleProperty(int index, Double value)
    {
        properties[index] = value;
    }

    /**
     * Set the specified property
     * 
     * @param index The property index
     * @param value The value
     */
    void setCharListProperty(int index, byte[] value)
    {
        properties[index] = value;
    }

    /**
     * Set the specified property
     * 
     * @param index The property index
     * @param value The value
     */
    void setShortListProperty(int index, short[] value)
    {
        properties[index] = value;
    }

    /**
     * Set the specified property
     * 
     * @param index The property index
     * @param value The value
     */
    void setIntListProperty(int index, int[] value)
    {
        properties[index] = value;
    }

    /**
     * Set the specified property
     * 
     * @param index The property index
     * @param value The value
     */
    void setFloatListProperty(int index, float[] value)
    {
        properties[index] = value;
    }

    /**
     * Set the specified property
     * 
     * @param index The property index
     * @param value The value
     */
    void setDoubleListProperty(int index, double[] value)
    {
        properties[index] = value;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Element[");
        boolean first = true;
        for (Entry<String, Integer> entry : propertyIndices.entrySet())
        {
            if (!first)
            {
                sb.append(", ");
            }
            first = false;
            String name = entry.getKey();
            Integer index = entry.getValue();
            Object value = properties[index];
            sb.append(name + "=" + Elements.valueToString(value));
        }
        sb.append("]");
        return sb.toString();
    }

}