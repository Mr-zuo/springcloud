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
//        s6_test1();
//        s7_test1();
//        s7_test2();//TODO
//        s8_test1();
        s8_test2();
//        s8_test3();
//        s10_test1();
    }

    /**
     * 给定一个整数数组 arr 和一个目标值 target，请你在该数组中找出加和等于目标值的那两个整数，并返回它们的在数组中下标。
     * 例如，arr = [1, 2, 3, 4, 5, 6]，target = 4。因为，arr[0] + arr[2] = 1 + 3 = 4 = target，则输出 0，2。
     */
    //时间复杂度O(n),空间复杂度O(n)
    public static void s10_test1(){
        int a[] = {1, 2, 3, 4, 5, 6};
        int target = 7;
        Map<Integer,Integer> map = new HashMap<>();
        for (int i=0;i<a.length;i++){
            map.put(a[i],i);
        }
        for (Integer key :map.keySet()){
                int j = target-key;
                if (key>j) break;
                if (map.containsKey(j)&&(!map.get(key).equals(map.get(j)))){
                    System.out.print(map.get(key));
                    System.out.print(",");
                    System.out.println(map.get(j));
                }
        }
    }

    /**
     * 给定一个字符串，逐个翻转字符串中的每个单词。例如，输入: "the sky is blue"，输出: "blue is sky the"。
     */
    //时间复杂度O(n),空间复杂度O(n)
    public static void s8_test3(){
        String a = "the sky is blue";
        String b = "";
        String[] s = a.split(" ");
        for (int i = 0;i<s.length;i++){
            b+=s[s.length-i-1];
            if (i!=s.length-1){
                b+=" ";
            }
        }
        System.out.println(b);
    }

    /**
     * 查找出两个字符串的最大公共字串。
     * 假设有且仅有 1 个最大公共子串。比如，输入 a = "13452439"， b = "123456"。
     * 由于字符串 "345" 同时在 a 和 b 中出现，且是同时出现在 a 和 b 中的最长子串。因此输出 "345"。
     */
    //时间复杂度O(nm2),空间复杂度O(1)
    public static void s8_test2(){
        String a = "123456";
        String b = "127456862439";
        String maxSubStr = "";
        int max_len = 0;
        for (int i =0; i<a.length();i++){
            for (int j =0; j<b.length();j++){
                if (a.charAt(i)==b.charAt(j)){
                    for (int m=i,n=j;m<a.length()&&n<b.length();m++,n++){
                        if (a.charAt(m)!=b.charAt(n)) break;
                        if (max_len<m-i+1){
                            max_len=m-i+1;
                            maxSubStr=a.substring(i,m+1);
                        }
                    }
                }
            }
        }
        System.out.println(maxSubStr);
    }

    /**
     * 从主串 s = "goodgoogle" 中找到 t = "google" 子串
     */
    //时间复杂度O(mn),空间复杂度O(1)
    public static void s8_test1(){
        String s = "goodgoogle";
        String t = "google";
        int isFind = 0;
        for (int i =0; i<s.length()-t.length()+1;i++){
            if (s.charAt(i)==t.charAt(0)){
                int jc = 0;
                for (int j =1; j<t.length();j++){
                    if (s.charAt(i+j)!=t.charAt(j)) break;
                    jc = j;
                }
                if (jc==t.length()-1){
                    isFind = 1;
                }
            }
        }
        System.out.println(isFind);
    }


    /**
     * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后的数组和新的长度，你不需要考虑数组中超出新长度后面的元素。
     * 要求，空间复杂度为 O(1)，即不要使用额外的数组空间。
     * 例如，给定数组 nums = [1,1,2]，函数应该返回新的长度 2，并且原数组 nums 的前两个元素被修改为 1, 2。
     * 又如，给定 nums = [0,0,1,1,1,2,2,3,3,4]，函数应该返回新的长度 5，并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
     */
    //时间复杂度O(n),空间复杂度O(1)
    public static void s7_test2(){
        //TODO
        int a[] = { 0,0,1,1,1,2,0,3,3,4 };
        List<Integer> list = new ArrayList<>();
        for (int i =0; i<a.length-1;i++){
            for (int j=i+1;j<a.length-1;j++){
                if (a[i]==a[j]){
                    for (;j<a.length-1;j++){
                        a[j]=a[j+1];
                    }
                }
            }
        }

        for (int i =0; i<a.length;i++){
            System.out.println(a[i]);
        }
    }


    /**
     * 假设数组存储了 5 个评委对 1 个运动员的打分，且每个评委的打分都不相等。现在需要你：
     * 1，用数组按照连续顺序保存，去掉一个最高分和一个最低分后的 3 个打分样本；
     * 2，计算这 3 个样本的平均分并打印。
     * 要求是，不允许再开辟 O(n) 空间复杂度的复杂数据结构。
     */
    //时间复杂度O(n),空间复杂度O(1)
    public static void s7_test1(){
        int a[] = { 2, 1, 4, 5, 3 };
        int b[] = { };
        int max_inx = -1;
        int max_val = -1;
        int min_inx= -1;
        int min_val = 99;
        int sum = 0;
        for(int i = 0; i < a.length; i++){
            if (a[i]>max_val){
                max_inx=i;
                max_val=a[i];
            }
            if (a[i]<min_val){
                min_inx=i;
                min_val=a[i];
            }
            sum+=a[i];
        }
        double avg = (sum-max_val-min_val)/3.0;
        System.out.println(avg);
    }

    //时间复杂度O(n),空间复杂度O(n)
    //约瑟夫环问题:这个问题的输入变量就是 n 和 m，即 n 个人和数到 m 的出列的人。输出的结果，就是 n 个人出列的顺序，从编号为 k 的人开始报数。
    public static void s6_test1(){
        int n =10;
        int m =5;
        LinkedList<Integer> q = new LinkedList<>();
        for (int i=1;i<=n;i++){
            q.add(i);
        }
        int k=2;
        int i=1;
        for (;i<k;i++){
            Integer element = q.poll();
            q.add(element);
        }
        i=1;
        while (q.size()>0){
            Integer p = q.poll();
            if (i<m){
                q.add(p);
                i++;
            }else {
                i=1;
                System.out.println(p);
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
            if ((i+1)%k==0||(i+1)==s.length()){
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
        int val = -1;
        for (int i = 0; i < a.length; i++) {
            if (map.containsKey(a[i])){
                map.put(a[i],map.get(a[i])+1);
            }else {
                map.put(a[i],1);
            }
            if (map.get(a[i])>val_max){
                val_max=map.get(a[i]);
                val = i;
            }
        }
        System.out.println(a[val]);
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
