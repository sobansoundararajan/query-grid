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
public class GroupBy implements GroupByAndFilter {

    private final Collection<Integer> colList;
    private GroupByResult groupByResult;

    public GroupBy(Collection<Integer> colList) {
        this.colList = colList;
    }

    public void setGroupByResult(GroupByResult groupByResult) {
        this.groupByResult = groupByResult;
    }

    @Override
    public VisibleRows getVisibleRows() {
        return groupByResult.getVisibleRows();
    }

    @Override
    public Map<List<Object>, VisibleRows> getGroupByMap() {
        return groupByResult.getGroupByMap();
    }

}
