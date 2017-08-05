
package com.competitionapp.nrgscouting;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class MatchEntry extends Fragment {
    private EditText matchNumber=(EditText)(getView().findViewById(R.id.matchNumber));
    private Spinner position=(Spinner)(getView().findViewById(R.id.teamPosition));
    private EditText gears=(EditText)(getView().findViewById(R.id.gearsRetrieved));
    private EditText balls=(EditText)(getView().findViewById(R.id.totalBallsScored));
    private EditText autoGears=(EditText)(getView().findViewById(R.id.autoGears));
    private EditText autoBalls=(EditText)(getView().findViewById(R.id.autoBalls));
    private RatingBar rating=(RatingBar)(getView().findViewById(R.id.sportsmanship));
    private CheckBox death=(CheckBox)(getView().findViewById((R.id.death)));
    private CheckBox baseline=(CheckBox)(getView().findViewById(R.id.baseline));
    private CheckBox ropeClimb=(CheckBox)(getView().findViewById((R.id.ropeClimb)));
    private EditText teamName=(EditText)(getView().findViewById(R.id.team));
    private ArrayList<Entry> listOfEntriesInFile=new ArrayList<Entry>();
    public MatchEntry() {
        // Required empty public constructor
    }
    public void saveEntry(View view){
        //Check if the device has an external storage
        //media mounted is the state that indicates that there is a memory card that is detected
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //Get the external storage directory
            File externalStoreDir=Environment.getExternalStorageDirectory();
            File entries=new File(externalStoreDir,"Entries.txt");
            getAllEntriesInFileIntoObjectForm(entries);//loading entrie into the array list
            //CREATE THE ENTRY OBJECT USING THE UI ELEMENTS ABOVE AND ALSO USING THE CUSTOM INITIALIZER
            Entry newOne=new Entry(getPosition(position.getSelectedItemPosition()),String.valueOf(teamName.getText()),
                    Integer.parseInt(String.valueOf(matchNumber.getText())), Integer.parseInt(String.valueOf(gears.getText())),
                    Integer.parseInt(String.valueOf(balls.getText())),Integer.parseInt(String.valueOf(autoGears.getText())),
                    Integer.parseInt(String.valueOf(autoBalls.getText())), rating.getNumStars(),death.isChecked(),baseline.isChecked(),
                    ropeClimb.isChecked());
            //CHECK IF THE MATCH NUMBERS OF ANY OBJECT IN THE ARRAYLIST MATCH
            for(Entry a:listOfEntriesInFile){
                if(a.matchNumber == newOne.matchNumber){
                    listOfEntriesInFile.remove(a);
                }
            }
            listOfEntriesInFile.add(newOne);
        }
        else{
            //Inform the user that there is no SD card or is undetected
        }
    }
    public void getAllEntriesInFileIntoObjectForm(File entries){
        Scanner fileScanner;
        try{
            String fileText="";
            fileScanner = new Scanner(entries);
            ArrayList<String> lines=new ArrayList<String>();
            while(fileScanner.hasNextLine()){
                lines.add(fileScanner.nextLine());
            }
            for(String line : lines){
                String[] properties=line.split("\t");
                Entry newEntry=new Entry();
                //USE AN INITIALIZER TO DO THE THING DONE BELOW USING A LOOP AND WITH A LOT LESS CODE
                String[] matchNum=properties[0].split(":");
                newEntry.matchNumber=Integer.parseInt(matchNum[1]);
                String[] gears=properties[1].split(":");
                newEntry.gearsRetrieved=Integer.parseInt(gears[1]);
                String[] autoGears=properties[2].split(":");
                newEntry.autoGearsRetrieved=Integer.parseInt(autoGears[1]);
                String[] balls=properties[3].split(":");
                newEntry.ballsShot=Integer.parseInt(balls[1]);
                String[] autoBalls=properties[4].split(":");
                newEntry.autoBallsShot=Integer.parseInt(autoBalls[1]);
                String[] rating=properties[5].split(":");
                newEntry.rating=Integer.parseInt(rating[1]);
                String[] death=properties[6].split(":");
                newEntry.death=Boolean.getBoolean(death[1]);
                String[] baseline=properties[7].split(":");
                newEntry.crossedBaseline=Boolean.getBoolean(baseline[1]);
                String[] rope=properties[8].split(":");
                newEntry.climbsRope=Boolean.getBoolean(rope[1]);
                String[] name=properties[9].split(":");
                newEntry.teamName=name[1];
                //ADD THE NEW ENTRY IN THE LINE TO THE LISTOFENTRIES ARRAYLIST OBJECT
                listOfEntriesInFile.add(newEntry);
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("The file does not exist");
        }
    }
    public Entry.Position getPosition(int row){
        if(row == 0)
            return Entry.Position.RED1;
        else if(row == 1)
            return Entry.Position.RED2;
        else if(row==2)
            return Entry.Position.RED3;
        else if(row==3)
            return Entry.Position.BLUE1;
        else if(row==4)
            return Entry.Position.BLUE2;
        return Entry.Position.BLUE3;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_entry, container, false);
    }

}
//sup dudes