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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * Implementation of a {@link PlyTarget} that can be configured to translate the
 * elements from the PLY into objects, assign the property values to these
 * objects, and pass the final objects to a consumer.
 */
public final class ObjectPlyTarget implements PlyTarget
{
    /**
     * The logger used in this class
     */
    private static final Logger logger =
        Logger.getLogger(ObjectPlyTarget.class.getName());

    /**
     * Internal structure that contains the information that is required for
     * processing a single element (type).
     */
    private static class ElementProcessor
    {
        /**
         * The supplier for the element objects
         */
        private Supplier<Object> supplier;

        /**
         * The list of consumers, one for each property of the element types.
         * Some of them may be null.
         */
        private List<BiConsumer<Object, ?>> propertyConsumers;

        /**
         * The consumer for the final elements
         */
        private Consumer<Object> consumer;

        /**
         * Creates a new instance
         */
        private ElementProcessor()
        {
            this.supplier = null;
            this.propertyConsumers = new ArrayList<BiConsumer<Object, ?>>();
            this.consumer = null;
        }
    }

    /**
     * A handle for a specific type of element.
     * 
     * An instance of this class is returned by the
     * {@link ObjectPlyTarget#register(String, Supplier)} method, and allows
     * configuring the handling of individual properties of a certain element
     * type.
     * 
     * @param <T> The element type
     */
    public static final class Handle<T>
    {
        /**
         * The supplier of the element objects
         */
        private Supplier<Object> supplier;

        /**
         * The mapping from property names to the consumers for the property
         * values.
         */
        private Map<String, BiConsumer<Object, Object>> propertyConsumers;

        /**
         * The consumer for the final element objects
         */
        private Consumer<Object> consumer;

        /**
         * Create a new instance
         * 
         * @param supplier The supplier for the element objects
         */
        private Handle(Supplier<T> supplier)
        {
            @SuppressWarnings("unchecked")
            Supplier<Object> internalSupplier = (Supplier<Object>) supplier;
            this.supplier = internalSupplier;
            this.propertyConsumers =
                new LinkedHashMap<String, BiConsumer<Object, Object>>();
        }

        /**
         * Handle the specified property with the given setter.
         * 
         * @param <U> The value type
         * @param propertyName The property name
         * @param setter The setter
         * @return This handle
         */
        // Note: The 'extends' part is necessary for some type safety.
        @SuppressWarnings("all")
        public <U extends Byte> Handle<T> withByte(String propertyName,
            BiConsumer<T, U> setter)
        {
            return withImpl(propertyName, setter);
        }

        /**
         * Handle the specified property with the given setter.
         * 
         * @param <U> The value type
         * @param propertyName The property name
         * @param setter The setter
         * @return This handle
         */
        // Note: The 'extends' part is necessary for some type safety.
        @SuppressWarnings("all")
        public <U extends Short> Handle<T> withShort(String propertyName,
            BiConsumer<T, U> setter)
        {
            return withImpl(propertyName, setter);
        }

        /**
         * Handle the specified property with the given setter.
         * 
         * @param <U> The value type
         * @param propertyName The property name
         * @param setter The setter
         * @return This handle
         */
        // Note: The 'extends' part is necessary for some type safety.
        @SuppressWarnings("all")
        public <U extends Integer> Handle<T> withInt(String propertyName,
            BiConsumer<T, U> setter)
        {
            return withImpl(propertyName, setter);
        }

        /**
         * Handle the specified property with the given setter.
         * 
         * @param <U> The value type
         * @param propertyName The property name
         * @param setter The setter
         * @return This handle
         */
        // Note: The 'extends' part is necessary for some type safety.
        @SuppressWarnings("all")
        public <U extends Float> Handle<T> withFloat(String propertyName,
            BiConsumer<T, U> setter)
        {
            return withImpl(propertyName, setter);
        }

        /**
         * Handle the specified property with the given setter.
         * 
         * @param <U> The value type
         * @param propertyName The property name
         * @param setter The setter
         * @return This handle
         */
        // Note: The 'extends' part is necessary for some type safety.
        @SuppressWarnings("all")
        public <U extends Double> Handle<T> withDouble(String propertyName,
            BiConsumer<T, U> setter)
        {
            return withImpl(propertyName, setter);
        }

        /**
         * Handle the specified property with the given setter.
         * 
         * @param propertyName The property name
         * @param setter The setter
         * @return This handle
         */
        public Handle<T> withCharList(String propertyName,
            BiConsumer<T, byte[]> setter)
        {
            return withImpl(propertyName, setter);
        }

        /**
         * Handle the specified property with the given setter.
         * 
         * @param propertyName The property name
         * @param setter The setter
         * @return This handle
         */
        public Handle<T> withShortList(String propertyName,
            BiConsumer<T, short[]> setter)
        {
            return withImpl(propertyName, setter);
        }

        /**
         * Handle the specified property with the given setter.
         * 
         * @param propertyName The property name
         * @param setter The setter
         * @return This handle
         */
        public Handle<T> withIntList(String propertyName,
            BiConsumer<T, int[]> setter)
        {
            return withImpl(propertyName, setter);
        }

