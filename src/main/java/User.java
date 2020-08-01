public class User {
    private long tgId;
    private String userName;
    private String settledCity;
    private boolean markUpToChangeCity;
    private boolean ifCitySetten;
    private long number;

    public boolean isIfCitySetten() {
        return ifCitySetten;
    }

    public void setIfCitySetten(boolean ifCitySetten) {
        this.ifCitySetten = ifCitySetten;
    }

    public boolean isMarkUpToChangeCity() {
        return markUpToChangeCity;
    }

    public void setMarkUpToChangeCity(boolean markUpToChangeCity) {
        this.markUpToChangeCity = markUpToChangeCity;
    }

    public long getTgId() {
        return tgId;
    }

    public String getUserName() {
        return userName;
    }

    public String getSettledCity() {
        return settledCity;
    }

    public void setTgId(long tgId) {
        this.tgId = tgId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSettledCity(String settledCity) {
        this.settledCity = settledCity;
    }


    public User(long tgId, String userName, long number) {
        this.tgId = tgId;
        this.userName = userName;
        this.number = number;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "ID: " + getTgId() + "\n" +
                "UserName: " + getUserName() + "\n";
    }
}