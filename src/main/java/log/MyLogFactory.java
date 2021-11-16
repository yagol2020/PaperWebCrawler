package log;

import cn.hutool.core.lang.caller.CallerUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * @author yagol
 * @TIME 2021/11/16 - 7:31 下午
 * @Description hutool的LogFactory的实现类，用于创建Swing Log
 **/
public class MyLogFactory extends LogFactory {

    MyLogFactory(Class<?> clazz) {
        this(clazz.getName());
    }

    /**
     * 构造
     *
     * @param name 日志框架名
     */
    public MyLogFactory(String name) {
        super(name);
    }

    @Override
    public Log createLog(String name) {
        return new MySwingTextAreaLog(name);
    }

    @Override
    public Log createLog(Class<?> clazz) {
        return new MySwingTextAreaLog(clazz);
    }

    public static MySwingTextAreaLog get() {
        return (MySwingTextAreaLog) LogFactory.setCurrentLogFactory(new MyLogFactory(CallerUtil.getCallerCaller())).getLog(CallerUtil.getCallerCaller());
    }

}
