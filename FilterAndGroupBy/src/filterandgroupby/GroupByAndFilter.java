/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filterandgroupby;

import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface GroupByAndFilter {
    public VisibleRows getVisibleRows();
    public Map<List<Object>, VisibleRows> getGroupByMap();
}
