# SemSearch
## Description
In this project, I wrote a simple database storing seminars information. User can insert/delete seminars and search by field and range, and print the structure of the database.

## Implementation
Since the database must support search by range, I used a binary search tree to store `Seminar` with a field as the key. To search by location, I also implemented a `BinTree` class. After finishing these, the project is easy.

## Something to improve
BST is not always the best data structure in this project. while users can't search some key by range, such as seminar id, those key should be stored in a hash table. However, implementing using BST is a requirement of the project, so I have no choice.

## Usage
`java SemSearch solutionTestData/P2_sampleInput.txt 128`

Or, you can write your test case.

`java Semsearch {path to your test case} {world size of the bin tree}`
