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
    
    private List<Condition>conList;
    private List<Integer>result;

    public Filter(List<Condition> conList, List<Integer> result) {
        this.conList = conList;
        this.result = result;
    }
    
}
