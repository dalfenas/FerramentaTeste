/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.org.fdte;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class SimpleCboxDataSource implements ComboBoxDataSource{

     public List<DataItem> getItemList() {
        List<DataItem> list = new ArrayList<DataItem>();
        list.add(new DataItem(32, "banana"));
        list.add(new DataItem(144, "laranja"));
        return list;
    } // getItemList

}
