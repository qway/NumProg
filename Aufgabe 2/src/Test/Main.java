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
                new double[][]{{1, 0}, {0, 2}},
                new double[]{1, 0.5}));
        a.add(new Backsub(
                new double[]{-1, 1},
                new double[][]{{1, 0}, {0, 1}},
                new double[]{-1, 1}));
        a.add(new Backsub(
                new double[]{1, 1},
                new double[][]{{1, 0}, {0, 1}},
                new double[]{1, 1}));

        a.add(new Solve(
                new double[]{1, 1},
                new double[][]{{1, 0}, {0, 1}},
                new double[]{1, 1}));
//        a.add(new Solve(
//                new double[]{5, 6, 7},
//                new double[][]{ {3, 2, 1},
//                                {5, 1, 2},
//                                {2, 5, 3}},
//                new double[]{1, 1, 0}));
        a.add(new SolveSing(
                new double[][]{ {-0.5, 0.5},
                                {0.5, -0.5}},
                new double[]{1, 0}));
        a.add(new SolveSing(
                new double[][]{ {3, 2, 1},
                        {5, 1, 2},
                        {2, 5, 3}},
                new double[]{1, 1, 0}));









        a.forEach(e -> e.test());
    }
}
