package qx.leizige.test.simple;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * RateLimiter使用,模拟每秒最多五个请求
 */
public class RateLimiterUse {

    private static final int THREAD_COUNT = 25;


    @Test
    public void test() {
        /**
         * 每秒放置5个令牌,即200ms会向令牌桶中放置一个令牌
         */
        RateLimiter rateLimiter = RateLimiter.create(5);

        Thread[] ts = new Thread[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            ts[i] = new Thread(new RateLimiterThread(rateLimiter), "RateLimiterThread = " + i);
        }

        for (int i = 0; i < THREAD_COUNT; i++) {
            ts[i].start();
        }

        for (; ; ) ;

    }

    public class RateLimiterThread implements Runnable {

        private RateLimiter rateLimiter;

        public RateLimiterThread(RateLimiter rateLimiter) {
            this.rateLimiter = rateLimiter;
        }

        @Override
        public void run() {
            /**
             * acquire是阻塞的且会一直等待到获取令牌为止，它有一个返回值为double型，意思是从阻塞开始到获取到令牌的等待时间，单位为秒
             * tryAcquire是另外一个方法，它可以指定超时时间，返回值为boolean型，即假设线程等待了指定时间后仍然没有获取到令牌，那么就会返回给客户端false，客户端根据自身情况是打回给前台错误还是定时重试
             */
            rateLimiter.acquire(1);

            System.out.println(Thread.currentThread().getName() + "获取到了令牌,时间 = " + LocalDateTime.now());
        }

    }

    /**
     * 在每次消耗一个令牌的情况下，RateLimiter可以保证每一秒内最多只有5个线程获取到令牌，使用这种方式可以很好的做单机对请求的QPS数控制
     * RateLimiterThread = 0获取到了令牌,时间 = 2022-01-12T10:51:54.001
     * RateLimiterThread = 1获取到了令牌,时间 = 2022-01-12T10:51:54.126
     * RateLimiterThread = 4获取到了令牌,时间 = 2022-01-12T10:51:54.326
     * RateLimiterThread = 5获取到了令牌,时间 = 2022-01-12T10:51:54.526
     * RateLimiterThread = 8获取到了令牌,时间 = 2022-01-12T10:51:54.724
     * RateLimiterThread = 9获取到了令牌,时间 = 2022-01-12T10:51:54.923
     * RateLimiterThread = 12获取到了令牌,时间 = 2022-01-12T10:51:55.120
     * RateLimiterThread = 2获取到了令牌,时间 = 2022-01-12T10:51:55.320
     * RateLimiterThread = 3获取到了令牌,时间 = 2022-01-12T10:51:55.521
     * RateLimiterThread = 6获取到了令牌,时间 = 2022-01-12T10:51:55.720
     * RateLimiterThread = 7获取到了令牌,时间 = 2022-01-12T10:51:55.921
     * RateLimiterThread = 10获取到了令牌,时间 = 2022-01-12T10:51:56.120
     * RateLimiterThread = 11获取到了令牌,时间 = 2022-01-12T10:51:56.320
     * RateLimiterThread = 14获取到了令牌,时间 = 2022-01-12T10:51:56.520
     * RateLimiterThread = 15获取到了令牌,时间 = 2022-01-12T10:51:56.720
     * RateLimiterThread = 18获取到了令牌,时间 = 2022-01-12T10:51:56.921
     * RateLimiterThread = 19获取到了令牌,时间 = 2022-01-12T10:51:57.120
     * RateLimiterThread = 22获取到了令牌,时间 = 2022-01-12T10:51:57.320
     * RateLimiterThread = 23获取到了令牌,时间 = 2022-01-12T10:51:57.520
     * RateLimiterThread = 16获取到了令牌,时间 = 2022-01-12T10:51:57.720
     * RateLimiterThread = 13获取到了令牌,时间 = 2022-01-12T10:51:57.921
     * RateLimiterThread = 17获取到了令牌,时间 = 2022-01-12T10:51:58.122
     * RateLimiterThread = 20获取到了令牌,时间 = 2022-01-12T10:51:58.320
     * RateLimiterThread = 21获取到了令牌,时间 = 2022-01-12T10:51:58.520
     * RateLimiterThread = 24获取到了令牌,时间 = 2022-01-12T10:51:58.720
     */

}
