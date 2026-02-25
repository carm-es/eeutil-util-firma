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

package es.mpt.dsic.inside.csv.ext;

public class AOSignedDataInfo {
  /** Tipo de dato. */
  private String mimetype = null;

  /** Descripcion del tipo de dato. */
  private String description = null;;

  /** Extensi&oacute;n por defecto para el fichero. */
  private String extension = null;

  /** Datos extra&iacute;dos. */
  private byte[] data = null;



  /**
   * Recupera los datos analizados.
   * 
   * @return Datos analizados.
   */
  public byte[] getData() {
    return this.data;
  }

  /**
   * Recupera la descripcion del tipo de dato. Si no se conoce, devuelve "Desconocido".
   * 
   * @return Descripci&oacute;n del tipo de dato.
   */
  public String getDataDescription() {
    return this.description;
  }

  /**
   * Recupera la extensi&oacute;n asignada por defecto a este tipo de dato. Si no se conoce esta
   * extensi&oacute;n, se devolver&aacute; <code>null</code>.
   * 
   * @return Extension por defecto del tipo de dato.
   */
  public String getDataExtension() {
    return this.extension;
  }

  /**
   * Recupera el identificador (MimeType) del tipo de dato.
   * 
   * @return Tipo de dato.
   */
  public String getDataMimeType() {
    return this.mimetype;
  }

  public void setDataMimeType(String mimetype) {
    this.mimetype = mimetype;
  }

  public void setData(byte[] data) {
    this.data = data;
  }
}
