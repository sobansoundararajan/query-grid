/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import java.util.*;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class GroupBy {

    private final Collection<Integer> colList;
    //private QueriedResult queriedResult;

    public GroupBy(Collection<Integer> colList) {
        this.colList = colList;
    }

/*    public void setQueriedResult(QueriedResult queriedResult) {
        this.queriedResult = queriedResult;
    }*/

    public Collection<Integer> getColList() {
        return colList;
    }
 /*   public QueriedResult getQueriedResult() {
        if(queriedResult==null)
               try {
                   throw new Exception("This Result is Reseted to null");
        } catch (Exception ex) {
            Logger.getLogger(GroupBy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.queriedResult;
    }*/

}
