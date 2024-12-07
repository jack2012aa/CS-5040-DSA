# MemManager
## Description
In this project, I write a memory manager that use the first-fit strategy to manage a memory pool. Other class can require memory from it and it will provide a handle for each request.

## Implementation
Implement the first-fit strategy is easy: write a `DoublyLinkedList` class (my `FreeBlockManager`) storing free memory blocks. When handling an allocate request, the `MemoryManager` find a space that is large enough from the linked-list, or extend if necessary, and cut the space based on the required size, then return the `Handle` representing the memory position. Later on, other class can use the `Handle` to get the content in the memory.

## Something to improve
Support more dynamic storage allocation strategies, such as buddy method.

## Usage
`java SemManager 512 4 P4Sample_input.txt`
