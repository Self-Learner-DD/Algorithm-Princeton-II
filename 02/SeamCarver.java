import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    
    private double[][] energy;
    private Picture picture;
    
    public SeamCarver(Picture picture) {                // create a seam carver object based on the given picture
        int w = picture.width(), h = picture.height();
        energy = new double[h][w];
        this.picture = new Picture(picture);
        
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                energy[j][i] = energy(i, j);
            }
        }
    }
    
    public Picture picture() {                          // current picture
        return new Picture(picture);
    }
    
    public int width() {                           // width of current picture
        return picture.width();
    } 
    
    public int height() {                          // height of current picture
        return picture.height();
    }
    
    public double energy(int x, int y) {              // energy of pixel at column x and row y
        assertX(x);
        assertY(y);
        
        int w = picture.width(), h = picture.height();
        if (x == 0 || x == w-1 || y == 0 || y == h-1) return 1000.00;
        
        int colorL = picture.getRGB(x-1, y), colorR = picture.getRGB(x+1, y);
        int colorT = picture.getRGB(x, y-1), colorB = picture.getRGB(x, y+1);
        
        int colorLB = colorL & 255, colorLG = (colorL >> 8) & 255, colorLR = (colorL >> 16) & 255;
        int colorRB = colorR & 255, colorRG = (colorR >> 8) & 255, colorRR = (colorR >> 16) & 255;
        int colorTB = colorT & 255, colorTG = (colorT >> 8) & 255, colorTR = (colorT >> 16) & 255;
        int colorBB = colorB & 255, colorBG = (colorB >> 8) & 255, colorBR = (colorB >> 16) & 255;        
        
        int deltaXsquare = (colorLB - colorRB)*((colorLB - colorRB)) + (colorLG - colorRG)*((colorLG - colorRG)) +
            (colorLR - colorRR)*((colorLR - colorRR));
      
        int deltaYsquare = (colorTB - colorBB)*((colorTB - colorBB)) + (colorTG - colorBG)*((colorTG - colorBG)) +
            (colorTR - colorBR)*((colorTR - colorBR));
        double energy = Math.sqrt(1.0 * deltaXsquare + 1.0 * deltaYsquare);
        
        return energy;
    }
    
    public   int[] findHorizontalSeam() {              // sequence of indices for horizontal seam  
        int w = picture.width(), h = picture.height();
        Picture transpose = new Picture(h, w);
        for (int col = 0; col < w; col++) {
            for (int row = 0; row < h; row++) {
                Color color = picture.get(col, row);
                transpose.set(row, col, color);
            }
        }
        SeamCarver sc = new SeamCarver(transpose);
        
        return sc.findVerticalSeam();
    }
            
    public   int[] findVerticalSeam() {                // sequence of indices for vertical seam
        int w = picture.width(), h = picture.height();
        if (w == 0) return null;
        int[] seamV = new int[h];
        if (w == 1) {
            for (int i = 0; i < h; i++) {
                seamV[i] = 0;
            }
            return seamV;
        }
        
        double[][] distTo = new double[h][w];
        int[][] edgeTo = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (i == 0) { distTo[i][j] = 0.0; }
                else distTo[i][j] = Double.POSITIVE_INFINITY;
                edgeTo[i][j] = j;
            }
        }
         for (int i = 1; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (j == 0) {
                    double distUpper = distTo[i-1][j] + energy[i-1][j];
                    double distRight = distTo[i-1][j+1] + energy[i-1][j+1];
                    if (distRight < distUpper) {
                        distTo[i][j] = distRight;
                        edgeTo[i][j] = j+1;
                    }
                    else {
                        distTo[i][j] = distUpper;
                        edgeTo[i][j] = j;
                    }
                }
                else if (j == w - 1) {
                    double distUpper = distTo[i-1][j] + energy[i-1][j];
                    double distLeft = distTo[i-1][j-1] + energy[i-1][j-1];
                    if (distLeft < distUpper) {
                        distTo[i][j] = distLeft;
                        edgeTo[i][j] = j-1;
                    }
                    else {
                        distTo[i][j] = distUpper;
                        edgeTo[i][j] = j;
                    }
                }
                else {
                    double distRight = distTo[i-1][j+1] + energy[i-1][j+1];
                    double distUpper = distTo[i-1][j] + energy[i-1][j];
                    double distLeft = distTo[i-1][j-1] + energy[i-1][j-1];
                    if (distRight <= distUpper && distRight <= distLeft) {
                        distTo[i][j] = distRight;
                        edgeTo[i][j] = j+1;
                    }
                    else if (distUpper <= distLeft && distUpper <= distRight) {
                        distTo[i][j] = distUpper;
                        edgeTo[i][j] = j;
                    }
                    else if (distLeft <= distUpper && distLeft <= distRight) {
                        distTo[i][j] = distLeft;
                        edgeTo[i][j] = j-1;
                    }
                }
            }
         }
         
         double min = Double.POSITIVE_INFINITY;
         int pos = -1;
         for (int j = 0; j < w; j++) {
             if (distTo[h-1][j] < min) {
                 pos = j; 
                 min = distTo[h-1][j];
             }
         }
         seamV[h-1] = pos;
         for (int i = h-2; i >= 0; i--) {
             seamV[i] = edgeTo[i+1][seamV[i+1]];
         }
         return seamV;
    }
    
    public void removeHorizontalSeam(int[] seam) {  // remove horizontal seam from current picture
        assertHorizontalSeam(seam);
        
        int w = picture.width(), h = picture.height();
        
        Picture newPicture = new Picture(w, h - 1);
        for (int j = 0; j < w; j++) {
            for (int i = 0; i < h-1; i++) {
                if ( i < seam[j]) {
                    newPicture.set(j, i, picture.get(j, i));
                }
                else {
                    newPicture.set(j, i, picture.get(j, i+1));
                    energy[i][j] = energy[i+1][j];
                }
            }
        }
        this.picture = newPicture; 
            
        for (int j = 0; j < w; j++) {
            for (int i = seam[j] - 1; i <= seam[j] ; i++) {
                if (i < 0) continue;
                if (i >= picture.height()) continue;
                energy[i][j] = energy(j, i);
            }
        }
    }
    
    public    void removeVerticalSeam(int[] seam) {    // remove vertical seam from current picture
        assertVerticalSeam(seam);
        int w = picture.width(), h = picture.height();
        
        Picture newPicture = new Picture(w - 1, h);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w-1; j++) {
                if (j < seam[i]) {
                    newPicture.set(j, i, picture.get(j, i));
                }
                else { 
                    newPicture.set(j, i, picture.get(j+1, i));
                    energy[i][j] = energy[i][j+1];
                }
            }
        }
        this.picture = newPicture;
        
        for (int i = 0; i < h; i++) {
            for (int j = seam[i]-1; j <= seam[i]; j++) {
                if (j < 0) continue;
                if (j >= picture.width()) continue;
                energy[i][j] = energy(j, i);
            }
        }
    }
    
    private void assertX(int x) {
        int w = picture.width();
        if (x < 0 || x > w - 1) throw new IllegalArgumentException("X Coordinate out of range");
    }
    
    private void assertY(int y) {
        int h = picture.height();
        if (y < 0 || y > h - 1) throw new IllegalArgumentException("Y Coordinate out of range");
    }
    
    private void assertVerticalSeam(int[] seam) {
        if (seam == null || picture == null) throw new IllegalArgumentException("Seam and picture cannot be null");
        int w = picture.width(), h = picture.height();
        if ( w <= 1) throw new IllegalArgumentException("width less than 1");
        if (seam.length != h) throw new IllegalArgumentException("Illegal seam");
        for (int i = 0; i < seam.length; i++) {
            assertX(seam[i]);
            if (i == 0) continue;
            if (seam[i] > seam[i-1] + 1 || seam[i] < seam[i-1] - 1) throw new IllegalArgumentException("Illegal seam");
        }
    }
    
    private void assertHorizontalSeam(int[] seam) {
        if (seam == null || picture == null ) throw new IllegalArgumentException("Seam and picture cannot be null");
        int w = picture.width(), h = picture.height();
        if ( h <= 1) throw new IllegalArgumentException("height less than 1");
        if (seam.length != w) throw new IllegalArgumentException("Illegal seam");
        for (int i = 0; i < seam.length; i++) {
            assertY(seam[i]);
            if (i == 0) continue;
            if (seam[i] > seam[i-1] + 1 || seam[i] < seam[i-1] - 1) throw new IllegalArgumentException("Illegal seam");
        }
    }     
} 
