/****************************************************************************
 *
 * File:            pdfoptimize.c
 *
 * Usage:           pdfoptimize PDF-input PDF-output
 *
 * Description:     Optimizes a PDF.
 *
 * Version:         1.04
 *
 * History:         1.03 (2013-01-07, Tobias Müller): Improved error handling
 *                  1.04 (2015-06-23, Christoph Reller)
 *
 * Author:          Philip Renggli, PDF Tools AG
 *
 * Copyright:       Copyright (C) 2005-2017 PDF Tools AG, Switzerland
 *                  Permission to use, copy, modify, and distribute this
 *                  software and its documentation for any purpose and without
 *                  fee is hereby granted, provided that the above copyright
 *                  notice appear in all copies and that both that copyright
 *                  notice and this permission notice appear in supporting
 *                  documentation.  This software is provided "as is" without
 *                  express or implied warranty.
 *
 ***************************************************************************/

#include <stdio.h>
#include "pdfoptimizeapi_c.h"

int main(int argc, char* argv[])
{
    TPdfOptimizer* pOpt;
    TPDFErrorCode iErrorCode;
    int iReturnValue = 0;
    int iStripFlags;

    if (argc < 3)
    {
        printf("Usage: pdfoptimize input.pdf output.pdf.\n");
        return 3;
    } 

    /* Initialize library */
    PdfOptimizeInitialize();

    /* Check license key */
    if (!PdfOptimizeGetLicenseIsValid())
    {
        printf("No valid license key found.\n");
        /* Cleanup */
        PdfOptimizeUnInitialize();
        return 10;
    }

    /* Create the object */
    pOpt = PdfOptimizeCreateObject();
    if (pOpt == NULL)
    {
        printf("Error: object could not be created.\n");
        /* Cleanup */
        PdfOptimizeUnInitialize();
        return 1;
    }

    /* Set an optimization profile */
    PdfOptimizeSetProfile(pOpt, eOptimizationProfileWeb);

    /* In the Web optimization profile the eStripMetadata and the eStripStructTree flags are set.
     * In the following example we clear these two flags */
    iStripFlags = PdfOptimizeGetStrip(pOpt);
    iStripFlags &= ~(eStripMetadata | eStripStructTree);
    PdfOptimizeSetStrip(pOpt, iStripFlags);

    /* Open the document */
    if (!PdfOptimizeOpenA(pOpt, argv[1], ""))
    {
        iErrorCode = PdfOptimizeGetErrorCode(pOpt);
        /* ErrorCodes are described in the header file bseerror.h. e.g.
         * 0x80410112 stands for "The authentication failed due to a wrong
         * password."
         */
        printf("Error 0x%08X while opening PDF file %s...\n", iErrorCode, argv[1]);
        /* Cleanup */
        PdfOptimizeDestroyObject(pOpt);
        PdfOptimizeUnInitialize();
        return 1;
    }

    /* Save the optimzied file */
    if (!PdfOptimizeSaveAsA(pOpt, argv[2], "", "", ePermNoEncryption))
    {
        iErrorCode = PdfOptimizeGetErrorCode(pOpt);
        /* ErrorCodes are described in the header file pdferror.h. */
        printf("Error 0x%08X while creating output file %s...\n", iErrorCode, argv[2]);
        iReturnValue = 2;
    }

    /* Close document */
    if (!PdfOptimizeClose(pOpt))
    {
        iErrorCode = PdfOptimizeGetErrorCode(pOpt);
        /* ErrorCodes are described in the header file pdferror.h. */
        printf("Error 0x%08X while closing input file.\n", iErrorCode);
    }

    /* Cleanup */
    PdfOptimizeDestroyObject(pOpt);
    PdfOptimizeUnInitialize();

    return iReturnValue;
}
