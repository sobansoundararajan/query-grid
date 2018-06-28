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
public class Range {
    final int sr;
    final int er;
    final int sc;
    final int ec;
    List<List<Integer>> visibleRows;
    private static final List<GroupByAndFilter>result=new LinkedList<GroupByAndFilter>();
    Range(int sr,int er,int sc,int ec)
    {
        this.sr=sr;
        this.er=er;
        this.sc=sc;
        this.ec=ec;
    }
    public static void addResult(GroupByAndFilter obj)
    {
        result.add(obj);
    }

    public void setVisibleRows(List<List<Integer>> visibleRows) {
        this.visibleRows=new LinkedList<List<Integer>>();
        this.visibleRows.addAll(visibleRows);
    }

    public List<List<Integer>> getVisibleRows() {
        return visibleRows;
    }
    
}
