
/****************************************************************************
 *
 * File:            pdfcodecdecl.h
 *
 * Description:     The include file for native C interfaces using codecs.
 *
 * Author:          Dr. Hans Bärfuss, PDF Tools AG   
 * 
 * Copyright:       Copyright (C) 2001 - 2012 PDF Tools AG, Switzerland
 *                  All rights reserved.
 *                  
 ***************************************************************************/

#ifndef _PDFCODECDECL_INCLUDED
#define _PDFCODECDECL_INCLUDED


typedef enum TPDFCompression 
{
    eComprRaw,                  // No compression (raw).
    eComprJPEG,                 // Lossy DCT (Discrete Cosine Transform, JPEG) compression. 
    eComprFlate,                // Lossless flate (ZIP)compression.
    eComprLZW,                  // Lossless LZW (Lempel-Ziv-Welch) compression.
    eComprGroup3,               // Lossless CCITT Fax Group3 compression.
    eComprGroup3_2D,            // Lossless CCITT Fax Group3 (2D) compression. 
    eComprGroup4,               // Lossless CCITT Fax Group4 compression.
    eComprJBIG2,                // Lossless JBGI2 compression.
    eComprJPEG2000,             // Lossy JPEG2000 compression.
    eComprTIFFJPEG,             // Lossy TIFF embedded JPEG (6) compression
    eComprUnknown,
    eComprDefault               // Default, decision based on image type
} TPDFCompression;

typedef enum TPDFColorSpace 
{
    eColorGray,                 // One channel gray.
    eColorGrayA,                // Two channels gray and alpha.
    eColorRGB,                  // Three channels red, green, blue.
    eColorRGBA,                 // Four channels red, green, blue, alpha.
    eColorCMYK,                 // Four channels cyan, magenta, yellow, black.
    eColorYCbCr,                // Three channels Luminance (Y) and Chroma (Cb, Cr)
    eColorYCbCrK,               // Four channels Luminance (Y), Chroma (Cb, Cr) and black.
    eColorPalette,              // One channel palette indices (into an RGB color table).
    eColorLAB,                  // Three channels IEC LAB.
    eColorCMYK_Konly,           // CMYK where only the black channel is used
    eColorCMYKA,                // Five channels cyan, magenta, yellow, black, alpha.
    eColorOther
} TPDFColorSpace;

typedef enum TPDFOrientation 
{
    eOrientationUndef       = 0,
	eOrientationTopLeft     = 1, 
	eOrientationTopRight    = 2, 
	eOrientationBottomRight = 3, 
	eOrientationBottomLeft  = 4,
    eOrientationLeftTop     = 5, 
	eOrientationRightTop    = 6, 
	eOrientationRightBottom = 7, 
	eOrientationLeftBottom  = 8
} TPDFOrientation;

typedef enum TPDFDithering
{
    eDitheringNone,
    eDitheringFloydSteinberg,
    eDitheringHalftone,
    eDitheringPattern,
    eDitheringG3Optimized,
    eDitheringG4Optimized,
    eDitheringAtkinson
} TPDFDithering;

#endif // _PDFCODECDECL_INCLUDED
