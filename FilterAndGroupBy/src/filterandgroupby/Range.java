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

    private final LinkedList<GroupByAndFilter>result;
    Range(int sr,int er,int sc,int ec)
    {
        this.sr=sr;
        this.er=er;
        this.sc=sc;
        this.ec=ec;
        this.result=new LinkedList<GroupByAndFilter>();
    }
    public void addResult(GroupByAndFilter obj)
    {
        result.add(obj);
    }
    public GroupByAndFilter getLastOperation()
    {
        return this.result.get(this.result.size()-1);
    }
    public Set<Set<Integer>> getVisibleRows()
    {
        VisibleRows vr;
        if(this.result.isEmpty())
        {
            vr=new VisibleRows();
            Set<Integer>temp=new LinkedHashSet();
            for(int i=this.sr;i<=this.er;i++)
            {
                temp.add(i);
            }
            vr.getVisibleRows().add(temp);
        }
        else
        {
            vr=this.result.getLast().getVisibleRows();
        }
        return vr.getVisibleRows();
    }
}
