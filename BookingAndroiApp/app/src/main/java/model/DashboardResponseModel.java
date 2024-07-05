package model;

public class DashboardResponseModel {
    public int status;
    public String message;
    public DashboardModel data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DashboardModel getData() {
        return data;
    }

    public void setData(DashboardModel data) {
        this.data = data;
    }
}
