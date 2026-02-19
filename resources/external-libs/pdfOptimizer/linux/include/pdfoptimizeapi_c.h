#ifndef _PDFOPTIMIZEAPI_INCLUDED
#define _PDFOPTIMIZEAPI_INCLUDED

#include "pdfcdecl.h"
#include "pdfcodecdecl.h"
#include "pdfsecuritydecl.h"
#include "pdfoptimizedecl.h"
#include "pdfrasterizerdecl.h"
#include "pdferror.h"

#ifdef __cplusplus
extern "C" {
#endif

#if defined(WIN32) && !defined(_ASSEMBLY)
#    ifdef PDFOPTIMIZE_EXPORTS
#        define PDFOPTIMIZEAPI
#    else
#        define PDFOPTIMIZEAPI __declspec(dllimport)
#    endif
#else
#    define PDFOPTIMIZEAPI
#endif

#ifndef PDFOPTIMIZECALL
#   if defined(WIN32)
#       define PDFOPTIMIZECALL __stdcall
#   else
#       define PDFOPTIMIZECALL
#   endif
#endif

#ifdef _UNICODE
#define PdfOptimizeSetLicenseKey    PdfOptimizeSetLicenseKeyW
#define PdfOptimizeGetProductVersion PdfOptimizeGetProductVersionW
#define PdfOptimizeOpen             PdfOptimizeOpenW
#define PdfOptimizeSaveAs           PdfOptimizeSaveAsW
#define PdfOptimizeRenameFont       PdfOptimizeRenameFontW
#define PdfOptimizeUnembedFont      PdfOptimizeUnembedFontW
#define PdfOptimizeListImages       PdfOptimizeListImagesW
#define PdfOptimizeListFonts        PdfOptimizeListFontsW
#define PdfOptimizeSetVersion       PdfOptimizeSetVersionW
#define PdfOptimizeSetInfoEntry     PdfOptimizeSetInfoEntryW
#define PdfLinerizeFile             PdfLinearizeFileW
#define PdfOptimizeSetCMSEngine     PdfOptimizeSetCMSEngineW
#else
#define PdfOptimizeSetLicenseKey    PdfOptimizeSetLicenseKeyA
#define PdfOptimizeGetProductVersion PdfOptimizeGetProductVersionA
#define PdfOptimizeOpen             PdfOptimizeOpenA
#define PdfOptimizeSaveAs           PdfOptimizeSaveAsA
#define PdfOptimizeRenameFont       PdfOptimizeRenameFontA
#define PdfOptimizeUnembedFont      PdfOptimizeUnembedFontW
#define PdfOptimizeListImages       PdfOptimizeListImagesA
#define PdfOptimizeListFonts        PdfOptimizeListFontsA
#define PdfOptimizeSetVersion       PdfOptimizeSetVersionA
#define PdfOptimizeSetInfoEntry     PdfOptimizeSetInfoEntryA
#define PdfLinerizeFile             PdfLinearizeFileA
#define PdfOptimizeSetCMSEngine     PdfOptimizeSetCMSEngineA
#endif

/*
 * Deprecated functions.
 */
#define PdfOptimizeGetCompressionQuality(handle)           PdfOptimizeGetImageQuality(handle)     /**< \deprecated Use ::PdfOptimizeGetImageQuality */
#define PdfOptimizeSetCompressionQuality(handle, iQuality) PdfOptimizeSetImageQuality(handle, iQuality) /**< \deprecated Use ::PdfOptimizeSetImageQuality */

/** \defgroup types Type definitions
 * @{
 */

typedef struct TPdfOptimizer TPdfOptimizer;                                                       /**< Handle to the TPdfOptimizer object. */

typedef TPdfOptimizer* TPdfOptimize;                                                              /**< \deprecated Use ::TPdfOptimizer */

/** @}
 * \defgroup api API initialization and termination functions
 * @{
 */

PDFOPTIMIZEAPI void PDFOPTIMIZECALL PdfOptimizeInitialize();                                      /**< This function must be called before using any other functions of the API */
PDFOPTIMIZEAPI void PDFOPTIMIZECALL PdfOptimizeUnInitialize();                                    /**< Call this function to free resources allocated during InitializeAPI */

PDFOPTIMIZEAPI int  PDFOPTIMIZECALL PdfOptimizeSetLicenseKeyA(const char* szLicenseKey);
PDFOPTIMIZEAPI int  PDFOPTIMIZECALL PdfOptimizeSetLicenseKeyW(const WCHAR* szLicenseKey);
PDFOPTIMIZEAPI int  PDFOPTIMIZECALL PdfOptimizeGetLicenseIsValid();

PDFOPTIMIZEAPI const char* PDFOPTIMIZECALL PdfOptimizeGetProductVersionA();
PDFOPTIMIZEAPI const WCHAR* PDFOPTIMIZECALL PdfOptimizeGetProductVersionW();

/** @}
 * \defgroup opt Optimization related functions
 * @{
 */

PDFOPTIMIZEAPI TPdfOptimizer* PDFOPTIMIZECALL PdfOptimizeCreateObject();                          /**< Create the PdfOptimize object.  */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeDestroyObject(TPdfOptimizer* handle);             /**< Destroy the PdfOptimize object. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeOpenA(TPdfOptimizer* handle,
                                                           const char* szPath,
                                                           const char* szPassword);               /**< Open the input PDF file.  */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeOpenW(TPdfOptimizer* handle,
                                                           const WCHAR* filepath,
                                                           const WCHAR* password);
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeOpenMemA(TPdfOptimizer* handle, 
                                                           void* pData, 
                                                           size_t nSize, 
                                                           const char* szPassword); 
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeOpenMemW(TPdfOptimizer* handle, 
                                                           void* pData, 
                                                           size_t nSize, 
                                                           const WCHAR* szPassword); 
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeSaveAsA(TPdfOptimizer* handle,
                                                           const char* szPath,
                                                           const char* szUserPw, 
                                                           const char* szOwnerPw, 
                                                           int iPermissionFlags);                 /**< Create the output PDF file. Return codes: 1=succeed; 0= fail*/
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeSaveAsW(TPdfOptimizer* handle,
                                                           const WCHAR* filepath,
                                                           const WCHAR* userpass,
                                                           const WCHAR* ownerpass,
                                                           int iPermissionflags);
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeSaveInMemory(TPdfOptimizer* handle);
PDFOPTIMIZEAPI TPDFByteArray*
                     PDFOPTIMIZECALL PdfOptimizeGetPdf(TPdfOptimizer* handle);
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeClose(TPdfOptimizer* handle);                     /**< Close both the input and output files. */
PDFOPTIMIZEAPI TPDFErrorCode
                     PDFOPTIMIZECALL PdfOptimizeGetErrorCode(TPdfOptimizer* handle);
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetProfile(TPdfOptimizer* handle,
                                                           TPDFOptimizationProfile iProfile);     /**< Set all parameters according to the given profile. */
PDFOPTIMIZEAPI TPDFCompression 
                     PDFOPTIMIZECALL PdfOptimizeGetBitonalCompression(TPdfOptimizer* handle);     /**< \deprecated Deprecated in Version 4.6. Use ::PdfOptimizeGetBitonalCompressions. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetBitonalCompression(TPdfOptimizer* handle,
                                                           TPDFCompression iCompression);         /**< \deprecated Deprecated in Version 4.6. Use ::PdfOptimizeSetBitonalCompressions. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetBitonalCompressions(TPdfOptimizer* handle);    /**< Get compressions for bitonal images. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetBitonalCompressions(TPdfOptimizer* handle,
                                                           int iCompressions);                    /**< Set compressions for bitonal images. */
PDFOPTIMIZEAPI TPDFCompression
                     PDFOPTIMIZECALL PdfOptimizeGetMonochromeCompression(TPdfOptimizer* handle);  /**< \deprecated Deprecated in Version 4.6. Use ::PdfOptimizeGetContinuousCompressions. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetMonochromeCompression(TPdfOptimizer* handle,
                                                           TPDFCompression iCompression);         /**< \deprecated Deprecated in Version 4.6. Use ::PdfOptimizeSetContinuousCompressions. */
PDFOPTIMIZEAPI TPDFCompression
                     PDFOPTIMIZECALL PdfOptimizeGetColorCompression(TPdfOptimizer* handle);       /**< \deprecated Deprecated in Version 4.6. Use ::PdfOptimizeGetContinuousCompressions. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetColorCompression(TPdfOptimizer* handle,
                                                           TPDFCompression iCompression);         /**< \deprecated Deprecated in Version 4.6. Use ::PdfOptimizeSetContinuousCompressions. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetContinuousCompressions(TPdfOptimizer* handle); /**< Get the compressions for color images. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetContinuousCompressions(TPdfOptimizer* handle,
                                                           int iCompression);                     /**< Set the compressions for color images. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetIndexedCompressions(TPdfOptimizer* handle);    /**< Get the compressions for indexed images. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetIndexedCompressions(TPdfOptimizer* handle, 
                                                           int iCompression);                     /**< Set the compressions for indexed images. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetForceRecompression(TPdfOptimizer* handle);     /**< return nonzero if image recompression is forced*/
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetForceRecompression(TPdfOptimizer* handle,
                                                           int bForce);                           /**< Specify whether to force image recompression */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetForceCompressionTypes(TPdfOptimizer* handle);  /**< Return nonzero if image compression types are restricted to those set in PdfOptimizeSet[Bitonal|Continuous|Indexed]Compressions */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetForceCompressionTypes(TPdfOptimizer* handle,
                                                           int bForce);                           /**< Specify whether to force image compression types are restricted to those set in PdfOptimizeSet[Bitonal|Continuous|Indexed]Compressions */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetImageStratConserv(TPdfOptimizer* handle);      /**< \deprecated Deprecated in Version 4.7. Return nonzero if image processing strategy is conservative */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetImageStratConserv(TPdfOptimizer* handle,
                                                           int bVal);                             /**< \deprecated Deprecated in Version 4.7. A non-zero value enables the conservative image processing strategy */
PDFOPTIMIZEAPI float PDFOPTIMIZECALL PdfOptimizeGetResolutionDPI(TPdfOptimizer* handle);          /**< Get the resolution in DPI after resampling. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetResolutionDPI(TPdfOptimizer* handle,
                                                           float fResolutionDPI);                 /**< Set the resolution in DPI after resampling. */
PDFOPTIMIZEAPI float PDFOPTIMIZECALL PdfOptimizeGetThresholdDPI(TPdfOptimizer* handle);           /**< Get the threshold in DPI to selectively activate resampling. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetThresholdDPI(TPdfOptimizer* handle,
                                                           float fThresholdDPI);                  /**< Set the threshold in DPI to selectively activate resampling. */
PDFOPTIMIZEAPI TPDFColorConversion
                     PDFOPTIMIZECALL PdfOptimizeGetColorConversion(TPdfOptimizer* handle);        /**< Get the color conversion. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetColorConversion(TPdfOptimizer* handle,
                                                           TPDFColorConversion iColorConversion); /**< Set the color conversion. */
PDFOPTIMIZEAPI TPDFDitheringMode
                     PDFOPTIMIZECALL PdfOptimizeGetDitheringMode(TPdfOptimizer* handle);          /**< Get the dithering mode for down-sampling bitonal images. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetDitheringMode(TPdfOptimizer* handle,
                                                           TPDFDitheringMode iDithering);         /**< Set the dithering mode for down-sampling bitonal images. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetImageQuality(TPdfOptimizer* handle);           /**< Get the quality (0..100) of the lossy compression. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetImageQuality(TPdfOptimizer* handle,
                                                           int iQuality);                         /**< Set the quality (0..100) of the lossy compression. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetClipImages(TPdfOptimizer* handle);             /**< Get the option to clip invisible parts of images. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetClipImages(TPdfOptimizer* handle,
                                                           int bClipImages);                      /**< Set the option to clip invisible parts of images. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetRemoveImages(TPdfOptimizer* handle);           /**< Get the option to remove images. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetRemoveImages(TPdfOptimizer* handle,
                                                           int bRemoveImages);                    /**< Set the option to remove images. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetReduceColorComplexity(TPdfOptimizer* handle);  /**< Get the option to reduce the color complexity of images. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetReduceColorComplexity(TPdfOptimizer* handle,
                                                           int bReduce);                          /**< Set the option to reduce the color complexity of images. */
PDFOPTIMIZEAPI TPDFCompression
                     PDFOPTIMIZECALL PdfOptimizeGetMrcMaskCompression(TPdfOptimizer* handle);     /**< Get the compression for MRC masks. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetMrcMaskCompression(TPdfOptimizer* handle,
                                                           TPDFCompression iCompression);         /**< Set the compression for MRC masks. */
PDFOPTIMIZEAPI TPDFCompression
                     PDFOPTIMIZECALL PdfOptimizeGetMrcLayerCompression(TPdfOptimizer* handle);    /**< Get the compression for MRC layers. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetMrcLayerCompression(TPdfOptimizer* handle,
                                                           TPDFCompression iCompression);         /**< Set the compression for MRC layers. */
PDFOPTIMIZEAPI int
                     PDFOPTIMIZECALL PdfOptimizeGetMrcRecognizePictures(TPdfOptimizer* handle);   /**< Get the option to recognize areas of photographic pictures when doing MRC */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetMrcRecognizePictures(TPdfOptimizer* handle,
                                                           int bRecognize);                       /**< Set the option to recognize areas of photographic pictures when doing MRC */
PDFOPTIMIZEAPI TPDFCompression
                     PDFOPTIMIZECALL PdfOptimizeGetMrcPictCompression(TPdfOptimizer* handle);     /**< Get the compression for MRC foreground pictures. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetMrcPictCompression(TPdfOptimizer* handle,
                                                           TPDFCompression iCompression);         /**< Set the compression for MRC foreground pictures. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetMrcLayerQuality(TPdfOptimizer* handle);        /**< Get the quality (0..100) of the lossy compression for MRC layers. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetMrcLayerQuality(TPdfOptimizer* handle,
                                                           int iQuality);                         /**< Set the quality (0..100) of the lossy compression for MRC layers. */
PDFOPTIMIZEAPI float PDFOPTIMIZECALL PdfOptimizeGetMrcLayerResolutionDPI(TPdfOptimizer* handle);  /**< Get the resolution in DPI after resampling for MRC layers. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetMrcLayerResolutionDPI(TPdfOptimizer* handle,
                                                           float fResolutionDPI);                 /**< Set the resolution in DPI after resampling for MRC layers. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetExtractImages(TPdfOptimizer* handle);          /**< Return nonzero if setting to extract images is enabled */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetExtractImages(TPdfOptimizer* handle,
                                                           int bExtract);                         /**< Specify the setting for extracting images */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetExtractFonts(TPdfOptimizer* handle);           /**< Return nonzero if setting to extract fonts is enabled */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetExtractFonts(TPdfOptimizer* handle,
                                                           int bExtract);                         /**< Specify the setting for extracting fonts */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetRemoveRedundantObjects(TPdfOptimizer* handle); /**< Return nonzero if setting to remove duplicate objects is enabled */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetRemoveRedundantObjects(TPdfOptimizer* handle,
                                                           int bRemove);                          /**< Specify the setting for removing duplicate objects */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetLinearize(TPdfOptimizer* handle);              /**< return nonzero if setting to linearize is enabled */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetLinearize(TPdfOptimizer* handle,
                                                           int bLinearize);                       /**< Specify the setting for linearizing */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeSetCMSEngineW(const WCHAR* szCMSEngine);          /**< Return the color management system */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeSetCMSEngineA(const char* szCMSEngine);           /**< Specify the color management system */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetStrip(TPdfOptimizer* handle);                  /**< Get the strip flags. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetStrip(TPdfOptimizer* handle,
                                                           int iStripModes);                      /**< Set the strip flags. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetFlattenSignatureFields(TPdfOptimizer* handle); /**< Return nonzero if flattening of appearances of signature fields is enabled */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetFlattenSignatureFields(TPdfOptimizer* handle,
    int bFlatten);                                                                                /**< Specify whether to flatten appearances of signature fields */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeRenameFontA(TPdfOptimizer* handle,
                                                           const char* szFontName1,
                                                           const char* szFontName2);              /**< Rename font name. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeRenameFontW(TPdfOptimizer* handle,
                                                           const WCHAR* szFontName1,
                                                           const WCHAR* szFontName2);             /**< Rename font name. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeUnembedFontA(TPdfOptimizer* handle,
                                                           const char* szFontName);               /**< Remove embedded font program. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeUnembedFontW(TPdfOptimizer* handle,
                                                          const WCHAR* szFontName);               /**< Remove embedded font program. */

PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetConvertToCFF(TPdfOptimizer* handle);
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetConvertToCFF(TPdfOptimizer* handle,
                                                           int bConvertToCFF);
PDFOPTIMIZEAPI float PDFOPTIMIZECALL PdfOptimizeGetColorResolutionDPI(TPdfOptimizer* handle);     /**< Get the Color resolution in DPI after resampling. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetColorResolutionDPI(TPdfOptimizer* handle,
                                                           float fResolutionDPI);                 /**< Set the Color resolution in DPI after resampling. */
PDFOPTIMIZEAPI float PDFOPTIMIZECALL PdfOptimizeGetColorThresholdDPI(TPdfOptimizer* handle);      /**< Get the Color threshold in DPI to selectively activate resampling. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetColorThresholdDPI(TPdfOptimizer* handle,
                                                           float fThresholdDPI);                  /**< Set the Color threshold in DPI to selectively activate resampling. */
PDFOPTIMIZEAPI float PDFOPTIMIZECALL PdfOptimizeGetBitonalResolutionDPI(TPdfOptimizer* handle);   /**< Get the Bitonal resolution in DPI after resampling. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetBitonalResolutionDPI(TPdfOptimizer* handle,
                                                           float fResolutionDPI);                 /**< Set the Bitonal resolution in DPI after resampling. */
PDFOPTIMIZEAPI float PDFOPTIMIZECALL PdfOptimizeGetBitonalThresholdDPI(TPdfOptimizer* handle);    /**< Get the Bitonal threshold in DPI to selectively activate resampling. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetBitonalThresholdDPI(TPdfOptimizer* handle,
                                                           float fThresholdDPI);                  /**< Set the Bitonal threshold in DPI to selectively activate resampling. */
PDFOPTIMIZEAPI float PDFOPTIMIZECALL PdfOptimizeGetMonochromeResolutionDPI(TPdfOptimizer* handle);/**< Get the Monochrome resolution in DPI after resampling. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetMonochromeResolutionDPI(TPdfOptimizer* handle,
                                                           float fResolutionDPI);                 /**< Set the Monochrome resolution in DPI after resampling. */
PDFOPTIMIZEAPI float PDFOPTIMIZECALL PdfOptimizeGetMonochromeThresholdDPI(TPdfOptimizer* handle); /**< Get the Monochrome threshold in DPI to selectively activate resampling. */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetMonochromeThresholdDPI(TPdfOptimizer* handle,
                                                           float fThresholdDPI);                  /**< Set the Monochrome threshold in DPI to selectively activate resampling. */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeListImagesA(TPdfOptimizer* handle,
                                                           const char* szFileName);               /**< Save the list of images to a file */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeListImagesW(TPdfOptimizer* handle,
                                                           const WCHAR* szFileName);              /**< Save the list of images to a file */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeListFontsA(TPdfOptimizer* handle,
                                                           const char* szFileName);               /**< Save the list of fonts to a file */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeListFontsW(TPdfOptimizer* handle,
                                                           const WCHAR* szFileName);              /**< Save the list of fonts to a file */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetSubsetFonts(TPdfOptimizer* handle);
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetSubsetFonts(TPdfOptimizer* handle,
                                                           int bSubset);
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetMergeEmbeddedFonts(TPdfOptimizer* handle);
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetMergeEmbeddedFonts(TPdfOptimizer* handle,
                                                           int bMerge);
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetOptimizeResources(TPdfOptimizer* handle);
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetOptimizeResources(TPdfOptimizer* handle,
                                                           int bOptimize);
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetRemoveStandardFonts(TPdfOptimizer* handle);
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetRemoveStandardFonts(TPdfOptimizer* handle,
                                                           int bRemove);
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetRemoveNonSymbolicFonts(TPdfOptimizer* handle); /**< \deprecated */
PDFOPTIMIZEAPI void  PDFOPTIMIZECALL PdfOptimizeSetRemoveNonSymbolicFonts(TPdfOptimizer* handle,
                                                           int bRemove);                          /**< \deprecated */
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeGetPageCount(TPdfOptimizer* handle);
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeSetVersionA(TPdfOptimizer* handle,
                                                           const char* szVersion);
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeSetVersionW(TPdfOptimizer* handle,
                                                           const WCHAR* szVersion);
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeSetInfoEntryA(TPdfOptimizer* handle,
                                                           const char* szKey,
                                                           const char* szValue);
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfOptimizeSetInfoEntryW(TPdfOptimizer* handle,
                                                           const WCHAR* szKey,
                                                           const WCHAR* szValue);
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfLinearizeFileA(const char* szInFileName,
                                                           const char* szPassword,
                                                           const char* szOutFileName,
                                                           const char* szUserPw,
                                                           const char* szOwnerPw,
                                                           int iPermissionFlags);
PDFOPTIMIZEAPI int   PDFOPTIMIZECALL PdfLinearizeFileW(const WCHAR* szInFileName,
                                                           const WCHAR* szPassword,
                                                           const WCHAR* szOutFileName,
                                                           const WCHAR* szUserPw,
                                                           const WCHAR* szOwnerPw,
                                                           int iPermissionFlags);

/** @}
 */

#ifdef __cplusplus
}
#endif

#endif /* _PDFOPTIMIZEAPI_INCLUDED */
