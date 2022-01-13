package qx.leizige.test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * @author leizige
 * 2022/01/12
 * 不可变集合
 */
public class ImmutableCollectionTest extends BaseTest {

    @Test
    public void test() {
        //调用静态方法of创建[of()方法通过给定集合里面的元素来生成不可变集合]
        ImmutableList<String> ofImmutableList = ImmutableList.of("北京", "上海", "广州");
        System.out.println("ofImmutableList = " + ofImmutableList);


        //调用静态方法copyOf创建
        List<String> oldList = new ArrayList<>();
        oldList.add("北京");
        oldList.add("广州");
        oldList.add("上海");
        ImmutableSet<String> copyOfImmutableList = ImmutableSet.copyOf(oldList);
        System.out.println("copyOfImmutableList = " + copyOfImmutableList);

        //调用静态方法build创建
        ImmutableMap<String, String> buildImmutableMap = ImmutableMap.<String, String>builder()
                .put("BJ", "北京")
                .put("SH", "上海")
                .put("GZ", "广州").build();
        System.out.println("buildImmutableMap = " + buildImmutableMap);

    }

}
