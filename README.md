# eeutil-util-firma

Instalación y evolutivo de la versión distribuible de Eeutil-Util-Firma.

Se parte de la versión distribuible que se ofrece en el área de descargas de la Suite Inside del Centro de Transferencia Tecnológica:
*  https://administracionelectronica.gob.es/ctt/inside/descargas -> Versión distribuible (Código fuente) -> Distribuible Eeutil-Util-Firma v4.2.0 (noviembre 2018)

Eeutils(CSV Creator) es el componente de la Suite CSV que agrupa diversas funcionalidades relacionadas con la generación de CSV y gestión de firmas e informes. Eeutils(CSV Creator) se divide en cinco módulos, entre los que se encuentra Eeutil-Util-Firma, que **permite obtener CSVs a partir de firmas o justificantes de firmas**.

### ¿Quién hace uso de este módulo en la CARM?

La aplicación INSIDE esta haciendo uso de dos operaciones de este módulo, a saber:

* **generarCSV** : Se llama a través del servicio ``/expedientesAlmacenados/expedienteGenerarTokenDescarga`` del controlador de expediente de INSIDE.

* **generarCSVAmbito** : Se llama a través de los servicios ``/saveDocument``, ``/generarDocumento`` y ``/editarDocumento/updateDocument`` del controlador de documentos de INSIDE.

El resto de operaciones de este módulo **no** están siendo utilizadas por la aplicación INSIDE.
