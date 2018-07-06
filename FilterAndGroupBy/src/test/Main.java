/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import query.control.*;
import query.model.*;
import grid.DateFormat;
import grid.Grid;
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
    static Scanner scanner = new Scanner(System.in);
    //  public static ArrayList<ArrayList<Value>>inputs=new ArrayList<ArrayList<Value>>();
    static Map<String, ConditionsList> conditions = new HashMap();

    static {
        conditions.put("equals", ConditionsList.EQUALS);
        conditions.put("does not equals", ConditionsList.DOESNOTEQUALS);
        conditions.put("greater than", ConditionsList.GREATERTHAN);
        conditions.put("greater than or equal to", ConditionsList.GREATERTHANOREQUALTO);
        conditions.put("less than", ConditionsList.LESSTHAN);
        conditions.put("less than or equalto", ConditionsList.LESSTHANOREQUALTO);
        conditions.put("begines with", ConditionsList.BEGINESWITH);
        conditions.put("does not begines with", ConditionsList.DOESNOTBEGINWITH);
        conditions.put("ends with", ConditionsList.ENDSWITH);
        conditions.put("does not ends with", ConditionsList.DOESNOTENDSWITH);
        conditions.put("contains", ConditionsList.CONTAINS);
        conditions.put("does not contains", ConditionsList.DOESNOTCONTAINS);
        conditions.put("matches", ConditionsList.MATCHES);
        conditions.put("does not matches", ConditionsList.DOESNOTMATCHES);
    }

    private static void print(QueriedResult queriedResult) {
        if (queriedResult.getNextAction().isEmpty()) {
            System.out.println(queriedResult.getValue() + " " + queriedResult.getRow());
        } else {
            System.out.print(queriedResult.getValue() + " ");
            for (QueriedResult vn : queriedResult.getNextAction()) {
                Main.print(vn);
            }
        }
    }

    private static void filter(Grid grid, QueriedRange range) throws Exception {
        Collection<Condition> conList = new ArrayList();
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
            Condition c = new Condition(col, conditions.get(condition), value);
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
        System.out.println("Enter the col:");
        int col = scanner.nextInt();
        scanner.nextLine();
        SortAction sortAction = new SortAction();
        sortAction.sort(grid, range, col);

    }

    private static void reEvaluate(Grid grid, QueriedRange range) throws Exception {
        RefershResult refershResult = new RefershResult();
        refershResult.reEvalute(range, grid);
    }

    private static void reset(QueriedRange range) {
        RefershResult refershResult = new RefershResult();
        refershResult.reSet(range);
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
            System.out.println("1-Filter\n2-GroupBy\n3-Sort\n4-ReEvaluate\n5-Reset(to NULL)\n0-Exit");
            op = scanner.nextInt();
            scanner.nextLine();
            if (op == 1) {
                Main.filter(grid, range);
            } else if (op == 2) {
                Main.groupBy(grid, range);
            } else if (op == 3) {
                Main.sort(grid, range);
            } else if (op == 4) {
                Main.reEvaluate(grid, range);
            } else if (op == 5) {
                Main.reset(range);
            }
        }
        Main.printFinalResult(grid, range);
    }

    private static void printFinalResult(Grid grid, QueriedRange range) {
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

    }

}
