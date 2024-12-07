# ExternalSorting
## Description
In this project, I wrote an external sorting algorithm. It takes a file containing {int value, float key} data, sorts them and put sorted data back to the file.

## Implementation
In external sorting, decreasing number of read from the file system extremely increases the performance. Hence, I use the replacement selection algorithm and multi-way merging in this project.

### Replacement selection
In replacement selection, we try to sort the input into k sorted runs.
1. In the memory, we have an input buffer, output buffer, and a heap buffer (min heap), each of size S.
2. At the beginning of each run, read data of size S into the input buffer, and read other data for size S into the heap buffer and heapify.
3. Put the heap top to the output buffer. Write the content of the output buffer to the file when the buffer is full.
4. If the first element of the input buffer is larger than the newly inserted element, this element can not be inserted into this run. Put it to the back of the heap and the heap size - 1 so the heap won't take the element into consideration.
5. Repeat steps 3, 4 until the heap size becomes zero or the file reach the end. This is the end of this run.
6. Back to step 2. Repeat until the file reach the end.

### Multi-way merging
It works like [this leetcode problem](https://leetcode.com/problems/merge-k-sorted-lists/description/). Use a heap to track the first element in the k sorted runs. Put the top of the heap to the output, and put a new element from the run which the inserted element coming from to the heap.

### Input buffer and output buffer
To simplify the sorting algorithm, I wrote a class to automate the IO. The input buffer will read S elements in once and give them one by one to the sorting algorithm. The output buffer will flush automatically when the buffer is full.

## Usage
`java Externalsort sampleInput16.bin`
