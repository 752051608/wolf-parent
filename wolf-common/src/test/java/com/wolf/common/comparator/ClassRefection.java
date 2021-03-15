package com.wolf.common.comparator;

/**
 * @description: d
 * @author: hon.g
 * @create: 2019-05-20 16:43
 **/

public class ClassRefection {
    // 通过运行下面示例，观察Class对象生成过程中，ClassInitialTest是否进行了初始化。
    public static void main(String[] args) {
        try {
            // ClassInitialTest不会初始化，无任何输出
            Class c = ClassInitialTest.class;
            System.out.println(".class---" + c);
//
            // 参数true表示对类ClassInitialTest进行初始化，否则不会
            Class cl1 = Class.forName("ClassInitialTest", true, ClassLoader.getSystemClassLoader());
            // 输出：---静态的参数初始化---
            System.out.println("param true，Class.forName--- " + cl1);


            Class cl2 = Class.forName("ClassInitialTest", false, ClassLoader.getSystemClassLoader());
            // ClassInitialTest不会初始化，无任何输出
            System.out.println("param false，Class.forName---" + cl2);

            Class cl3 = Class.forName("ClassInitialTest");  // 默认true进行初始化
            // 输出：---静态的参数初始化---
            System.out.println("Class.forName---" + cl3);

            ClassInitialTest classInitialTest = new ClassInitialTest();
            Class cla = classInitialTest.getClass();
            // 输出结果如下：
            // ---静态的参数初始化---
            // ---非静态的参数初始化---
            // ---构造函数---
            System.out.println("实例对象.getClass()---" + cla);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ClassInitialTest {

   public ClassInitialTest() {
       System.out.println("---构造函数---");
   }

   static {
       System.out.println("---静态的参数初始化---");
   }

   {
       System.out.println("---非静态的参数初始化---");
   }
}
