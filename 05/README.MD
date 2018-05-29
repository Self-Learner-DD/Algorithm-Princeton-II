Project specification: http://coursera.cs.princeton.edu/algs4/assignments/burrows.html

Project checklist: http://coursera.cs.princeton.edu/algs4/checklists/burrows.html

Tips:

1.When implement CircularSuffixArray, do not create String[] that contains each suffix, since that would take quadratic space, instead
for each suffix, only need to keep an index that indicates which character is the beginning of the suffix.

2.when implement inverseTransform method of BurrowsWheeler, pay attention that the next[i] with character of first_col[i] is just correspoinding to the 
original index of that character in the last_col, so the array 'next' can be achieved by sort the original index array according to the natural order
of the correspoinding character; here I used character-index array as the sorting method to ensure O(n) time complexity...
