
/****************************************************************************
 *
 * File:            pdferror.h
 *
 * Description:     The PDF error codes.
 *
 * Author:          Dr. Hans Bärfuss, PDF Tools AG   
 * 
 * Copyright:       Copyright (C) 2001 - 2012 PDF Tools AG, Switzerland
 *                  All rights reserved.
 *                  
 ***************************************************************************/

#ifndef _PDFERROR_INCLUDED
#define _PDFERROR_INCLUDED

#ifndef BSE_ERROR_CODE
#  include "bseerror.h"
#endif

typedef void*           TPDFErrorHandler;

typedef enum TPDFConformanceCategory
{
    eConfFormat     = 0x00000001,       // The file format (header, trailer, objects, xref, streams) is corrupted.
    eConfPDF        = 0x00000002,       // The document doesn't conform to the PDF reference  (missing required entries, wrong value types, etc.).
    eConfEncrypt    = 0x00000004,       // The file is encrypted and the password was not provided.
    eConfColor      = 0x00000008,       // The document contains device-specific color spaces.
    eConfRendering  = 0x00000010,       // The document contains illegal rendering hints (unknown intents, interpolation, transfer and halftone functions).
    eConfAlternate  = 0x00000020,       // The document contains alternate information (images).
    eConfPostScript = 0x00000040,       // The document contains embedded PostScript code.
    eConfExternal   = 0x00000080,       // The document contains references to external content (reference XObjects, file attachments, OPI).
    eConfFont       = 0x00000100,       // The document contains fonts without embedded font programs or encoding information (CMAPs)
    eConfUnicode    = 0x00000200,       // The document contains fonts without appropriate character to Unicode mapping information (ToUnicode maps)
    eConfTransp     = 0x00000400,       // The document contains transparency.
    eConfAnnot      = 0x00000800,       // The document contains unknown annotation types.
    eConfMultimedia = 0x00001000,       // The document contains multimedia annotations (sound, movies).
    eConfPrint      = 0x00002000,       // The document contains hidden, invisible, non-viewable or non-printable annotations.
    eConfAppearance = 0x00004000,       // The document contains annotations or form fields with ambiguous or without appropriate appearances. 
    eConfAction     = 0x00008000,       // The document contains actions types other than for navigation (launch, JavaScript, ResetForm, etc.)
    eConfMetaData   = 0x00010000,       // The document's meta data is either missing or inconsistent or corrupt.
    eConfStructure  = 0x00020000,       // The document doesn't provide appropriate logical structure information.
    eConfOptional   = 0x00040000        // The document contains optional content (layers).
} TPDFConformanceCategory;

#endif // _PDFERROR_INCLUDED
