package pa7.table;

/**
 * Contains Cell content to display in GUI and the type of Cell.
 * Cell Type is used to determine the styling in the table
 */
public class PACell {
    private final PACellType PACellType;
    private final String cellContent;

    public PACell(PACellType PACellType, String cellContent) {
        this.PACellType = PACellType;
        this.cellContent = cellContent;
    }

    public PACellType getPACellType() {
        return PACellType;
    }

    public String getCellContent() {
        return cellContent;
    }
}
