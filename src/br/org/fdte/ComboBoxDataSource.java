
package br.org.fdte;

import java.util.List;

public interface ComboBoxDataSource {

    public List<DataItem> getItemList();

    public class DataItem {
        long id;
        String value;

        public DataItem(long id, String value) {
            this.id = id;
            this.value = value;
        }
    } // DataItem
} // ComboBoxDataSource
