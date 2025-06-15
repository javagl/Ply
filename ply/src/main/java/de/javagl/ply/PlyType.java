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

/**
 * An enumeration of types that may appear in PLY data.
 * 
 * All types have aliases in PLY. For example, the type <code>UCHAR</code> is
 * identical to <code>UINT8</code>
 * 
 * The standard enum {@link #valueOf(String)} function can only resolve one
 * specific representation of these types. Therefore, this enum offers a
 * {@link #valueFor(String)} method that resolves both representations to one
 * enum value.
 */
public enum PlyType
{
        /**
         * An 8-bit unsigned char (byte)
         */
        UCHAR,

        /**
         * An 8-bit signed char (byte)
         */
        CHAR,

        /**
         * A 16-bit unsigned short
         */
        USHORT,

        /**
         * A 16-bit signed short
         */
        SHORT,

        /**
         * A 32-bit unsigned int
         */
        UINT,

        /**
         * A 32-bit signed short
         */
        INT,

        /**
         * A 32-bit floating point type
         */
        FLOAT,

        /**
         * A 64-bit floating point type
         */
        DOUBLE;

    /**
     * An alias for {@link PlyType#UCHAR}
     */
    public static final PlyType UINT8 = UCHAR;

    /**
     * An alias for {@link PlyType#CHAR}
     */
    public static final PlyType INT8 = CHAR;

    /**
     * An alias for {@link PlyType#USHORT}
     */
    public static final PlyType UINT16 = USHORT;

    /**
     * An alias for {@link PlyType#SHORT}
     */
    public static final PlyType INT16 = SHORT;

    /**
     * An alias for {@link PlyType#UINT}
     */
    public static final PlyType UINT32 = UINT;

    /**
     * An alias for {@link PlyType#INT}
     */
    public static final PlyType INT32 = INT;

    /**
     * An alias for {@link PlyType#FLOAT}
     */
    public static final PlyType FLOAT32 = FLOAT;

    /**
     * An alias for {@link PlyType#DOUBLE}
     */
    public static final PlyType FLOAT64 = DOUBLE;

    /**
     * A mapping from different string representations to the respective type
     */
    private static final Map<String, PlyType> representations;
    static
    {
        Map<String, PlyType> m = new LinkedHashMap<String, PlyType>();
        m.put("uchar", UCHAR);
        m.put("char", CHAR);
        m.put("ushort", USHORT);
        m.put("short", SHORT);
        m.put("uint", UINT);
        m.put("int", INT);
        m.put("float", FLOAT);
        m.put("double", DOUBLE);
        m.put("uint8", UINT8);
        m.put("int8", INT8);
        m.put("uint16", UINT16);
        m.put("int16", INT16);
        m.put("uint32", UINT32);
        m.put("int32", INT32);
        m.put("float32", FLOAT32);
        m.put("float64", FLOAT64);
        representations = m;
    }

    /**
     * Returns the type that corresponds to the given string.
     * 
     * In contrast to {@link #valueOf(String)}, this will accept all
     * representations of types in PLY, including aliases.
     * 
     * @param s The string
     * @return The type
     * @throws IllegalArgumentException If the given string does not represent a
     *         valid type
     */
    public static PlyType valueFor(String s)
    {
        PlyType t = representations.get(s);
        if (t != null)
        {
            return t;
        }
        throw new IllegalArgumentException(
            "The string '" + s + "' does not represent a valid type");
    }

    /**
     * Returns whether the given string represents a valid type.
     * 
     * @param s The string
     * @return Whether the given string represents a valid type
     */
    static boolean isValid(String s)
    {
        return representations.get(s) != null;
    }

    /**
     * Returns whether the given type is a valid size type
     * 
     * @param type The type
     * @return Whether the type is valid
     */
    static boolean isValidSize(PlyType type)
    {
        return type == PlyType.UCHAR || type == PlyType.CHAR
            || type == PlyType.USHORT || type == PlyType.SHORT
            || type == PlyType.INT || type == PlyType.UINT;
    }

    /**
     * Returns whether the given type string is a valid size type
     * 
     * @param s The type string
     * @return Whether the type is valid
     */
    static boolean isValidSize(String s)
    {
        PlyType t = representations.get(s);
        if (t == null)
        {
            return false;
        }
        return isValidSize(t);
    }

    /**
     * Creates a map that maps types to their default string representation
     * (e.g. <code>"uchar"</code>
     * 
     * @return The map
     */
    static Map<PlyType, String> createDefaultStrings()
    {
        Map<PlyType, String> m = new LinkedHashMap<PlyType, String>();
        m.put(UCHAR, "uchar");
        m.put(CHAR, "char");
        m.put(USHORT, "ushort");
        m.put(SHORT, "short");
        m.put(UINT, "uint");
        m.put(INT, "int");
        m.put(FLOAT, "float");
        m.put(DOUBLE, "double");
        return m;
    }

    /**
     * Creates a map that maps types to the string representation that indicates
     * the number of bits (e.g. <code>"uint8"</code>
     * 
     * @return The map
     */
    static Map<PlyType, String> createBitnessStrings()
    {
        Map<PlyType, String> m = new LinkedHashMap<PlyType, String>();
        m.put(UINT8, "uint8");
        m.put(INT8, "int8");
        m.put(UINT16, "uint16");
        m.put(INT16, "int16");
        m.put(UINT32, "uint32");
        m.put(INT32, "int32");
        m.put(FLOAT32, "float32");
        m.put(FLOAT64, "float64");
        return m;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private PlyType()
    {
        // Private constructor to prevent instantiation
    }

}
