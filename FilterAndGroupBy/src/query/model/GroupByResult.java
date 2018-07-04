/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import java.util.*;

/**
 *
 * @author admin
 */
public class GroupByResult {

    private final Map<List<Object>, VisibleRows> groupByMap;

    public GroupByResult(Map<List<Object>, VisibleRows> groupByMap) {
        this.groupByMap = groupByMap;
    }

    public Map<List<Object>, VisibleRows> getGroupByMap() {
        return this.groupByMap;
    }

    public VisibleRows getVisibleRows() {
        Set<Set<Integer>> vr = new HashSet();
        for (Map.Entry<List<Object>, VisibleRows> map : this.groupByMap.entrySet()) {
            vr.addAll(map.getValue().getVisibleRows());
        }
        VisibleRows visibleRows = new VisibleRows(vr);
        return visibleRows;
    }
}
