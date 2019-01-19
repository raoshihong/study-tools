package com.rao.study.tools.caffeine;

import com.github.benmanes.caffeine.cache.*;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.junit.Test;

import java.beans.Introspector;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CaffeineDemo {

    @Test
    public void simpleTest(){
//NodeFactory 能不的相关优化操作，实际上是通过NodeFactory对Node节点的操作, 所有缓存的操作都在BoundedLocalCache这个类
        Cache<String, Object> manualCache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES) //设置过期策略，会检测是否已经设置过了
                .maximumSize(10_000) //设置最大缓存空间,会先检查是否已经设置过了，避免之前的配置被覆盖
                .build();//构建同步缓存,先检测是否有权重设置,是否不需要重新加载缓存，是否有界

        //如果缓存中没有则返回null,有则返回对应的value
        Object obj = manualCache.getIfPresent("name");//从ConcurrentMap中获取缓存的数据,并通过StatsCounter来设置命中状态

        System.out.println(obj);

        //如果缓存缓存中有就直接返回对应的value,如果没有则使用后面的函数定义的value加载到缓存中,并返回
        Object obj1 = manualCache.get("name", new Function<String, Object>() {//找不到会通过NodeFactory创建一个Node节点来存储key-value,并为key和value创建reference引用
            @Override
            public Object apply(String s) {
                return "shihong";
            }
        });

        System.out.println(obj1);

        //添加缓存，计算key-value的权重值，使用ConcurrentHashMap存储key-value
        manualCache.put("name1","ssss");//key重复,后面的value会覆盖前面的value

        System.out.println(manualCache.getIfPresent("name1"));

        //清除缓存
        manualCache.invalidate("name");
        System.out.println(manualCache.getIfPresent("name"));

        manualCache.put("name","ss");
        manualCache.put("age",10);
        manualCache.put("birthday",new Date());

        //获取缓存的
        System.out.println(manualCache.estimatedSize());
    }

    //同步加载
    @Test
    public void synchTest(){

        LoadingCache<String, Map<String,String>> loadingCache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES) //设置过期策略，会检测是否已经设置过了
                .maximumSize(10_000)
