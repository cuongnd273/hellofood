package com.nguyen.cuong.hellofoods.models;

/**
 * Created by cuong on 11/18/2017.
 */

public class NavMenu {
    private int icon;
    private String title;
    private boolean selected;
    private boolean openUI;
    public NavMenu(int icon, String title) {
        this.icon = icon;
        this.title = title;
        this.selected=false;
        this.openUI=true;
    }

    public NavMenu(int icon, String title, boolean selected) {
        this.icon = icon;
        this.title = title;
        this.selected = selected;
        this.openUI=true;
    }

    public NavMenu(int icon, String title, boolean selected, boolean openUI) {
        this.icon = icon;
        this.title = title;
        this.selected = selected;
        this.openUI = openUI;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isOpenUI() {
        return openUI;
    }

    public void setOpenUI(boolean openUI) {
        this.openUI = openUI;
    }
}
