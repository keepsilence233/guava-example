package qx.leizige.test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author leizige
 * 2022/01/12
 */
public class CollectionsTest extends BaseTest {

    @Test
    public void listsTest() {
        List<String> list1 = Lists.newArrayList();
        List<Integer> list2 = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7);
        List<String> list3 = Lists.newArrayList("1", "2", "3", "4", "5", "6");
        ArrayList<String> list4 = Lists.newArrayList(list3);
        // 对list做分区处理
        List<List<String>> partition = Lists.partition(list3, 2);
        //String转换成不可变更的ImmutableList<Character>
        ImmutableList<Character> characters = Lists.charactersOf("666");
        //反转
        List<String> reverse = Lists.reverse(list3);
        System.out.println("reverse = " + reverse);
    }


    @Test
    public void setsTest() {
        Set<String> set1 = Sets.newHashSet();
        Set<Integer> set2 = Sets.newHashSet(1, 2, 3, 4, 5, 6, 7);
        Set<String> set3 = Sets.newHashSet("1", "2", "3", "4", "5", "6");
        Set<String> set4 = Sets.newHashSet(set3);

        //创建一个空的Set
        Set<@Nullable Object> newIdentityHashSet = Sets.newIdentityHashSet();
        //交集
        Sets.SetView<? extends Serializable> union = Sets.union(set1, set2);
        //并集
        Sets.SetView<String> intersection = Sets.intersection(set1, set2);
        //差集
        Sets.SetView<String> difference = Sets.difference(set1, set2);
    }


    @Test
    public void mapsTest() {

    }
}
