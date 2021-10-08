import java.io.*;
public class Rover {
    public static int INF = Integer.MAX_VALUE / 2; // бесконечность
    public static void calculateRoverPath(int[][] map) {
        int M = map[0].length;
        int N = map.length;
        int n = map.length * map[0].length; // размер основного графа
        final int[][] tree_natural = new int[n][n]; // основной граф

        for (int i = 0; i < tree_natural.length; i++) {
            for (int i1 = 0; i1 < tree_natural[0].length; i1++) {
                tree_natural[i][i1] = INF;
            }
            tree_natural[i][i] = 0;
        }


        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (j + 1 < map[0].length) { // right
                    tree_natural[i * map[0].length + j][j + i * map[0].length + 1] = (Math.abs(map[i][j] - map[i][j + 1]) + 1);
                }
                if (i + 1 < map.length) { // down
                    tree_natural[i * map[0].length + j][j + (i * map[0].length) + map[0].length] = (Math.abs(map[i][j] - map[i + 1][j]) + 1);
                }
                if (j - 1 >= 0) { // left
                    tree_natural[i * map[0].length + j][j + i * map[0].length - 1] = (Math.abs(map[i][j] - map[i][j - 1]) + 1);
                }
                if (i - 1 >= 0) { // up
                    tree_natural[i * map[0].length + j][j + (i * map[0].length) - map[0].length] = (Math.abs(map[i][j] - map[i - 1][j]) + 1);
                }
            }
        }

        Dekst(tree_natural, n, M, N); //Алгоритм Дейкстра
    }



    private static void Dekst(int[][] a, int veriecesCount, int M, int N) {
        int[] d = new int[veriecesCount];
        int[] v = new int[veriecesCount];
        int temp, minindex, min = 0;
        int begin_index = 0;

        for (int i = 0; i<veriecesCount; i++)
        {
            d[i] = INF;
            v[i] = 1;
        }
        d[begin_index] = 0;
        // Шаг алгоритма
        do {
            minindex = INF;
            min = INF;
            for (int i = 0; i<veriecesCount; i++) {
                if ((v[i] == 1) && (d[i]<min)) {
                    min = d[i];
                    minindex = i;
                }
            }
            if (minindex != INF) {
                for (int i = 0; i<veriecesCount; i++) {
                    if (a[minindex][i] > 0) {
                        temp = min + a[minindex][i];
                        if (temp < d[i]) {
                            d[i] = temp;
                        }
                    }
                }
                v[minindex] = 0;
            }
        } while (minindex < INF);
        int[] ver = new int[veriecesCount];
        int end = veriecesCount - 1;
        ver[0] = end + 1;
        int k = 1;
        int weight = d[end];

        while (end != begin_index) {
            for (int i = 0; i<veriecesCount; i++)
                if (a[i][end] != 0) {
                    temp = weight - a[i][end];
                    if (temp == d[i]) {
                        weight = temp;
                        end = i;
                        ver[k] = i + 1;
                        k++;
                    }
                }
        }
        String path = "";
        int steps = 0;
        for (int i = k - 1; i >= 0; i--) {
            for (int j = 1; j < N + 1; j++) {
                if (ver[i] <= j * M) {
                    path += "[" + (j - 1) + "][" + (ver[i] - (M * j) + M - 1) + "]";
                    break;
                }else {
                    continue;
                }

            }
            if (i != 0){
                path += "->";
                steps++;
            }
        }
        try(FileWriter writer = new FileWriter("path-plan.txt", false))
        {
            writer.write(path + "\n");
            writer.write("steps: " + steps + "\n");
            writer.write("fuel: " + d[veriecesCount - 1] + "\n");
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }



    }
}




