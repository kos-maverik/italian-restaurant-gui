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
import javax.swing.*;
import java.util.*;

public class Waiter extends javax.swing.JFrame {

    /**
     * Creates new form Waiter
     */
    // helpful variables
    public int table;
    public String drink;
    public Dish dish;
    public Order order;

    private DefaultListModel<String> ingredients;
    private DefaultListModel<String> custom;
    private DefaultListModel<String> other;

    public Waiter() {
        initComponents();
        order = new Order();
        dish = new Dish();
        ingredients = new DefaultListModel<>();
        custom = new DefaultListModel<>();
        other = new DefaultListModel<>();
        jList1.setModel(ingredients);
        jList3.setModel(custom);
        jList4.setModel(other);
        jList4.setVisible(false);
    }

    // drink list
    public String[] getDrinks()
    {
        String[] strings=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select name from drink");
            int count=0;
            
            while(rs.next())  
                ++count;
            
            strings = new String[count];
            rs.beforeFirst();
            count=0;
            
            while(rs.next())
                strings[count++] = rs.getString(1);
            
            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return strings;
    }

    // dish of the day list
    public String[] getSpecial()
    {
        String[] strings=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select name from dish_of_the_day");
            int count=0;
            
            while(rs.next())  
                ++count;
            
            strings = new String[count];
            rs.beforeFirst();
            count=0;
            
            while(rs.next())
                strings[count++] = rs.getString(1);
            
            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return strings;
    }

    // list with all dishes
    public String[] getFood()
    {
        String[] strings=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select name from dish");
            int count=0;
            
            while(rs.next())  
                ++count;
            
            strings = new String[count];
            rs.beforeFirst();
            count=0;
            
            while(rs.next())
                strings[count++] = rs.getString(1);
            
            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return strings;
    }

    // list with the ingredients of a dish
    public String[] getIngredients()
    {
        String[] strings=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select material from materials "
                    + "where recipe='" + dish.name + "'");
            int count=0;
            
            while(rs.next())  
                ++count;
            
            strings = new String[count];
            rs.beforeFirst();
            count=0;
            
