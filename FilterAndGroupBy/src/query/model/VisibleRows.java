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
public class VisibleRows {

    private final Set<Set<Integer>> visibleRows;

    public VisibleRows(Set<Set<Integer>> visibleRows) {
        this.visibleRows = visibleRows;
    }

    public Set<Set<Integer>> getVisibleRows() {
        return visibleRows;
    }

}
