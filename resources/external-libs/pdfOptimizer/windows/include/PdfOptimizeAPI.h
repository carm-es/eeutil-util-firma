

/* this ALWAYS GENERATED file contains the definitions for the interfaces */


 /* File created by MIDL compiler version 7.00.0555 */
/* at Sun Apr 02 10:15:14 2017
 */
/* Compiler settings for PdfOptimizeAPI.idl:
    Oicf, W1, Zp8, env=Win64 (32b run), target_arch=AMD64 7.00.0555 
    protocol : dce , ms_ext, c_ext, robust
    error checks: allocation ref bounds_check enum stub_data 
    VC __declspec() decoration level: 
         __declspec(uuid()), __declspec(selectany), __declspec(novtable)
         DECLSPEC_UUID(), MIDL_INTERFACE()
*/
/* @@MIDL_FILE_HEADING(  ) */

#pragma warning( disable: 4049 )  /* more than 64k source lines */


/* verify that the <rpcndr.h> version is high enough to compile this file*/
#ifndef __REQUIRED_RPCNDR_H_VERSION__
#define __REQUIRED_RPCNDR_H_VERSION__ 475
#endif

#include "rpc.h"
#include "rpcndr.h"

#ifndef __RPCNDR_H_VERSION__
#error this stub requires an updated version of <rpcndr.h>
#endif // __RPCNDR_H_VERSION__


#ifndef __PdfOptimizeAPI_h__
#define __PdfOptimizeAPI_h__

#if defined(_MSC_VER) && (_MSC_VER >= 1020)
#pragma once
#endif

/* Forward Declarations */ 

#ifndef __IPDFOptimizer_FWD_DEFINED__
#define __IPDFOptimizer_FWD_DEFINED__
typedef interface IPDFOptimizer IPDFOptimizer;
#endif 	/* __IPDFOptimizer_FWD_DEFINED__ */


#ifndef __PDFOptimizer_FWD_DEFINED__
#define __PDFOptimizer_FWD_DEFINED__

#ifdef __cplusplus
typedef class PDFOptimizer PDFOptimizer;
#else
typedef struct PDFOptimizer PDFOptimizer;
#endif /* __cplusplus */

#endif 	/* __PDFOptimizer_FWD_DEFINED__ */


/* header files for imported files */
#include "oaidl.h"
#include "ocidl.h"
#include "pdfsecuritydecl.h"
#include "pdfcodecdecl.h"
#include "pdfoptimizedecl.h"
#include "pdfrasterizerdecl.h"

