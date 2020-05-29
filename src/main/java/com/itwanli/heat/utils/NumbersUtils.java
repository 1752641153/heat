package com.itwanli.heat.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

public class NumbersUtils {

    public static Integer[] getNumArray(int a, int b){
        Integer[] num=new Integer[a];
        Random r=new Random();
        for(int i=0;i<a;){
            int temp=r.nextInt(b);
            if(!Arrays.asList(num).contains(temp)){
                num[i]=temp;
                i++;
            }
        }
        return num;
    }

    public static BigDecimal getTemperature(){
        float Max = 36, Min = 40;
        BigDecimal bd = new BigDecimal(Math.random() * (Max - Min) + Min);
        BigDecimal num = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        //System.out.println(bd.setScale(2, BigDecimal.ROUND_HALF_UP));// 保留2位小数并四舍五入
        return num;
    }

    public static void main(String[] args) {
        System.out.println(getTemperature());
        //System.out.println(getNumArray(27 ,30));
    }

}
