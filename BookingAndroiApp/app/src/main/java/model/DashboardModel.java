package model;

public class DashboardModel {
    public int users;
    public int rooms;
    public int availableRooms;

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }
}
