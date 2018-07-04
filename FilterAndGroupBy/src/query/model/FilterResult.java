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

    private final VisibleRows visibleRows;

    public FilterResult(VisibleRows visibleRows) {
        this.visibleRows = visibleRows;
    }

    public VisibleRows getVisibleRows() {
        return visibleRows;
    }
}
