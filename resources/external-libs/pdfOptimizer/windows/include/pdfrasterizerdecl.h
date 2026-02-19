
/****************************************************************************
 *
 * File:            pdfrasterizerdecl.h
 *
 * Description:     The include file for native C interfaces using the PDF rasterizer.
 *
 * Author:          Dr. Hans Bärfuss, PDF Tools AG   
 * 
 * Copyright:       Copyright (C) 2001 - 2012 PDF Tools AG, Switzerland
 *                  All rights reserved.
 *                  
 ***************************************************************************/

#ifndef _PDFRASTERIZERDECL_INCLUDED
#define _PDFRASTERIZERDECL_INCLUDED
        
typedef enum TPDFDitheringMode
{
    eDitherNone, 
    eDitherFloydSteinberg, 
    eDitherHalftone, 
    eDitherPattern,
    eDitherG3Optimized, 
    eDitherG4Optimized,
    eDitherAtkinson
} TPDFDitheringMode;


#endif  //_PDFRASTERIZERDECL_INCLUDED



