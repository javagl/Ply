/// *
// * www.javagl.de - Ply
// *
// * Copyright (c) 2011-2025 Marco Hutter - http://www.javagl.de
// *
// * Permission is hereby granted, free of charge, to any person
// * obtaining a copy of this software and associated documentation
// * files (the "Software"), to deal in the Software without
// * restriction, including without limitation the rights to use,
// * copy, modify, merge, publish, distribute, sublicense, and/or sell
// * copies of the Software, and to permit persons to whom the
// * Software is furnished to do so, subject to the following
// * conditions:
// *
// * The above copyright notice and this permission notice shall be
// * included in all copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
// * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
// * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
// * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
// * OTHER DEALINGS IN THE SOFTWARE.
// */
// package de.javagl.ply;
//
/// **
// * Methods related to types in PLY data
// */
// class Types
// {
// /**
// * Returns whether the given string is a valid type string
// *
// * @param type The type
// * @return Whether the type is valid
// */
// static boolean isValid(String type)
// {
// return isChar(type) || isShort(type) || isInt(type) || isFloat(type)
// || isDouble(type);
// }
//
// /**
// * Returns whether the given string is a valid size type
// *
// * @param type The type
// * @return Whether the type is valid
// */
// static boolean isValidSize(String type)
// {
// return isChar(type) || isShort(type) || isInt(type);
// }
//
// /**
// * Returns whether the given type is an unsigned type
// *
// * @param type The type
// * @return The result
// */
// static boolean isUnsigned(String type)
// {
// return type.equals("uchar") || type.equals("uint8")
// || type.equals("ushort") || type.equals("uint16")
// || type.equals("uint") || type.equals("uint32");
// }
//
// /**
// * Returns whether the given type is a char type
// *
// * @param type The type
// * @return The result
// */
// static boolean isChar(String type)
// {
// return type.equals("char") || type.equals("uchar")
// || type.equals("int8") || type.equals("uint8");
// }
//
// /**
// * Returns whether the given type is a short type
// *
// * @param type The type
// * @return The result
// */
// static boolean isShort(String type)
// {
// return type.equals("short") || type.equals("ushort")
// || type.equals("int16") || type.equals("uint16");
// }
//
// /**
// * Returns whether the given type is an int type
// *
// * @param type The type
// * @return The result
// */
// static boolean isInt(String type)
// {
// return type.equals("int") || type.equals("uint") || type.equals("int32")
// || type.equals("uint32");
// }
//
// /**
// * Returns whether the given type is a float type
// *
// * @param type The type
// * @return The result
// */
// static boolean isFloat(String type)
// {
// return type.equals("float") || type.equals("float32");
// }
//
// /**
// * Returns whether the given type is a double type
// *
// * @param type The type
// * @return The result
// */
// static boolean isDouble(String type)
// {
// return type.equals("double") || type.equals("float64");
// }
//
// /**
// * Private constructor to prevent instantiation
// */
// private Types()
// {
// // Private constructor to prevent instantiation
// }
//
// }
