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
public class VisibleRows {
    Set<Set<Integer>>visibleRows;

    public VisibleRows() {
        this.visibleRows =new HashSet ();
    }

    public void setVisibleRows(Set<Set<Integer>> visibleRows) {
        this.visibleRows = visibleRows;
    }

    public Set<Set<Integer>> getVisibleRows() {
        return visibleRows;
    }

}