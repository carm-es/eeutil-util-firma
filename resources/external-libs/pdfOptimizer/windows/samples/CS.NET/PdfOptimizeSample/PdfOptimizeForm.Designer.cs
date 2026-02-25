using System.Windows.Forms;

namespace PdfOptimizeSample
{
    public partial class PdfOptimizeForm : Form
    {
        private System.ComponentModel.IContainer components = null;

        /********************************************************************/

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        /********************************************************************/

        #region Windows Form Designer generated code

        private void InitializeComponent()
        {
            this.Icon = PdfOptimizeSample.Properties.Resources.pdf_tools;
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(PdfOptimizeForm));
            this.txtLog = new System.Windows.Forms.TextBox();
            this.buttonOutputDir = new System.Windows.Forms.Button();
            this.txtOutput = new System.Windows.Forms.TextBox();
            this.labelOutput = new System.Windows.Forms.Label();
            this.labelPassword = new System.Windows.Forms.Label();
            this.labelInput = new System.Windows.Forms.Label();
            this.buttonBrowsePDF = new System.Windows.Forms.Button();
            this.txtInput = new System.Windows.Forms.TextBox();
            this.txtPW = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.ButtonOptimizeWeb = new System.Windows.Forms.Button();
            this.ButtonOptimizePrint = new System.Windows.Forms.Button();
            this.labelComment = new System.Windows.Forms.Label();
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            this.folderBrowserDialog1 = new System.Windows.Forms.FolderBrowserDialog();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.SuspendLayout();
            // 
            // txtLog
            // 
            this.txtLog.BackColor = System.Drawing.Color.White;
            resources.ApplyResources(this.txtLog, "txtLog");
            this.txtLog.Name = "txtLog";
            this.txtLog.ReadOnly = true;
            this.txtLog.TabStop = false;
            // 
            // buttonOutputDir
            // 
            this.buttonOutputDir.BackColor = System.Drawing.SystemColors.Control;
            this.buttonOutputDir.Cursor = System.Windows.Forms.Cursors.Default;
            resources.ApplyResources(this.buttonOutputDir, "buttonOutputDir");
            this.buttonOutputDir.ForeColor = System.Drawing.SystemColors.ControlText;
            this.buttonOutputDir.Name = "buttonOutputDir";
            this.buttonOutputDir.UseVisualStyleBackColor = false;
            this.buttonOutputDir.Click += new System.EventHandler(this.SetOutputDir_Click);
            // 
            // txtOutput
            // 
            this.txtOutput.AcceptsReturn = true;
            this.txtOutput.BackColor = System.Drawing.SystemColors.Window;
            this.txtOutput.Cursor = System.Windows.Forms.Cursors.IBeam;
            resources.ApplyResources(this.txtOutput, "txtOutput");
            this.txtOutput.ForeColor = System.Drawing.SystemColors.WindowText;
            this.txtOutput.Name = "txtOutput";
            // 
            // labelOutput
            // 
            this.labelOutput.BackColor = System.Drawing.Color.Transparent;
            this.labelOutput.Cursor = System.Windows.Forms.Cursors.Default;
            resources.ApplyResources(this.labelOutput, "labelOutput");
            this.labelOutput.ForeColor = System.Drawing.SystemColors.ControlText;
            this.labelOutput.Name = "labelOutput";
            // 
            // labelPassword
            // 
            this.labelPassword.BackColor = System.Drawing.Color.Transparent;
            this.labelPassword.Cursor = System.Windows.Forms.Cursors.Default;
            resources.ApplyResources(this.labelPassword, "labelPassword");
            this.labelPassword.ForeColor = System.Drawing.SystemColors.ControlText;
            this.labelPassword.Name = "labelPassword";
            // 
            // labelInput
            // 
            this.labelInput.BackColor = System.Drawing.Color.Transparent;
            this.labelInput.Cursor = System.Windows.Forms.Cursors.Default;
            resources.ApplyResources(this.labelInput, "labelInput");
            this.labelInput.ForeColor = System.Drawing.SystemColors.ControlText;
            this.labelInput.Name = "labelInput";
            // 
            // buttonBrowsePDF
            // 
            this.buttonBrowsePDF.BackColor = System.Drawing.SystemColors.Control;
            this.buttonBrowsePDF.Cursor = System.Windows.Forms.Cursors.Default;
            resources.ApplyResources(this.buttonBrowsePDF, "buttonBrowsePDF");
            this.buttonBrowsePDF.ForeColor = System.Drawing.SystemColors.ControlText;
            this.buttonBrowsePDF.Name = "buttonBrowsePDF";
            this.buttonBrowsePDF.UseVisualStyleBackColor = false;
            this.buttonBrowsePDF.Click += new System.EventHandler(this.OpenPDF_Click);
            // 
            // txtInput
            // 
            this.txtInput.AcceptsReturn = true;
            this.txtInput.BackColor = System.Drawing.SystemColors.Window;
            this.txtInput.Cursor = System.Windows.Forms.Cursors.IBeam;
            resources.ApplyResources(this.txtInput, "txtInput");
            this.txtInput.ForeColor = System.Drawing.SystemColors.WindowText;
            this.txtInput.Name = "txtInput";
            // 
            // txtPW
            // 
            this.txtPW.AcceptsReturn = true;
            this.txtPW.BackColor = System.Drawing.SystemColors.Window;
            this.txtPW.Cursor = System.Windows.Forms.Cursors.IBeam;
            resources.ApplyResources(this.txtPW, "txtPW");
            this.txtPW.ForeColor = System.Drawing.SystemColors.WindowText;
            this.txtPW.Name = "txtPW";
            // 
            // label4
            // 
            this.label4.BackColor = System.Drawing.Color.Transparent;
            this.label4.Cursor = System.Windows.Forms.Cursors.Default;
            resources.ApplyResources(this.label4, "label4");
            this.label4.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(64)))), ((int)(((byte)(64)))), ((int)(((byte)(64)))));
            this.label4.Name = "label4";
            // 
            // ButtonOptimizeWeb
            // 
            this.ButtonOptimizeWeb.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(102)))), ((int)(((byte)(153)))));
            resources.ApplyResources(this.ButtonOptimizeWeb, "ButtonOptimizeWeb");
            this.ButtonOptimizeWeb.ForeColor = System.Drawing.Color.White;
            this.ButtonOptimizeWeb.Name = "ButtonOptimizeWeb";
            this.ButtonOptimizeWeb.UseVisualStyleBackColor = false;
            this.ButtonOptimizeWeb.Click += new System.EventHandler(this.ButtonOptimize_Click);
            // 
            // ButtonOptimizePrint
            // 
            this.ButtonOptimizePrint.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(102)))), ((int)(((byte)(153)))));
            resources.ApplyResources(this.ButtonOptimizePrint, "ButtonOptimizePrint");
            this.ButtonOptimizePrint.ForeColor = System.Drawing.Color.White;
            this.ButtonOptimizePrint.Name = "ButtonOptimizePrint";
            this.ButtonOptimizePrint.UseVisualStyleBackColor = false;
            this.ButtonOptimizePrint.Click += new System.EventHandler(this.ButtonOptimize_Click);
            // 
            // labelComment
            // 
            this.labelComment.BackColor = System.Drawing.Color.Transparent;
            this.labelComment.Cursor = System.Windows.Forms.Cursors.Default;
            resources.ApplyResources(this.labelComment, "labelComment");
            this.labelComment.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(64)))), ((int)(((byte)(64)))), ((int)(((byte)(64)))));
            this.labelComment.Name = "labelComment";
            // 
            // groupBox1
            // 
            this.groupBox1.BackColor = System.Drawing.Color.Transparent;
            this.groupBox1.Controls.Add(this.buttonOutputDir);
            this.groupBox1.Controls.Add(this.labelInput);
            this.groupBox1.Controls.Add(this.txtOutput);
            this.groupBox1.Controls.Add(this.label4);
            this.groupBox1.Controls.Add(this.labelOutput);
            this.groupBox1.Controls.Add(this.txtPW);
            this.groupBox1.Controls.Add(this.labelPassword);
            this.groupBox1.Controls.Add(this.txtInput);
            this.groupBox1.Controls.Add(this.buttonBrowsePDF);
            resources.ApplyResources(this.groupBox1, "groupBox1");
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.TabStop = false;
            // 
            // groupBox2
            // 
            this.groupBox2.BackColor = System.Drawing.Color.Transparent;
            this.groupBox2.Controls.Add(this.ButtonOptimizeWeb);
            this.groupBox2.Controls.Add(this.ButtonOptimizePrint);
            resources.ApplyResources(this.groupBox2, "groupBox2");
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.TabStop = false;
            // 
            // groupBox3
            // 
            this.groupBox3.BackColor = System.Drawing.Color.Transparent;
            this.groupBox3.Controls.Add(this.txtLog);
            resources.ApplyResources(this.groupBox3, "groupBox3");
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.TabStop = false;
            // 
            // PdfOptimizeForm
            // 
            resources.ApplyResources(this, "$this");
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.BackgroundImage = global::PdfOptimizeSample.Properties.Resources.stripe_gray;
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.labelComment);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.MaximizeBox = false;
            this.Name = "PdfOptimizeForm";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        /********************************************************************/

        private Button buttonBrowsePDF,
                       buttonOutputDir,
                       ButtonOptimizeWeb,
                       ButtonOptimizePrint;
        private FolderBrowserDialog folderBrowserDialog1;
        private Label labelOutput,
                      labelPassword,
                      labelInput,
                      labelComment,
                      label4;
        private OpenFileDialog openFileDialog1;
        public TextBox txtInput,
                       txtPW,
                       txtOutput,
                       txtLog;
        private GroupBox groupBox1;
        private GroupBox groupBox2;
        private GroupBox groupBox3;
    }
}

