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

import java.util.AbstractList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.Function;

/**
 * Implementation of a {@link PlySource} that can be backed by list of objects
 */
public final class ObjectPlySource implements PlySource
{
    /**
     * The {@link Descriptor}
     */
    private final Descriptor descriptor;

    /**
     * The {@link Handle} instances for each element type
     */
    private final Map<String, Handle<?>> elementHandles;

    /**
     * Creates a new instance with the given {@link Descriptor}
     * 
     * @param descriptor The {@link Descriptor}
     */
    public ObjectPlySource(Descriptor descriptor)
    {
        this.descriptor = Objects.requireNonNull(descriptor,
            "The descriptor may not be null");
        this.elementHandles = new LinkedHashMap<String, Handle<?>>();
    }

    @Override
    public Descriptor getDescriptor()
    {
        return descriptor;
    }

    /**
     * Register a new element type to be provided by this instance.
     * 
     * This will return a {@link Handle} that allows further configuration about
     * how to provide the specific element type. The handle offers methods to
     * configure how each property is supposed to be provided, using the
     * <code>handle.with...</code> functions.
     * 
     * @param <T> The element type
     * @param elementName The element name
     * @param objects The objects
     * @return The {@link Handle}
     */
    public <T> Handle<T> register(String elementName, List<T> objects)
    {
        Handle<?> existingElementHandle = elementHandles.get(elementName);
        if (existingElementHandle != null)
        {
            throw new IllegalStateException(
                "Element type '" + elementName + "' was already registered");
        }
        Objects.requireNonNull(objects, "The objects may not be null");

        Handle<T> elementHandle = new Handle<T>(objects);
        @SuppressWarnings("unchecked")
        Handle<Object> internalElementHandle = (Handle<Object>) elementHandle;
        elementHandles.put(elementName, internalElementHandle);
        return elementHandle;
    }

    /**
     * The handle that is returned from
     * {@link ObjectPlySource#register(String, List)} and that allows
     * configuring how the objects are converted into elements.
     *
     * @param <T> The object type
     */
    public static final class Handle<T>
    {
        /**
         * The objects
         */
        private final List<T> objects;

        /**
         * The mapping from property names to the providers for the property
         * values.
         */
        private final Map<String, Function<T, ?>> propertyProviders;

        /**
         * Creates a new instance with the given objects
         * 
         * @param objects The objects
         */
        Handle(List<T> objects)
        {
            this.objects = objects;
            this.propertyProviders =
                new LinkedHashMap<String, Function<T, ?>>();
        }

        /**
         * Provide the specified property with the given getter.
         * 
         * @param <U> The value type
         * @param propertyName The property name
         * @param getter The getter
         * @return This handle
         */
        // Note: The 'extends' part is necessary for some type safety.
        @SuppressWarnings("all")
        public <U extends Byte> Handle<T> withByte(String propertyName,
            Function<T, U> getter)
        {
            this.propertyProviders.put(propertyName, getter);
            return this;
        }

        /**
         * Provide the specified property with the given getter.
         * 
         * @param <U> The value type
         * @param propertyName The property name
         * @param getter The getter
         * @return This handle
         */
        // Note: The 'extends' part is necessary for some type safety.
        @SuppressWarnings("all")
        public <U extends Short> Handle<T> withShort(String propertyName,
            Function<T, U> getter)
        {
            this.propertyProviders.put(propertyName, getter);
            return this;
        }

        /**
         * Provide the specified property with the given getter.
         * 
         * @param <U> The value type
         * @param propertyName The property name
         * @param getter The getter
         * @return This handle
         */
        // Note: The 'extends' part is necessary for some type safety.
        @SuppressWarnings("all")
        public <U extends Integer> Handle<T> withInt(String propertyName,
            Function<T, U> getter)
        {
            this.propertyProviders.put(propertyName, getter);
            return this;
        }

        /**
         * Provide the specified property with the given getter.
         * 
         * @param <U> The value type
         * @param propertyName The property name
         * @param getter The getter
         * @return This handle
         */
        // Note: The 'extends' part is necessary for some type safety.
        @SuppressWarnings("all")
        public <U extends Float> Handle<T> withFloat(String propertyName,
            Function<T, U> getter)
        {
            this.propertyProviders.put(propertyName, getter);
            return this;
        }

        /**
         * Provide the specified property with the given getter.
         * 
         * @param <U> The value type
         * @param propertyName The property name
         * @param getter The getter
         * @return This handle
         */
        // Note: The 'extends' part is necessary for some type safety.
        @SuppressWarnings("all")
        public <U extends Double> Handle<T> withDouble(String propertyName,
            Function<T, U> getter)
        {
            this.propertyProviders.put(propertyName, getter);
            return this;
        }

