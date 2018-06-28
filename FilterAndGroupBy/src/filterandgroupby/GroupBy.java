/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filterandgroupby;

import java.util.*;

/**
 *
 * @author admin
 */
public class GroupBy  implements GroupByAndFilter{
    private List<Integer>colList;
    private static Map<List<Object>,List<List<Integer>>>result;

    public GroupBy(List<Integer> colList, Map<List<Object>, List<List<Integer>>> result) {
        this.colList = colList;
        this.result = result;
    }

    public static Map<List<Object>, List<List<Integer>>> getResult() {
        return result;
    }
    
}
