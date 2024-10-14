package src;

public class googleD {

    private String category;
    
    private float avgRating;
    private String highestRatedApp;
    private String lowestRatedApp;
    private int totalNumOfApps;
    private int numDiscardedRec;

    public String getCategory() {
        return category;
    }
    public float getAvgRating() {
        return avgRating;
    }
    public String getHighestRatedApp() {
        return highestRatedApp;
    }
    public String getLowestRatedApp() {
        return lowestRatedApp;
    }
    public int getTotalNumOfApps() {
        return totalNumOfApps;
    }
    public int getNumDiscardedRec() {
        return numDiscardedRec;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }
    public void setHighestRatedApp(String highestRatedApp) {
        this.highestRatedApp = highestRatedApp;
    }
    public void setLowestRatedApp(String lowestRatedApp) {
        this.lowestRatedApp = lowestRatedApp;
    }
    public void setTotalNumOfApps(int totalNumOfApps) {
        this.totalNumOfApps = totalNumOfApps;
    }
    public void setNumDiscardedRec(int numDiscardedRec) {
        this.numDiscardedRec = numDiscardedRec;
    }
    
}
