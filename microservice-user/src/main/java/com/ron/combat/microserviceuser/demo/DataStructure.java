package com.ron.combat.microserviceuser.demo;

import java.util.*;

/**
 * @author zxl
 * @Description: 数据结构与算法
 * @date 2020/11/3
 */
public class DataStructure {

    public static void main(String[] args) {

//        s1_test1();
//        s1_test2();
//        s1_test3();
//        s2_test1();
//        s2_test2();
//        s4_test1();
//        s5_test1();
        s6_test1();
    }

    //约瑟夫环问题:这个问题的输入变量就是 n 和 m，即 n 个人和数到 m 的出列的人。输出的结果，就是 n 个人出列的顺序。
    public static void s6_test1(){
        int n =10;
        int m =5;
        LinkedList<Integer> q = new LinkedList<Integer>();
        for (int i = 1; i <= n; i++) {
            q.add(i);
        }
        int k = 2;
        int element = 0;
        int i = 0;
        for (; i<k; i++) {
            element = q.poll();
            q.add(element);
        }
        i = 1;
        while (q.size() > 0) {
            element = q.poll();
            if (i < m) {
                q.add(element);
                i++;
            } else {
                i = 1;
                System.out.println(element);
            }
        }
    }

    //时间复杂度O(n),空间复杂度O(n)
    //给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效
    public static void s5_test1(){
        String s = "{[()()]}}";
        Stack stack = new Stack();
        for (int i=0;i<s.length();i++){
            char curr = s.charAt(i);
            if (curr=='{'||curr=='['||curr=='('){
                stack.push(curr);
            }else {
                if (stack.empty()){
                    System.out.println("illegal");
                    break;
                }else {
                    char p = (char)stack.pop();
                    if (!((p == '{' && curr == '}') || (p == '[' && curr == ']') || (p == '(' && curr == ')'))){
                        System.out.println("illegal");
                        break;
                    }
                }
            }
        }
        if (stack.isEmpty()){
            System.out.println("success");
        }else {
            System.out.println("illegal");
        }


    }

    //时间复杂度O(n),空间复杂度O(1)
    //给定一个含有 n 个元素的链表，现在要求每 k 个节点一组进行翻转，打印翻转后的链表结果。其中，k 是一个正整数，且可被 n 整除
    //例如，链表为 1 -> 2 -> 3 -> 4 -> 5 -> 6，k = 3，则打印 321654
    public static void s4_test1(){
        String result = "";
        char a[];
        String s = "12345678";
        int k = 3;
        Stack stack = new Stack();
        for (int i = 0; i<s.length(); i++){
            char c = s.charAt(i);
            stack.push(c);
            if ((i+1)%3==0||(i+1)==s.length()){
                while (!stack.isEmpty()){
                    char p = (char) stack.pop();
                    result+=p;
                }
            }
        }
       System.out.println(result);
    }

    //时间复杂度O(n2),空间复杂度O(1)
    //假设有任意多张面额为 2 元、3 元、7 元的货币，现要用它们凑出 100 元，求总共有多少种可能性
    public static void s2_test2(){
        int count = 0;
        for (int i = 0; i < 100/7; i++){
            for (int j = 0; j < (100-7*i)/3; j++){
                if ((100-7*i-3*j>=0)&&(100-7*i-3*j)%2==0){
                    count+=1;
                }
            }
        }
        System.out.println(count);
    }

    //时间复杂度O(n),空间复杂度O(n)
    //查找出一个数组中，出现次数最多的那个元素的数值。例如，输入数组 a = [1,2,3,4,5,5,6 ] 中，查找出现次数最多的数值
    public static void s2_test1(){
        int a[] = { 1,2,3,4,5,5,6 };
        Map<Integer,Integer> map = new HashMap<>();
        int val_max = -1;
        for (int i = 0; i < a.length; i++) {
            if (map.containsKey(a[i])){
                map.put(a[i],map.get(a[i])+1);
            }else {
                map.put(a[i],1);
            }
            if (map.get(a[i])>val_max){
                val_max=map.get(a[i]);
            }
        }
        System.out.println(val_max);
    }

    //时间复杂度O(n2),空间复杂度O(1)
    //查找出一个数组中，出现次数最多的那个元素的数值。例如，输入数组 a = [ 1, 3, 4, 3, 4, 1, 3 ] 中，查找出现次数最多的数值
    public static void s1_test3(){
        int a[] = { 1, 3, 4, 3, 4, 1, 3 };
        int val_max = -1;
        int time_max = 0;
        int time_tmp = 0;
        for (int i = 0; i < a.length; i++) {
            time_tmp = 0;
            for (int j = 0; j < a.length; j++){
                if (a[i]==a[j]) time_tmp+=1;
            }
            if (time_tmp>time_max){
                time_max=time_tmp;
                val_max= a[i];
            }
        }
        System.out.println(val_max);
    }

    //时间复杂度O(n/2),空间复杂度O(1)
    //对于输入的数组，输出与之逆序的数组。例如，输入 a=[1,2,3,4,5]，输出 [5,4,3,2,1]。
    public static void s1_test2(){
        int a[] = { 1, 2, 3, 4, 5 };
        int temp = 0;
        for (int i = 0; i < (a.length/2); i++) {
            temp =  a[i];
            a[i] = a[a.length-i-1];
            a[a.length-i-1] = temp;
        }
        System.out.println(Arrays.toString(a));
    }

    //时间复杂度O(n),空间复杂度O(n)
    //对于输入的数组，输出与之逆序的数组。例如，输入 a=[1,2,3,4,5]，输出 [5,4,3,2,1]。
    public static void s1_test1(){
        int a[] = { 1, 2, 3, 4, 5 };
        int b[] = new int[5];
        for (int i = 0; i < a.length; i++) {
            b[a.length - i - 1] = a[i];
        }
        System.out.println(Arrays.toString(b));
    }
}