//                .build(new CacheLoader<String, Object>() {
//                    @Override
//                    public Object load(String key) throws Exception {
//                        return null;
//                    }
//                });
                .build(key -> {
                    //定义缓存加载,当调用get(key)时,如果对应的key没值,则将loadMyConfig()中返回的value作为key对应的value存到缓存中并返回
                    //通过这里的key,我们可以定义不同的加载数据,比如if(key.equals("a")){} else if(key.equals("b")){}  这样的话，我们直接将loadingCache变量定义为 Loading<String,Object>
                    try{
                        return loadMyConfig();
                    }catch (Exception e){

                    }
                    return new ConcurrentHashMap<>();
                });//自定义加载缓存

        System.out.println(loadingCache.getIfPresent("name"));//这种不会调用我们自定义的CacheLoad
        System.out.println(loadingCache.get("name"));//这种会调用我们上面自定义的CacheLoad
        System.out.println(loadingCache.get("name"));//当上面加载过一次缓存了后,这里再次调用的话,就不会再去调用我们自定义的loadMyConfig()方法了,而是会直接从缓存中获取,只有当缓存中没数据时,才会重新去调用
        loadingCache.invalidate("name");
        System.out.println(loadingCache.get("name"));//因为上面将name对应的缓存置为无效了,所以再次去获取时发现没有,则重新调用loadMyConfig()

        //通过上面的方法,我们可以不用自己去判断是否有缓存的逻辑,比如没缓存就调用数据库,有就直接从缓存获取,这个逻辑直接让caffeine框架处理了
    }

    public Map<String,String> loadMyConfig(){

        Map<String,String> configs = new ConcurrentHashMap<>();
        configs.put("update","update student set name = 'fff' where id = 1");
        configs.put("delete","delete from student");

        return configs;
    }

    @Test
    public void synchTest1(){
        LoadingCache<String, Object> loadingCache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES) //设置过期策略，会检测是否已经设置过了
                .maximumSize(10_000)
                .build(key -> {
                    //定义缓存加载,当调用get(key)时,如果对应的key没值,则将loadMyConfig()中返回的value作为key对应的value存到缓存中并返回
                    //通过这里的key,我们可以定义不同的加载数据,比如if(key.equals("a")){} else if(key.equals("b")){}  这样的话，我们直接将loadingCache变量定义为 Loading<String,Object>
                    try{
                        if("name".equals(key)){
                            return loadMyConfig();
                        }else{
                            return loadValue(key);
                        }

                    }catch (Exception e){

                    }
                    return new ConcurrentHashMap<>();
                });//自定义加载缓存

        System.out.println(loadingCache.getIfPresent("name"));//这种不会调用我们自定义的CacheLoad,所以当缓存中没有时就会返回null
        System.out.println(loadingCache.get("name"));//这种会调用我们上面自定义的CacheLoad
        System.out.println(loadingCache.get("name"));//当上面加载过一次缓存了后,这里再次调用的话,就不会再去调用我们自定义的loadMyConfig()方法了,而是会直接从缓存中获取,只有当缓存中没数据时,才会重新去调用
        System.out.println(loadingCache.getIfPresent("name"));//在这里,因为缓存中有name的value值了,所以会获取到值
        loadingCache.invalidate("name");
        Map<String,String> ma = (Map<String, String>) loadingCache.get("name");
        System.out.println(ma);//因为上面将name对应的缓存置为无效了,所以再次去获取时发现没有,则重新调用loadMyConfig()
        String str = (String) loadingCache.get("aa");
        System.out.println(str);
    }

    public String loadValue(String key){
        Map<String,String> map = new HashMap<>();
        map.put("aa","ff");
        return map.get(key);
    }

    //异步加载
    @Test
    public void asynchTest(){

        AsyncLoadingCache<String, Object> asyncLoadingCache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
//                .buildAsync(new CacheLoader<String, Object>() {
//                    @Override
//                    public Object load(String key) throws Exception {
//                        return null;
//                    }
//                });
                .buildAsync(key -> {
//                    Thread.sleep(12*1000);
                    return loadMyConfig();
                });
//        .buildAsync(new AsyncCacheLoader<String, Object>() {
//            @Override
//            public CompletableFuture<Object> asyncLoad(String key, Executor executor) {
//                return null;
//            }
//        });//使用Executor自定义调度器

        //构建异步加载的方式

        //异步方式获取,使用future来获取异步返回的数据,通过future.get方法进行阻塞,当有数据返回时就会获取到数据
        CompletableFuture<Object> future = asyncLoadingCache.get("key1");

        try {
            Object o = future.get(10, TimeUnit.SECONDS);//表示等待10s,10s没有数据就返回null
            System.out.println(o);
        }catch (Exception e){
            System.out.println("获取失败");
            e.printStackTrace();
        }

        //异步转同步
        LoadingCache<String, Object> loadingCache = asyncLoadingCache.synchronous();
        System.out.println(loadingCache.get("key1"));
    }

    //缓存失效策略
    //Caffeine提供三类驱逐策略：基于大小（size-based），基于时间（time-based）和基于引用（reference-based）。
    @Test
    public void evictionTest(){

        //maximumSize 根据缓存的个数来设置
        //maximumWeight 设置权重大小
        //expire 基于时间的失效策略
        //weakKeys 基于引用
        /**
         * Caffeine提供了三种定时驱逐策略：
         *
         * expireAfterAccess(long, TimeUnit):在最后一次访问或者写入后开始计时，在指定的时间后过期。假如一直有请求访问该key，那么这个缓存将一直不会过期。
         * expireAfterWrite(long, TimeUnit): 在最后一次写入缓存后开始计时，在指定的时间后过期。
         * expireAfter(Expiry): 自定义策略，过期时间由Expiry实现独自计算。
         */

        LoadingCache<String, Object> loadingCache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .maximumWeight(10_000)
                .weigher((key, value) -> {//自定义权重的计算方式
                    return 1*1;
                })
                .weakKeys()  //当key没有引用时清除
               .softValues() //当内存不足时清除
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .build(key -> {
                    try{
                        if("name".equals(key)){
                            return loadMyConfig();
                        }else{
                            return loadValue(key);
                        }

                    }catch (Exception e){

                    }
                    return new ConcurrentHashMap<>();
                });

    }

    //使用监听器
    @Test
    public void listenerTest(){
        Cache<String, Object> cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .removalListener((key, value, cause) -> {
                    //对移除做监听处理
                    System.out.println("asdfasdf");
                })
                .build();

        cache.put("name","sss");
        System.out.println(cache.getIfPresent("name"));
        cache.invalidate("name");
    }

    //设置自动刷新
    @Test
    public void refreshTest() throws InterruptedException {
        LoadingCache<String, Object> loadingCache = Caffeine.newBuilder()
                .maximumSize(10_000)
//                .expireAfterAccess(1,TimeUnit.SECONDS)
                .refreshAfterWrite(1,TimeUnit.SECONDS) //表示在添加缓存后的固定间隔1s刷新缓存,如果不存在对应的key,则不会刷新这个key的值,刷新的话会调用我们自定义的loadMyConfig()去加载对应key的数据
                .build(key -> loadMyConfig1());

        System.out.println(loadingCache.get("name"));
        System.out.println(loadingCache.getIfPresent("name"));

        while (true){
            //因为上面每隔1s刷新一次,所以这里获取的时候会有不同的值
            Thread.sleep(1*1000);
            System.out.println(loadingCache.getIfPresent("name"));
        }



    }

    static boolean flag = true;
    public Map<String,String> loadMyConfig1(){

        if(flag){
            flag = false;
            Map<String,String> configs = new ConcurrentHashMap<>();
            configs.put("update","update student set name = 'fff' where id = 1");
            configs.put("delete","delete from student");
            return configs;
        }else{
            flag = true;
            Map<String,String> configs = new ConcurrentHashMap<>();
            configs.put("age","ffff");
            return configs;
        }
    }


    //对缓存进行统计
    @Test
    public void statsTest(){

        //recordStats 对缓存进行统计
        /**
         * 使用Caffeine.recordStats()，您可以打开统计信息收集。Cache.stats() 方法返回提供统计信息的CacheStats，如：
         *
         * hitRate()：返回命中与请求的比率
         * hitCount(): 返回命中缓存的总数
         * evictionCount()：缓存逐出的数量
         * averageLoadPenalty()：加载新值所花费的平均时间
         */

        Cache<String, Object> loadingCache = Caffeine.newBuilder()
                .recordStats().build();

        loadingCache.getIfPresent("name");
        loadingCache.put("name","sss");
        loadingCache.getIfPresent("name");

        CacheStats cacheStats = loadingCache.stats();
        System.out.println(cacheStats.hitCount());
        System.out.println(cacheStats.hitRate());
    }

    //手动清除缓存
    @Test
    public void cleanUpTest() {

        Cache<String, Object> loadingCache = Caffeine.newBuilder().build();

        loadingCache.getIfPresent("name");
        loadingCache.put("name", "sss");
        loadingCache.getIfPresent("name");

        loadingCache.cleanUp();
        System.out.println(loadingCache.getIfPresent("name"));
    }

    //在运行中获取策略
    @Test
    public void getTest(){
        Cache<String, Object> cache = Caffeine.newBuilder().build();
        cache.policy().eviction().ifPresent(eviction -> {
            eviction.setMaximum(2 * eviction.getMaximum());
        });
    }
}
