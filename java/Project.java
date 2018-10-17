package Restaurant;
/**
 *
 * @author kos
 */


class Project {  
    // chef ui
    public static Chef chef;
    // waiter ui
    public static Waiter waiter;
    // cashier ui
    public static Cashier cashier;
public static void main(String args[]){  
    java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
        (chef = new Chef()).setVisible(true);
        (waiter = new Waiter()).setVisible(true);
        (cashier = new Cashier()).setVisible(true);
    }
});

    
    
    
    
}
}
