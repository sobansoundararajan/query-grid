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
public class Filter  implements GroupByAndFilter{
    
    private final Collection<Condition>conList;
    private FilterResult filterResult;
    public Filter(Collection<Condition> conList) {
        this.conList = conList;
        this.filterResult=new FilterResult();
    }

    public void setFilterResult(FilterResult filterResult) {
        this.filterResult = filterResult;
    }

    @Override
    public VisibleRows getVisibleRows() {
        return filterResult.getVisibleRows();
    }

    @Override
    public Map<List<Object>, VisibleRows> getGroupByMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
