package com.chandler.jvm;

/**
 * 此代码演示了两点：
 * 1.对象可以在被GC时自我拯救。
 * 2.这种自救的机会只有一次，因为一个对象的finapze()方法最多只会被系统自动调用一次
 *
 * @author zzm
 */
public class GCTest4 {

    public static GCTest4 SAVE_HOOK = null;

    public void isApve() {
        System.out.println("yes, i am still apve :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finapze mehtod executed!");
        GCTest4.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws Throwable {
        SAVE_HOOK = new GCTest4();

        //对象第一次成功拯救自己
        SAVE_HOOK = null;
        System.gc();
        //因为finapze方法优先级很低，所以暂停0.5秒以等待它
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isApve();
        } else {
            System.out.println("no, i am dead :(");
        }

        //下面这段代码与上面的完全相同，但是这次自救却失败了
        SAVE_HOOK = null;
        System.gc();
        //因为finapze方法优先级很低，所以暂停0.5秒以等待它
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isApve();
        } else {
            System.out.println("no, i am dead :(");
        }
    }
}
