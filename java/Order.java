/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Restaurant;

/**
 *
 * @author kos
 */
import java.util.*;

// order details
public class Order {
    // staff
    public int waiter;
    public int chef;
    public int cashier;

    public int table; // table number
    public int people; // number of people
    // order number shown to the chef
    // (not the order's id)
    public int number;

    public int customer; // customer id
    public boolean card; // did they bring their card?
    public int coupon; // number of coupons brought

    public List<Dish> dishes; // dish list
    public List<String> drinks; // drink list

    public Order(){
        dishes = new ArrayList<>();
        drinks = new ArrayList<>();
    }
}
