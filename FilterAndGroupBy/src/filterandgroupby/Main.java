/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filterandgroupby;

import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 *
 * @author admin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static Scanner scanner=new Scanner(System.in);
  //  public static ArrayList<ArrayList<Value>>inputs=new ArrayList<ArrayList<Value>>();
    static List<ConditionsEnum>conditions=new ArrayList<ConditionsEnum>();
    static
    {
                    conditions.add(ConditionsEnum.Equals);
                    conditions.add(ConditionsEnum.DoesNotEquals);
                    conditions.add(ConditionsEnum.GreaterThan);
                    conditions.add(ConditionsEnum.GreaterThanOrEqualTo);
                    conditions.add(ConditionsEnum.LessThan);
                    conditions.add(ConditionsEnum.LessThanOrEqualTo);
                    conditions.add(ConditionsEnum.BeginesWith);
                    conditions.add(ConditionsEnum.DoesNotBeginWith);
                    conditions.add(ConditionsEnum.EndsWith);
                    conditions.add(ConditionsEnum.DoesNotEndsWith);
                    conditions.add(ConditionsEnum.Contains);
                    conditions.add(ConditionsEnum.DoesNotContains);
                    conditions.add(ConditionsEnum.Matches);
                    conditions.add(ConditionsEnum.DoesNotMatchs);
    }
    public static void print(Grid grid,Range range)
    {
       for(Set<Integer>p:range.getVisibleRows())
       {
           for(Integer row:p)
           {
               for(int col=range.startCol;col<=range.endCol;col++)
               {
                    Value v=grid.get(row, col);
                    System.out.print(v.getValue()+" ");
               }
               System.out.println();
           }
       }
    }

    public static void filter(Grid grid,Range range)
    {
        ArrayList<Condition>conList=new ArrayList ();
        int op=1;
        while(op!=0)
        {
            System.out.println("Enter the col Number");
            int col=scanner.nextInt();
            for(int i=0;i<conditions.size();i++)
            {
                System.out.println(i+"-"+conditions.get(i));
            }
            int con=scanner.nextInt();
            scanner.nextLine();
            String condition=scanner.nextLine();
            Condition c=new Condition(col+range.startCol,conditions.get(con),condition);
            conList.add(c);
            System.out.println("0-Finish");
            op=scanner.nextInt();
            scanner.nextLine();
        }
        UtilityForFilter.filter(grid,range,conList);
    }
    public static void groupBy(Grid grid,Range range)
    {
        System.out.print("Enter the col Number");
        String input=scanner.nextLine();
        System.out.print(input);
        String [] inArr=input.split(" ");
        Collection<Integer>colList=new LinkedList ();
        for (String inArr1 : inArr) {
            colList.add(Integer.valueOf(inArr1) + range.startCol);
        }
        UtilityForGroupBy.groupBy(grid,range,colList);
    }
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        // TODO code application logic here
         BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\admin\\Desktop\\CSVinputs.csv"));
  
         
         LinkedList<DateFormat>dfList=new LinkedList ();
		dfList.add(DateFormat.d);
		dfList.add(DateFormat.m);
		dfList.add(DateFormat.y);
         String line;
         Grid grid=new Grid();
         int r=0;
         while((line=br.readLine())!=null)
         {
             Value v;
             line=line.substring(1,line.length()-1);
             String []in=line.split("\",\"");
             for(int i=0;i<in.length;i++)
             {
                v=Source.TypeandValue(in[i],dfList);
                grid.set(r,i,v);
             }
             
             r++;
         }
        
         System.out.println("Enter the range");
         int startRow,endRow,startCol,endCol;
         startRow=scanner.nextInt();
         endRow=scanner.nextInt();
         startCol=scanner.nextInt();
         endCol=scanner.nextInt();
         Range range=new Range(startRow,endRow,startCol,endCol);
         Main.print(grid,range);
         int op=4;
         while(op!=0)
         {
             System.out.println("1-Filter\n2-GroupBy\n0-Exit");
             op=scanner.nextInt();
             scanner.nextLine();
            if(op==1)
            {
                Main.filter(grid,range);
            }
            else if(op==2)
            {
                Main.groupBy(grid,range);
                
            }   
              // Main.print(r);
         }
         Main.printFinalResult(grid,range);
    }

    private static void printFinalResult(Grid grid,Range range) {
        ArrayList<GroupByAndFilter>result=range.getResult();
        int opNum=1;
        for(GroupByAndFilter gnf:result)
        {
            if(gnf instanceof Filter)
            {
                System.out.println(opNum+"#Filter");
                System.out.println(gnf.getVisibleRows().visibleRows);
            }
            else if(gnf instanceof GroupBy)
            {
                System.out.println(opNum+"#GroupBy");
                Map<List<Object>, VisibleRows>map=gnf.getGroupByMap();
                for(Map.Entry<List<Object>,VisibleRows>entry:map.entrySet())
                {
                    System.out.println(entry.getKey());
                    System.out.println(entry.getValue().getVisibleRows());
                }
            }
            opNum++;
            System.out.println();
            System.out.println();
            System.out.println();
        }
        
    }
    
}
