#Package information
This package contains class need in read a executable file[.class.obj.pyc].

#Class Function
1.EditDistanceAlgorithm class can compare two Array<byte>, see 

detail at https://en.wikipedia.org/wiki/Edit_distance

2.Reader can read a binary file and return a Array<byte>.

3.BinaryCodeCompare integrate 1 and 2 together so that users don't

need to call EditDistanceAlgotrithm and Reader. Other 

consideration is to integrate other compare algorithms in future.

4.EDATest,Test, Writer is used in program developing which will not 

be used practically.