#ifdef __cplusplus
extern "C"{
#endif 



#ifndef __PDFOPTIMIZEAPILib_LIBRARY_DEFINED__
#define __PDFOPTIMIZEAPILib_LIBRARY_DEFINED__

/* library PDFOPTIMIZEAPILib */
/* [helpstring][version][uuid] */ 


EXTERN_C const IID LIBID_PDFOPTIMIZEAPILib;

#ifndef __IPDFOptimizer_INTERFACE_DEFINED__
#define __IPDFOptimizer_INTERFACE_DEFINED__

/* interface IPDFOptimizer */
/* [unique][helpstring][dual][uuid][object] */ 


EXTERN_C const IID IID_IPDFOptimizer;

#if defined(__cplusplus) && !defined(CINTERFACE)
    
    MIDL_INTERFACE("F3232F5A-42B6-4A89-BCFC-55CDC1575241")
    IPDFOptimizer : public IDispatch
    {
    public:
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE Open( 
            BSTR bstrPath,
            /* [defaultvalue][in] */ BSTR bstrPassword,
            /* [retval][out] */ VARIANT_BOOL *pDone) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE Close( 
            /* [retval][out] */ VARIANT_BOOL *pDone) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SaveAs( 
            BSTR bstrPath,
            /* [defaultvalue][in] */ BSTR bstrUserPw,
            /* [defaultvalue][in] */ BSTR bstrOwnerPw,
            /* [defaultvalue] */ long iPermissionFlags,
            /* [retval][out] */ VARIANT_BOOL *pDone) = 0;
        
        virtual /* [hidden][id][propget] */ HRESULT STDMETHODCALLTYPE get_BitonalCompression( 
            /* [retval][out] */ TPDFCompression *pVal) = 0;
        
        virtual /* [hidden][id][propput] */ HRESULT STDMETHODCALLTYPE put_BitonalCompression( 
            /* [in] */ TPDFCompression newVal) = 0;
        
        virtual /* [hidden][id][propget] */ HRESULT STDMETHODCALLTYPE get_MonochromeCompression( 
            /* [retval][out] */ TPDFCompression *pVal) = 0;
        
        virtual /* [hidden][id][propput] */ HRESULT STDMETHODCALLTYPE put_MonochromeCompression( 
            /* [in] */ TPDFCompression newVal) = 0;
        
        virtual /* [hidden][id][propget] */ HRESULT STDMETHODCALLTYPE get_ColorCompression( 
            /* [retval][out] */ TPDFCompression *pVal) = 0;
        
        virtual /* [hidden][id][propput] */ HRESULT STDMETHODCALLTYPE put_ColorCompression( 
            /* [in] */ TPDFCompression newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ResolutionDPI( 
            /* [retval][out] */ float *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ResolutionDPI( 
            /* [in] */ float newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ThresholdDPI( 
            /* [retval][out] */ float *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ThresholdDPI( 
            /* [in] */ float newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ColorConversion( 
            /* [retval][out] */ TPDFColorConversion *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ColorConversion( 
            /* [in] */ TPDFColorConversion newVal) = 0;
        
        virtual /* [hidden][id][propget] */ HRESULT STDMETHODCALLTYPE get_CompressionQuality( 
            /* [retval][out] */ short *pVal) = 0;
        
        virtual /* [hidden][id][propput] */ HRESULT STDMETHODCALLTYPE put_CompressionQuality( 
            /* [in] */ short newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ExtractImages( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ExtractImages( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ExtractFonts( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ExtractFonts( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_RemoveRedundantObjects( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_RemoveRedundantObjects( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_Linearize( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_Linearize( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_Strip( 
            /* [retval][out] */ long *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_Strip( 
            /* [in] */ long newVal) = 0;
        
        virtual /* [hidden][id][propput] */ HRESULT STDMETHODCALLTYPE put__Permission( 
            /* [in] */ TPDFPermission newVal) = 0;
        
        virtual /* [hidden][id][propput] */ HRESULT STDMETHODCALLTYPE put__FontType( 
            /* [in] */ TPDFFontType newVal) = 0;
        
        virtual /* [hidden][id][propput] */ HRESULT STDMETHODCALLTYPE put__StripType( 
            /* [in] */ TPDFStripType newVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE RenameFont( 
            BSTR bstrFontName1,
            BSTR bstrFontName2,
            /* [retval][out] */ VARIANT_BOOL *pDone) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ColorResolutionDPI( 
            /* [retval][out] */ float *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ColorResolutionDPI( 
            /* [in] */ float newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_BitonalResolutionDPI( 
            /* [retval][out] */ float *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_BitonalResolutionDPI( 
            /* [in] */ float newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_MonochromeResolutionDPI( 
            /* [retval][out] */ float *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_MonochromeResolutionDPI( 
            /* [in] */ float newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ColorThresholdDPI( 
            /* [retval][out] */ float *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ColorThresholdDPI( 
            /* [in] */ float newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_BitonalThresholdDPI( 
            /* [retval][out] */ float *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_BitonalThresholdDPI( 
            /* [in] */ float newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_MonochromeThresholdDPI( 
            /* [retval][out] */ float *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_MonochromeThresholdDPI( 
            /* [in] */ float newVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE ListImages( 
            BSTR bstrFileName,
            /* [retval][out] */ VARIANT_BOOL *pDone) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE ListFonts( 
            BSTR bstrFileName,
            /* [retval][out] */ VARIANT_BOOL *pDone) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE OpenMem( 
            VARIANT *varMem,
            /* [defaultvalue][in] */ BSTR bstrPassword,
            /* [retval][out] */ VARIANT_BOOL *bDone) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SaveInMemory( 
            /* [retval][out] */ VARIANT_BOOL *bDone) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE GetPdf( 
            /* [retval][out] */ VARIANT *pVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_SubsetFonts( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_SubsetFonts( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_OptimizeResources( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_OptimizeResources( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ErrorCode( 
            /* [retval][out] */ TPDFErrorCode *pVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_RemoveStandardFonts( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_RemoveStandardFonts( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [hidden][id][propget] */ HRESULT STDMETHODCALLTYPE get_RemoveNonSymbolicFonts( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [hidden][id][propput] */ HRESULT STDMETHODCALLTYPE put_RemoveNonSymbolicFonts( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_PageCount( 
            /* [retval][out] */ long *pVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SetVersion( 
            BSTR bstrVersion,
            /* [retval][out] */ VARIANT_BOOL *pDone) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ImageQuality( 
            /* [retval][out] */ short *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ImageQuality( 
            /* [in] */ short newVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE LinearizeFile( 
            /* [in] */ BSTR bstrInFileName,
            /* [in] */ BSTR bstrPassword,
            /* [in] */ BSTR bstrOutFileName,
            /* [in] */ BSTR bstrUserPw,
            /* [in] */ BSTR bstrOwnerPw,
            /* [in] */ LONG iPermissionFlags,
            /* [retval][out] */ VARIANT_BOOL *pDone) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SetLicenseKey( 
            /* [in] */ BSTR bstrLicenseKey,
            /* [retval][out] */ VARIANT_BOOL *pValid) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_LicenseIsValid( 
            /* [retval][out] */ VARIANT_BOOL *pValid) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ForceRecompression( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ForceRecompression( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ConvertToCFF( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ConvertToCFF( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_MergeEmbeddedFonts( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_MergeEmbeddedFonts( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SetInfoEntry( 
            /* [in] */ BSTR sKey,
            /* [in] */ BSTR newVal,
            /* [retval][out] */ VARIANT_BOOL *pDone) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_BitonalCompressions( 
            /* [retval][out] */ int *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_BitonalCompressions( 
            /* [in] */ int newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ContinuousCompressions( 
            /* [retval][out] */ int *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ContinuousCompressions( 
            /* [in] */ int newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_IndexedCompressions( 
            /* [retval][out] */ int *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_IndexedCompressions( 
            /* [in] */ int newVal) = 0;
        
        virtual /* [hidden][id][propget] */ HRESULT STDMETHODCALLTYPE get_ImageStratConserv( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [hidden][id][propput] */ HRESULT STDMETHODCALLTYPE put_ImageStratConserv( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_MrcMaskCompression( 
            /* [retval][out] */ TPDFCompression *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_MrcMaskCompression( 
            /* [in] */ TPDFCompression newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_MrcLayerCompression( 
            /* [retval][out] */ TPDFCompression *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_MrcLayerCompression( 
            /* [in] */ TPDFCompression newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_MrcPictCompression( 
            /* [retval][out] */ TPDFCompression *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_MrcPictCompression( 
            /* [in] */ TPDFCompression newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_MrcLayerQuality( 
            /* [retval][out] */ short *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_MrcLayerQuality( 
            /* [in] */ short newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_MrcLayerResolutionDPI( 
            /* [retval][out] */ float *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_MrcLayerResolutionDPI( 
            /* [in] */ float newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ClipImages( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ClipImages( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ForceCompressionTypes( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ForceCompressionTypes( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_DitheringMode( 
            /* [retval][out] */ TPDFDitheringMode *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_DitheringMode( 
            /* [in] */ TPDFDitheringMode newVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SetCMSEngine( 
            BSTR bstrCMSEngine,
            /* [retval][out] */ VARIANT_BOOL *pDone) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_Profile( 
            /* [in] */ TPDFOptimizationProfile newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_MrcRecognizePictures( 
            /* [retval][out] */ VARIANT_BOOL *pVAL) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_MrcRecognizePictures( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE UnembedFont( 
            BSTR bstrFontName,
            /* [retval][out] */ VARIANT_BOOL *pDone) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ProductVersion( 
            /* [retval][out] */ BSTR *pVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ReduceColorComplexity( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ReduceColorComplexity( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_FlattenSignatureFields( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_FlattenSignatureFields( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
    };
    
#else 	/* C style interface */

    typedef struct IPDFOptimizerVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE *QueryInterface )( 
            IPDFOptimizer * This,
            /* [in] */ REFIID riid,
            /* [annotation][iid_is][out] */ 
            __RPC__deref_out  void **ppvObject);
        
        ULONG ( STDMETHODCALLTYPE *AddRef )( 
            IPDFOptimizer * This);
        
        ULONG ( STDMETHODCALLTYPE *Release )( 
            IPDFOptimizer * This);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfoCount )( 
            IPDFOptimizer * This,
            /* [out] */ UINT *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfo )( 
            IPDFOptimizer * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo **ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetIDsOfNames )( 
            IPDFOptimizer * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR *rgszNames,
            /* [range][in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE *Invoke )( 
            IPDFOptimizer * This,
            /* [in] */ DISPID dispIdMember,
            /* [in] */ REFIID riid,
            /* [in] */ LCID lcid,
            /* [in] */ WORD wFlags,
            /* [out][in] */ DISPPARAMS *pDispParams,
            /* [out] */ VARIANT *pVarResult,
            /* [out] */ EXCEPINFO *pExcepInfo,
            /* [out] */ UINT *puArgErr);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *Open )( 
            IPDFOptimizer * This,
            BSTR bstrPath,
            /* [defaultvalue][in] */ BSTR bstrPassword,
            /* [retval][out] */ VARIANT_BOOL *pDone);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *Close )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pDone);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SaveAs )( 
            IPDFOptimizer * This,
            BSTR bstrPath,
            /* [defaultvalue][in] */ BSTR bstrUserPw,
            /* [defaultvalue][in] */ BSTR bstrOwnerPw,
            /* [defaultvalue] */ long iPermissionFlags,
            /* [retval][out] */ VARIANT_BOOL *pDone);
        
        /* [hidden][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_BitonalCompression )( 
            IPDFOptimizer * This,
            /* [retval][out] */ TPDFCompression *pVal);
        
        /* [hidden][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_BitonalCompression )( 
            IPDFOptimizer * This,
            /* [in] */ TPDFCompression newVal);
        
        /* [hidden][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_MonochromeCompression )( 
            IPDFOptimizer * This,
            /* [retval][out] */ TPDFCompression *pVal);
        
        /* [hidden][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_MonochromeCompression )( 
            IPDFOptimizer * This,
            /* [in] */ TPDFCompression newVal);
        
        /* [hidden][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ColorCompression )( 
            IPDFOptimizer * This,
            /* [retval][out] */ TPDFCompression *pVal);
        
        /* [hidden][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ColorCompression )( 
            IPDFOptimizer * This,
            /* [in] */ TPDFCompression newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ResolutionDPI )( 
            IPDFOptimizer * This,
            /* [retval][out] */ float *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ResolutionDPI )( 
            IPDFOptimizer * This,
            /* [in] */ float newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ThresholdDPI )( 
            IPDFOptimizer * This,
            /* [retval][out] */ float *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ThresholdDPI )( 
            IPDFOptimizer * This,
            /* [in] */ float newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ColorConversion )( 
            IPDFOptimizer * This,
            /* [retval][out] */ TPDFColorConversion *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ColorConversion )( 
            IPDFOptimizer * This,
            /* [in] */ TPDFColorConversion newVal);
        
        /* [hidden][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_CompressionQuality )( 
            IPDFOptimizer * This,
            /* [retval][out] */ short *pVal);
        
        /* [hidden][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_CompressionQuality )( 
            IPDFOptimizer * This,
            /* [in] */ short newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ExtractImages )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ExtractImages )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ExtractFonts )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ExtractFonts )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_RemoveRedundantObjects )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_RemoveRedundantObjects )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_Linearize )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_Linearize )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_Strip )( 
            IPDFOptimizer * This,
            /* [retval][out] */ long *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_Strip )( 
            IPDFOptimizer * This,
            /* [in] */ long newVal);
        
        /* [hidden][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put__Permission )( 
            IPDFOptimizer * This,
            /* [in] */ TPDFPermission newVal);
        
        /* [hidden][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put__FontType )( 
            IPDFOptimizer * This,
            /* [in] */ TPDFFontType newVal);
        
        /* [hidden][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put__StripType )( 
            IPDFOptimizer * This,
            /* [in] */ TPDFStripType newVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *RenameFont )( 
            IPDFOptimizer * This,
            BSTR bstrFontName1,
            BSTR bstrFontName2,
            /* [retval][out] */ VARIANT_BOOL *pDone);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ColorResolutionDPI )( 
            IPDFOptimizer * This,
            /* [retval][out] */ float *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ColorResolutionDPI )( 
            IPDFOptimizer * This,
            /* [in] */ float newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_BitonalResolutionDPI )( 
            IPDFOptimizer * This,
            /* [retval][out] */ float *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_BitonalResolutionDPI )( 
            IPDFOptimizer * This,
            /* [in] */ float newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_MonochromeResolutionDPI )( 
            IPDFOptimizer * This,
            /* [retval][out] */ float *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_MonochromeResolutionDPI )( 
            IPDFOptimizer * This,
            /* [in] */ float newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ColorThresholdDPI )( 
            IPDFOptimizer * This,
            /* [retval][out] */ float *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ColorThresholdDPI )( 
            IPDFOptimizer * This,
            /* [in] */ float newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_BitonalThresholdDPI )( 
            IPDFOptimizer * This,
            /* [retval][out] */ float *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_BitonalThresholdDPI )( 
            IPDFOptimizer * This,
            /* [in] */ float newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_MonochromeThresholdDPI )( 
            IPDFOptimizer * This,
            /* [retval][out] */ float *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_MonochromeThresholdDPI )( 
            IPDFOptimizer * This,
            /* [in] */ float newVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *ListImages )( 
            IPDFOptimizer * This,
            BSTR bstrFileName,
            /* [retval][out] */ VARIANT_BOOL *pDone);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *ListFonts )( 
            IPDFOptimizer * This,
            BSTR bstrFileName,
            /* [retval][out] */ VARIANT_BOOL *pDone);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *OpenMem )( 
            IPDFOptimizer * This,
            VARIANT *varMem,
            /* [defaultvalue][in] */ BSTR bstrPassword,
            /* [retval][out] */ VARIANT_BOOL *bDone);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SaveInMemory )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *bDone);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *GetPdf )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT *pVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_SubsetFonts )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_SubsetFonts )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_OptimizeResources )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_OptimizeResources )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ErrorCode )( 
            IPDFOptimizer * This,
            /* [retval][out] */ TPDFErrorCode *pVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_RemoveStandardFonts )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_RemoveStandardFonts )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [hidden][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_RemoveNonSymbolicFonts )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [hidden][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_RemoveNonSymbolicFonts )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_PageCount )( 
            IPDFOptimizer * This,
            /* [retval][out] */ long *pVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SetVersion )( 
            IPDFOptimizer * This,
            BSTR bstrVersion,
            /* [retval][out] */ VARIANT_BOOL *pDone);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ImageQuality )( 
            IPDFOptimizer * This,
            /* [retval][out] */ short *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ImageQuality )( 
            IPDFOptimizer * This,
            /* [in] */ short newVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *LinearizeFile )( 
            IPDFOptimizer * This,
            /* [in] */ BSTR bstrInFileName,
            /* [in] */ BSTR bstrPassword,
            /* [in] */ BSTR bstrOutFileName,
            /* [in] */ BSTR bstrUserPw,
            /* [in] */ BSTR bstrOwnerPw,
            /* [in] */ LONG iPermissionFlags,
            /* [retval][out] */ VARIANT_BOOL *pDone);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SetLicenseKey )( 
            IPDFOptimizer * This,
            /* [in] */ BSTR bstrLicenseKey,
            /* [retval][out] */ VARIANT_BOOL *pValid);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_LicenseIsValid )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pValid);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ForceRecompression )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ForceRecompression )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ConvertToCFF )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ConvertToCFF )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_MergeEmbeddedFonts )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_MergeEmbeddedFonts )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SetInfoEntry )( 
            IPDFOptimizer * This,
            /* [in] */ BSTR sKey,
            /* [in] */ BSTR newVal,
            /* [retval][out] */ VARIANT_BOOL *pDone);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_BitonalCompressions )( 
            IPDFOptimizer * This,
            /* [retval][out] */ int *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_BitonalCompressions )( 
            IPDFOptimizer * This,
            /* [in] */ int newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ContinuousCompressions )( 
            IPDFOptimizer * This,
            /* [retval][out] */ int *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ContinuousCompressions )( 
            IPDFOptimizer * This,
            /* [in] */ int newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_IndexedCompressions )( 
            IPDFOptimizer * This,
            /* [retval][out] */ int *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_IndexedCompressions )( 
            IPDFOptimizer * This,
            /* [in] */ int newVal);
        
        /* [hidden][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ImageStratConserv )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [hidden][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ImageStratConserv )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_MrcMaskCompression )( 
            IPDFOptimizer * This,
            /* [retval][out] */ TPDFCompression *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_MrcMaskCompression )( 
            IPDFOptimizer * This,
            /* [in] */ TPDFCompression newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_MrcLayerCompression )( 
            IPDFOptimizer * This,
            /* [retval][out] */ TPDFCompression *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_MrcLayerCompression )( 
            IPDFOptimizer * This,
            /* [in] */ TPDFCompression newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_MrcPictCompression )( 
            IPDFOptimizer * This,
            /* [retval][out] */ TPDFCompression *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_MrcPictCompression )( 
            IPDFOptimizer * This,
            /* [in] */ TPDFCompression newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_MrcLayerQuality )( 
            IPDFOptimizer * This,
            /* [retval][out] */ short *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_MrcLayerQuality )( 
            IPDFOptimizer * This,
            /* [in] */ short newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_MrcLayerResolutionDPI )( 
            IPDFOptimizer * This,
            /* [retval][out] */ float *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_MrcLayerResolutionDPI )( 
            IPDFOptimizer * This,
            /* [in] */ float newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ClipImages )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ClipImages )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ForceCompressionTypes )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ForceCompressionTypes )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_DitheringMode )( 
            IPDFOptimizer * This,
            /* [retval][out] */ TPDFDitheringMode *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_DitheringMode )( 
            IPDFOptimizer * This,
            /* [in] */ TPDFDitheringMode newVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SetCMSEngine )( 
            IPDFOptimizer * This,
            BSTR bstrCMSEngine,
            /* [retval][out] */ VARIANT_BOOL *pDone);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_Profile )( 
            IPDFOptimizer * This,
            /* [in] */ TPDFOptimizationProfile newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_MrcRecognizePictures )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVAL);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_MrcRecognizePictures )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *UnembedFont )( 
            IPDFOptimizer * This,
            BSTR bstrFontName,
            /* [retval][out] */ VARIANT_BOOL *pDone);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ProductVersion )( 
            IPDFOptimizer * This,
            /* [retval][out] */ BSTR *pVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ReduceColorComplexity )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ReduceColorComplexity )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_FlattenSignatureFields )( 
            IPDFOptimizer * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_FlattenSignatureFields )( 
            IPDFOptimizer * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        END_INTERFACE
    } IPDFOptimizerVtbl;

    interface IPDFOptimizer
    {
        CONST_VTBL struct IPDFOptimizerVtbl *lpVtbl;
    };

    

#ifdef COBJMACROS


#define IPDFOptimizer_QueryInterface(This,riid,ppvObject)	\
    ( (This)->lpVtbl -> QueryInterface(This,riid,ppvObject) ) 

#define IPDFOptimizer_AddRef(This)	\
    ( (This)->lpVtbl -> AddRef(This) ) 

#define IPDFOptimizer_Release(This)	\
    ( (This)->lpVtbl -> Release(This) ) 


#define IPDFOptimizer_GetTypeInfoCount(This,pctinfo)	\
    ( (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo) ) 

#define IPDFOptimizer_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    ( (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo) ) 

#define IPDFOptimizer_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    ( (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId) ) 

#define IPDFOptimizer_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    ( (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr) ) 


#define IPDFOptimizer_Open(This,bstrPath,bstrPassword,pDone)	\
    ( (This)->lpVtbl -> Open(This,bstrPath,bstrPassword,pDone) ) 

#define IPDFOptimizer_Close(This,pDone)	\
    ( (This)->lpVtbl -> Close(This,pDone) ) 

#define IPDFOptimizer_SaveAs(This,bstrPath,bstrUserPw,bstrOwnerPw,iPermissionFlags,pDone)	\
    ( (This)->lpVtbl -> SaveAs(This,bstrPath,bstrUserPw,bstrOwnerPw,iPermissionFlags,pDone) ) 

#define IPDFOptimizer_get_BitonalCompression(This,pVal)	\
    ( (This)->lpVtbl -> get_BitonalCompression(This,pVal) ) 

#define IPDFOptimizer_put_BitonalCompression(This,newVal)	\
    ( (This)->lpVtbl -> put_BitonalCompression(This,newVal) ) 

#define IPDFOptimizer_get_MonochromeCompression(This,pVal)	\
    ( (This)->lpVtbl -> get_MonochromeCompression(This,pVal) ) 

#define IPDFOptimizer_put_MonochromeCompression(This,newVal)	\
    ( (This)->lpVtbl -> put_MonochromeCompression(This,newVal) ) 

#define IPDFOptimizer_get_ColorCompression(This,pVal)	\
    ( (This)->lpVtbl -> get_ColorCompression(This,pVal) ) 

#define IPDFOptimizer_put_ColorCompression(This,newVal)	\
    ( (This)->lpVtbl -> put_ColorCompression(This,newVal) ) 

#define IPDFOptimizer_get_ResolutionDPI(This,pVal)	\
    ( (This)->lpVtbl -> get_ResolutionDPI(This,pVal) ) 

#define IPDFOptimizer_put_ResolutionDPI(This,newVal)	\
    ( (This)->lpVtbl -> put_ResolutionDPI(This,newVal) ) 

#define IPDFOptimizer_get_ThresholdDPI(This,pVal)	\
    ( (This)->lpVtbl -> get_ThresholdDPI(This,pVal) ) 

#define IPDFOptimizer_put_ThresholdDPI(This,newVal)	\
    ( (This)->lpVtbl -> put_ThresholdDPI(This,newVal) ) 

#define IPDFOptimizer_get_ColorConversion(This,pVal)	\
    ( (This)->lpVtbl -> get_ColorConversion(This,pVal) ) 

#define IPDFOptimizer_put_ColorConversion(This,newVal)	\
    ( (This)->lpVtbl -> put_ColorConversion(This,newVal) ) 

#define IPDFOptimizer_get_CompressionQuality(This,pVal)	\
    ( (This)->lpVtbl -> get_CompressionQuality(This,pVal) ) 

#define IPDFOptimizer_put_CompressionQuality(This,newVal)	\
    ( (This)->lpVtbl -> put_CompressionQuality(This,newVal) ) 

#define IPDFOptimizer_get_ExtractImages(This,pVal)	\
    ( (This)->lpVtbl -> get_ExtractImages(This,pVal) ) 

#define IPDFOptimizer_put_ExtractImages(This,newVal)	\
    ( (This)->lpVtbl -> put_ExtractImages(This,newVal) ) 

#define IPDFOptimizer_get_ExtractFonts(This,pVal)	\
    ( (This)->lpVtbl -> get_ExtractFonts(This,pVal) ) 

#define IPDFOptimizer_put_ExtractFonts(This,newVal)	\
    ( (This)->lpVtbl -> put_ExtractFonts(This,newVal) ) 

#define IPDFOptimizer_get_RemoveRedundantObjects(This,pVal)	\
    ( (This)->lpVtbl -> get_RemoveRedundantObjects(This,pVal) ) 

#define IPDFOptimizer_put_RemoveRedundantObjects(This,newVal)	\
    ( (This)->lpVtbl -> put_RemoveRedundantObjects(This,newVal) ) 

#define IPDFOptimizer_get_Linearize(This,pVal)	\
    ( (This)->lpVtbl -> get_Linearize(This,pVal) ) 

#define IPDFOptimizer_put_Linearize(This,newVal)	\
    ( (This)->lpVtbl -> put_Linearize(This,newVal) ) 

#define IPDFOptimizer_get_Strip(This,pVal)	\
    ( (This)->lpVtbl -> get_Strip(This,pVal) ) 

#define IPDFOptimizer_put_Strip(This,newVal)	\
    ( (This)->lpVtbl -> put_Strip(This,newVal) ) 

#define IPDFOptimizer_put__Permission(This,newVal)	\
    ( (This)->lpVtbl -> put__Permission(This,newVal) ) 

#define IPDFOptimizer_put__FontType(This,newVal)	\
    ( (This)->lpVtbl -> put__FontType(This,newVal) ) 

#define IPDFOptimizer_put__StripType(This,newVal)	\
    ( (This)->lpVtbl -> put__StripType(This,newVal) ) 

#define IPDFOptimizer_RenameFont(This,bstrFontName1,bstrFontName2,pDone)	\
    ( (This)->lpVtbl -> RenameFont(This,bstrFontName1,bstrFontName2,pDone) ) 

#define IPDFOptimizer_get_ColorResolutionDPI(This,pVal)	\
    ( (This)->lpVtbl -> get_ColorResolutionDPI(This,pVal) ) 

#define IPDFOptimizer_put_ColorResolutionDPI(This,newVal)	\
    ( (This)->lpVtbl -> put_ColorResolutionDPI(This,newVal) ) 

#define IPDFOptimizer_get_BitonalResolutionDPI(This,pVal)	\
    ( (This)->lpVtbl -> get_BitonalResolutionDPI(This,pVal) ) 

#define IPDFOptimizer_put_BitonalResolutionDPI(This,newVal)	\
    ( (This)->lpVtbl -> put_BitonalResolutionDPI(This,newVal) ) 

#define IPDFOptimizer_get_MonochromeResolutionDPI(This,pVal)	\
    ( (This)->lpVtbl -> get_MonochromeResolutionDPI(This,pVal) ) 

#define IPDFOptimizer_put_MonochromeResolutionDPI(This,newVal)	\
    ( (This)->lpVtbl -> put_MonochromeResolutionDPI(This,newVal) ) 

#define IPDFOptimizer_get_ColorThresholdDPI(This,pVal)	\
    ( (This)->lpVtbl -> get_ColorThresholdDPI(This,pVal) ) 

#define IPDFOptimizer_put_ColorThresholdDPI(This,newVal)	\
    ( (This)->lpVtbl -> put_ColorThresholdDPI(This,newVal) ) 

#define IPDFOptimizer_get_BitonalThresholdDPI(This,pVal)	\
    ( (This)->lpVtbl -> get_BitonalThresholdDPI(This,pVal) ) 

#define IPDFOptimizer_put_BitonalThresholdDPI(This,newVal)	\
    ( (This)->lpVtbl -> put_BitonalThresholdDPI(This,newVal) ) 

#define IPDFOptimizer_get_MonochromeThresholdDPI(This,pVal)	\
    ( (This)->lpVtbl -> get_MonochromeThresholdDPI(This,pVal) ) 

#define IPDFOptimizer_put_MonochromeThresholdDPI(This,newVal)	\
    ( (This)->lpVtbl -> put_MonochromeThresholdDPI(This,newVal) ) 

#define IPDFOptimizer_ListImages(This,bstrFileName,pDone)	\
    ( (This)->lpVtbl -> ListImages(This,bstrFileName,pDone) ) 

#define IPDFOptimizer_ListFonts(This,bstrFileName,pDone)	\
    ( (This)->lpVtbl -> ListFonts(This,bstrFileName,pDone) ) 

#define IPDFOptimizer_OpenMem(This,varMem,bstrPassword,bDone)	\
    ( (This)->lpVtbl -> OpenMem(This,varMem,bstrPassword,bDone) ) 

#define IPDFOptimizer_SaveInMemory(This,bDone)	\
    ( (This)->lpVtbl -> SaveInMemory(This,bDone) ) 

#define IPDFOptimizer_GetPdf(This,pVal)	\
    ( (This)->lpVtbl -> GetPdf(This,pVal) ) 

#define IPDFOptimizer_get_SubsetFonts(This,pVal)	\
    ( (This)->lpVtbl -> get_SubsetFonts(This,pVal) ) 

#define IPDFOptimizer_put_SubsetFonts(This,newVal)	\
    ( (This)->lpVtbl -> put_SubsetFonts(This,newVal) ) 

#define IPDFOptimizer_get_OptimizeResources(This,pVal)	\
    ( (This)->lpVtbl -> get_OptimizeResources(This,pVal) ) 

#define IPDFOptimizer_put_OptimizeResources(This,newVal)	\
    ( (This)->lpVtbl -> put_OptimizeResources(This,newVal) ) 

#define IPDFOptimizer_get_ErrorCode(This,pVal)	\
    ( (This)->lpVtbl -> get_ErrorCode(This,pVal) ) 

#define IPDFOptimizer_get_RemoveStandardFonts(This,pVal)	\
    ( (This)->lpVtbl -> get_RemoveStandardFonts(This,pVal) ) 

#define IPDFOptimizer_put_RemoveStandardFonts(This,newVal)	\
    ( (This)->lpVtbl -> put_RemoveStandardFonts(This,newVal) ) 

#define IPDFOptimizer_get_RemoveNonSymbolicFonts(This,pVal)	\
    ( (This)->lpVtbl -> get_RemoveNonSymbolicFonts(This,pVal) ) 

#define IPDFOptimizer_put_RemoveNonSymbolicFonts(This,newVal)	\
    ( (This)->lpVtbl -> put_RemoveNonSymbolicFonts(This,newVal) ) 

#define IPDFOptimizer_get_PageCount(This,pVal)	\
    ( (This)->lpVtbl -> get_PageCount(This,pVal) ) 

#define IPDFOptimizer_SetVersion(This,bstrVersion,pDone)	\
    ( (This)->lpVtbl -> SetVersion(This,bstrVersion,pDone) ) 

#define IPDFOptimizer_get_ImageQuality(This,pVal)	\
    ( (This)->lpVtbl -> get_ImageQuality(This,pVal) ) 

#define IPDFOptimizer_put_ImageQuality(This,newVal)	\
    ( (This)->lpVtbl -> put_ImageQuality(This,newVal) ) 

#define IPDFOptimizer_LinearizeFile(This,bstrInFileName,bstrPassword,bstrOutFileName,bstrUserPw,bstrOwnerPw,iPermissionFlags,pDone)	\
    ( (This)->lpVtbl -> LinearizeFile(This,bstrInFileName,bstrPassword,bstrOutFileName,bstrUserPw,bstrOwnerPw,iPermissionFlags,pDone) ) 

#define IPDFOptimizer_SetLicenseKey(This,bstrLicenseKey,pValid)	\
    ( (This)->lpVtbl -> SetLicenseKey(This,bstrLicenseKey,pValid) ) 

#define IPDFOptimizer_get_LicenseIsValid(This,pValid)	\
    ( (This)->lpVtbl -> get_LicenseIsValid(This,pValid) ) 

#define IPDFOptimizer_get_ForceRecompression(This,pVal)	\
    ( (This)->lpVtbl -> get_ForceRecompression(This,pVal) ) 

#define IPDFOptimizer_put_ForceRecompression(This,newVal)	\
    ( (This)->lpVtbl -> put_ForceRecompression(This,newVal) ) 

#define IPDFOptimizer_get_ConvertToCFF(This,pVal)	\
    ( (This)->lpVtbl -> get_ConvertToCFF(This,pVal) ) 

#define IPDFOptimizer_put_ConvertToCFF(This,newVal)	\
    ( (This)->lpVtbl -> put_ConvertToCFF(This,newVal) ) 

#define IPDFOptimizer_get_MergeEmbeddedFonts(This,pVal)	\
    ( (This)->lpVtbl -> get_MergeEmbeddedFonts(This,pVal) ) 

#define IPDFOptimizer_put_MergeEmbeddedFonts(This,newVal)	\
    ( (This)->lpVtbl -> put_MergeEmbeddedFonts(This,newVal) ) 

#define IPDFOptimizer_SetInfoEntry(This,sKey,newVal,pDone)	\
    ( (This)->lpVtbl -> SetInfoEntry(This,sKey,newVal,pDone) ) 

#define IPDFOptimizer_get_BitonalCompressions(This,pVal)	\
    ( (This)->lpVtbl -> get_BitonalCompressions(This,pVal) ) 

#define IPDFOptimizer_put_BitonalCompressions(This,newVal)	\
    ( (This)->lpVtbl -> put_BitonalCompressions(This,newVal) ) 

#define IPDFOptimizer_get_ContinuousCompressions(This,pVal)	\
    ( (This)->lpVtbl -> get_ContinuousCompressions(This,pVal) ) 

#define IPDFOptimizer_put_ContinuousCompressions(This,newVal)	\
    ( (This)->lpVtbl -> put_ContinuousCompressions(This,newVal) ) 

#define IPDFOptimizer_get_IndexedCompressions(This,pVal)	\
    ( (This)->lpVtbl -> get_IndexedCompressions(This,pVal) ) 

#define IPDFOptimizer_put_IndexedCompressions(This,newVal)	\
    ( (This)->lpVtbl -> put_IndexedCompressions(This,newVal) ) 

#define IPDFOptimizer_get_ImageStratConserv(This,pVal)	\
    ( (This)->lpVtbl -> get_ImageStratConserv(This,pVal) ) 

#define IPDFOptimizer_put_ImageStratConserv(This,newVal)	\
    ( (This)->lpVtbl -> put_ImageStratConserv(This,newVal) ) 

#define IPDFOptimizer_get_MrcMaskCompression(This,pVal)	\
    ( (This)->lpVtbl -> get_MrcMaskCompression(This,pVal) ) 

#define IPDFOptimizer_put_MrcMaskCompression(This,newVal)	\
    ( (This)->lpVtbl -> put_MrcMaskCompression(This,newVal) ) 

#define IPDFOptimizer_get_MrcLayerCompression(This,pVal)	\
    ( (This)->lpVtbl -> get_MrcLayerCompression(This,pVal) ) 

#define IPDFOptimizer_put_MrcLayerCompression(This,newVal)	\
    ( (This)->lpVtbl -> put_MrcLayerCompression(This,newVal) ) 

#define IPDFOptimizer_get_MrcPictCompression(This,pVal)	\
    ( (This)->lpVtbl -> get_MrcPictCompression(This,pVal) ) 

#define IPDFOptimizer_put_MrcPictCompression(This,newVal)	\
    ( (This)->lpVtbl -> put_MrcPictCompression(This,newVal) ) 

#define IPDFOptimizer_get_MrcLayerQuality(This,pVal)	\
    ( (This)->lpVtbl -> get_MrcLayerQuality(This,pVal) ) 

#define IPDFOptimizer_put_MrcLayerQuality(This,newVal)	\
    ( (This)->lpVtbl -> put_MrcLayerQuality(This,newVal) ) 

#define IPDFOptimizer_get_MrcLayerResolutionDPI(This,pVal)	\
    ( (This)->lpVtbl -> get_MrcLayerResolutionDPI(This,pVal) ) 

#define IPDFOptimizer_put_MrcLayerResolutionDPI(This,newVal)	\
    ( (This)->lpVtbl -> put_MrcLayerResolutionDPI(This,newVal) ) 

#define IPDFOptimizer_get_ClipImages(This,pVal)	\
    ( (This)->lpVtbl -> get_ClipImages(This,pVal) ) 

#define IPDFOptimizer_put_ClipImages(This,newVal)	\
    ( (This)->lpVtbl -> put_ClipImages(This,newVal) ) 

#define IPDFOptimizer_get_ForceCompressionTypes(This,pVal)	\
    ( (This)->lpVtbl -> get_ForceCompressionTypes(This,pVal) ) 

#define IPDFOptimizer_put_ForceCompressionTypes(This,newVal)	\
    ( (This)->lpVtbl -> put_ForceCompressionTypes(This,newVal) ) 

#define IPDFOptimizer_get_DitheringMode(This,pVal)	\
    ( (This)->lpVtbl -> get_DitheringMode(This,pVal) ) 

#define IPDFOptimizer_put_DitheringMode(This,newVal)	\
    ( (This)->lpVtbl -> put_DitheringMode(This,newVal) ) 

#define IPDFOptimizer_SetCMSEngine(This,bstrCMSEngine,pDone)	\
    ( (This)->lpVtbl -> SetCMSEngine(This,bstrCMSEngine,pDone) ) 

#define IPDFOptimizer_put_Profile(This,newVal)	\
    ( (This)->lpVtbl -> put_Profile(This,newVal) ) 

#define IPDFOptimizer_get_MrcRecognizePictures(This,pVAL)	\
    ( (This)->lpVtbl -> get_MrcRecognizePictures(This,pVAL) ) 

#define IPDFOptimizer_put_MrcRecognizePictures(This,newVal)	\
    ( (This)->lpVtbl -> put_MrcRecognizePictures(This,newVal) ) 

#define IPDFOptimizer_UnembedFont(This,bstrFontName,pDone)	\
    ( (This)->lpVtbl -> UnembedFont(This,bstrFontName,pDone) ) 

#define IPDFOptimizer_get_ProductVersion(This,pVal)	\
    ( (This)->lpVtbl -> get_ProductVersion(This,pVal) ) 

#define IPDFOptimizer_get_ReduceColorComplexity(This,pVal)	\
    ( (This)->lpVtbl -> get_ReduceColorComplexity(This,pVal) ) 

#define IPDFOptimizer_put_ReduceColorComplexity(This,newVal)	\
    ( (This)->lpVtbl -> put_ReduceColorComplexity(This,newVal) ) 

#define IPDFOptimizer_get_FlattenSignatureFields(This,pVal)	\
    ( (This)->lpVtbl -> get_FlattenSignatureFields(This,pVal) ) 

#define IPDFOptimizer_put_FlattenSignatureFields(This,newVal)	\
    ( (This)->lpVtbl -> put_FlattenSignatureFields(This,newVal) ) 

#endif /* COBJMACROS */


#endif 	/* C style interface */




#endif 	/* __IPDFOptimizer_INTERFACE_DEFINED__ */


EXTERN_C const CLSID CLSID_PDFOptimizer;

#ifdef __cplusplus

class DECLSPEC_UUID("E42B90DA-30CE-4187-9974-BC9B44D39147")
PDFOptimizer;
#endif
#endif /* __PDFOPTIMIZEAPILib_LIBRARY_DEFINED__ */

/* Additional Prototypes for ALL interfaces */

/* end of Additional Prototypes */

#ifdef __cplusplus
}
#endif

#endif


