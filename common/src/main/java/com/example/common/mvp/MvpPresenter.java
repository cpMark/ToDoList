package com.example.common.mvp;

/**
 * function：Mvp架构P层的顶层接口，用来约束类型
 * <p>
 * Mvp以中介者模式为骨架。中介者模式一共有以下几种角色：
 * （1）抽象中介者 -- 各同事之间的链接桥梁，只有通过中介者才能访问到别的同事，起到解耦的作用
 * （2）具体中介者 -- 抽象中介者的实现类
 * （3）抽象同事A、B、C...等，根据实际情况定义，Mvp的话两个抽象同事即可，分别对应M层和V层
 * （4）具体同事 -- 抽象同事的实现类
 * <p>
 * author by admin
 * create on 2018/5/17.
 */
public interface MvpPresenter<V extends MvpView> {

    /**
     *  绑定View，建议在生命周期初始化的时候调用
     */
    void attachView(V view);

    /**
     *  解绑View，在元素销毁的时候及时回收，否则可能导致空指针异常、内存泄漏等
     */
    void detachView();

    /**
     * 销毁功能(例如：网络请求停止、数据库查询停止等等...)
     */
    void destory();

}
