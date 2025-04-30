package university;

public class Util {
    
    public static int count(int[] arr) {
		int count = 0;
		for (int value : arr) {
			if (value>0) {
				count++;
			}
		}
		return count;
	}

    public static int count(int[][] matrix, int columnIndex) {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][columnIndex]>0) {
                count++;
            }
        }
        return count;
    }

    public static int count(int[][] matrix, int columnIndex, int threshold) {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][columnIndex]>threshold) {
                count++;
            }
        }
        return count;
    }

    public static int avg(){return 0;}
}
