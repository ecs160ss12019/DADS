package com.example.misslecommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class CSVFileCtrl {
    InputStream inputStream;


    public CSVFileCtrl(InputStream inputStream){

        this.inputStream = inputStream;
    }

    public List read(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> myLeaderboards = new ArrayList<String>();
        try {
            String leaderBoardsLine;
            while ((leaderBoardsLine = reader.readLine()) != null) {
                //String[] row = leaderBoardsLine.split(",");
                String newString = leaderBoardsLine.replace(",", ": ");
                myLeaderboards.add(newString);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error reading in the file " + ex);
        }

        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error trying to close the input stream: "+e);
            }
        }
        return myLeaderboards;
    }

    public List modifyResults(List<String> listToMod){
        int length = listToMod.size();
        for(int i = 0; i < length; i++){
            listToMod.get(i).replace(",", ": ");
        }
        return listToMod;
    }
}
