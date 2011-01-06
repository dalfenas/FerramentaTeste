
package br.org.fdte;

import java.awt.Font;
import java.awt.Color;
import java.util.Vector;
import br.org.fdte.ComboBoxDataSource.DataItem;

public class ColumnConfiguration {

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the fieldType
     */
    public FieldType getFieldType() {
        return fieldType;
    }

    /**
     * @param fieldType the fieldType to set
     */
    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * @return the alignment
     */
    public Alignment getAlignment() {
        return alignment;
    }

    /**
     * @param alignment the alignment to set
     */
    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    /**
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * @return the cBoxDataSource
     */
    public ComboBoxDataSource getcBoxDataSource() {
        return cBoxDataSource;
    }

    /**
     * @param cBoxDataSource the cBoxDataSource to set
     */
    public void setcBoxDataSource(ComboBoxDataSource cBoxDataSource) {
        this.cBoxDataSource = cBoxDataSource;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }
    public enum FieldType {
       SEQ, TEXT, ID_TEXT_COMBO, COMBO, CHECKBOX;
    } // FIELD_TYPE

    public enum Alignment {
        CENTERED, LEFT, RIGTH;
    } // Alignment

    private String title = "nonada";
    private FieldType fieldType = FieldType.TEXT;
    private Alignment alignment;
    private Font font;
    private Color color;
    private boolean editable = true;
    private ComboBoxDataSource cBoxDataSource = null;
    private int width = 50;
    private Vector<DataItem> dataItens = null;
    
    boolean hasNonDefaultGraphicalProperty() {
        if ( font != null )
            return true;
        if ( color != null )
            return true;
        return false;
    } // hasNonDefaultGraphicalProperty

    void addDataItem(DataItem di) {
        if (dataItens == null) {
            dataItens = new Vector<DataItem>();
        }
        dataItens.add(di);
    } // addDataItem

    String getTextForId(Object id) {
        Object value = null;
        if (dataItens == null) {
            return null;
        }
        if ( id == null)
            return "no text for id" + id.toString();

        for (int i = 0; i < dataItens.size(); i++) {
            if ( dataItens.elementAt(i).id == (Long)id) {
                return dataItens.elementAt(i).value;
            }
        }
        return null;
    } // getTextForId

    Object getIdForText(Object text) {
        if ( dataItens == null )
            return null;
        if (text == null)
            return null;
        for (int i=0; i < dataItens.size(); i++) {
            System.out.println("comparing value=" + dataItens.elementAt(i).value + " to " + text);
            if ( dataItens.elementAt(i).value.equals(text.toString())) {
                return dataItens.elementAt(i).id;
            }
        }
        return null;
    } // getIdForText

    

} // ColumnConfiguration
