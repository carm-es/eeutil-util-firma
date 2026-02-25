/****************************************************************************
 *
 * File:            pdfoptimizedecl.h
 *
 * Description:     The included file for native C interfaces using optimize functions.
 *
 * Author:          Dr. Hans Bärfuss, PDF Tools AG
 *
 * Copyright:       Copyright (C) 2001 - 2016 PDF Tools AG, Switzerland
 *                  All rights reserved.
 *
 ***************************************************************************/

#ifndef _PDFOPTIMIZEDECL_INCLUDED
#define _PDFOPTIMIZEDECL_INCLUDED

typedef enum TPDFFontType
{
    eFontType1          = 1,        // PostScript Type1 font program
    eFontTrueType       = 2,        // TrueType font program
    eFontCFF            = 4,        // PostScript Compact Font Format (CFF) font program
    eFontType3          = 8         // PDF font program
} TPDFFontType;

typedef enum TPDFStripType 
{
    eStripThreads       =   0x1,    // Remove Thread, Bead dictionaries
    eStripMetadata      =   0x2,    // Remove Metadata dictionaries
    eStripPieceInfo     =   0x4,    // PieceInfo dictionaries
    eStripStructTree    =   0x8,    // StructTree dictionaries
    eStripThumb         =  0x10,    // Thumb dictionaries
    eStripSpider        =  0x20,    // Spider dictionaries
    eStripAlternates    =  0x40,    // Strip alternate images
    eStripForms         =  0x80,    // Strip and flatten form fields
    eStripLinks         = 0x100,    // Strip and flatten link annotations
    eStripAnnots        = 0x200,    // Strip and flatten all annotations except form fields and links
    eStripFormsAnnots   = 0x380,    // Strip and flatten all annotations including form fields
    eStripOutputIntents = 0x400,    // Remove output intents
    eStripAll           = 0xfff     // Strip (and flatten) all of the above
} TPDFStripType;

typedef enum TPDFComprAttempt
{
    eComprAttemptNone      =   0x0, // Exclude from processing
    eComprAttemptRaw       =   0x1, // No compression (raw)
    eComprAttemptJPEG      =   0x2, // Lossy DCT (Discrete Cosine Transform, JPEG) compression
    eComprAttemptFlate     =   0x4, // Lossless flate (ZIP)compression
    eComprAttemptLZW       =   0x8, // Lossless LZW (Lempel-Ziv-Welch) compression
    eComprAttemptGroup3    =  0x10, // Lossless CCITT Fax Group3 compression
    eComprAttemptGroup3_2D =  0x20, // Lossless CCITT Fax Group3 (2D) compression
    eComprAttemptGroup4    =  0x40, // Lossless CCITT Fax Group4 compression
    eComprAttemptJBIG2     =  0x80, // Lossless JBGI2 compression
    eComprAttemptJPEG2000  = 0x100, // Lossy JPEG2000 compression
    eComprAttemptMRC       = 0x200, // Perform MRC
    eComprAttemptSource    = 0x400  // Use same compression as source image
} TPDFComprAttempt;

typedef enum TPDFColorConversion
{
    eConvNone,                      // No conversion
    eConvRGB,                       // Color conversion to the ICE sRGB color space (Web)
    eConvCMYK,                      // Color conversion to the CMYK color space (Printing)
    eConvGray                       // Color conversion to the Gray color space (B/W TV compatible)
} TPDFColorConversion;

typedef enum TPDFOptimizationProfile
{
    eOptimizationProfileDefault,    // No optimization
    eOptimizationProfileWeb,        // Optimize for the web
    eOptimizationProfilePrint,      // Optimize for printing
    eOptimizationProfileMax,        // Optimize file size as much as possible
    eOptimizationProfileMRC         // Make MRC optimization
} TPDFOptimizationProfile;

#endif // _PDFOPTIMIZEDECL_INCLUDED
