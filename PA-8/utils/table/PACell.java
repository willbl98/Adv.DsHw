package edu.advalg.utils.table;

/**
 * Contains Cell content to display in GUI and the type of Cell.
 * Cell Type is used to determine the styling in the src.main.java.table
 */
public class PACell {
    private final Enum cellType;
    private final String cellContent;

    public PACell(Enum cellType, String cellContent) {
        this.cellType = cellType;
        this.cellContent = cellContent;
    }

    public Enum getPACellType() {
        return cellType;
    }

    public String getCellContent() {
        return cellContent;
    }
}
