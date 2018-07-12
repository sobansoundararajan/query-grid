/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import java.util.Comparator;
import java.util.List;

/**
 *
 * @author admin
 */
public class FuctionCompare implements Comparator<QueriedResult>{

    private final List<FunctionCondition> functionConditionList;

    public FuctionCompare(List<FunctionCondition> functionConditionList) {
        this.functionConditionList = functionConditionList;
    }
    
    @Override
    public int compare(QueriedResult queriedResult1, QueriedResult queriedResult2) {
        for(FunctionCondition functionCondition:this.functionConditionList)
        {
            double val1=(double)queriedResult1.getFunctionMap().get(functionCondition).getValue();
            double val2=(double)queriedResult2.getFunctionMap().get(functionCondition).getValue();
            if(val1!=val2)
                return (int) (val1-val2);
        }
        return 0;
    }
    
}