        /**
         * Handle the specified property with the given setter.
         * 
         * @param propertyName The property name
         * @param setter The setter
         * @return This handle
         */
        public Handle<T> withFloatList(String propertyName,
            BiConsumer<T, float[]> setter)
        {
            return withImpl(propertyName, setter);
        }

        /**
         * Handle the specified property with the given setter.
         * 
         * @param propertyName The property name
         * @param setter The setter
         * @return This handle
         */
        public Handle<T> withDoubleList(String propertyName,
            BiConsumer<T, double[]> setter)
        {
            return withImpl(propertyName, setter);
        }

        /**
         * Internal method to handle the specified property with the given
         * setter.
         * 
         * @param propertyName The property name
         * @param setter The setter
         * @return This handle
         */
        private Handle<T> withImpl(String propertyName, BiConsumer<T, ?> setter)
        {
            @SuppressWarnings("unchecked")
            BiConsumer<Object, Object> internalSetter =
                (BiConsumer<Object, Object>) setter;
            this.propertyConsumers.put(propertyName, internalSetter);
            return this;
        }

        /**
         * Pass the elements to the given consumer when they are finished.
         * 
         * @param consumer The consumer
         */
        public void consume(Consumer<? super T> consumer)
        {
            @SuppressWarnings("unchecked")
            Consumer<Object> internalConsumer = (Consumer<Object>) consumer;
            this.consumer = internalConsumer;
        }

    }

    /**
     * The mapping from element names to {@link Handle} objects
     */
    private final Map<String, Handle<Object>> elementHandles;

    /**
     * The list of {@link ElementProcessor} objects, one for each element type.
     * 
     * This is initialized when the {@link #setDescriptor(Descriptor)} function
     * is called, based on the {@link #elementHandles}. Some of the resulting
     * processors may be <code>null</code>.
     */
    private List<ElementProcessor> elementProcessors;

    /**
     * The element that is currently being built
     */
    private Object currentElement;

    /**
     * Default constructor
     */
    public ObjectPlyTarget()
    {
        this.elementHandles = new LinkedHashMap<String, Handle<Object>>();
        this.elementProcessors = null;
        this.currentElement = null;
    }

    @Override
    public void setDescriptor(Descriptor descriptor)
    {
        this.elementProcessors =
            createElementProcessors(descriptor, this.elementHandles);
    }

    /**
     * Convert the given {@link Handle} objects into a list of
     * {@link ElementProcessor} objects, based on the indices of the element
     * types in the given {@link Descriptor}.
     * 
     * The resulting list may contain <code>null</code> elements for the
     * elements for which no handler was registered.
     * 
     * @param descriptor The {@link Descriptor}
     * @param elementHandles The element {@link Handle} objects
     * @return The {@link ElementProcessor} objects
     */
    private static List<ElementProcessor> createElementProcessors(
        Descriptor descriptor, Map<String, Handle<Object>> elementHandles)
    {
        List<ElementProcessor> elementProcessors =
            new ArrayList<ElementProcessor>();
        List<ElementDescriptor> elementDescriptors =
            descriptor.getElementDescriptors();
        for (int t = 0; t < elementDescriptors.size(); t++)
        {
            ElementDescriptor elementDescriptor = elementDescriptors.get(t);
            String elementName = elementDescriptor.getName();
            Handle<Object> elementHandle = elementHandles.get(elementName);
            if (elementHandle == null)
            {
                elementProcessors.add(null);
            }
            else
            {
                ElementProcessor elementProcessor =
                    createElementProcessor(elementDescriptor, elementHandle);
                elementProcessors.add(elementProcessor);
            }
        }
        return elementProcessors;
    }

    /**
     * Create a single {@link ElementProcessor} from the given {@link Handle}
     * and its corresponding {@link ElementDescriptor}.
     * 
     * If no consumer was registered for the given element handle, then a
     * warning is printed and <code>null</code> is returned.
     * 
     * @param elementDescriptor The {@link ElementDescriptor}
     * @param elementHandle THe {@link Handle}
     * @return The {@link ElementProcessor}.
     */
    private static ElementProcessor createElementProcessor(
        ElementDescriptor elementDescriptor, Handle<Object> elementHandle)
    {
        ElementProcessor elementProcessor = new ElementProcessor();
        elementProcessor.supplier = elementHandle.supplier;
        if (elementHandle.consumer == null)
        {
            logger.warning("No consumer has been specified for elment type '"
                + elementDescriptor.getName() + "', ignoring");
            return null;
        }
        elementProcessor.consumer = elementHandle.consumer;

        List<PropertyDescriptor> propertyDescriptors =
            elementDescriptor.getPropertyDescriptors();
        elementProcessor.propertyConsumers =
            new ArrayList<BiConsumer<Object, ?>>();

        for (int i = 0; i < propertyDescriptors.size(); i++)
        {
            PropertyDescriptor propertyDescriptor = propertyDescriptors.get(i);
            String propertyName = propertyDescriptor.getName();
            BiConsumer<Object, Object> propertyConsumer =
                elementHandle.propertyConsumers.get(propertyName);
            elementProcessor.propertyConsumers.add(propertyConsumer);
        }
        return elementProcessor;
    }

