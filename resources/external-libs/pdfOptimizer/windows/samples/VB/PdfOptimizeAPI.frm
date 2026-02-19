VERSION 5.00
Object = "{F9043C88-F6F2-101A-A3C9-08002B2F49FB}#1.2#0"; "COMDLG32.OCX"
Begin VB.Form Form1 
   BackColor       =   &H00FFFFFF&
   Caption         =   "Form1"
   ClientHeight    =   2565
   ClientLeft      =   60
   ClientTop       =   450
   ClientWidth     =   4680
   LinkTopic       =   "Form1"
   ScaleHeight     =   2565
   ScaleWidth      =   4680
   StartUpPosition =   3  'Windows Default
   Begin MSComDlg.CommonDialog FileDialog 
      Left            =   4080
      Top             =   1920
      _ExtentX        =   847
      _ExtentY        =   847
      _Version        =   393216
   End
   Begin VB.CommandButton Browse2 
      BackColor       =   &H00C0C0C0&
      Caption         =   "..."
      Height          =   375
      Left            =   4080
      TabIndex        =   9
      Top             =   1320
      Width           =   375
   End
   Begin VB.CommandButton Browse1 
      BackColor       =   &H00C0C0C0&
      Caption         =   "..."
      Height          =   375
      Left            =   4080
      TabIndex        =   8
      Top             =   840
      Width           =   375
   End
   Begin VB.TextBox txtOutdir 
      Height          =   285
      Left            =   1320
      TabIndex        =   7
      Top             =   1320
      Width           =   2655
   End
   Begin VB.TextBox txtInput 
      Height          =   285
      Left            =   1320
      TabIndex        =   6
      Top             =   840
      Width           =   2655
   End
   Begin VB.CommandButton Command1 
      Caption         =   "Optimize File for (1) Printing and (2) Web"
      Height          =   495
      Left            =   600
      TabIndex        =   0
      Top             =   1800
      Width           =   3375
   End
   Begin VB.TextBox TextLogo 
      BorderStyle     =   0  'None
      BeginProperty Font 
         Name            =   "Verdana"
         Size            =   12.75
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00404040&
      Height          =   375
      Left            =   0
      TabIndex        =   1
      Text            =   "  pdf-tools.com"
      Top             =   120
      Width           =   4695
   End
   Begin VB.Frame Frame3 
      BackColor       =   &H00E0E0E0&
      BorderStyle     =   0  'None
      Height          =   615
      Left            =   0
      TabIndex        =   2
      Top             =   0
      Width           =   4695
   End
   Begin VB.Frame Frame2 
      BackColor       =   &H00E0E0E0&
      BorderStyle     =   0  'None
      Height          =   3015
      Left            =   0
      TabIndex        =   3
      Top             =   0
      Width           =   1215
      Begin VB.Label Label2 
         BackColor       =   &H00E0E0E0&
         BackStyle       =   0  'Transparent
         Caption         =   "Output Dir"
         BeginProperty Font 
            Name            =   "Verdana"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         ForeColor       =   &H00AC7A3E&
         Height          =   255
         Left            =   120
         TabIndex        =   5
         Top             =   1320
         Width           =   1095
      End
      Begin VB.Label Label1 
         BackColor       =   &H00E0E0E0&
         BackStyle       =   0  'Transparent
         Caption         =   "PDF File"
         BeginProperty Font 
            Name            =   "Verdana"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         ForeColor       =   &H00AC7A3E&
         Height          =   255
         Left            =   120
         TabIndex        =   4
         Top             =   840
         Width           =   975
      End
   End
End
Attribute VB_Name = "Form1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
' This is a Visual Basic 6 sample for the 3-Heights PDF
' Optimization API from PDF Tools AG. (www.pdf-tools.com)
'
' Copyright (C) 2005-2017 PDF Tools AG, Switzerland
' Permission to use, copy, modify, and distribute this
' software and its documentation for any purpose and without
' fee is hereby granted, provided that the above copyright
' notice appear in all copies and that both that copyright
' notice and this permission notice appear in supporting
' documentation.  This software is provided "as is" without
' express or implied warranty.

' The following is translated from the C include file "pdfoptimizedecl.h":
Const eOptimizationProfileDefault = 0 'No optimization
Const eOptimizationProfileWeb = 1     'Optimize for the web
Const eOptimizationProfilePrint = 2   'Optimize for printing
Const eOptimizationProfileMax = 3     'Optimize file size as much as possible
Const eOptimizationProfileMRC = 4     'Make MRC optimization

Private Sub Command1_Click()
    Dim Opt As New PDFOPTIMIZEAPILib.PDFOptimizer
    
    ' Open and analyze the input file.
    If Not Opt.Open(txtInput) Then
        MsgBox "Error " & Opt.ErrorCode & " while opening input file" & txtInput.Text & "."
        Exit Sub
    End If
    
    ' Optimize output file for printing.
    Opt.Profile = eOptimizationProfilePrint
    If Not Opt.SaveAs(txtOutdir & "\opt-print.pdf", "", "", ePermNoEncryption) Then
        MsgBox "Error " & Opt.ErrorCode & " while writing to output file " & txtOutdir.Text & "\opt-print.pdf."
        Exit Sub
    End If
    
    ' Optimize output for the web.
    Opt.Profile = eOptimizationProfileWeb
    If Not Opt.SaveAs(txtOutdir & "\opt-web.pdf", "", "", ePermNoEncryption) Then
        MsgBox "Error " & Opt.ErrorCode & " while writing to output file " & txtOutdir.Text & "\opt-web.pdf."
        Exit Sub
        End If
End Sub
Private Sub Browse1_Click()
    FileDialog.FileName = txtInput.Text
    FileDialog.ShowOpen
    txtInput.Text = FileDialog.FileName
End Sub
Private Sub Browse2_Click()
    FileDialog.FileName = txtOutdir.Text
    FileDialog.ShowOpen
    txtOutdir.Text = FileDialog.FileName
End Sub
Private Sub Form_Load()
    Dim Opt As New PDFOPTIMIZEAPILib.PDFOptimizer
    If Not Opt.LicenseIsValid Then
        MsgBox "No valid license key found."
        Unload Me
        End
    End If
    txtOutdir = "C:\temp"
End Sub
