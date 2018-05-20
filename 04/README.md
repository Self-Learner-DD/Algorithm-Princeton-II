Project specification: http://coursera.cs.princeton.edu/algs4/assignments/boggle.html

Project checklist: http://coursera.cs.princeton.edu/algs4/assignments/boggle.html

Tips:

1. Use R-way tries to store dictionary

2. Do DFS to enumerate all strings that can be composed by following sequences of adjacent dice, backtracking
when current path is not a prefix of any word in the dictionary 

3. when check if the current path is a valid prefix, do not check from the root, check from the previous node
