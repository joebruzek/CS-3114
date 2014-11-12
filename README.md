This is the repository for project 2 for CS 3114 Data Structures and Algorithms at Virginia Tech, Fall 2014.
Joe Bruzek
Marcus Stewart

Project 2 is a database system for storing and retrieving artist/song names. The specs for the project can be found here: http://courses.cs.vt.edu/~cs3114/Fall14/P2.pdf

This project extends the Memory Manager system of Project 1. The Memory Manager operates by processing a command from a file. The Memory Manager then finds an open space in memory using a doubly linked "free block" list using the best fit method of finding available memory. The position in memory for this data is then inserted into a closed hash table using a quadratic probing collision detection process. This allows for fast retrieval of the information from the memory pool.

Adding to the Memory Manager, project 2 implements a 2-3+ tree. This is essentially a B+ tree of order 3. Each node of the tree stores up two two keys, and branches to up to 3 child nodes. This tree differs from a binary search tree in that everything inserted into the tree must be contained in a leaf node, and all of the leaf nodes are on the same level. The leaf nodes are implemented as a doubly linked list. A good visualization of a 2-3+ tree can be found here: https://www.cs.usfca.edu/~galles/visualization/BPlusTree.html
