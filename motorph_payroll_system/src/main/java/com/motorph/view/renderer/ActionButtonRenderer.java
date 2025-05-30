package com.motorph.view.renderer;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.motorph.util.UIConstants;
import com.motorph.util.UIUtils;

/**
 * Custom table cell renderer for action buttons in employee table.
 * Displays View, Edit, and Delete buttons like in the prototype.
 */
public class ActionButtonRenderer extends JPanel implements TableCellRenderer {
    
    private JButton viewButton;
    private JButton editButton;
    private JButton deleteButton;
    private ActionButtonListener listener;
    
    public interface ActionButtonListener {
        void onViewClicked(int row);
        void onEditClicked(int row);
        void onDeleteClicked(int row);
    }
    
    public ActionButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        setBackground(UIConstants.PANEL_BACKGROUND);
        
        // Create action buttons
        viewButton = UIUtils.createActionButton("View", false);
        editButton = UIUtils.createActionButton("Edit", false);
        deleteButton = UIUtils.createActionButton("Delete", true);
        
        add(viewButton);
        add(editButton);
        add(deleteButton);
    }
    
    public void setActionListener(ActionButtonListener listener) {
        this.listener = listener;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        // Update button listeners with current row
        removeAllActionListeners();
        
        if (listener != null) {
            viewButton.addActionListener(e -> listener.onViewClicked(row));
            editButton.addActionListener(e -> listener.onEditClicked(row));
            deleteButton.addActionListener(e -> listener.onDeleteClicked(row));
        }
        
        return this;
    }
    
    private void removeAllActionListeners() {
        for (ActionListener al : viewButton.getActionListeners()) {
            viewButton.removeActionListener(al);
        }
        for (ActionListener al : editButton.getActionListeners()) {
            editButton.removeActionListener(al);
        }
        for (ActionListener al : deleteButton.getActionListeners()) {
            deleteButton.removeActionListener(al);
        }
    }
}
