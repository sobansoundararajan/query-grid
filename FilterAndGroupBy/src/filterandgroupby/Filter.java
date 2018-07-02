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
public class Filter  implements GroupByAndFilter{
    
    private final Collection<Condition>conList;
    private final FilterResult fr;
    public Filter(Collection<Condition> conList,FilterResult fr) {
        this.conList = conList;
        this.fr = fr;
    }

    @Override
    public VisibleRows getVisibleRows() {
        return fr.getVisibleRows();
    }

    @Override
    public Map<List<Object>, VisibleRows> getGroupByMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
