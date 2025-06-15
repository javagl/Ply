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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Default implementation of a {@link MutableElement}
 */
class DefaultMutableElement implements MutableElement
{
    /**
     * The property values
     */
    private final Map<String, Object> propertyValues;

    /**
     * Default constructor
     */
    DefaultMutableElement()
    {
        this.propertyValues = new LinkedHashMap<String, Object>();
    }

    @Override
    public Byte getCharProperty(String name)
    {
        Object propertyValue = propertyValues.get(name);
        return (Byte) propertyValue;
    }

    @Override
    public Short getShortProperty(String name)
    {
        Object propertyValue = propertyValues.get(name);
        return (Short) propertyValue;
    }

    @Override
    public Integer getIntProperty(String name)
    {
        Object propertyValue = propertyValues.get(name);
        return (Integer) propertyValue;
    }

    @Override
    public Float getFloatProperty(String name)
    {
        Object propertyValue = propertyValues.get(name);
        return (Float) propertyValue;
    }

    @Override
    public Double getDoubleProperty(String name)
    {
        Object propertyValue = propertyValues.get(name);
        return (Double) propertyValue;
    }

    @Override
    public byte[] getCharListProperty(String name)
    {
        Object propertyValue = propertyValues.get(name);
        return (byte[]) propertyValue;
    }

    @Override
    public short[] getShortListProperty(String name)
    {
        Object propertyValue = propertyValues.get(name);
        return (short[]) propertyValue;
    }

    @Override
    public int[] getIntListProperty(String name)
    {
        Object propertyValue = propertyValues.get(name);
        return (int[]) propertyValue;
    }

    @Override
    public float[] getFloatListProperty(String name)
    {
        Object propertyValue = propertyValues.get(name);
        return (float[]) propertyValue;
    }

    @Override
    public double[] getDoubleListProperty(String name)
    {
        Object propertyValue = propertyValues.get(name);
        return (double[]) propertyValue;
    }

    @Override
    public void setCharProperty(String name, Byte value)
    {
        propertyValues.put(name, value);
    }

    @Override
    public void setShortProperty(String name, Short value)
    {
        propertyValues.put(name, value);
    }

    @Override
    public void setIntProperty(String name, Integer value)
    {
        propertyValues.put(name, value);
    }

    @Override
    public void setFloatProperty(String name, Float value)
    {
        propertyValues.put(name, value);
    }

    @Override
    public void setDoubleProperty(String name, Double value)
    {
        propertyValues.put(name, value);
    }

    @Override
    public void setCharListProperty(String name, byte[] value)
    {
        propertyValues.put(name, value);
    }

    @Override
    public void setShortListProperty(String name, short[] value)
    {
        propertyValues.put(name, value);
    }

    @Override
    public void setIntListProperty(String name, int[] value)
    {
        propertyValues.put(name, value);
    }

    @Override
    public void setFloatListProperty(String name, float[] value)
    {
        propertyValues.put(name, value);
    }

    @Override
    public void setDoubleListProperty(String name, double[] value)
    {
        propertyValues.put(name, value);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Element[");
        boolean first = true;
        for (Entry<String, Object> entry : propertyValues.entrySet())
        {
            if (!first)
            {
                sb.append(", ");
            }
            first = false;
            String name = entry.getKey();
            Object value = entry.getValue();
            sb.append(name + "=" + Elements.valueToString(value));
        }
        sb.append("]");
        return sb.toString();
    }
}
