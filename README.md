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

There are convenience classes for reading and writing data directly from
and to existing classes. The `ObjectPlyTarget` class allows
registering a certain type for the instances that should be created
from the PLY elements, and configuring how the PLY data is assigned
to these instances:


```java
// Create a new ObjectPlyTarget
ObjectPlyTarget plyTarget = new ObjectPlyTarget();

// Configure the target to create an ExampleVertex for
// each "vertex" element that is read from the file
Handle<ExampleVertex> v =
    plyTarget.register("vertex", ExampleVertex::new);

// Assign the properties to the vertices using setters
v.withFloat("x", ExampleVertex::setX);
v.withFloat("y", ExampleVertex::setY);
v.withFloat("z", ExampleVertex::setZ);

// Put each vertex into a list
List<ExampleVertex> vertices = new ArrayList<ExampleVertex>();
v.consume(vertices::add);

// Read the descriptor (header) and the actual content form
// an input stream, passing it to the target to create vertices
r.readDescriptor(inputStream);
r.readContent(inputStream, plyTarget);
```

The [examples](/ply-examples/src/main/java/de/javagl/ply/examples) directory 
contains further examples for different tasks.
