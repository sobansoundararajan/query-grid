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
public class GroupByResult{
    Map<List<Object>,VisibleRows>groupByMap;
    GroupByResult()
    {
        this.groupByMap=new HashMap();
    }
    public Map<List<Object>, VisibleRows> getGroupByMap() {
        return this.groupByMap;
    }   
    public VisibleRows getVisibleRows()
    {
        VisibleRows vr=new VisibleRows();
        for(Map.Entry<List<Object>,VisibleRows>map:this.groupByMap.entrySet())
        {
            vr.getVisibleRows().addAll(map.getValue().getVisibleRows());
        }
        return vr;
    }
}