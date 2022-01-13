package qx.leizige.test;

import com.google.common.cache.CacheStats;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;


public class CacheTest extends BaseTest {


    @Before
    public void before(){
        super.before();
        cache.put("1","1");
        cache.put("2","3");
        cache.put("3","3");
    }

    @Test
    public void test() throws ExecutionException {

        ConcurrentMap<String, String> cacheMap = cache.asMap();
        System.out.println("cacheMap = " + cacheMap);

        //key(123)不存在会走MyCacheLoader方法获取value
        String s = cache.get("123");
        System.out.println("s = " + s);

        //获取缓存统计信息
        CacheStats stats = cache.stats();
        System.out.println("stats = " + stats);

        //移除缓存
        cache.invalidateAll();

        String ifPresent = cache.getIfPresent("1");
        System.out.println("ifPresent = " + ifPresent);
    }


}
