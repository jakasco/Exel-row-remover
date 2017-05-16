
package teetxt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeeTxt {

    public static void main(String[] args) {
        
        //tämä teksti tiedosto avataan exelillä yms, niin saadaan toivottu versio josta on poistettu ylimääräiset rivit
        try{
            // tee uusi tiedosto
            String polku="c:/mihin haluat tallentaa ja millä nimellä .txt ....";
            File file = new File(polku);
			
           
            if (!file.exists()) {  // jos ei tiedosto ole olemassa, tee sellainen
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            String[][] joku = tutkiSamat();
            System.out.println("pituus: "+joku.length+" "+Arrays.deepToString(joku));
            int pituus = joku.length;
            // tee taulukosta josta poistettu pilkut uusi txt tiedosto, ota pois turhat merkit ja rivit.
            for(int i=0; i<pituus; i++){
            String rivi = Arrays.toString(joku[i]);
            rivi = rivi.replaceAll("[\\[\\]\"]", "");
            bw.write(rivi);
            bw.newLine();
            }
            // sulje yhteys
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    //poistaa pilkut csv tiedostosta
    public static String[][] muokkaaString(int row) throws FileNotFoundException, IOException{  //int row -> montako saraketta yhdessä rivissä [][][][]...
      
        String cvs =  "C:\csv_tiedoston_sijtainti .csv ....";
       
        // otetaan tiedostosta rivien määrä jota tarvitaan alempana
        LineNumberReader  lnr = new LineNumberReader(new FileReader(cvs));
        lnr.skip(Long.MAX_VALUE);
        int col =  (int)lnr.getLineNumber();
        lnr.close();
        //
        
        String line = "";
        String split = ",";
        String[] rivi = new String[row]; //kolumnint ja rowit
        String[][] taulukko = new String[col][row];  //muuta n'm' luvut
        
         int num = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(cvs))) {

            while ((line = br.readLine()) != null) {

                String[] c = line.split(split); 

                for(int i=0; i<5; i++){
                rivi[i] = c[i];
   
                }
                for(int j=0; j<5; j++){
                taulukko[num][j] = rivi[j];

                }
                num++;  //menee jokaisen columnin läpi kunnes String[x][] max asti
            }
            return taulukko;
                    
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
     }
    
    public static String[][] tutkiSamat() throws IOException{
        
          String[][] test =  muokkaaString(5);
          int num = 5; //arr[][5]
          int pituus = test.length;
          ArrayList eiPoisteta = new ArrayList(); //ettei poisteta arrayta kahteen kertaan
          List<List<String>> listOfLists = new ArrayList<List<String>>(); //lista johon kopioidaan eka lista

          for(int i=0; i<pituus; i++){
   
              String[] arr = test[i];
              String as =  Arrays.toString(arr);

              //tee lista jokaisesta rivistä, ja poista alemmassa loopissa samat
              List<String> list = new ArrayList<String>(Arrays.asList(test[i]));
              listOfLists.add(list);

            for(int j=0; j<pituus; j++){
              String[] arr1 = test[j];
              String as1 =  Arrays.toString(arr1);
            
              if(as.equals(as1) && j!=i){ //löytyy 2 samaa riviä

                   eiPoisteta.add(i);
                   List<String> list1 = new ArrayList<String>(Arrays.asList(test[j])); //koko on 5
                   if(eiPoisteta.contains(j)){  //jos sisältää jo valmiiksi poistetun, ei poisteta mitään
                   }else{
                    // näkee mitkä rivit poistetaan   System.out.println("RIVI: "+i+" : "+Arrays.toString(arr1)+" || RIVI:"+j+" :"+Arrays.toString(arr));
                            listOfLists.remove(list1);
                   }
              }

          }
          } //For loop loppuu
          String[][] test2 = listOfLists.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new); //uusi taulukko, missä päivitetyt tiedot

          return test2;
    }
    
    
}
