/* This is a C#.NET sample for the 3-Heights PDF Optimization API
 * from PDF Tools AG (www.pdf-tools.com) for MS Visual Studio 2010.
 * 
 * Copyright (C) 2008-2017 PDF Tools AG, Switzerland
 * Permission to use, copy, modify, and distribute this
 * software and its documentation for any purpose and without
 * fee is hereby granted, provided that the above copyright
 * notice appear in all copies and that both that copyright
 * notice and this permission notice appear in supporting
 * documentation.  This software is provided "as is" without
 * express or implied warranty.
 */

using System;
using System.Windows.Forms;
using Pdftools.Pdf;
using Pdftools.PdfOptimize;
using PdfOptimizeSample.Properties;

namespace PdfOptimizeSample
{
    public partial class PdfOptimizeForm : Form
    {
        [STAThread]
        static void Main()
        {
            Application.Run(new PdfOptimizeForm());
        }

        /********************************************************************/

        public PdfOptimizeForm()
        {
            InitializeComponent();
        }

        protected override void OnShown(EventArgs e)
        {
            base.OnShown(e);
            try
            {
                if (!Optimizer.LicenseIsValid)
                    MessageBox.Show(this, "No valid license found.", "Pdf Optimization Sample", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            catch (TypeInitializationException)
            {
                MessageBox.Show(this, "A type initialization exception was thrown.\n\n" +
                                      " The usual reasons for this are:\n" +
                                      "  - The native DLL (PdfOptimizeAPI.dll) could not be found.\n" +
                                      "  - The native DLL has the wrong format (32-Bit vs. 64-Bit)\n\n" +
                                      "Click 'OK' to debug or 'Help' to get more information.",
                                      "Pdf Optimization Sample", MessageBoxButtons.OK, MessageBoxIcon.Error, MessageBoxDefaultButton.Button1, 0,
                                      "http://www.pdf-tools.com/pdf/Support/FAQ/Article.aspx?name=Exception-type-initializer");
                throw;
            }
        }

        /********************************************************************/

        private void ButtonOptimize_Click(object sender, EventArgs e)
        {
            try
            {
                ButtonOptimizePrint.Enabled = false;
                ButtonOptimizeWeb.Enabled = false;
                
                txtLog.Text = String.Empty;
                if (txtOutput.Text.Equals(String.Empty))
                {
                    txtLog.Text = Resources.strOutputDirErr;
                    return;
                }
                
                using (Optimizer opt = new Optimizer())
                {
                    if (!opt.Open(txtInput.Text, txtPW.Text))
                    {
                        txtLog.Text = Resources.strInputErr + opt.ErrorCode.ToString();
                        return;
                    }

                    // Optimize output file for either printing or web
                    String strFilename = null;
                    if (sender.Equals(ButtonOptimizePrint))
                    {
                        // Use the optimization profile for printing
                        opt.Profile = PDFOptimizationProfile.eOptimizationProfilePrint;
                        strFilename = Resources.strOptPrint;
                    }
                    else if (sender.Equals(ButtonOptimizeWeb))
                    {
                        // Use the optimization profile for printing
                        opt.Profile = PDFOptimizationProfile.eOptimizationProfileWeb;
                        strFilename = Resources.strOptWeb;
                    }
                    if (strFilename != null)
                    {
                        strFilename = txtOutput.Text + strFilename;
                        if (opt.SaveAs(strFilename, String.Empty, String.Empty, PDFPermission.ePermNoEncryption))
                            txtLog.Text = Resources.strSaveOk + strFilename;
                        else
                            txtLog.Text = Resources.strSaveErr + opt.ErrorCode.ToString();
                    }
                    opt.Close();
                }
            }
            catch (Exception ex)
            {
                txtLog.Text = ex.Message;
            }
            finally
            {
                ButtonOptimizePrint.Enabled = true;
                ButtonOptimizeWeb.Enabled = true;
            }
        }

        /********************************************************************/

        private void OpenPDF_Click(object sender, EventArgs e)
        {
            try
            {
                openFileDialog1.FileName = txtInput.Text;
                openFileDialog1.ShowDialog();
                txtInput.Text = openFileDialog1.FileName;
            }
            catch (InvalidOperationException)
            {
                txtInput.Text = String.Empty;
                return;
            }
        }

        /********************************************************************/

        private void SetOutputDir_Click(object sender, EventArgs e)
        {
            folderBrowserDialog1.SelectedPath = txtOutput.Text;
            if (folderBrowserDialog1.ShowDialog() == DialogResult.OK)
                txtOutput.Text = folderBrowserDialog1.SelectedPath;
        }

        /********************************************************************/
    }
}