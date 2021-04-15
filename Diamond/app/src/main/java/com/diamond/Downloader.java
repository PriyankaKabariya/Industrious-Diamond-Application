package com.diamond;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Downloader extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    GridView gv;

    ProgressDialog pd;

    public Downloader(Context c, String urlAddress, GridView gv) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.gv = gv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Retrieve");
        pd.setMessage("Retrieving...Please Wait");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.downloadData();
    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);

        pd.dismiss();

        if(jsonData==null){
            Toast.makeText(c,"Unsuccessfull,No data retrieved",Toast.LENGTH_SHORT).show();
        }
        else {
            DataParser parser=new DataParser(c,jsonData,gv);
            parser.execute();
        }
    }

    private String downloadData(){

        HttpURLConnection con=Connector.connection(urlAddress);
        if(con==null){
            return null;
        }

        try{
            InputStream is=new BufferedInputStream(con.getInputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(is));

            String line;
            StringBuffer jsonData=new StringBuffer();

            while ((line=br.readLine())!=null){
                jsonData.append(line+"\n");
            }

            br.close();
            is.close();

            return jsonData.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

