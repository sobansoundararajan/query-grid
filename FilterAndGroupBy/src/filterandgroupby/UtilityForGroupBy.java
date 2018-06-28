/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filterandgroupby;

import static filterandgroupby.Main.inputs;
import java.util.*;

/**
 *
 * @author admin
 */
public class UtilityForGroupBy {
    private Map<List<Object>,List<Integer>>store= new LinkedHashMap<List<Object>,List<Integer>>();
    private static boolean flage=true;
    private List<Object>resultKey=new LinkedList<Object>();
    public LinkedList<Integer> groupBy(Range r,List<Integer> colList)
    {
        List<Integer>input=r.getVisibleRows();
        List<LinkedList<Integer>>in=new LinkedList<LinkedList<Integer>>();
        in.add((LinkedList<Integer>) input);
        
        for(Integer tcol:colList)
        {
            List<LinkedList<Integer>>result=new LinkedList<LinkedList<Integer>>();
            Map<List<Object>,List<Integer>>tempstore= new LinkedHashMap<List<Object>,List<Integer>>();
            for(List<Integer> list:in)
            {
                Map<Object, LinkedList<Integer>>tMap=new LinkedHashMap<Object,LinkedList<Integer>>();
                LinkedList<Integer>tempResult=new LinkedList<Integer>();
                for(Integer row:list)
                {
                    Value v=(Value) inputs.get(row).get(tcol);
                    tempResult=(LinkedList<Integer>) tMap.get(v.getValue());
                    if(tempResult==null)
                    {
                        tempResult=new LinkedList<Integer>();
                        tempResult.add(row);
                        tMap.put(v.getValue(),tempResult);
                    }
                    else
                        tempResult.add(row);
                }
                
                for(Object key:tMap.keySet())
                {
                    if(flage)
                    {
                        LinkedList<Object>resultKey=new LinkedList<Object>();
                        resultKey.add(key);
                        store.put(resultKey,tMap.get(key));
                    }
                    else
                    {
                       
                        for(Map.Entry<List<Object>, List<Integer>>entry:store.entrySet())
                        {
                                resultKey=entry.getKey();
                                List<Integer>tvalue=entry.getValue();
                                
                                if(tvalue.containsAll(tMap.get(key)))
                                {
                                    List<Object>temp=new LinkedList<Object>();
                                    temp.addAll(resultKey);
                                    temp.add(key);
                                    tempstore.put(temp,tMap.get(key));
                                }
                        }
                        
                    } 
                   
                    result.add(tMap.get(key));
                }
               
            }
             if(!flage)
                    store=tempstore;
            if(flage)
                flage=false;
            in=result;
        } 
        GroupBy gb=new GroupBy(colList,store);
        Range.addResult(gb);
        input=new LinkedList<Integer>();
        for(List<Integer> t:in)
        input.addAll(t);
        return (LinkedList<Integer>) input;
    }
}
    

