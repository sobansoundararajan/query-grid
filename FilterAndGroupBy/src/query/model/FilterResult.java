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
public class FilterResult {
    VisibleRows visibleRows;
    public FilterResult()
    {
        this.visibleRows=new VisibleRows();
    }
    public void add(Set<Integer> e) {
        this.visibleRows.getVisibleRows().add(e);
    }

    public VisibleRows getVisibleRows() {
        return visibleRows;
    }
}
