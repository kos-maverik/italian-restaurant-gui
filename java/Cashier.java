/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Restaurant;

/**
 *
 * @author kos
 */
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Cashier extends javax.swing.JFrame {

    /**
     * Creates new form Cashier
     */
    private DefaultComboBoxModel<String> tables;
    public Order ord; // order for receipt

    public Cashier() {
        initComponents();
        DefaultListCellRenderer dlcr = new DefaultListCellRenderer(); 
        dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER); 
        jComboBox1.setRenderer(dlcr); 

        tables = new DefaultComboBoxModel<>();
        jComboBox1.setModel(tables);
        jComboBox3.setModel(new DefaultComboBoxModel(getCustomers()));
    }

    // update ready tables
    public void refresh() {
        tables.removeAllElements();
        for (int i = 0; i < Lists.receipt.size(); i++) {
            tables.addElement(String.valueOf(Lists.receipt.get(i).table));
        }

        start.repaint();
        start.revalidate();
    }


    // get id and name of every customer
    public Vector<Integer> getCustomers() {
        Vector<Integer> customers = new Vector<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select id "
                     + "from customer");
            
            while(rs.next()) {
                customers.add(Integer.parseInt(rs.getString(1)));
            }

            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return customers;
    }

    // total number of customer's coupons (either used or unused)
    public int getCoupons(int id) {
        int coupons = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select count(1) from coupon "
                    + "where customer_id = " + id);
            
            if(rs.next()) coupons = rs.getInt(1);

            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return coupons;

    }

    // next order id
    public int getNumber() {
        int number = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select num from order_dishes "
                    + "order by num desc limit 1");
            
            if(rs.next()) number = rs.getInt(1);
            ++number;

            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return number;
    }

    // returns date and time
    public String getDateTime() {
        String date=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select now()");
            
            if(rs.next()) date = rs.getString(1);

            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return date;
    }

    // returns shift (before / after 19:00)
    public String getShift(String datetime) {
        int hour = Integer.parseInt(datetime.substring(11, 13));

        if (hour < 19) return "day";
        else return "night";
    }

    // full name of employee
    public String getName(int emp) {
        String name=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select full_name " +
                    "from employee where id = " + emp);
            
            if(rs.next()) name = rs.getString(1);

            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return name;
    }

    // price of a dish
    public float getFoodPrice(String s) {
        float price=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select price from dish " +
                    "where name = '" + s + "'");
            
            if(rs.next()) price = rs.getFloat(1);

            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return price;
    }

    // price of a drink
    public float getDrinkPrice(String s) {
        float price=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select price from drink " +
                    "where name = '" + s + "'");
            
            if(rs.next()) price = rs.getFloat(1);

            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return price;
    }

    // price of an order, including discounts
    // and the extra / less ingredients
    public float getPrice(int p) {
        float price=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select price(" + p + ")");
            
            if(rs.next()) price = rs.getFloat(1);

            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return price;
    }

    // checks if the customer has 20 euro discount
    public boolean getDiscount(int c) {
        float discount=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select discount from customer " +
                    "where id = " + c);
            
            if(rs.next()) discount = rs.getInt(1);

            con.close();
        } catch(Exception e){ System.out.println(e);}  
        if (discount == 0) return false;
        else return true;
    }

    // inserts order and prints the receipt!
    public void insertOrder(Order p) {
        String[] strings=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 
            Statement stmt=con.createStatement();  
            int number = getNumber();
            int coupons = getCoupons(p.customer);

            String sql = "insert into order_dishes values ";

            for (int i = 0; i < p.dishes.size(); i++) {
                sql = sql + "(" + number + "," + (i+1) + ",'"
                    + p.dishes.get(i).name + "',";
                if (p.dishes.get(i).pasta == null ||
                    p.dishes.get(i).pasta.isEmpty()) {
                        sql = sql + "null";
                }
                else {
                        sql = sql + "'" + p.dishes.get(i).pasta + "'";
                }
                sql = sql + ",";
                String categ = Dish.getCategory(p.dishes.get(i).name);
                if (categ!=null && categ.equals("pizza")) {
                    if (p.dishes.get(i).pizza==0)
                        sql = sql + "'4'), ";
                    else
                        sql = sql + "'" + p.dishes.get(i).pizza + "'), ";
                }
                else {
                    sql = sql + "null), ";
                }
            }
            if (!sql.equals("insert into order_dishes values ")) {
                sql = sql.substring(0,sql.length()-2);
                stmt.executeUpdate(sql);
            }


            sql = "insert into extra_ingredients values ";

            for (int i = 0; i < p.dishes.size(); i++) {
                if (!p.dishes.get(i).extra.isEmpty()) {
                    for (int j = 0; j < p.dishes.get(i).extra.size(); j++) {
                        sql = sql + "(null,'" + p.dishes.get(i).extra.get(j) + "'," +
                                number + "," + (j+1) + "), ";
                    }
                }
            }
            if (!sql.equals("insert into extra_ingredients values ")) {
                sql = sql.substring(0,sql.length()-2);
                stmt.executeUpdate(sql);
            }

            sql = "insert into without_ingredients values ";

            for (int i = 0; i < p.dishes.size(); i++) {
                if (!p.dishes.get(i).without.isEmpty()) {
                    for (int j = 0; j < p.dishes.get(i).without.size(); j++) {
                        sql = sql + "(null,'" + p.dishes.get(i).without.get(j) + "'," +
                                number + "," + (j+1) + "), ";
                    }
                }
            }
            if (!sql.equals("insert into without_ingredients values ")) {
                sql = sql.substring(0,sql.length()-2);
                stmt.executeUpdate(sql);
            }


            sql = "insert into order_drinks values ";
            for (int i = 0; i < p.drinks.size(); i++) {
                sql = sql + "(" + number + "," + (i+1) + ",'"
                    + p.drinks.get(i) + "'), ";
            }
            if (!sql.equals("insert into order_drinks values ")) {
                sql = sql.substring(0,sql.length()-2);
                stmt.executeUpdate(sql);
            }


            sql = "insert into orders values (" +
                    number + ",'" + getDateTime() + "','" +
                    getShift(getDateTime()) + "'," + p.chef + "," + p.waiter +
                    "," + p.table + "," + p.customer + "," +
                    p.coupon + ",";

            if (p.card) sql = sql + "'yes',";
            else sql = sql + "'no',";

            sql = sql + "0)";

            stmt.executeUpdate(sql);

            // Receipt!!
            String rec;
            rec = "Italian Restaurant Maverik\r\n" +
                    "Order no. " + number + "\r\n" +
                    "Waiter : " + getName(p.waiter) + "\r\n" +
                    "Chef : " + getName(p.chef) + "\r\n" +
                    "Cashier: " + getName(p.cashier) + "\r\n" +
                    
                    "Table : " + p.table + "\r\n" +
                    "People : " + p.people + "\r\n" +
                    "Date : " + getDateTime() + "\r\n" +
                    "\r\n\r\n";

            for (int i = 0; i < p.dishes.size(); i++) {
                rec = rec + p.dishes.get(i).name + "\t" +
                        getFoodPrice(p.dishes.get(i).name) + " $\r\n";
            }

            for (int i = 0; i < p.drinks.size(); i++) {
                rec = rec + p.drinks.get(i) + "\t" +
                        getDrinkPrice(p.drinks.get(i)) + " $\r\n";
            }

            rec = rec + "\r\nFinal Price : " + getPrice(number) + " $";

            if (getDiscount(p.customer)) {
                rec = rec + "\r\n\r\n ********** \r\n\r\n" +
                        "Congratulations, you got 20$ discount\r\n" +
                        "for your next order!!";
            }
            if (getCoupons(p.customer)>coupons) {
                rec = rec + "\r\n\r\n ********** \r\n\r\n" +
                        "Congratulations, you won a 10$ discount ticket!!\r\n";
            }

            rec = rec + "\r\nThanks for coming :)";
            jTextPane1.setText(rec);
            con.close();
        } catch(Exception e){ System.out.println(e);}  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        start = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        print = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jButton3 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cashier", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18)))); // NOI18N
        jPanel1.setLayout(new java.awt.CardLayout());

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe Print", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tables");

        jCheckBox1.setText("Bonus Card");

        jButton2.setText("Print Receipt");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Coupons");

        jLabel3.setText("Customer");

        jComboBox3.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout startLayout = new javax.swing.GroupLayout(start);
        start.setLayout(startLayout);
        startLayout.setHorizontalGroup(
            startLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(startLayout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addGroup(startLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, startLayout.createSequentialGroup()
                .addContainerGap(126, Short.MAX_VALUE)
                .addGroup(startLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, startLayout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, startLayout.createSequentialGroup()
                        .addGroup(startLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(startLayout.createSequentialGroup()
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, startLayout.createSequentialGroup()
                                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addGap(6, 6, 6)))
                        .addGap(144, 144, 144))))
        );
        startLayout.setVerticalGroup(
            startLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(startLayout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(startLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(startLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap())
        );

        jPanel1.add(start, "card2");

        jScrollPane1.setViewportView(jTextPane1);

        jButton3.setFont(new java.awt.Font("Segoe Print", 0, 18)); // NOI18N
        jButton3.setText("Bye!");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout printLayout = new javax.swing.GroupLayout(print);
        print.setLayout(printLayout);
        printLayout.setHorizontalGroup(
            printLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(printLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, printLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(155, 155, 155))
        );
        printLayout.setVerticalGroup(
            printLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(printLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(print, "card2");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15", "16" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Lists.receipt.remove(jComboBox1.getSelectedIndex());
        refresh();
        jPanel1.removeAll();

        jPanel1.add(start);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jComboBox1.getSelectedItem()!=null) {
            int index=jComboBox1.getSelectedIndex();

            ord = Lists.receipt.get(index);
            ord.cashier = Integer.parseInt(jComboBox2.getSelectedItem().toString());
            ord.customer = Integer.parseInt(jComboBox3.getSelectedItem().toString());
            ord.card = jCheckBox1.isSelected();
            ord.coupon =  Integer.parseInt(jSpinner2.getValue().toString());

            insertOrder(ord);

            jPanel1.removeAll();

            jPanel1.add(print);
            jPanel1.repaint();
            jPanel1.revalidate();   
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cashier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cashier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cashier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cashier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cashier().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JPanel print;
    private javax.swing.JPanel start;
    // End of variables declaration//GEN-END:variables
}
