package src;

import java.io.*;
import java.util.*;



public class googlePlayStore {

    public static void main(String[] args) throws IOException {

        String fileName = "googleplaystore.csv";
        String currDir = System.getProperty("user.dir");
        String filePath = currDir + File.separator + "data" + File.separator + fileName;

        File f = new File(filePath);

        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String lineRead = "";

        Map<String, Map<String, Float>> categoryInfo = new HashMap<>();
        // float avgRating;
        // String highestRatedApp;
        // String lowestRatedApp;
        // int totalNumOfApps;
        // int numDiscardedRec;
        
        int count = 0;
        while ((lineRead = br.readLine())!=null){
            
            if (count >0){
                
                // https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
                String[] rowEntry = lineRead.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                String category = rowEntry[1].toUpperCase();
                String appName = rowEntry[0];
                Float rating = 0f;
                // https://stackoverflow.com/questions/24833364/how-to-check-a-string-contains-only-digits-and-decimal-points
                if (rowEntry[2].matches("[0-9.]*")){
                    rating = Float.parseFloat(rowEntry[2]);
                }
                else{
                    rating = Float.parseFloat("NaN");
                }
                
                
                Map<String, Float> appInfo = new HashMap<>();

                if (categoryInfo.containsKey(category)){
                    appInfo = categoryInfo.get(category);
                }
                appInfo.put(appName, rating);
                categoryInfo.put(category, appInfo);
            }
            count ++;
        }
        System.out.println(categoryInfo.keySet());

        for (String category : categoryInfo.keySet()){
            System.out.printf("Category: %s \n", category);

            Map<String, Float> appInfo = categoryInfo.get(category);
            String[] highestRatedAppInfo = findHigestRatedApp(appInfo);
            String[] lowestRatedAppInfo = findLowestRatedApp(appInfo);
            float avgRating = calcAvgRating(appInfo);
            int[] counts = countApp(appInfo);
    
            System.out.printf("     Higest:     %s,     %s\n", highestRatedAppInfo[0], highestRatedAppInfo[1]);
            System.out.printf("     Lowest:     %s,     %s\n", lowestRatedAppInfo[0], lowestRatedAppInfo[1]);
            System.out.printf("     Average Rating:     %.2f\n", avgRating);
            System.out.printf("     Count:     %d\n", counts[1]);
            System.out.printf("     Discarded:     %d\n", counts[0]);

            System.out.println("============================================");
        }

    }

    public static String[] findHigestRatedApp(Map<String, Float> appInfo){
        String highestRatedApp = "";
        float maxRating = 0f;

        for (String appName : appInfo.keySet()){
            float rating = appInfo.get(appName);
            if (rating > maxRating){
                highestRatedApp = appName;
                maxRating = rating;
            }
        }
        String[] highestRatedAppInfo = new String[]{highestRatedApp, Float.toString(maxRating)};
        return highestRatedAppInfo;
    }

    public static String[] findLowestRatedApp(Map<String, Float> appInfo){
        String lowestRated = "";
        float minRating = 5f;

        for (String appName : appInfo.keySet()){
            float rating = appInfo.get(appName);
            if (rating < minRating){
                lowestRated = appName;
                minRating = rating;
            }
        }
        String[] lowestRatedApp = new String[]{lowestRated, Float.toString(minRating)};
        return lowestRatedApp;
    }

    public static float calcAvgRating(Map<String, Float> appInfo){

        int counts[] = countApp(appInfo);
        int numOfValidRatings = counts[1]-counts[0];

        float totalRatings = 0f;
        for (String appName : appInfo.keySet()){
            if (!(appInfo.get(appName).isNaN())){
                totalRatings += appInfo.get(appName);
            }
            
        }

        float avgRating = totalRatings/numOfValidRatings;
        return avgRating;

    }

    public static int appCount(Map<String, Float> appInfo){
        // total number of lines read per category
        int count = appInfo.keySet().size();
        
        return count;
    }

    public static int[] countApp(Map<String, Float> appInfo){
        int discardedCount = 0;
        int totalCount = 0;
        for (String appName : appInfo.keySet()){
            Float rating = appInfo.get(appName);
            if (rating.isNaN()){
                discardedCount += 1;
            }
            totalCount += 1;
        }
        int[] counts = new int[]{discardedCount, totalCount};
        return counts;
    }

}

