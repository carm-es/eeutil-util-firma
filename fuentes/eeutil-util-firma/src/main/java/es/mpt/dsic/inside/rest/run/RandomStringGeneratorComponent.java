/*
 * Copyright (C) 2025, Gobierno de España This program is licensed and may be used, modified and
 * redistributed under the terms of the European Public License (EUPL), either version 1.1 or (at
 * your option) any later version as soon as they are approved by the European Commission. Unless
 * required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and more details. You
 * should have received a copy of the EUPL1.1 license along with this program; if not, you may find
 * it at http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 */

package es.mpt.dsic.inside.rest.run;

import java.util.Random;

import org.springframework.stereotype.Component;

import es.mpt.dsic.inside.configure.ConfigureRestInfo;

@Component
public class RandomStringGeneratorComponent {

  // 52 characters valid
  private final static String sb1 = "0123456789bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";

  /*
   * public String generarCadenaAleatoria() { StringBuilder sbResult= new StringBuilder("");
   * StringBuilder sb = new StringBuilder(sb1);
   * 
   * for(int i=0;i<7;i++){ Random random = new Random(); int pos = random.ints(0,
   * 51).findFirst().getAsInt();
   * 
   * sbResult.append(sb.charAt(pos)); }
   * 
   * return sbResult.toString();
   * 
   * }
   */
  public String generarCadenaAleatoriaOptimized() {
    // Numero de caracteres de la secuencia
    int numChars = ConfigureRestInfo.getNumberCharacteresKeyRun();

    StringBuilder sbResult = new StringBuilder("");
    StringBuilder sb = new StringBuilder(sb1);


    Random random = new Random();

    int[] posi = random.ints(0, 51).limit(numChars).toArray();

    for (int i = 0; i < numChars; i++) {
      sbResult.append(sb.charAt(posi[i]));
    }

    return sbResult.toString();

  }


  /*
   * public static void main(String []args) {
   * 
   * RandomStringGenerator r= new RandomStringGenerator();
   * 
   * 
   * Date d= new Date(); long inicio=d.getTime();
   * 
   * for(int i=0;i<1000;i++) { String sResult=r.generarCadenaAleatoria();
   * System.out.println("CADENA ALEATORIA:"+ sResult.toString()); }
   * 
   * Date d2= new Date(); long fin = d2.getTime();
   * 
   * System.out.println(fin-inicio);
   * 
   * 
   * 
   * Date d3= new Date(); long inicio2=d3.getTime();
   * 
   * for(int i=0;i<1000;i++) { String sResult2=r.generarCadenaAleatoriaOptimized();
   * System.out.println("CADENA ALEATORIA:"+ sResult2.toString()); }
   * 
   * Date d4= new Date(); long fin2 = d4.getTime();
   * 
   * System.out.println(fin2-inicio2);
   * 
   * }
   */

}
