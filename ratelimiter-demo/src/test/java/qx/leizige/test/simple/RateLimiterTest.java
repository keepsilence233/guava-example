package qx.leizige.test.simple;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import qx.leizige.test.BaseTest;

import java.util.concurrent.TimeUnit;


@Slf4j
public class RateLimiterTest extends BaseTest {

    /**
     * Guava的 RateLimiter提供了令牌桶算法实现：平滑突发限流(SmoothBursty)和平滑预热限流(SmoothWarmingUp)实现
     */
    RateLimiter r = RateLimiter.create(666);

    @Before
    public void before() {
    }

    @After
    public void after() {

    }

    /**
     * 平滑突发限流
     */
    @Test
    public void testSmoothBursty() {
        /**
         * 使用 RateLimiter的静态方法创建一个限流器，设置每秒放置的令牌数为5个。
         * 返回的RateLimiter对象可以保证1秒内不会给超过5个令牌，并且以固定速率进行放置，达到平滑输出的效果。
         */
        RateLimiter rateLimiter = RateLimiter.create(5);
        while (true) {
            System.out.println("get tokens " + rateLimiter.acquire() + "s");
            /**
             * output: 基本上都是0.2s执行一次,符合一秒发放5个令牌的设定
             * get tokens 0.0s
             * get tokens 0.198995s
             * get tokens 0.198144s
             * get tokens 0.199221s
             * get tokens 0.200246s
             * get tokens 0.187142s
             * get tokens 0.199213s
             * get tokens 0.200017s
             * get tokens 0.199608s
             * get tokens 0.196247s
             * get tokens 0.200518s
             * get tokens 0.199315s
             * get tokens 0.200152s
             */
        }
    }

    /**
     * 平滑预热限流
     * RateLimiter的 SmoothWarmingUp是带有预热期的平滑限流，它启动后会有一段预热期，逐步将分发频率提升到配置的速率。
     */
    @Test
    public void testSmoothwarmingUp() {
        /**
         * 创建一个平均分发令牌速率为2，预热期为3分钟。由于设置了预热时间是3秒，令牌桶一开始并不会0.5秒发一个令牌，而
         * 是形成一个平滑线性下降的坡度，频率越来越高，在3秒钟之内达到原本设置的频率，以后就以固定的频率输出。这种功能适合系统刚启动需要一点时间来“热身”的场景。
         */
        RateLimiter rateLimiter = RateLimiter.create(2, 3, TimeUnit.SECONDS);

        while (true) {
            System.out.println("get tokens " + rateLimiter.acquire(1) + "s");
            System.out.println("get tokens " + rateLimiter.acquire(1) + "s");
            System.out.println("get tokens " + rateLimiter.acquire(1) + "s");
            System.out.println("get tokens " + rateLimiter.acquire(1) + "s");
            System.out.println("end");
        }

        /**
         * output
         * get tokens 0.0s
         * get tokens 1.332133s
         * get tokens 0.99714s
         * get tokens 0.666395s 上边三次获取的时间相加正好为3秒
         * end
         * get tokens 0.499628s 正常速率0.5秒一个令牌
         * get tokens 0.496153s
         * get tokens 0.500036s
         * get tokens 0.493558s
         * end
         */
    }

}