            while(rs.next())
                strings[count++] = rs.getString(1);
            
            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return strings;
    }

    // list with all ingredients available
    public String[] getAll()
    {
        String[] strings=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("SELECT name from ingredients");
            int count=0;
            
            while(rs.next())  
                ++count;
            
            strings = new String[count];
            rs.beforeFirst();
            count=0;
            
            while(rs.next())
                strings[count++] = rs.getString(1);
            
            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return strings;
    }

    // pasta list
    public static String[] getPasta()
    {
        String[] strings=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select name from ingredients "
                    + "where name like '%pasta'");
            int count=0;
            
            while(rs.next())  
                ++count;
            
            strings = new String[count];
            rs.beforeFirst();
            count=0;
            
            while(rs.next())
                strings[count++] = rs.getString(1);
            
            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return strings;
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
        tables = new javax.swing.JPanel();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        food = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList4 = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jLabel6 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        misc = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList5 = new javax.swing.JList();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jList6 = new javax.swing.JList();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Waiter", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        jPanel1.setLayout(new java.awt.CardLayout());

        jButton27.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton27.setText("2");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jButton28.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton28.setText("4");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jButton29.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton29.setText("1");
        jButton29.setFocusable(false);
        jButton29.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton29.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jButton30.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton30.setText("3");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        jButton31.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton31.setText("7");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jButton32.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton32.setText("8");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jButton33.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton33.setText("5");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        jButton34.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton34.setText("6");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tablesLayout = new javax.swing.GroupLayout(tables);
        tables.setLayout(tablesLayout);
        tablesLayout.setHorizontalGroup(
            tablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablesLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(tablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tablesLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(tablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tablesLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tablesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42)
                .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        tablesLayout.setVerticalGroup(
            tablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablesLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(tablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tablesLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(tablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(tablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        jPanel1.add(tables, "card2");

        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = getFood();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.setVerifyInputWhenFocusTarget(false);
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList2);

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jButton3.setText("-");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jList4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList4.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList4ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jList4);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(" ");

        jButton4.setText("Next");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Recipes");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Ingredients");

        jList3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(jList3);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Custom");

        jButton11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton11.setText("+");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton5.setText("Add");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout foodLayout = new javax.swing.GroupLayout(food);
        food.setLayout(foodLayout);
        foodLayout.setHorizontalGroup(
            foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(foodLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
            .addGroup(foodLayout.createSequentialGroup()
                .addGroup(foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(foodLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, foodLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(93, 93, 93)))
                .addGroup(foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                    .addGroup(foodLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jButton11)))
                .addGap(22, 22, 22)
                .addGroup(foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        foodLayout.setVerticalGroup(
            foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(foodLayout.createSequentialGroup()
                .addGroup(foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(foodLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton4)))
                    .addGroup(foodLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, foodLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(13, 13, 13)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, foodLayout.createSequentialGroup()
                                .addGroup(foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(foodLayout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane2))
                                    .addGroup(foodLayout.createSequentialGroup()
                                        .addGroup(foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton11, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(0, 72, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel1.add(food, "card2");

        jList5.setModel(new javax.swing.AbstractListModel() {
            String[] strings = getDrinks();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(jList5);

        jButton6.setText("Back");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Done");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe Print", 0, 36)); // NOI18N
        jLabel1.setText("Drinks!");

        jButton9.setText("Add");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe Print", 0, 36)); // NOI18N
        jLabel2.setText("Today's Special!");

        jButton10.setText("Add");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jList6.setModel(new javax.swing.AbstractListModel() {
            String[] strings = getSpecial();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList6.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(jList6);

        jSpinner1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setText("People");

        javax.swing.GroupLayout miscLayout = new javax.swing.GroupLayout(misc);
        misc.setLayout(miscLayout);
        miscLayout.setHorizontalGroup(
            miscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(miscLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(miscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addGap(561, 561, 561)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(miscLayout.createSequentialGroup()
                        .addGroup(miscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(42, 42, 42)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(miscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(miscLayout.createSequentialGroup()
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addContainerGap())
        );
        miscLayout.setVerticalGroup(
            miscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(miscLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(miscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(miscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(miscLayout.createSequentialGroup()
                        .addGroup(miscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(miscLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(miscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                    .addComponent(jScrollPane4))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, miscLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addGroup(miscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton6)
                                    .addComponent(jButton7)
                                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(miscLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel1.add(misc, "card2");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        order.table=5;
        jPanel1.removeAll();

        jPanel1.add(food);
        jPanel1.repaint();
        jPanel1.revalidate();    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        order.table=3;
        jPanel1.removeAll();

        jPanel1.add(food);
        jPanel1.repaint();
        jPanel1.revalidate();    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        order.table=1;
        jPanel1.removeAll();

        jPanel1.add(food);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        order = new Order();
        jPanel1.removeAll();

        jPanel1.add(tables);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        order.table=2;
        jPanel1.removeAll();

        jPanel1.add(food);
        jPanel1.repaint();
        jPanel1.revalidate();    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        order.table=4;
        jPanel1.removeAll();

        jPanel1.add(food);
        jPanel1.repaint();
        jPanel1.revalidate();    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        order.table=6;
        jPanel1.removeAll();

        jPanel1.add(food);
        jPanel1.repaint();
        jPanel1.revalidate();    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        order.table=7;
        jPanel1.removeAll();

        jPanel1.add(food);
        jPanel1.repaint();
        jPanel1.revalidate();    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        order.table=8;
        jPanel1.removeAll();

        jPanel1.add(food);
        jPanel1.repaint();
        jPanel1.revalidate();    }//GEN-LAST:event_jButton32ActionPerformed

    // dish details
    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        if (!evt.getValueIsAdjusting() ) {
            if (jList2.getSelectedValue()!=null) {
                dish = new Dish();
                dish.name = jList2.getSelectedValue().toString();
                String[] str = getIngredients();
                ingredients.clear();
                for ( int i=0; i < str.length; i++ )
                    ingredients.addElement(str[i]);

                jLabel3.setText(null);
                other.clear();
                String categ = Dish.getCategory(dish.name);
                if (categ!=null && categ.equals("pasta"))
                {
                    jList4.setVisible(true);
                    jLabel3.setText("Pasta");
                    String[] str2 = getPasta();
                    for ( int i=0; i < str2.length; i++ )
                        other.addElement(str2[i]);
                }
                else if (categ!=null && categ.equals("pizza"))
                {
                    jList4.setVisible(true);
                    jLabel3.setText("Pizza Size");
                    other.addElement("4");
                    other.addElement("8");
                    other.addElement("16");
                }
                else
                {
                    jList4.setVisible(false);
                }

                custom.clear();
                String[] str3 = getAll();
                for ( int i=0; i < str3.length; i++ )
                    custom.addElement(str3[i]);


            }
        }
    }//GEN-LAST:event_jList2ValueChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jPanel1.removeAll();

        jPanel1.add(misc);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(jList1.getSelectedValue()!=null) {
            int ind = jList1.getSelectedIndex();
            dish.without.add(jList1.getSelectedValue().toString());
            ingredients.remove(ind);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        if(jList3.getSelectedValue()!=null) {
            String str = jList3.getSelectedValue().toString();
            ingredients.addElement(str);
            dish.extra.add(jList3.getSelectedValue().toString());
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jList4ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList4ValueChanged

    }//GEN-LAST:event_jList4ValueChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(jList2.getSelectedValue()!=null) {
            List<String> common = new ArrayList<>(dish.extra);
            common.retainAll(dish.without);

            // if (+) or (-) is selected for an ingredient
            dish.extra.removeAll(common);
            dish.without.removeAll(common);

            if (jList4.getSelectedValue()!=null) {
                if (jLabel3.getText().equals("Pizza Size"))
                    dish.pizza = Integer.parseInt(jList4.getSelectedValue().toString());
                else if (jLabel3.getText().equals("Pasta"))
                    dish.pasta = jList4.getSelectedValue().toString();
            }

            order.dishes.add(dish);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jPanel1.removeAll();

        jPanel1.add(food);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jButton6ActionPerformed

    // order is finished!
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (!order.dishes.isEmpty() || !order.drinks.isEmpty()) {
            order.waiter = Integer.valueOf(jComboBox1.getSelectedItem().toString());
            order.people = Integer.valueOf(jSpinner1.getValue().toString());
            Lists.count++;
            order.number = Lists.count;

            Lists.menu.add(order);
        }

        order = new Order();
        
        jPanel1.removeAll();

        jPanel1.add(tables);
        jPanel1.repaint();
        jPanel1.revalidate();

        // send the order to the chef
        Project.chef.refresh();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if(jList5.getSelectedValue()!=null) {
            order.drinks.add(jList5.getSelectedValue().toString());
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if(jList6.getSelectedValue()!=null) {
            Dish pi1;
            Dish pi2, pi3;
            pi1 = new Dish();
            pi2 = new Dish();
            pi3 = new Dish();
            try{
                Class.forName("com.mysql.jdbc.Driver");  
                Connection con=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/restaurant","root",""); 

                Statement stmt=con.createStatement();  
                ResultSet rs = stmt.executeQuery("select soup, appetizer, main from dish_of_the_day "
                        + "where name = '" + jList6.getSelectedValue().toString() + "'");

                if (rs.next()) {
                    pi1.name = rs.getString(1);
                    pi2.name = rs.getString(2);
                    pi3.name = rs.getString(3);
                }

                con.close();
            } catch(Exception e){ System.out.println(e);}  
            order.dishes.add(pi1);
            order.dishes.add(pi2);
            order.dishes.add(pi3);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(Waiter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Waiter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Waiter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Waiter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Waiter().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel food;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JList jList4;
    private javax.swing.JList jList5;
    private javax.swing.JList jList6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JPanel misc;
    private javax.swing.JPanel tables;
    // End of variables declaration//GEN-END:variables
}
