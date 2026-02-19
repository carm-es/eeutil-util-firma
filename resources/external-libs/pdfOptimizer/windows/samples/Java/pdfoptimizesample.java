/****************************************************************************
*
* File:            pdfoptimizesample.java
*
* Usage:           java pdfoptimizesample PDF-InputFile PDF-OutputFile
*
* Description:     Optimize a PDF document.
*
* Version:         1.05
*
* History:         1.03 (2007-10-03)
*                  1.04 (2013-01-07, Tobias M�ller): Rewrite of most parts
*                  1.05 (2013-11-10, Christoph Reller)
*                  1.06 (2015-06-23, Christoph Reller)
*
* Author:          Mei Yang, PDF Tools AG
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

import com.pdftools.pola.PdfOptimize;
import com.pdftools.NativeLibrary;

public class pdfoptimizesample {
    public static void main(String[] args) {
        PdfOptimize doc = null;

        try {
            // Check commandline parameters
            if (args.length != 2) {
                System.out.println("Usage: pdfoptimizesample <inputDocument> <outputDocument>");
                System.exit(3);
            }

            // Check license
            if (!PdfOptimize.getLicenseIsValid()) {
                System.out.println("No valid license found.");
                System.exit(10);
            }

            doc = new PdfOptimize();

            // Open input file
            if (!doc.open(args[0], "")) {
                System.out.printf("Input file %s cannot be opened (Error 0x%08x).\n", args[0], doc.getErrorCode());
                System.exit(1);
            }

            // Choose the optimization profile for the web
            doc.setProfile(PdfOptimize.OPTIMIZATIONPROFILE.eOptimizationProfileWeb);

            // Disable linearizetion
            doc.setLinearize(false);

            // Save output file
            if (!doc.saveAs(args[1], "", "", NativeLibrary.PERMISSION.ePermNoEncryption)) {
                System.out.printf("Output file %s cannot be created (Error 0x%08x).\n", args[1], doc.getErrorCode());
                System.exit(2);
            }

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            // Release the optimizer
        	if(doc!=null)
        	{
        		doc.close();
        		doc.destroyObject();
        	}
        }
    }
}
