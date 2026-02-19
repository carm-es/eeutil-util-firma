
/****************************************************************************
 *
 * File:            pdfsecuritydecl.h
 *
 * Description:     The include file for native C interfaces using security handlers.
 *
 * Author:          Dr. Hans Bärfuss, PDF Tools AG   
 * 
 * Copyright:       Copyright (C) 2001 - 2012 PDF Tools AG, Switzerland
 *                  All rights reserved.
 *                  
 ***************************************************************************/

#ifndef _PDFPERMISSION_INCLUDED
#define _PDFPERMISSION_INCLUDED

typedef enum TPDFPermission 
{
    ePermPrint					= 0x00000004,
    ePermModify					= 0x00000008,
    ePermCopy					= 0x00000010,
    ePermAnnotate				= 0x00000020,
    ePermFillForms				= 0x00000100,
    ePermSupportDisabilities	= 0x00000200,
    ePermAssemble				= 0x00000400,
    ePermDigitalPrint			= 0x00000800,
    ePermAll                    = 0x00000F3C,
    ePermNoEncryption           = -1
} TPDFPermission;

typedef TPDFPermission TPdfPermission;

#endif // _PDFPERMISSION_INCLUDED
