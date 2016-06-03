package Test;

import java.util.ArrayList;

/**
 * Created by bene on 6/3/16.
 */
public class Main {

    public  static void main(String[] args){
        ArrayList<Test.Gauss> a = new ArrayList<>();
        a.add(new Backsub(
                new double[]{1, 1},
                new double[][]{{1, 0}, {0, 1}},
                new double[]{1, 1}));
        a.add(new Backsub(
                new double[]{1, 1},
                new double[][]{{1, 0}, {0, 1}},
                new double[]{1, 1}));
        a.add(new Backsub(
                new double[]{1, 1},
                new double[][]{{1, 0}, {0, 1}},
                new double[]{1, 1}));
        a.add(new Backsub(
                new double[]{1, 1},
                new double[][]{{1, 0}, {0, 1}},
                new double[]{1, 1}));

        a.add(new Solve(
                new double[]{1, 1},
                new double[][]{{1, 0}, {0, 1}},
                new double[]{1, 1}));








        a.forEach(e -> e.test());
    }
}
