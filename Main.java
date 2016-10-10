package com.company;

import java.io.*;
import java.net.*;

import javax.net.ssl.HttpsURLConnection;

public class Main {

    public static String getInfo(String infoLine, String infoBeginString) {
        String firstSubstring = infoLine.substring(infoLine.indexOf(infoBeginString));
        String secondSubstring = firstSubstring.substring(firstSubstring.indexOf(">"));
        String finalSubstring = secondSubstring.substring(1, secondSubstring.indexOf("<"));
        return finalSubstring;
    }

    public static void printNameFromInitials(BufferedReader inputReader) throws IOException
    {
        System.out.print("Enter initials : ");
        String initials = inputReader.readLine();
        String webPageAddress = "http://www.ecs.soton.ac.uk/people/" + initials;
        URL address = new URL(webPageAddress);
        BufferedReader urlIN = new BufferedReader(new InputStreamReader(address.openStream()));
        String inputLine;

        String nameProperty = "property=\"name\"";
        while((inputLine = urlIN.readLine())!=null)
        {
            int beginIndex = inputLine.indexOf(nameProperty);
            if(beginIndex > -1)
            {
                System.out.println("Name: " + getInfo(inputLine, nameProperty));
                break;
            }
        }
        urlIN.close();
    }

    public static void printHomePageFromName(BufferedReader inputReader) throws IOException
    {
        System.out.println("Enter name:");
        String name = inputReader.readLine();
        String secondName = name.substring(name.indexOf(" ") + 1);
        String firstName = name.substring(0, name.indexOf(" "));
        String webPageAddress = "https://www.google.bg/search?q=ecs+" + firstName + "+" + secondName;
        URL address = new URL(webPageAddress);
        HttpsURLConnection con = (HttpsURLConnection)address.openConnection();
        BufferedReader urlIN = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;

        String resultProperty = "<h3 class=\"r\">";

        while((inputLine = urlIN.readLine())!=null)
        {
            int beginIndex = inputLine.indexOf(resultProperty);
            if(beginIndex > -1)
            {
                System.out.println("Name: " + getInfo(inputLine, resultProperty));
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        printNameFromInitials(br);
    }

}
