package qx.leizige.test;

import com.google.common.cache.*;
import org.junit.Before;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    LoadingCache<String, String> cache;

    @Before
    public void before() {
        cache = CacheBuilder.newBuilder()
                //设置并发级别为8，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(8)
                //设置缓存容器的初始容量为10
                .initialCapacity(10)
                //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(100)
                //是否需要统计缓存情况,该操作消耗一定的性能,生产环境应该去除
//                .recordStats()
                //设置写缓存后n秒钟过期
                .expireAfterWrite(10, TimeUnit.SECONDS)
                //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
                //.expireAfterAccess(17, TimeUnit.SECONDS)
                //只阻塞当前数据加载线程，其他线程返回旧值
                //.refreshAfterWrite(13, TimeUnit.SECONDS)
                //设置缓存的移除通知
                .removalListener(new MyRemovalListener())
                //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
                .build(new MyCacheLoader());
    }

    /**
     * 缓存移除监听器
     */
    public static class MyRemovalListener implements RemovalListener<String, String> {
        @Override
        public void onRemoval(RemovalNotification<String, String> notification) {
            System.out.println("key:" + notification.getKey() + " " + "value:" + notification.getValue() + " 被移除,原因:" + notification.getCause());
        }
    }

    /**
     * 随机缓存加载,实际使用时应实现业务的缓存加载逻辑,例如从数据库获取数据
     */
    public static class MyCacheLoader extends CacheLoader<String, String> {
        /**
         * 当调用LoadingCache的get方法时，如果缓存不存在对应key的记录，则CacheLoader中的load方法会被自动调用从外存加载数据，
         * load方法的返回值会作为key对应的value存储到LoadingCache中，并从get方法返回。
         *
         * @param key key
         * @return key对应的value
         * @throws Exception
         */
        @Override
        public String load(String key) throws Exception {
            System.out.println(Thread.currentThread().getName() + " 加载数据开始");
            TimeUnit.SECONDS.sleep(2);
            Random random = new Random();
            System.out.println(Thread.currentThread().getName() + " 加载数据结束");
            return "value:" + random.nextInt(10000);
        }
    }

}
