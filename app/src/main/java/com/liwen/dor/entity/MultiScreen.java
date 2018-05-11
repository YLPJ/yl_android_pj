package com.liwen.dor.entity;

import java.util.List;

public class MultiScreen {
    private int ID;
    private int Name;
    private List<ScreenWindow> Windows;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getName() {
        return Name;
    }

    public void setName(int name) {
        Name = name;
    }

    public List<ScreenWindow> getWindows() {
        return Windows;
    }

    public void setWindows(List<ScreenWindow> windows) {
        Windows = windows;
    }


    public class ScreenWindow{
        private int ID;
        private int WinID;
        private String SourceName;


        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getWinID() {
            return WinID;
        }

        public void setWinID(int winID) {
            WinID = winID;
        }

        public String getSourceName() {
            return SourceName;
        }

        public void setSourceName(String sourceName) {
            SourceName = sourceName;
        }
    }
}
