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
public class FilterResult {
    VisibleRows vr;
    public FilterResult()
    {
        this.vr=new VisibleRows();
    }
    public void add(Set<Integer> e) {
        this.vr.getVisibleRows().add(e);
    }

    public VisibleRows getVisibleRows() {
        return vr;
    }
}
