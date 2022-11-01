// CIS 121, QuadTree
public class QuadTreeNodeImpl implements QuadTreeNode {
    /**
     * ! Do not delete this method !
     * Please implement your logic inside this method without modifying the signature
     * of this method, or else your code won't compile.
     * <p/>
     * As always, if you want to create another method, make sure it is not public.
     *
     * @param image image to put into the tree
     * @return the newly build QuadTreeNode instance which stores the compressed image
     * @throws IllegalArgumentException if image is null
     * @throws IllegalArgumentException if image is empty
     * @throws IllegalArgumentException if image.length is not a power of 2
     * @throws IllegalArgumentException if image, the 2d-array, is not a perfect square
     */
    private int color;
    private boolean isLeaf;
    private int dim;
    private QuadTreeNodeImpl topLeft;
    private QuadTreeNodeImpl topRight;
    private QuadTreeNodeImpl bottomLeft;
    private QuadTreeNodeImpl bottomRight;


    private QuadTreeNodeImpl(int c, int d) {
        isLeaf = true;
        color = c;
        dim = d;
        topLeft = null;
        topRight = null;
        bottomLeft = null;
        bottomRight = null;
    }

    private QuadTreeNodeImpl(QuadTreeNodeImpl topl, QuadTreeNodeImpl topr,
                             QuadTreeNodeImpl botl, QuadTreeNodeImpl botr, int d) {
        isLeaf = false;
        dim = d;
        topLeft = topl;
        topRight = topr;
        bottomLeft = botl;
        bottomRight = botr;
    }

    public static QuadTreeNodeImpl buildFromIntArray(int[][] image) {
        if (image == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < image.length; i++) {
            if (image.length != image[i].length) {
                throw new IllegalArgumentException();
            }
        }
        int ydim = image.length;
        if (ydim == 0) {
            throw new IllegalArgumentException();
        }

        if ((int) Math.ceil(Math.log(ydim) / Math.log(2)) !=
                (int) Math.floor(Math.log(ydim) / Math.log(2))) {
            throw new IllegalArgumentException();
        }
        return (recursiveCall(image));
    }

    private static QuadTreeNodeImpl recursiveCall(int[][] image) {
        if (image.length == 1) {
            return (new QuadTreeNodeImpl(image[0][0], 1));
        } else {
            QuadTreeNodeImpl tL = recursiveCall(getSubArray(image, 0));
            QuadTreeNodeImpl tR = recursiveCall(getSubArray(image, 1));
            QuadTreeNodeImpl bL = recursiveCall(getSubArray(image, 2));
            QuadTreeNodeImpl bR = recursiveCall(getSubArray(image, 3));
            if (tL.isLeaf() && tR.isLeaf() && bL.isLeaf() &&
                    bR.isLeaf() && tL.getColor() == tR.getColor() &&
                    tR.getColor() == bL.getColor() && bL.getColor() == bR.getColor()) {
                return (new QuadTreeNodeImpl(image[0][0], image.length));
            } else {
                return (new QuadTreeNodeImpl(tL, tR, bL, bR, image.length));
            }
        }
    }


    private static int[][] getSubArray(int[][] image, int dir) {
        int[][] result = new int[image.length / 2][image.length / 2];
        int ri;
        int rj;
        //top left
        if (dir == 0) {
            ri = 0;
            for (int i = 0; i < image.length / 2; i++) {
                rj = 0;
                for (int j = 0; j < image.length / 2; j++) {
                    result[ri][rj] = image[i][j];
                    rj++;
                }
                ri++;
            }
            //top right
        } else if (dir == 1) {
            ri = 0;
            for (int i = 0; i < image.length / 2; i++) {
                rj = 0;
                for (int j = image.length / 2; j < image.length; j++) {
                    result[ri][rj] = image[i][j];
                    rj++;
                }
                ri++;
            }
            //bottom left
        } else if (dir == 2) {
            ri = 0;
            for (int i = image.length / 2; i < image.length; i++) {
                rj = 0;
                for (int j = 0; j < image.length / 2; j++) {
                    result[ri][rj] = image[i][j];
                    rj++;
                }
                ri++;
            }
            //bottom right
        } else if (dir == 3) {
            ri = 0;
            for (int i = image.length / 2; i < image.length; i++) {
                rj = 0;
                for (int j = image.length / 2; j < image.length; j++) {
                    result[ri][rj] = image[i][j];
                    rj++;
                }
                ri++;
            }
        }
        return result;
    }


    private int getColor() {
        return color;
    }

