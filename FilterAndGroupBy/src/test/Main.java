/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import grid.DataTypes;
import query.control.*;
import query.model.*;
import grid.DateFormat;
import grid.Grid;
import grid.StringValue;
import grid.Value;
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
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Condition> conditions = new LinkedHashMap();

    static {
        conditions.put("equals", Condition.EQUALS);
        conditions.put("does not equals", Condition.DOESNOTEQUALS);
        conditions.put("greater than", Condition.GREATERTHAN);
        conditions.put("greater than or equal to", Condition.GREATERTHANOREQUALTO);
        conditions.put("less than", Condition.LESSTHAN);
        conditions.put("less than or equal to", Condition.LESSTHANOREQUALTO);
        conditions.put("begines with", Condition.BEGINESWITH);
        conditions.put("does not begines with", Condition.DOESNOTBEGINWITH);
        conditions.put("ends with", Condition.ENDSWITH);
        conditions.put("does not ends with", Condition.DOESNOTENDSWITH);
        conditions.put("contains", Condition.CONTAINS);
        conditions.put("does not contains", Condition.DOESNOTCONTAINS);
        conditions.put("matches", Condition.MATCHES);
        conditions.put("does not matches", Condition.DOESNOTMATCHES);
    }

    private static void print(QueriedResult queriedResult) {
        if (queriedResult.getNextAction().isEmpty()) {
            System.out.println(queriedResult.getValue() + " " + queriedResult.getRow());
            Map<FunctionCondition, Value> functionMap = queriedResult.getFunctionMap();
            if (!functionMap.isEmpty()) {
                for (Map.Entry<FunctionCondition, Value> entry : functionMap.entrySet()) {
                    System.out.println(entry.getKey().getFunction() + " " + entry.getKey().getCol() + " " + entry.getValue().getValue());
                }
            }
        } else {
            System.out.print(queriedResult.getValue() + " ");
            Map<FunctionCondition, Value> functionMap = queriedResult.getFunctionMap();
            if (!functionMap.isEmpty()) {
                for (Map.Entry<FunctionCondition, Value> entry : functionMap.entrySet()) {
                    System.out.println(entry.getKey().getFunction() + " " + entry.getKey().getCol() + " " + entry.getValue().getValue());
                }
            }
            for (QueriedResult vn : queriedResult.getNextAction()) {
                Main.print(vn);
            }
        }
    }

    private static void filter(Grid grid, QueriedRange range) throws Exception {
        Collection<FilterCondition> conList = new ArrayList();
        int op = 1;
        while (op != 0) {
            System.out.println("Enter the col Number");
            int col = scanner.nextInt();
            scanner.nextLine();
            for (String str : conditions.keySet()) {
                System.out.println(str);
            }
            String condition = scanner.nextLine().toLowerCase();
            String value = scanner.nextLine();
            FilterCondition c = new FilterCondition(col, conditions.get(condition), value);
            conList.add(c);
            System.out.println("0-Finish");
            op = scanner.nextInt();
            scanner.nextLine();
        }
        FilterAction f = new FilterAction();
        f.filter(grid, range, conList);
    }

    private static void groupBy(Grid grid, QueriedRange range) throws Exception {
        System.out.print("Enter the col Number");
        String input = scanner.nextLine();
        String[] inArr = input.split(" ");
        Collection<Integer> colList = new LinkedList();
        for (String inArr1 : inArr) {
            colList.add(Integer.valueOf(inArr1));
        }
        GroupByAction g = new GroupByAction();
        g.groupBy(grid, range, colList);
    }

    private static void sort(Grid grid, QueriedRange range) throws Exception {
        System.out.println("Enter the cols:");
        String column = scanner.nextLine();
        String[] colArr = column.split(" ");
        List<Integer> col = new LinkedList();
        for (String colStr : colArr) {
            col.add(Integer.valueOf(colStr));
        }
        System.out.println("Sort ASCENDING/DESCENDING");
        SortingCriteria ascOrDec = SortingCriteria.valueOf(scanner.next());
        SortingCondition sortingCondition = new SortingCondition(ascOrDec, col);
        SortAction sortAction = new SortAction();
        sortAction.sort(grid, range, sortingCondition);

    }

    private static void function(Grid grid, QueriedRange range) throws Exception {
        int op = 1;
        while (op != 0) {
            System.out.println("Select Functions\nSUM\nAVERAGE\nMAXIMUM\nMINIMUM\nCOUNT");
            FunctionName function = FunctionName.valueOf(scanner.next());
            System.out.println("Enter col");
            int col = scanner.nextInt();
            FunctionCondition functionCondition = new FunctionCondition(col, function);
            range.getQueriedResult().getFunctionMap().put(functionCondition, new StringValue(DataTypes.String, ""));
            System.out.println("0-Finish");
            op = scanner.nextInt();
            scanner.nextLine();
        }
        FunctionAction.function(grid, range);
    }

    private static void filterOnFunctions(Grid grid,QueriedRange range) throws Exception {
        System.out.println("Maximum Level is :"+range.getMaxLevel()+"\nEnter the Level :");
            int level=scanner.nextInt();
            scanner.nextLine();
        int op=1;
        List<FilterOnFunctionCondition>filterOnFunctionsConditionList=new LinkedList ();
        while (op != 0) {
            System.out.println("Select Functions\nSUM\nAVERAGE\nMAXIMUM\nMINIMUM");
            FunctionName function = FunctionName.valueOf(scanner.next());
            System.out.println("Enter col");
            int col = scanner.nextInt();
            scanner.nextLine();
            FunctionCondition functionCondition = new FunctionCondition(col, function);
             for (String str : conditions.keySet()) {
                System.out.println(str);
                if(str.equals("less than or equal to"))
                    break;
            }
            String condition = scanner.nextLine().toLowerCase();
            String value = scanner.nextLine();
            FilterOnFunctionCondition filterOnFunctionCondition=new FilterOnFunctionCondition(functionCondition,conditions.get(condition),value);
            filterOnFunctionsConditionList.add(filterOnFunctionCondition);
            System.out.println("0-Finish");
            op = scanner.nextInt();
            scanner.nextLine();
            FilterOnFunctionsAction filterOnFunctionsAction=new FilterOnFunctionsAction ();
            filterOnFunctionsAction.filterOnFunction(grid,range, filterOnFunctionsConditionList,level);
        }
    }
    private static void reEvaluate(Grid grid, QueriedRange range) throws Exception {
        RefershResult refershResult = new RefershResult();
        refershResult.reEvalute(range, grid);
    }

    private static void reset(QueriedRange range) {
        range.setQueriedResult(null);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, Exception {
        // TODO code application logic here
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\admin\\Desktop\\CSVinputs.csv"));

        LinkedList<DateFormat> dfList = new LinkedList();
        dfList.add(DateFormat.d);
        dfList.add(DateFormat.m);
        dfList.add(DateFormat.y);
        String line;
        Grid grid = new Grid();
        int r = 0;
        while ((line = br.readLine()) != null) {
            Value v;
            line = line.substring(1, line.length() - 1);
            String[] in = line.split("\",\"");
            for (int i = 0; i < in.length; i++) {
                v = ValueParser.TypeandValue(in[i], dfList);
                grid.set(r, i, v);
            }

            r++;
        }

        System.out.println("Enter the range");
        int startRow, endRow, startCol, endCol;
        startRow = scanner.nextInt();
        endRow = scanner.nextInt();
        startCol = scanner.nextInt();
        endCol = scanner.nextInt();
        QueriedRange range = new QueriedRange(startRow, endRow, startCol, endCol);
        Main.print(range.getQueriedResult());
        int op = 4;
        while (op != 0) {
            System.out.println("1-Filter\n2-GroupBy\n3-Sort\n4-Function\n5-FilterOnFunctions\n6-ReEvaluate\n7-Reset(to NULL)\n0-Exit");
            op = scanner.nextInt();
            scanner.nextLine();
            if (op == 0) {
                break;
            } else if (op == 1) {
                Main.filter(grid, range);
            } else if (op == 2) {
                Main.groupBy(grid, range);
            } else if (op == 3) {
                Main.sort(grid, range);
            } else if (op == 4) {
                Main.function(grid, range);
            }else if(op==5){
                Main.filterOnFunctions(grid,range);
            } else if (op == 6) {
                Main.reEvaluate(grid, range);
            } else if (op == 7) {
                Main.reset(range);
            }
            Main.print(range.getQueriedResult());
        }

    }

    /* private static void printFinalResult(Grid grid, QueriedRange range) {
        List<GroupByAndFilter> result = range.getResult();
        int opNum = 1;
        for (GroupByAndFilter gnf : result) {
            if (gnf instanceof Filter) {
                Filter filter = (Filter) gnf;
                System.out.println(opNum + "#Filter");
                Main.print(filter.getQueriedResult());
            } else if (gnf instanceof GroupBy) {
                GroupBy groupBy = (GroupBy) gnf;
                System.out.println(opNum + "#GroupBy");
                Main.print(groupBy.getQueriedResult());
            } else if (gnf instanceof Sorting) {
                Sorting sort = (Sorting) gnf;
                System.out.println(opNum + "#Sort");
                Main.print(sort.getQueriedResult());
            }
            opNum++;
            System.out.println();
            System.out.println();
            System.out.println();
        }

    }*/

}
