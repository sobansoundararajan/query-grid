/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.Grid;
import grid.Value;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import query.model.FunctionCondition;
import query.model.Function;
import query.model.QueriedRange;
import query.model.QueriedResult;

/**
 *
 * @author admin
 */
public class FunctionAction {
    private static Map<Function,Function<List<Value>,Value>>leafEvaluator=new EnumMap (Function.class);
    private static Map<Function,Function<List<Value>,Value>>interEvaluator=new EnumMap (Function.class);
    static{
        leafEvaluator.put(Function.SUM,(k)-> Functions.sum(k));
        leafEvaluator.put(Function.AVERAGE,(k)-> Functions.average(k));
        leafEvaluator.put(Function.MAXIMUM,(k)-> Functions.maximum(k));
        leafEvaluator.put(Function.MINIMUM,(k)-> Functions.minimum(k));
        leafEvaluator.put(Function.COUNT, (k)->Functions.count(k));
        interEvaluator.put(Function.SUM,(k)-> Functions.sum(k));
        interEvaluator.put(Function.AVERAGE,(k)-> Functions.average(k));
        interEvaluator.put(Function.MAXIMUM,(k)-> Functions.maximum(k));
        interEvaluator.put(Function.MINIMUM,(k)-> Functions.minimum(k));
        interEvaluator.put(Function.COUNT, (k)->Functions.sum(k));
    }
    public static void excute(Grid grid, QueriedRange range) throws Exception {
        QueriedResult queriedResult = range.getQueriedResult();
        FunctionAction.functionAction(grid, range, queriedResult);

    }

    private static void functionAction(Grid grid, QueriedRange range, QueriedResult queriedResult) throws Exception {
        if (queriedResult.getNextAction().isEmpty()) {
            for (Map.Entry<FunctionCondition, Value> entry : range.getQueriedResult().getFunctionMap().entrySet()) {
                FunctionCondition functionCondition = entry.getKey();
                Value value;
                List<Value>valueList=new LinkedList ();
                int col = functionCondition.getCol();
                Function function = functionCondition.getFunction();
                col += range.getStartCol();
                for (Integer row:queriedResult.getRow()) {
                    valueList.add(grid.get(row + range.getStartRow(), col));
                }
                queriedResult.getFunctionMap().put(functionCondition,leafEvaluator.get(function).apply(valueList));
            }
        } else {
            Map<FunctionCondition, List<Value>> refineMap = new HashMap();
            for (QueriedResult qr : queriedResult.getNextAction()) {
                FunctionAction.functionAction(grid, range, qr);
                for (Map.Entry<FunctionCondition, Value> entry : qr.getFunctionMap().entrySet()) {
                    refineMap.computeIfAbsent(entry.getKey(), k -> new LinkedList()).add(entry.getValue());
                }
            }
            for (Map.Entry<FunctionCondition, List<Value>> entry : refineMap.entrySet()) {
                FunctionCondition functionCondition = entry.getKey();
                queriedResult.getFunctionMap().put(functionCondition,interEvaluator.get(functionCondition.getFunction()).apply(entry.getValue()));
            }
        }
    }
}
