package com.hsw.motionjava;

import android.util.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * @author heshuai
 * created on: 2020/6/24 3:26 PM
 * description:
 */
public class Cheese {

    public static List<Integer> IMAGE_LIST = Arrays.asList(R.drawable.cheese_1,
            R.drawable.cheese_2,
            R.drawable.cheese_3,
            R.drawable.cheese_4,
            R.drawable.cheese_5);

    public static List<Pair> IMAGE_SIZES = Arrays.asList(new Pair(640, 640),
            new Pair(640, 589),
            new Pair(640, 480),
            new Pair(1024, 683),
            new Pair(1024, 683));
}
