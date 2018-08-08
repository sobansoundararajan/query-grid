/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import java.util.Objects;
import query.formula.Formula;

/**
 *
 * @author admin
 */
public class ColumnFormula {

    private final int col;
    private final Formula formula;

    public ColumnFormula(int col, Formula formula) {
        this.col = col;
        this.formula = formula;
    }

    public int getCol() {
        return col;
    }

    public Formula getFormula() {
        return formula;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + this.col;
        hash = 19 * hash + Objects.hashCode(this.formula);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ColumnFormula other = (ColumnFormula) obj;
        if (this.col != other.col) {
            return false;
        }
        if (!Objects.equals(this.formula, other.formula)) {
            return false;
        }
        return true;
    }

}
