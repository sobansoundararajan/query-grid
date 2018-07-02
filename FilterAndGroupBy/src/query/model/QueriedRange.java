/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import query.model.GroupByAndFilter;
import java.util.*;

/**
 *
 * @author admin
 */
public class QueriedRange {
    public final int startRow;
    public final int endRow;
    public final int startCol;
    public final int endCol;

    private final ArrayList<GroupByAndFilter>result;
    public QueriedRange(int startRow,int endRow,int startCol,int endCol)
    {
        this.startRow=startRow;
        this.endRow=endRow;
        this.startCol=startCol;
        this.endCol=endCol;
        this.result=new ArrayList ();
    }
    public void addResult(GroupByAndFilter obj)
    {
        result.add(obj);
    }

    public ArrayList<GroupByAndFilter> getResult() {
        return result;
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
            for(int i=this.startRow;i<=this.endRow;i++)
            {
                temp.add(i);
            }
            vr.getVisibleRows().add(temp);
        }
        else
        {
            vr=this.result.get(result.size()-1).getVisibleRows();
        }
        return vr.getVisibleRows();
    }
}
