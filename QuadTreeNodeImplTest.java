import org.junit.Test;

import static org.junit.Assert.*;

public class QuadTreeNodeImplTest {
    @Test(expected = IllegalArgumentException.class)
    public void testBuildFromIntArrayNull() {
        QuadTreeNodeImpl.buildFromIntArray(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildFromIntArrayEmpty() {
        int[][] array = new int[0][0];
        QuadTreeNodeImpl.buildFromIntArray(array);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildFromIntArrayNotPowerOf2() {
        int[][] array = new int[5][5];
        QuadTreeNodeImpl.buildFromIntArray(array);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildFromIntArrayPerfectSquare() {
        int[][] array = new int[2][4];
        QuadTreeNodeImpl.buildFromIntArray(array);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildFromIntArrayRectangular() {
        int[][] array = {{0, 0, 0}, {0, 0}, {0, 0, 0, 0}};
        QuadTreeNodeImpl.buildFromIntArray(array);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetColorOutOfBoundNegative() {
        int[][] array = {
                {0, 0, 3, 4},
                {3, 1, 2, 0},
                {0, 0, 0, 0},
                {3, 3, 2, 1}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        tree.getColor(-3, -2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetColorOutOfBoundGreater() {
        int[][] array = {
                {0, 0, 3, 4},
                {3, 1, 2, 0},
                {0, 0, 0, 0},
                {3, 3, 2, 1}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        tree.getColor(8, 5);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetColorOutOfBoundGreater() {
        int[][] array = {
                {0, 0, 3, 4},
                {3, 1, 2, 0},
                {0, 0, 0, 0},
                {3, 3, 2, 1}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        tree.setColor(8, 5, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetColorOutOfBoundNegative() {
        int[][] array = {
                {0, 0, 3, 4},
                {3, 1, 2, 0},
                {0, 0, 0, 0},
                {3, 3, 2, 1}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        tree.setColor(-3, -2, 1);
    }

    @Test
    public void testSetColorTreeStructureDoesntChange() {
        int[][] array = {
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertEquals(tree.getSize(), 13);
        tree.setColor(7, 0, 2);
        assertEquals(tree.getSize(), 13);
    }

    @Test
    public void testSetColorTreeStructureBreakNodes() {
        int[][] array = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertEquals(tree.getSize(), 5);
        assertEquals(tree.getColor(7, 0), 0);
        tree.setColor(7, 0, 1);
        assertEquals(tree.getColor(7, 0), 1);
        assertEquals(tree.getSize(), 13);
    }

    @Test
    public void testSetColorTreeStructureMergeNodes() {
        int[][] array = {
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertEquals(tree.getSize(), 13);
        tree.setColor(7, 0, 1);
        assertEquals(tree.getSize(), 9);
    }

    @Test
    public void testGetQuadrantTopRight() {
        int[][] array = {
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 1, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0}};
        int[][] check = {{0, 0, 1, 0}, {0, 0, 1, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertArrayEquals(check, (tree.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT)).decompress());


    }

    @Test
    public void testGetQuadrantBottomRight() {
        int[][] array = {
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 1, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0}};
        int[][] check = {
                {0, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertArrayEquals(check,
                (tree.getQuadrant(QuadTreeNode.QuadName.BOTTOM_RIGHT)).decompress());

    }

    @Test
    public void testGetQuadrantTopLeft() {
        int[][] array = {
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 1, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0}};
        int[][] check = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertArrayEquals(check, (tree.getQuadrant(QuadTreeNode.QuadName.TOP_LEFT)).decompress());

    }

    @Test
    public void testGetQuadrantBottomLeft() {
        int[][] array = {
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 1, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0}};
        int[][] check = {
                {0, 0, 1, 0},
                {0, 0, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertArrayEquals(check, (tree.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT)).decompress());

    }

    @Test
    public void testGetColorSinglePixel() {
        int[][] array = {
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertEquals(tree.getColor(6, 0), 1);
    }

    @Test
    public void testGetColorBiggerQuadrant() {
        int[][] array = {
                {0, 0, 0, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertEquals(tree.getColor(6, 0), 1);
    }

    @Test
    public void testGetDimension() {
        int[][] array = {
                {0, 0, 0, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertEquals(tree.getDimension(), 8);
    }

    @Test
    public void testGetSize1Node() {
        int[][] array = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertEquals(tree.getSize(), 1);
    }

    @Test
    public void testIsLeaf() {
        int[][] array = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertEquals(tree.isLeaf(), true);
        int[][] arrayFalse = {
                {0, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};
        QuadTreeNodeImpl treeFalse = QuadTreeNodeImpl.buildFromIntArray(arrayFalse);
        assertEquals(treeFalse.isLeaf(), false);
    }

    @Test
    public void testDecompress() {
        int[][] array = {
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 1, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertArrayEquals(tree.decompress(), array);

    }

    @Test
    public void testGetCompressionRatio() {
        int[][] array = {
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertEquals(tree.getCompressionRatio(), 13.0 / 64.0, 0.0);
    }

    @Test
    public void testLeafGetColor() {
        int[][] array = {
                {0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertEquals(tree.getColor(7, 0), 1);
    }

    @Test
    public void test4x4() {
        int[][] array = {
                {1, 1, 1, 1},
                {1, 1, 0, 1},
                {0, 1, 1, 1},
                {1, 1, 1, 1}};
        int[][] newArray = {
                {1, 1, 1, 1},
                {1, 1, 0, 1},
                {0, 1, 1, 1},
                {0, 1, 1, 1}};
        QuadTreeNodeImpl tree = QuadTreeNodeImpl.buildFromIntArray(array);
        assertEquals(tree.getColor(3, 0), 1);
        tree.setColor(0, 3, 0);
        assertEquals(tree.getColor(0, 3), 0);
        assertArrayEquals(tree.decompress(), newArray);

    }


}
