project specification: http://coursera.cs.princeton.edu/algs4/assignments/seam.html

Some tips:

1. energy(x, y):
   use method getRGB(x, y) to return the color of pixel in int, and use bit manipulation to get the red, green, and blue component
   this process will be faster compare to using get(x, y) to return a color instance, and use color.getRed() to get each component
   
2. findVerticalSeam: 
   (1) the target is to find a shortest path from any of the W pixels in the top row to any of the W pixels at the bottom row
   (2) it can be analogy with the problem of finding shortest path in DAG, the difference is here the topological order is known :
   since the directed edges only exist from every pixel to its three downward neighbors, as long as we visit the pixel row by row,
   we are following the topological order
   (3) visit the pixel row by row, use energy[][] to update distTo[][] and edgeTo[][] (similar to dynamic programming) 
   (4) start from the minimum entry of distTo[][] in the last row, get the vertical seam index array
   
3. findHorizontalSeam:
   Make a new picture, which is the transpose of the original one, call the findVertical Seam to calculate the horizontal seam.

4. Have an instance variable energy[][] to store the energy of each pixel
   update energy[][] when remove a seam (do not need to recalculate from scrath)