        /**
         * Provide the specified property with the given getter.
         * 
         * @param propertyName The property name
         * @param getter The getter
         * @return This handle
         */
        public Handle<T> withByteList(String propertyName,
            Function<T, byte[]> getter)
        {
            this.propertyProviders.put(propertyName, getter);
            return this;
        }

        /**
         * Provide the specified property with the given getter.
         * 
         * @param propertyName The property name
         * @param getter The getter
         * @return This handle
         */
        public Handle<T> withShortList(String propertyName,
            Function<T, short[]> getter)
        {
            this.propertyProviders.put(propertyName, getter);
            return this;
        }

        /**
         * Provide the specified property with the given getter.
         * 
         * @param propertyName The property name
         * @param getter The getter
         * @return This handle
         */
        public Handle<T> withIntList(String propertyName,
            Function<T, int[]> getter)
        {
            this.propertyProviders.put(propertyName, getter);
            return this;
        }

        /**
         * Provide the specified property with the given getter.
         * 
         * @param propertyName The property name
         * @param getter The getter
         * @return This handle
         */
        public Handle<T> withFloatList(String propertyName,
            Function<T, float[]> getter)
        {
            this.propertyProviders.put(propertyName, getter);
            return this;
        }

        /**
         * Provide the specified property with the given getter.
         * 
         * @param propertyName The property name
         * @param getter The getter
         * @return This handle
         */
        public Handle<T> withDoubleList(String propertyName,
            Function<T, double[]> getter)
        {
            this.propertyProviders.put(propertyName, getter);
            return this;
        }

        /**
         * Create the {@link Element} list, as a view on the objects
         * 
         * @return The {@link Element} list
         */
        private List<Element> createList()
        {
            class Result extends AbstractList<Element> implements RandomAccess
            {
                @Override
                public int size()
                {
                    return objects.size();
                }

                @Override
                public Element get(int index)
                {
                    return createElement(objects.get(index));
                }
            }
            return new Result();
        }

        /**
         * Create an {@link Element} from the given object
         * 
         * @param object The object
         * @return The {@link Element}
         */
        private Element createElement(T object)
        {
            return new Element()
            {
                @Override
                public Byte getCharProperty(String name)
                {
                    Function<T, ?> getter = propertyProviders.get(name);
                    Object result = getter.apply(object);
                    Byte typedResult = (Byte) result;
                    return typedResult;
                }

                @Override
                public Short getShortProperty(String name)
                {
                    Function<T, ?> getter = propertyProviders.get(name);
                    Object result = getter.apply(object);
                    Short typedResult = (Short) result;
                    return typedResult;
                }

                @Override
                public Integer getIntProperty(String name)
                {
                    Function<T, ?> getter = propertyProviders.get(name);
                    Object result = getter.apply(object);
                    Integer typedResult = (Integer) result;
                    return typedResult;
                }

                @Override
                public Float getFloatProperty(String name)
                {
                    Function<T, ?> getter = propertyProviders.get(name);
                    Object result = getter.apply(object);
                    Float typedResult = (Float) result;
                    return typedResult;
                }

                @Override
                public Double getDoubleProperty(String name)
                {
                    Function<T, ?> getter = propertyProviders.get(name);
                    Object result = getter.apply(object);
                    Double typedResult = (Double) result;
                    return typedResult;
                }

                @Override
                public byte[] getCharListProperty(String name)
                {
                    Function<T, ?> getter = propertyProviders.get(name);
                    Object result = getter.apply(object);
                    byte[] typedResult = (byte[]) result;
                    return typedResult;
                }

                @Override
                public short[] getShortListProperty(String name)
                {
                    Function<T, ?> getter = propertyProviders.get(name);
                    Object result = getter.apply(object);
                    short[] typedResult = (short[]) result;
                    return typedResult;
                }

                @Override
                public int[] getIntListProperty(String name)
                {
                    Function<T, ?> getter = propertyProviders.get(name);
                    Object result = getter.apply(object);
                    int[] typedResult = (int[]) result;
                    return typedResult;
                }

                @Override
                public float[] getFloatListProperty(String name)
                {
                    Function<T, ?> getter = propertyProviders.get(name);
                    Object result = getter.apply(object);
                    float[] typedResult = (float[]) result;
                    return typedResult;
                }

                @Override
                public double[] getDoubleListProperty(String name)
                {
                    Function<T, ?> getter = propertyProviders.get(name);
                    Object result = getter.apply(object);
                    double[] typedResult = (double[]) result;
                    return typedResult;
                }

            };
        }
    }

    @Override
    public List<Element> getElementList(String elementName)
    {
        Handle<?> handle = elementHandles.get(elementName);
        if (handle == null)
        {
            return null;
        }
        return handle.createList();
    }

    @Override
    public List<Element> getElementList(int elementTypeIndex)
    {
        String elementName = descriptor.getElementName(elementTypeIndex);
        return getElementList(elementName);
    }

}