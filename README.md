# Ply

A PLY library for Java

### Overview

A simple reader and writer for `.PLY` files.

It supports reading and writing all `ascii`, `binary_big_endian` and 
`binary_little_endian` PLY representations. 

The library offers a generic, strongly typed baseline representation of the
contents of PLY files. The process of reading and accessing the PLY data in 
the most basic form is shown here:

```java
// Create a PLY reader
PlyReader p = PlyReaders.create();

// Read the PLY data from an input stream
PlySource plySource = p.read(inputStream);

// Access one specific element and one specific
// property of the PLY data
List<Element> vertices = plySource.getElementList("vertex");
Element vertex = vertices.get(1);
float z = vertex.getFloatProperty("z");
System.out.println("Value: " + z);
```

The library also allows passing the data that is read from a PLY file into 
custom structures, using the `PlyTarget` callback, as well as
functions for creating PLY data in memory and writing it to a file.

The [examples](/ply-main/src/main/java/de/javagl/ply/examples) directory 
contains examples for different tasks.

