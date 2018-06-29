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
    private final Collection<Integer>colList;
    private final GroupByResult g;

    public GroupBy(Collection<Integer> colList, GroupByResult g) {
        this.colList = colList;
        this.g=g;
    }
    @Override
    public VisibleRows getVisibleRows() {
        return g.getVisibleRows();
    }
    

}
    
