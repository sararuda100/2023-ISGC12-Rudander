package com.example.labb_3_v2;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

public class FetchArtists {
    private final String APIKey = "85b0cc796e032d1f3552a216bd787c0a";
    private String list;
    public void fetchData(String input){
        URL url;
        list = "";

        try {
            //hantering av mellanslag från sökningen i url:en
            if (input.contains(" ")) {
                input = input.replace(" ", "%20");
            }

            String apiString = "http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&limit=6&artist=" + input + "&api_key=" + APIKey;
            url = new URL(apiString);

            // Initializing en XML-parser
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            //variabel som håller koll på parsad tagg
            String tagName = "";

            while(parserEvent!=XmlPullParser.END_DOCUMENT){
                if(parserEvent==XmlPullParser.START_TAG){
                    //tilldelar nuvarande tagg till tagName
                    tagName = parser.getName();
                }
                if(parserEvent==XmlPullParser.TEXT && tagName.contains("name")){
                    String name = parser.getText();
                    list = list + name.toString();

                }
                parserEvent = parser.next();
            }

        } catch(Exception e){
            //för att printa vilka metoder som ledde till exception
            e.printStackTrace();
        }
    }

    public String getList(){
        return list;
    }

}

