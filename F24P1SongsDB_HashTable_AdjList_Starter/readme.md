# SongDB
## Description
In this project, I wrote a simple database storing artists and songs data. User can insert/remove artists and songs, and print artists and songs in the database as well as the structure of the database. By requirement, the database should only save artist's name, song's name, and a connection between artists and songs. Duplicate artists and songs are not allowed.

While insert {artist-name}<SEP>{song-name}, if the artist name and song-name exist in the database, then the database won't create a new record, it creates a connection between the exist artist and song.

To do this, the database consists of two part: hash tables used for access control, and a graph discribing the connection between artists and songs. Before insertion/deletion, the controller will check the existency of artist and song in artists and songs hash tables and deny requirement if input unvalid. Then, it will create or remove representing nodes in the graph.

## Implementation
Libraries such as ArrayList are not allowed.

The `Hash` class is the implementation of the hash table. It is a closed hash table, using quadradic probing and extending automatically when the table is half full.

The `Graph` class is the implementation of the graph. It uses a `DoublyLinkedList` object as an adjacency list.

The `Controller` class use two `Hash` objects and one `Graph` object to work as the database.

Overall, this project is easy. Some requirements are not intuitive, but the only thing to do is to implement each class correctly, and integrate them carefully in the `Controller` class.

## Usage
`java GraphProject solutionTestData/P1_sampleInput.txt 10`

Or, write your own test case.

`java GraphProject {path to the test case} {initial size of the hash tables}`  
