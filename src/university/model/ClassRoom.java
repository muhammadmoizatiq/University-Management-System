package university.model;

import java.io.Serializable;

public class ClassRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roomNo;
    private String building;
    private int capacity;

    public ClassRoom(String roomNo, String building, int capacity) {
        this.roomNo = roomNo;
        this.building = building;
        this.capacity = capacity;
    }

    public String getRoomNo() { return roomNo; }
    public String getBuilding() { return building; }
    public int getCapacity() { return capacity; }

    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }


    public String getRoomName() {
        return roomNo + " - " + building;
    }

    @Override
    public String toString() {
        return roomNo;
    }
}