    @Override
    public int getColor(int x, int y) {

        if (x < 0 || x >= dim || y < 0 || y >= dim) {
            throw new IllegalArgumentException();
        }

        if (isLeaf()) {
            return color;
        } else {
            if (x < dim / 2 && y < dim / 2) {
                return topLeft.getColor(x, y);
            } else if (x < dim / 2 && y >= dim / 2) {
                return bottomLeft.getColor(x, y - dim / 2);
            } else if (x >= dim / 2 && y < dim / 2) {
                return topRight.getColor(x - dim / 2, y);
            } else {
                return bottomRight.getColor(x - dim / 2, y - dim / 2);
            }

        }
    }

    @Override
    public void setColor(int x, int y, int c) {

        if (x < 0 || x >= dim || y < 0 || y >= dim) {
            throw new IllegalArgumentException();
        }


        if (dim == 1) {
            isLeaf = true;
            color = c;
        } else {
            if (isLeaf) {
                isLeaf = false;
                topLeft = new QuadTreeNodeImpl(color, dim / 2);
                topRight = new QuadTreeNodeImpl(color, dim / 2);
                bottomLeft = new QuadTreeNodeImpl(color, dim / 2);
                bottomRight = new QuadTreeNodeImpl(color, dim / 2);
            }
            if (x < dim / 2 && y < dim / 2) {
                topLeft.setColor(x, y, c);
            } else if (x < dim / 2 && y >= dim / 2) {
                bottomLeft.setColor(x, y - dim / 2, c);
            } else if (x >= dim / 2 && y < dim / 2) {
                topRight.setColor(x - dim / 2, y, c);
            } else {
                bottomRight.setColor(x - dim / 2, y - dim / 2, c);
            }


            if (topLeft.isLeaf() && topRight.isLeaf() && bottomLeft.isLeaf()
                    && bottomRight.isLeaf() && topLeft.getColor() == topRight.getColor()
                    && topRight.getColor() == bottomLeft.getColor() && bottomLeft.getColor()
                    == bottomRight.getColor()) {
                isLeaf = true;
                color = c;

            }
        }

    }


    @Override
    public QuadTreeNode getQuadrant(QuadName quadrant) {
        if (isLeaf) {
            return null;
        } else if (quadrant == QuadName.TOP_RIGHT) {
            return topRight;
        } else if (quadrant == QuadName.TOP_LEFT) {
            return topLeft;
        } else if (quadrant == QuadName.BOTTOM_RIGHT) {
            return bottomRight;
        } else {
            return bottomLeft;
        }
    }

    @Override
    public int getDimension() {
        return dim;
    }

    @Override
    public int getSize() {
        if (isLeaf) {
            return 1;
        } else {
            return (1 + topLeft.getSize() + topRight.getSize()
                    + bottomLeft.getSize() + bottomRight.getSize());
        }
    }

    @Override
    public boolean isLeaf() {
        return isLeaf;
    }

    @Override
    public int[][] decompress() {
        int[][] result = new int[dim][dim];
        int[][] subArray;

        if (isLeaf()) {
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    result[i][j] = color;
                }
            }
        } else {
            //topLeft
            int arrx, arry = 0;
            subArray = topLeft.decompress();
            for (int i = 0; i < dim / 2; i++) {
                arrx = 0;
                for (int j = 0; j < dim / 2; j++) {
                    result[i][j] = subArray[arry][arrx];
                    arrx++;
                }
                arry++;
            }
            //topRight
            arry = 0;
            subArray = topRight.decompress();
            for (int i = 0; i < dim / 2; i++) {
                arrx = 0;
                for (int j = dim / 2; j < dim; j++) {
                    result[i][j] = subArray[arry][arrx];
                    arrx++;
                }
                arry++;
            }
            //bottomRight
            arry = 0;
            subArray = bottomRight.decompress();
            for (int i = dim / 2; i < dim; i++) {
                arrx = 0;
                for (int j = dim / 2; j < dim; j++) {
                    result[i][j] = subArray[arry][arrx];
                    arrx++;
                }
                arry++;
            }
            //bottomLeft
            arry = 0;
            subArray = bottomLeft.decompress();
            for (int i = dim / 2; i < dim; i++) {
                arrx = 0;
                for (int j = 0; j < dim / 2; j++) {
                    result[i][j] = subArray[arry][arrx];
                    arrx++;
                }
                arry++;
            }
        }
        return result;
    }

    @Override
    public double getCompressionRatio() {
        return ((getSize() / (double) (getDimension() * getDimension())));
    }
}
