package qx.leizige.test;

import com.google.common.collect.*;
import org.junit.Test;

/**
 * @author leizige
 * 2022/01/12
 * guava提供的新集合类型
 */
public class NewCollectionTest extends BaseTest {

    /**
     * Multiset:它可以多次添加相等的元素到Set里面去
     */
    @Test
    public void multiSetTest() {
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("1");
        multiset.add("1");
        multiset.add("2");
        System.out.println("multiset = " + multiset);
        System.out.println("multiset 1 count = " + multiset.count("1"));
        System.out.println("multiset 不重复元素集合 = " + multiset.elementSet());
        System.out.println("multiset size = " + multiset.size());
    }

    /**
     * BiMap它提供了key和value的双向关联的数据结构
     * BiMap有一个神奇的方法BiMap<V, K> inverse()．可以把Map里面的key，value反过来．BiMap里面key和value都是唯一的不能重复．
     */
    @Test
    public void biMapTest() {
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("BJ", "北京");
        biMap.put("SH", "上海");
        biMap.put("GZ", "广州");

        BiMap<String, String> inverse = biMap.inverse();
        System.out.println("inverse = " + inverse);
    }

    /**
     * table支持“row”和“column”
     * 由rowKey+columnKey+value组成它有两个键，一个值，和一个n行三列的数据表类似，n行取决于Table对对象中存储了多少个数据．
     */
    @Test
    public void tableTest() {
        //对于Table而言里面的每个value都有两个key(rowKey,columnKey)
        Table<String, String, String> table = HashBasedTable.create();
        table.put("广东省", "广州市", "天河区");
        table.put("上海市", "上海市", "闵行区");
        /**
         * 用元素类型为Table.Cell<R, C, V>的Set表现Table<R, C, V>。
         * Cell类似于Map.Entry，但它是用行和列两个键区分的。
         */
        table.cellSet().forEach(v -> {
            String rowKey = v.getRowKey();
            String columnKey = v.getColumnKey();
            String value = v.getValue();
            System.out.println("rowKey: " + rowKey + " columnKey: " + columnKey + " value: " + value);
        });
    }


    @Test
    public void rangeSetTest() {
//        RangeSet<Integer> immutableRangeSet = ImmutableRangeSet.of(1,2,3,4,5);
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10));
        System.out.println("rangeSet = " + rangeSet);
    }
}
