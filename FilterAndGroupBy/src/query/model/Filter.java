/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class Filter {

    private final Collection<FilterCondition> conList;

    public Filter(Collection<FilterCondition> conList) {
        this.conList = conList;
    }

/*    public void setQueriedResult(QueriedResult queriedResult) {
        this.queriedResult = queriedResult;
    }

    public QueriedResult getQueriedResult() {
        if(queriedResult==null)
               try {
                   throw new Exception("This Result is Reseted to null");
        } catch (Exception ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return queriedResult;
    }*/
    public Collection<FilterCondition> getConList() {
        return conList;
    }

}
