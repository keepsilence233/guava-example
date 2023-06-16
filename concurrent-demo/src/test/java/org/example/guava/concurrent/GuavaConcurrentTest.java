package org.example.guava.concurrent;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuavaConcurrentTest {

    public static final ExecutorService executor = Executors.newFixedThreadPool(10);


    /**
     * 可监听的Future,带回调方法
     */
    @Test
    public void test1() {
        //定义监听执行方法
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executor);

        //定义可监听带返回值的任务
        ListenableFuture<String> listenableFuture = listeningExecutorService.submit(() -> {
//            throw new RuntimeException("execute error");
            return "666";
        });

        //添加回调,由指定监听服务来执行,监听可监听的future,监听到事件时执行对应回调方法
        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("success,result=" + result);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("fail! " + throwable.getMessage());
            }
        }, listeningExecutorService);

    }

    /**
     * 多任务并发执行取结果list
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executor);

        ListenableFuture<String> listenableFuture1 = listeningExecutorService.submit(() -> {
            return "2";
        });

        ListenableFuture<String> listenableFuture2 = listeningExecutorService.submit(() -> {
            Thread.sleep(3000);
            return "1";
        });


        ListenableFuture<List<String>> listListenableFutureList =
                Futures.allAsList(Lists.newArrayList(listenableFuture1, listenableFuture2));
        // 返回结果list就是添加任务的顺序
        System.out.println("多任务并发执行取结果list result=" + listListenableFutureList.get());
    }

}