    @Override
    public void startElementList(int elementTypeIndex, int elementCount)
    {
        // Nothing to do here
    }

    @Override
    public void startElement(int elementTypeIndex, int elementIndex)
    {
        ElementProcessor elementProcessor =
            this.elementProcessors.get(elementTypeIndex);
        if (elementProcessor == null)
        {
            return;
        }
        Supplier<Object> elementSupplier = elementProcessor.supplier;
        currentElement = elementSupplier.get();
    }

    @Override
    public void handleCharProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, byte value)
    {
        consumeImpl(elementTypeIndex, propertyIndex, value);
    }

    @Override
    public void handleShortProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, short value)
    {
        consumeImpl(elementTypeIndex, propertyIndex, value);
    }

    @Override
    public void handleIntProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, int value)
    {
        consumeImpl(elementTypeIndex, propertyIndex, value);
    }

    @Override
    public void handleFloatProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, float value)
    {
        consumeImpl(elementTypeIndex, propertyIndex, value);
    }

    @Override
    public void handleDoubleProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, double value)
    {
        consumeImpl(elementTypeIndex, propertyIndex, value);
    }

    @Override
    public void handleCharListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, byte[] value)
    {
        consumeImpl(elementTypeIndex, propertyIndex, value);
    }

    @Override
    public void handleShortListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, short[] value)
    {
        consumeImpl(elementTypeIndex, propertyIndex, value);
    }

    @Override
    public void handleIntListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, int[] value)
    {
        consumeImpl(elementTypeIndex, propertyIndex, value);
    }

    @Override
    public void handleFloatListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, float[] value)
    {
        consumeImpl(elementTypeIndex, propertyIndex, value);
    }

    @Override
    public void handleDoubleListProperty(int elementTypeIndex, int elementIndex,
        int propertyIndex, double[] value)
    {
        consumeImpl(elementTypeIndex, propertyIndex, value);
    }

    /**
     * Internal method that all <code>handle...</code> methods delegate to.
     * 
     * This will find the {@link ElementProcessor} for the given element type
     * index, and the consumer for the given property index, and pass the
     * {@link #currentElement} and the given value to this consumer.
     * 
     * If the process or handle are not found, then nothing is done.
     * 
     * @param elementTypeIndex The element type index
     * @param propertyIndex The property index
     * @param value The value
     */
    private void consumeImpl(int elementTypeIndex, int propertyIndex,
        Object value)
    {
        ElementProcessor elementProcessor =
            this.elementProcessors.get(elementTypeIndex);
        if (elementProcessor == null)
        {
            return;
        }
        List<BiConsumer<Object, ?>> propertyConsumers =
            elementProcessor.propertyConsumers;
        BiConsumer<Object, ?> propertyConsumer =
            propertyConsumers.get(propertyIndex);
        if (propertyConsumer == null)
        {
            return;
        }
        @SuppressWarnings("unchecked")
        BiConsumer<Object, Object> localConsumer =
            (BiConsumer<Object, Object>) propertyConsumer;
        localConsumer.accept(currentElement, value);
    }

    @Override
    public void endElement(int elementTypeIndex, int elementIndex)
    {
        ElementProcessor elementProcessor =
            this.elementProcessors.get(elementTypeIndex);
        if (elementProcessor == null)
        {
            return;
        }
        Consumer<Object> consumer = elementProcessor.consumer;
        consumer.accept(currentElement);
        currentElement = null;
    }

    @Override
    public void endElementList(int elementTypeIndex)
    {
        // Nothing to do here
    }

    /**
     * Register a new element type to be handled by this instance.
     * 
     * This will return a {@link Handle} that allows further configuration about
     * how to handle the specific element type. The handle offers methods to
     * configure how each property is supposed to be handled, using the
     * <code>handle.with...</code> functions, and how the final elements should
     * be consumed, using the {@link Handle#consume(Consumer)} method.
     * 
     * @param <T> The element type
     * @param elementName The element name
     * @param supplier The supplier for the elements
     * @return The {@link Handle}
     */
    public <T> Handle<T> register(String elementName, Supplier<T> supplier)
    {
        Handle<?> existingElementHandle = elementHandles.get(elementName);
        if (existingElementHandle != null)
        {
            throw new IllegalStateException(
                "Element type '" + elementName + "' was already registered");
        }
        Objects.requireNonNull(supplier, "The supplier may not be null");

        Handle<T> elementHandle = new Handle<T>(supplier);
        @SuppressWarnings("unchecked")
        Handle<Object> internalElementHandle = (Handle<Object>) elementHandle;
        elementHandles.put(elementName, internalElementHandle);
        return elementHandle;
    }

}