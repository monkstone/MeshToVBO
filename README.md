MeshToVBO
=========

A library for processing that supports conversion of [Hemesh mesh](http://hemesh.wblut.com/) to PShape (VBO).
Will only with processing-2.0 and since hemesh svn 109.
The conversion from mesh to PShape should be done in processing setup for efficiency,
the shape should be declared prior to setup so that it can be called using shape (mesh)
in the draw loop (it will be stored in graphics memory as a VBO). Now features built in
arcball rotation, and mouse zoom facility.
