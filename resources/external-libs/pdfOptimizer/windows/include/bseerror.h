/****************************************************************************
 *
 * File:            bseerror.h
 *
 * Description:     Error codes.
 *
 * Author:          Dr. Hans Baerfuss, PDF Tools AG
 * 
 * Copyright:       Copyright (C) 2001 - 2017 PDF Tools AG, Switzerland
 *                  All rights reserved.
 *
 ***************************************************************************/

#ifndef _BSEERROR_INCLUDED
#define _BSEERROR_INCLUDED

#define BSE_ERROR_CODE(error, warning, facility, code)  (TBSEErrorCode) \
                                                        ((((unsigned int) (error)    &      1) << 31) | \
                                                         (((unsigned int) (facility) & 0x7FFF) << 16) | \
                                                         (((unsigned int) (warning)  &      1) << 15) | \
                                                         (((unsigned int) (code)     & 0x7FFF)      )   \
                                                         )
#define BSE_IS_SYSTEM_ERROR(code)                       ( (unsigned int) (code) < 0x00008000 &&   \
                                                          (unsigned int) (code) > 0x00000000      )
#define BSE_IS_INTERNAL_ERROR(code)                     (((unsigned int) (code) & 0x80008000) == 0x80008000)
#define BSE_IS_ERROR(code)                              (((unsigned int) (code) & 0x80000000) != 0)
#define BSE_IS_WARNING(code)                            (((unsigned int) (code) & 0x00008000) != 0)
#define BSE_IS_INFO(code)                               (((unsigned int) (code) & 0x80008000) == 0)
#define BSE_ERROR_GET_FACILITY(code)                    (((unsigned int) (code) >> 16) & 0x7FFF   )
#define BSE_ERROR_GET_CODE(code)                        ( (unsigned int) (code)        & 0x7FFF   )



/** Facilities
  */ 
#define FACILITY_NONE           0x000
#define FACILITY_BSE            0x030
#define FACILITY_BSE_TEST       0x050
#define FACILITY_CAIRO          0xA01
#define FACILITY_GIF            0xA02
#define FACILITY_JB2            0xA03
#define FACILITY_JPEG           0xA04
#define FACILITY_JP2            0xA05
#define FACILITY_LCMS           0xA06
#define FACILITY_LIBAFW         0xA07
#define FACILITY_LIBXML         0xA08
#define FACILITY_LIBXMP         0xA09
#define FACILITY_GDI            0xA0A
#define FACILITY_GDIPLUS        0xA0B
#define FACILITY_LIC            0xA0C
#define FACILITY_LIBTIFF        0xA0D
#define FACILITY_OCR            0xA0E
#define FACILITY_PNG            0xA0F
#define FACILITY_XMPSDK         0xA10
#define FACILITY_ZLIB           0xA11
#define FACILITY_LIBLICENSE     0xA12
#define FACILITY_SIG            0xA13
#define FACILITY_PDF_SPLMRG     0xA14
#define FACILITY_XFA            0xA15
#define FACILITY_PDF_VIEWER     0xA16
#define FACILITY_PDF_I2P        0xA17
#define FACILITY_PDF_COMP       0xA18
#define FACILITY_PDF_STAMP      0xA19
#define FACILITY_PDF_CREATOR    0xA1A
#define FACILITY_PDF_OPT        0xA1B
#define FACILITY_PDF            0x041
#define FACILITY_PDF_RENDERER   0x141
#define FACILITY_PDF_CHKLEGACY  0x241
#define FACILITY_PDF_VAL        0x341
#define FACILITY_PDF_VALPROFILE 0x351
#define FACILITY_PDF_XMPVAL     0x441
#define FACILITY_PDF_SIG        0x541
#define FACILITY_PDF_MSG        0x641

typedef enum TPDFErrorCode
{

/** General codes */
    BSE_INFO_SUCCESS                    = 0x00000000, /* The operation was completed successfully. */
    PDF_S_SUCCESS                       = 0x00000000, /* The operation was completed successfully. */

/** BSE */
    BSE_ERROR_MEMORY_ALLOCATE           = 0x80308001, /* Memory allocation error. */
    BSE_ERROR_ASCII85_OVERFLOW          = 0x80300002, /* The value represented by a group of 5 characters is greater than 2^32 - 1. */
    BSE_ERROR_ASCII85_Z                 = 0x80300003, /* A 'z' character occurs in the middle of a group in an ASCII85 stream. */
    BSE_ERROR_ASCII85_INCOMPLETE        = 0x80300004, /* A final partial group contains only one character in an ASCII85 stream. */
    BSE_ERROR_ASCII85_CHAR              = 0x80300005, /* An invalid character was encountered in an ASCII85 stream. */
    BSE_ERROR_ASCII85_EOS               = 0x80300006, /* An EOD code was missing in an ASCII85 stream. */
    BSE_ERROR_ASCIIHEX_CHAR             = 0x80300007, /* A character in an ASCIIHex stream is invalid. */
    BSE_ERROR_ASCIIHEX_EOS              = 0x80300008, /* An EOD code was missing in an ASCIIHex stream. */
    BSE_ERROR_CCITT_LINE                = 0x80300009, /* The CCITTFax stream contains a line which is too long. */
    BSE_ERROR_CCITT_1DEXT               = 0x8030000A, /* The CCITTFax stream contains 1D extensions. */
    BSE_ERROR_CCITT_HUF1D               = 0x8030000B, /* The CCITTFax stream contains an invalid 1D huffman code. */
    BSE_ERROR_CCITT_2DEXT               = 0x8030000C, /* The CCITTFax stream contains 2D extensions. */
    BSE_ERROR_CCITT_HUF2D               = 0x8030000D, /* The CCITTFax stream contains an invalid 2D huffman code. */
    BSE_ERROR_LZW_EOS                   = 0x80300019, /* An EOD code was missing in an LZW stream. */
    BSE_ERROR_LZW_CODE                  = 0x8030001A, /* An invalid code was encountered in an LZW stream. */
    BSE_ERROR_RLE_EOS                   = 0x8030001B, /* An EOD code was missing in an RunLength stream. */
    BSE_E_TCP_CONN                      = 0x8030001D, /* Failed to establish TCP connection to %host%:%port%. */
    BSE_E_TCP_RESP                      = 0x8030001E, /* TCP failed to send %url% (error code %code%). */
    BSE_E_HTTP_REQ                      = 0x8030001F, /* Failed to send HTTP %verb% request to http://%host%%resource%. */
    BSE_E_HTTP_RESP                     = 0x80300020, /* Failed to send %url%. Server returned HTTP status code %code%. */
    BSE_E_MAXPATH                       = 0x80300021, /* Path too long: '%path%'. Maximum length is %maxlen%. */
    BSE_I_TASK_SCHEDULED                = 0x00300030, /* Task '%name%' scheduled. */
    BSE_I_TASK_STARTED                  = 0x00300031, /* Task '%name%' started. */
    BSE_I_TASK_FINISHED                 = 0x00300032, /* Task '%name%' finished. */
    BSE_E_TASK_FAILED                   = 0x80300033, /* Task '%name%' failed: %message%. */
    BSE_E_STREAM_LENGTH                 = 0x80300040, /* Invalid number of bytes in stream. */
    BSE_E_UTF8                          = 0x80300041, /* Invalid UTF8. */
    BSE_E_UTF16                         = 0x80300042, /* Invalid UTF16. */
    BSE_E_SYSTEMCP                      = 0x80300043, /* Failed to decode data using system code page. */
    BSE_ERROR_FATAL                     = 0x8030FFFF, /* Unknown fatal error. */
    PDF_E_FATAL                         = 0x8030FFFF, /* Unknown fatal error. */

/** PDF */
    PDF_E_FILEOPEN                      = 0x80410101, /* The file couldn't be opened. */
    PDF_E_FILECREATE                    = 0x80410102, /* The file couldn't be created. */
    PDF_E_SETPOS                        = 0x80410103, /* The file's position couldn't be set. */
    PDF_E_HEADER                        = 0x80410104, /* The file header was not found. */
    PDF_W_HEADEROFFS                    = 0x00418105, /* The file header must be located at the beginning. */
    PDF_I_VERSION                       = 0x00410106, /* The file header version does not conform to the standard. */
    PDF_W_BINARY                        = 0x00418107, /* The comment, classifying the file as containing 8-bit binary data, is missing. */
    PDF_E_EOF                           = 0x80410108, /* The end-of-file marker was not found. */
    PDF_W_EOFOFFS                       = 0x00418109, /* The last line of the file must contain an end-of-file marker. */
    PDF_E_STARTXREF                     = 0x8041010A, /* The 'startxref' keyword or the xref position was not found. */
    PDF_E_XREF                          = 0x8041010B, /* The 'xref' keyword was not found or the xref table is malformed. */
    PDF_W_XREFOFFS                      = 0x0041810C, /* The 'xref' keyword must be located at the given offset. */
    PDF_W_XREFSEP                       = 0x0041810D, /* The xref fields must be separated by a single space and terminated by a single end-of-line marker. */
    PDF_E_TRAILER                       = 0x8041010E, /* The file trailer dictionary is missing or invalid. */
    PDF_E_ROOT                          = 0x8041010F, /* The root object was not found. */
    PDF_W_ENCRYPT                       = 0x00418110, /* The file trailer dictionary must not have an encrypt key. */
    PDF_W_ID                            = 0x00418111, /* The file trailer dictionary must have an id key. */
    PDF_E_PASSWORD                      = 0x80410112, /* The authentication failed due to a wrong password. */
    PDF_E_CORRUPT                       = 0x80410113, /* The file is corrupt and cannot be repaired. Some of the contents can possibly be recovered. */
    PDF_W_CORRUPT                       = 0x00418114, /* The file is corrupt and needs to be repaired. */
    PDF_I_OBJNUMBER                     = 0x00410115, /* The object with the number %n% doesn't exist. */
    PDF_I_GENNUMBER                     = 0x00410116, /* The generation number %n1% of the object doesn't match with the generation number %n2% of the object's reference. */
    PDF_E_XFANEEDSRENDERING             = 0x80410117, /* The file contains unrendered XFA fields. */
    PDF_E_UNKSECHANDLER                 = 0x80410118, /* The file uses a proprietary security handler. */
    PDF_E_SECALGONIMP                   = 0x80410119, /* The file uses a security algorithm that is not implemented. */
    PDF_E_COLLECTION                    = 0x8041011A, /* The file is a collection (PDF Portfolio). */
    PDF_W_HEADER                        = 0x0041811B, /* The file header format does not conform to the standard. */
    PDF_E_ENCPARAM                      = 0x8041011C, /* Invalid or inconsistent encryption parameters specified. */
    PDF_E_FILTER                        = 0x80410201, /* The filter %name% is unknown. */
    PDF_E_IMAGEDATA                     = 0x80410218, /* The image's sample stream's computed length %l1% is different to the actual length %l2%. */
    PDF_W_DECODE                        = 0x00418219, /* Errors in decode filter. */
    PDF_E_OBJNO                         = 0x80410301, /* The object number is missing. */
    PDF_E_GENNO                         = 0x80410302, /* The generation number is missing. */
    PDF_E_IDENTITY                      = 0x80410303, /* The object's identity %n1% doesn't match with the object's reference identity %n2%. */
    PDF_E_OBJ                           = 0x80410304, /* The "obj" keyword is missing. */
    PDF_I_NULL                          = 0x00410305, /* The object %obj% is empty (null). */
    PDF_E_LENGTH                        = 0x80410306, /* The "Length" key of the stream object is wrong. */
    PDF_E_ENDSTREAM                     = 0x80410307, /* The "endstream" keyword is missing. */
    PDF_E_ENDOBJ                        = 0x80410308, /* The "endobj" keyword is missing. */
    PDF_W_XREFOFF                       = 0x00418309, /* The offset in the xref table is not correct. */
    PDF_W_ARRAYSIZE                     = 0x00418310, /* An array contains more than 8191 elements. */
    PDF_W_NAMELENGTH                    = 0x00418311, /* A name is longer than 127 bytes. */
    PDF_W_INTEGERVAL                    = 0x00418312, /* An integer value is larger than 2^31-1. */
    PDF_W_DICTCAPACITY                  = 0x00418313, /* A dictionary contains more than 4095 entries. */
    PDF_W_REALVAL                       = 0x00418314, /* A real value is larger than 2^15-1. */
    PDF_W_PAGEBOX                       = 0x00418315, /* The page boundary %name% is not within the allowed range. */
    PDF_W_REALVAL2                      = 0x00418316, /* A real value's absolute value is larger than 3.403 x 10^38. */
    PDF_W_STRINGLENGTH                  = 0x00418317, /* A string length exceeds the limit defined by the standard. */
    PDF_W_GSSTACK                       = 0x00418318, /* The size of the graphics state stack exceeds the limit defined by the standard. */
    PDF_W_OBJCOUNT                      = 0x00418319, /* The file has more than 8388607 indirect objects. */
    PDF_E_ARRAYSIZE                     = 0x8041031A, /* The %name% array has %n1% but must have %n2% elements. */
    PDF_E_INVFUNC                       = 0x8041031B, /* The function is invalid. */
    PDF_E_DEVICENCOMP                   = 0x8041031C, /* Number of colorants %n% exceeds maximum of %nmax%. */
    PDF_E_QNESTING                      = 0x8041031D, /* Maximum depth of graphics state nesting by q and Q operators exceeded. */
    PDF_E_CIDMAX                        = 0x8041031E, /* Maximum value of a CID exceeded. */
    PDF_W_NUMBERVAL                     = 0x0041831F, /* A number value is larger than 2^15-1. */
    PDF_W_NUMBERVAL2                    = 0x00418320, /* A number value's absolute value is larger than 2^31-1. */
    PDF_E_PAGE                          = 0x80410401, /* The page doesn't exist. */
    PDF_E_PAGETYPE                      = 0x80410402, /* The page or page tree node has a missing or invalid "Type" key. */
    PDF_E_PAGEKIDS                      = 0x80410403, /* The page tree node has a missing or invalid "Kids" key. */
    PDF_E_PAGECOUNT                     = 0x80410404, /* The page tree node has a missing or invalid "Count" key. */
    PDF_E_PAGEPARENT                    = 0x80410405, /* The page or page tree node has a missing or invalid "Parent" key. */
    PDF_E_PAGERES                       = 0x80410406, /* The page has a missing or invalid "Resources" key. */
    PDF_E_PAGEMEDIABOX                  = 0x80410407, /* The page has a missing or invalid "MediaBox" key. */
    PDF_E_NOPAGES                       = 0x80410408, /* The document contains no pages. */
    PDF_E_TOKEN                         = 0x80410501, /* An unexpected token was found. */
    PDF_E_OPERATOR                      = 0x80410502, /* The content stream contains an invalid operator. */
    PDF_E_OPERANDS                      = 0x80410503, /* The operator has an invalid number of operands. */
    PDF_E_OPNDSTACK                     = 0x80410504, /* An operand stack over- or underflow occurred. */
    PDF_E_OPNDSTRING                    = 0x80410506, /* The operand must be a string. */
    PDF_E_OPNDNAME                      = 0x80410507, /* The operand must be a name. */
    PDF_E_OPNDARRAY                     = 0x80410508, /* The operand must be an array. */
    PDF_E_OPNDDICT                      = 0x80410509, /* The operand must be a dictionary. */
    PDF_E_OPNDVALUE                     = 0x8041050A, /* The value %value% of an operand is out of range. */
    PDF_E_UNKFONT                       = 0x8041050B, /* The name %name% of a font resource is unknown. */
    PDF_E_UNKCOLORSPACE                 = 0x8041050C, /* The name %name% of a color space resource is unknown. */
    PDF_E_UNKGS                         = 0x8041050D, /* The name %name% of a graphics state dictionary resource is unknown. */
    PDF_E_UNKPAT                        = 0x8041050E, /* The name %name% of a pattern dictionary resource is unknown. */
    PDF_E_UNKXOBJ                       = 0x8041050F, /* The name %name% of a xobject resource is unknown. */
    PDF_E_GSSTACK                       = 0x80410511, /* A graphics state stack over- or underflow occurred. */
    PDF_E_BEGINTEXT                     = 0x80410512, /* A begin text operator is missing. */
    PDF_E_ENDTEXT                       = 0x80410513, /* An end text operator is missing. */
    PDF_W_BEGINPATH                     = 0x00418514, /* A path start operator was missing. */
    PDF_E_BEGINPATH                     = 0x80410514, /* A path start operator was missing. */
    PDF_E_EMPTYXOBJ                     = 0x80410515, /* The form xobject %name% has an empty or unreadable content stream. */
    PDF_I_UNUSEDPATH                    = 0x00410516, /* An path was constructed but not painted. */
    PDF_I_EMPTYPATH                     = 0x00410518, /* A path painting operator was used on an empty path. */
    PDF_E_STOPPED                       = 0x80410601, /* The analysis has been stopped. */
    PDF_I_KEYIGNORED                    = 0x00410602, /* The key %key% was ignored. */
    PDF_E_VALTYPE                       = 0x80410603, /* The value of the key %key% must be of type %type%. */
    PDF_W_KEYREQ                        = 0x00418604, /* The key %key% is required but missing. */
    PDF_E_KEYREQ                        = 0x80410604, /* The key %key% is required but missing. */
    PDF_E_VALIND                        = 0x80410605, /* The value of the key %key% must be an indirect object. */
    PDF_E_VALDIR                        = 0x80410606, /* The value of the key %key% must be a direct object. */
    PDF_W_VALREQ                        = 0x00418607, /* The value of the key %key% is %v1% but must be %v2%. */
    PDF_E_VALREQ                        = 0x80410607, /* The value of the key %key% is %v1% but must be %v2%. */
    PDF_W_KEYPROHIB                     = 0x00418608, /* The dictionary must not contain the key '%key%'. */
    PDF_W_VALPROHIB                     = 0x00418609, /* The key %key% has a value %val% which is prohibited. */
    PDF_E_VALPROHIB                     = 0x80410609, /* The key %key% has a value %val% which is prohibited. */
    PDF_I_ATTRNOTREC                    = 0x0041060A, /* The key %key% is present but not recommended. */
    PDF_I_KEYRECOMM                     = 0x0041060B, /* The key %key% is recommended. */
    PDF_W_INCONSISTENCY                 = 0x0041860C, /* The key %key1% is inconsistent with the key %key2%. */
    PDF_E_VALNTYPE                      = 0x8041060D, /* The value of the key %key% must not be of type %type%. */
    PDF_W_NAMEENC                       = 0x0041860E, /* The name object must be UTF-8 encoded. */
    PDF_W_EFPDF                         = 0x0041860F, /* The embedded file '%name%' is not a PDF file. */
    PDF_W_EFPDFA                        = 0x00418610, /* The embedded file '%name%' must be PDF/A compliant. */
    PDF_W_IMPLCCONV                     = 0x00418611, /* ICCBased CMYK color space must not be used with overprint mode 1. */
    PDF_W_COLORANTMIS                   = 0x00418612, /* All '%name%' colorants must have the same tint transform and alternate space. */
    PDF_W_JPXNUMCHAN                    = 0x00418613, /* The number of color channels in the JPEG2000 data must not be %n%. */
    PDF_W_JPXCOLRAPPROX                 = 0x00418614, /* JPEG2000 data with multiple color space definitions and incorrect APPROX field. */
    PDF_W_JPXCOLRMETH                   = 0x00418615, /* The value of the METH entry in the 'colr' box in the JPEG2000 data is invalid. */
    PDF_W_JPXENUMCS                     = 0x00418616, /* The enumerated colour space %i% must not be used in JPEG2000 data. */
    PDF_W_JPXCIEJAB                     = 0x00418617, /* JPEG2000 enumerated colour space 19 (CIEJab) shall not be used. */
    PDF_W_JPXBITDEPTH                   = 0x00418618, /* All channels in JPEG2000 data must have a bit depth in the range from 1 to 38. */
    PDF_W_JPXCLRBD                      = 0x00418619, /* All color channels in JPEG2000 data must have the same bit depth. */
    PDF_E_JPXOPACITY                    = 0x8041061A, /* JPEG2000 data contains invalid opacity channels. */
    PDF_E_JPXINVALID                    = 0x8041061B, /* Error in JPEG2000 data. */
    PDF_W_DEFBLENDCS                    = 0x0041861C, /* The page must define a default blending color space. */
    PDF_E_BLENDCS                       = 0x0041861D, /* The blending color space is of an invalid type. */
    PDF_W_OCCNAMECOLL                   = 0x0041861E, /* Two optional content configurations must not have the same name '%name%'. */
    PDF_W_OCORDER                       = 0x0041861F, /* The order array in the OC configuration '%conf%' must contain '%ocg%'. */
    PDF_W_OIMISSMATCH                   = 0x00418620, /* The file must not contain different output intents. */
    PDF_W_APPNOSTATE                    = 0x00418621, /* The appearance must have state dictionaries (subdictionaries to 'N'). */
    PDF_W_NORES                         = 0x00418622, /* The content stream must have an explicitly associated Resources dictionary. */
    PDF_W_NOUNICODE                     = 0x00418623, /* The unicode for cid %cid% is unknown. */
    PDF_E_FONTPROG                      = 0x80410701, /* The embedded font program '%font%' cannot be read. */
    PDF_E_FONTFILE                      = 0x80410702, /* The attribute %attr% of the font descriptor must match with the font type %type%. */
    PDF_E_CMAPEMB                       = 0x80410703, /* The CMAP %name% must be embedded. */
    PDF_W_EMBED                         = 0x00418704, /* The font %font% must be embedded. */
    PDF_W_SYSTEMFONT                    = 0x00418808, /* Font %f1%: The pre-installed font '%f2%' is used. */
    PDF_W_FONTREPLACE                   = 0x00418809, /* Font %f1%: The font was replaced with '%f2%'. */
    PDF_W_OTFFONTPROG                   = 0x0041880A, /* Font %font% has an OpenType font program that is not allowed. */
    PDF_W_NOAFREF                       = 0x0041880B, /* File specification '%name%' not associated with an object. */
    PDF_E_LOADPROFILE                   = 0x8041080C, /* Error loading profile configuration file: %msg% */

/** BSE unit test */
    BSE_INFO_UNITTEST1                  = 0x00500000, /* BSE_INFO_UNITTEST1, %data% %context%. */
    BSE_WARNING_UNITTEST1               = 0x00508001, /* BSE_WARNING_UNITTEST1, %data% %context%. */
    BSE_ERROR_UNITTEST1                 = 0x80500002, /* BSE_ERROR_UNITTEST1, %data% %context%. */
    BSE_ERROR_UNITTEST2                 = 0x80500003, /* BSE_ERROR_UNITTEST2, %data% %context%. */

/** PDF rendering module */
    PDF_E_PATTERNTYPE                   = 0x81410801, /* The pattern type %type% is unknown or not yet implemented. */
    PDF_E_CHARPROC                      = 0x81410802, /* Font %font%: The charproc of glyph '%glyph%' was not found. */
    PDF_E_SHADINGTYPE                   = 0x81410803, /* The shading type %type% is unknown or not yet implemented. */
    PDF_E_FONTINSTALL                   = 0x81410804, /* Font %font%: The font program '%fp%' couldn't be installed successfully. */
    PDF_W_PAINTMISSING                  = 0x01418807, /* The path is constructed but the painting operator is missing. */
    PDF_E_GDIPLUS                       = 0x8141880A, /* GDI+ error. */
    PDF_E_GDI                           = 0x8141880B, /* GDI error. */

/** Legacy PDF custom modules */
    PDF_W_CUSTOM01                      = 0x02418A01, /* The file size exceeds the limit. */
    PDF_E_CUSTOM01                      = 0x82410A01, /* The file size exceeds a critical limit. */
    PDF_E_CUSTOM02                      = 0x82410A02, /* The file must not be password protected. */
    PDF_E_CUSTOM03                      = 0x82410A03, /* The font %font% must be embedded and subsetted. */
    PDF_E_CUSTOM05                      = 0x82410A05, /* The file's version must be %version% or older. */
    PDF_E_CUSTOM06                      = 0x82410A06, /* The page no. %Page No.% must not be blank. */
    PDF_E_CUSTOM07                      = 0x82410A07, /* The page no. %Page No.% must not have annotations (comments or drawing markups). */
    PDF_E_CUSTOM08                      = 0x82410A08, /* The document must not have layers. */
    PDF_W_CUSTOM09                      = 0x02418A09, /* The image's resolution on page no. %Page No.% must be in the configured range. */
    PDF_E_CUSTOM10                      = 0x82410A0A, /* The producer "%prod%" must meet the organization's standard. */
    PDF_E_CUSTOM11                      = 0x82410A0B, /* The font %font% is not on the approved list. */
    PDF_E_CUSTOM12                      = 0x82410A0C, /* The digital signature of "%name%" must be valid and meet the organization's standard. */
    PDF_W_CUSTOM13                      = 0x02418A0D, /* The page no. %Page No.% contains a scanned image and is not word searchable. */
    PDF_W_CUSTOM14                      = 0x02418A0E, /* The page no. %Page No.% contains a scanned image that includes color. */
    PDF_E_CUSTOM15                      = 0x82410A0F, /* The document was modified after it has been signed. */
    PDF_E_CUSTOM16                      = 0x82410A10, /* The creator "%crea%" must meet the organization's standard. */
    PDF_E_CUSTOM17                      = 0x82410A11, /* The document must not have file attachments (%F%). */
    PDF_E_CUSTOM19                      = 0x82410A13, /* The document must not have 3D annotations. */
    PDF_I_CUSTOM20                      = 0x02410A14, /* The document contains a link to an external resource. */
    PDF_E_CUSTOM21                      = 0x82410A15, /* The document contains hidden layers. */
    PDF_E_CUSTOM22                      = 0x82410A16, /* The file's version must be %version% or newer. */
    PDF_E_CUSTOM23                      = 0x82410A17, /* The page no. %Page No.% contains colored objects. */
    PDF_E_CUSTOM24                      = 0x82410A18, /* The document contains an invisible signature. */

/** PDF validation module */
    PDF_W_APPSTATE                      = 0x03418516, /* The appearance has state dictionaries (subdictionaries to 'N'). */
    PDF_E_ICCINVALID                    = 0x83410517, /* The embedded ICC profile couldn't be read. */
    PDF_W_ICCVERSION                    = 0x03418518, /* The embedded ICC profile's version is not supported. */
    PDF_E_MULTIPLEOI                    = 0x83410519, /* The document has multiple PDF/A output intents. */
    PDF_W_HEXODD                        = 0x0341851A, /* A hexadecimal string contains an odd number of bytes. */
    PDF_W_EOFDATA                       = 0x0341851B, /* There is data after the EOF marker. */
    PDF_W_LINEARID                      = 0x0341851C, /* The ID in the 1st page and last trailer of a linearized file are different. */
    PDF_W_XREFSPACE                     = 0x0341851D, /* The separator in a xref subsection header must be a single space. */
    PDF_W_XREFEOL                       = 0x0341851E, /* The separator between 'xref' and the subsection must be an EOL. */
    PDF_W_STREAMEOL                     = 0x0341851F, /* The separator after 'stream' must be CR-LF or LF. */
    PDF_W_EOLENDSTREAM                  = 0x03418520, /* The separator before 'endstream' must be an EOL. */
    PDF_W_NUMSPACEGEN                   = 0x03418521, /* The separator between the object and generation number must be a single space. */
    PDF_W_GENSPACEOBJ                   = 0x03418522, /* The separator between the generation number and 'obj' must be a single space. */
    PDF_W_EOLOBJNUM                     = 0x03418523, /* The separator before the object number must be an EOL. */
    PDF_W_EOLENDOBJ                     = 0x03418524, /* The separator before an 'endobj' must be an EOL. */
    PDF_W_OBJEOL                        = 0x03418525, /* The separator after an 'obj' must be an EOL. */
    PDF_W_ENDOBJEOL                     = 0x03418526, /* The separator after an 'endobj' must be an EOL. */
    PDF_I_TRUETYPECMAP                  = 0x03410527, /* The embedded TrueType font %font% contains more than one cmap entries. */
    PDF_W_TRUETYPECMAP                  = 0x03418527, /* The embedded TrueType font %font% contains %err% cmap entries. */
    PDF_E_ICCDEVCLASS                   = 0x83410528, /* The embedded ICC profile's device class '%cls%' is not supported. */
    PDF_W_TTFCMAPFMT                    = 0x03418529, /* The embedded TrueType font %font% has a cmap(%pid%, %eid%) with format %format% that is not allowed. */
    PDF_E_XMPPROPREQ                    = 0x8341052B, /* The required XMP property '%prefix%:%propname%' is missing. */
    XMP_W_PROPINV                       = 0x0341852C, /* The property '%prop%' is not allowed in this XMP schema. */
    PDF_E_XMPVALREQ                     = 0x8341052E, /* The XMP property '%prefix%:%propname%' has the invalid value '%invval%'. Required is '%reqval%'. */
    PDF_W_XMPENCODING                   = 0x0341852F, /* Invalid encoding of XMP packet: %enc%, expected %exp%. */
    PDF_W_WIDTHMISSMATCH                = 0x03418531, /* The width for character %cid% in font '%font%' does not match. */
    PDF_E_GLYPHMISSING                  = 0x83410531, /* The glyph for character %cid% in font '%font%' is missing. */
    PDF_E_NOENCODING                    = 0x83410532, /* The encoding for character code %code% in font '%font%' is missing. */
    PDF_E_XREFSTREAM                    = 0x03418533, /* The file contains cross reference streams. */
    PDF_I_INFOXMPMISS                   = 0x0341053D, /* The recommended XMP property '%prefix%:%propname%' for the document information entry '%infoentry%' is missing. */
    PDF_E_INFOXMPMISS                   = 0x8341053D, /* The required XMP property '%prefix%:%propname%' for the document information entry '%infoentry%' is missing. */
    PDF_I_INFOXMPNSYNC                  = 0x0341053E, /* The XMP property '%prefix%:%propname%' is not synchronized with the document information entry '%infoentry%'. */
    PDF_E_INFOXMPNSYNC                  = 0x8341053E, /* The XMP property '%prefix%:%propname%' is not synchronized with the document information entry '%infoentry%'. */
    PDF_E_CONVERSION                    = 0x83410540, /* Conversion errors in PDF to PDF/A conversion. */
    PDF_E_POSTANALYSIS                  = 0x83410541, /* Post analysis errors in PDF to PDF/A conversion. */
    PDF_E_DOWNGRADE                     = 0x83410542, /* File cannot be converted to meet this compliance: %msg% */
    PDF_E_MISSINGFONT                   = 0x83410543, /* Font '%font%' required but missing in font directories. */
    PDF_E_LINEARIZATION                 = 0x83410544, /* Linearization of the file failed. */
    PDF_E_ZUGFERDXML                    = 0x83410545, /* Failed to add ZUGFeRD invoice file. */
    PDF_E_INVCOMPLIANCE                 = 0x83410546, /* Invalid compliance specified. */
    PDF_W_NOENCRYPTION                  = 0x0341860D, /* The file must not be encrypted to be PDF/A conform. */
    PDF_E_DESTPAGE                      = 0x8341060F, /* The page reference in a destination is invalid. */
    PDF_E_DESTTYPE                      = 0x83410610, /* The type of a destination is unknown. */
    PDF_E_DESTOPNDS                     = 0x83410611, /* The number or the type of the destination operands is wrong. */
    PDF_E_CONFORMANCE                   = 0x83410612, /* The document does not conform to the requested standard. */
    PDF_W_EMBFILES                      = 0x03418613, /* The document contains embedded files. */
    PDF_W_DEVCOLOR                      = 0x03418614, /* A device-specific color space (%cs%) without an appropriate output intent is used. */
    PDF_W_APPNOENTRY                    = 0x03418615, /* The appearance dictionary doesn't contain an entry. */
    PDF_W_EXTSTM                        = 0x03418616, /* The content of the stream must not be in an external file. */
    PDF_E_INVCS                         = 0x83410617, /* The color space is invalid. */
    PDF_W_CIDSETVAL                     = 0x03418618, /* The value of the CIDSet[%cid%] of font %font% is %v1% but must be %v2%. */
    PDF_W_CHARSETM                      = 0x03418619, /* The CharSet of the font %font% must contain the name %name%. */
    PDF_W_CHARSETE                      = 0x0341861A, /* The CharSet of the font %font% must not contain the name %name%. */

/** PDF custom validation profile modules */
    CHK_E_FILESIZE1                     = 0x83510000, /* The file size exceeds the first limit. */
    CHK_E_FILESIZE2                     = 0x83510001, /* The file size exceeds the critical limit. */
    CHK_E_MAXPDFVERS                    = 0x83510002, /* The file's version is %v% but must be %max% or older. */
    CHK_E_MINPDFVERS                    = 0x83510003, /* The file's version is %v% but must be %min% or newer. */
    CHK_E_ENCRYPTION                    = 0x83510004, /* The file must %neg%be password protected. */
    CHK_E_FILTER                        = 0x83510005, /* The filter "%filter%" is not allowed. */
    CHK_E_CREATOR                       = 0x83510100, /* The creator "%crea%" must meet the organization's standard. */
    CHK_E_PRODUCER                      = 0x83510101, /* The producer "%prod%" must meet the organization's standard. */
    CHK_E_EFTYPE                        = 0x83510102, /* The file attachment "%name%" is not allowed. */
    CHK_E_EF                            = 0x83510103, /* The file attachment "%name%" is not allowed. */
    CHK_E_PAGESIZE                      = 0x83510180, /* The page size %width%x%height%mm of page no. %Page No.% is not on the approved list. */
    CHK_E_EMPTYPAGE                     = 0x83510181, /* The page no. %Page No.% must %neg%be blank. */
    CHK_E_SCANMAXDPI                    = 0x83510200, /* The scanned image's resolution is %dpi% DPI but must %max% DPI or less. */
    CHK_E_SCANMINDPI                    = 0x83510201, /* The scanned image's resolution is %dpi% DPI but must %min% DPI or more. */
    CHK_E_SCANCLR                       = 0x83510202, /* The scanned image must %neg%include color. */
    CHK_E_OCRTEXT                       = 0x83510203, /* The scanned image must %neg%be word searchable. */
    CHK_E_CLRUSED                       = 0x83510210, /* The page no. %Page No.% must not contain colored objects. */
    CHK_E_TRANSPARENCYUSED              = 0x83510211, /* The page no. %Page No.% must not contain transparent objects. */
    CHK_E_LAYERS                        = 0x83510220, /* The document must %neg%contain layers. */
    CHK_E_HIDDENLAYERS                  = 0x83510221, /* The document must %neg%contain hidden layers. */
    CHK_E_FONT                          = 0x83510300, /* The font %font% is not on the approved list. */
    CHK_E_FNTSUB                        = 0x83510301, /* The font %font% must %neg%be subsetted. */
    CHK_E_FNTEMB                        = 0x83510302, /* The font %font% must %neg%be embedded. */
    CHK_E_ANNOTATION                    = 0x83510600, /* The page no. %Page No.% must not contain %annot%. */
    CHK_E_ACTION                        = 0x83510601, /* The document must not contain %action%. */
    CHK_E_SIGVAL                        = 0x83510700, /* The digital signature of "%name%" is invalid: %msg% */

/** PDF signature handler */
    PDF_E_SIGCREA                       = 0x85410001, /* Signature creation error. */
    PDF_E_SIGVAL                        = 0x85410002, /* Signature validation error. */
    PDF_E_SIGGETLENGTH                  = 0x85410003, /* Signature length is zero. */
    PDF_E_SIGLENGTH                     = 0x85410004, /* Signature creation returns a length of %l1% which should not exceed %l2%. */
    PDF_E_INPSIG                        = 0x85410005, /* Input document must not be signed. */
    PDF_E_PDFASIG                       = 0x85410006, /* Signature would destroy PDF/A compliance. */
    PDF_E_SIGABG                        = 0x85410007, /* Unable to open signature background image '%path%'. */

/** PDF messages */
    PDF_M_FILEOPEN                      = 0x06410000, /* Open file.\n */
    PDF_M_FILECLOSE                     = 0x06410001, /* Close file.\n */
    PDF_M_FILESAVE                      = 0x06410002, /* Save output file.\n */
    PDF_M_ERRORTEMPLATE                 = 0x06410101, /* "%s", %d, %d, 0x%08X, "%s", %d\n */
    PDF_M_CONFCAT_1                     = 0x06410102, /* The file format (header, trailer, objects, xref, streams) is corrupted.\n */
    PDF_M_CONFCAT_2                     = 0x06410103, /* The document doesn't conform to the PDF reference (missing required entries, wrong value types, etc.).\n */
    PDF_M_CONFCAT_3                     = 0x06410104, /* The file is encrypted.\n */
    PDF_M_CONFCAT_4                     = 0x06410105, /* The document contains device-specific color spaces.\n */
    PDF_M_CONFCAT_5                     = 0x06410106, /* The document contains illegal rendering hints (unknown intents, interpolation, transfer and halftone functions).\n */
    PDF_M_CONFCAT_6                     = 0x06410107, /* The document contains alternate information (images).\n */
    PDF_M_CONFCAT_7                     = 0x06410108, /* The document contains embedded PostScript code.\n */
    PDF_M_CONFCAT_8                     = 0x06410109, /* The document contains references to external content (reference XObjects, file attachments, OPI).\n */
    PDF_M_CONFCAT_9                     = 0x0641010A, /* The document contains fonts without embedded font programs or encoding information (CMAPs).\n */
    PDF_M_CONFCAT_10                    = 0x0641010B, /* The document contains fonts without appropriate character to unicode mapping information (ToUnicode maps).\n */
    PDF_M_CONFCAT_11                    = 0x0641010C, /* The document contains transparency.\n */
    PDF_M_CONFCAT_12                    = 0x0641010D, /* The document contains unknown annotation types.\n */
    PDF_M_CONFCAT_13                    = 0x0641010E, /* The document contains multimedia annotations (sound, movies).\n */
    PDF_M_CONFCAT_14                    = 0x0641010F, /* The document contains hidden, invisible, non-viewable or non-printable annotations.\n */
    PDF_M_CONFCAT_15                    = 0x06410110, /* The document contains annotations or form fields with ambigous or without appropriate appearances.\n */
    PDF_M_CONFCAT_16                    = 0x06410111, /* The document contains actions types other than for navigation (Launch, JavaScript, ResetForm, etc.).\n */
    PDF_M_CONFCAT_17                    = 0x06410112, /* The document's meta data is either missing or inconsistent or corrupt.\n */
    PDF_M_CONFCAT_18                    = 0x06410113, /* The document doesn't provide appropriate logical structure information.\n */
    PDF_M_CONFCAT_19                    = 0x06410114, /* The document contains optional content (layers).\n */
    PDF_M_ANALYZEOBJECTS                = 0x06410201, /* Analyze Objects.\n */
    PDF_M_ANALYZEOUTLINES               = 0x06410202, /* Analyze Outlines.\n */
    PDF_M_ANALYZEPAGES                  = 0x06410203, /* Analyze Pages.\n */
    PDF_M_RECOVERXREF                   = 0x06410204, /* Recover XREF table.\n */
    PDF_M_RECOVERPAGES                  = 0x06410205, /* Recover Pages.\n */
    PDF_M_RECOVERIMAGES                 = 0x06410206, /* Recover Images.\n */
    PDF_M_CONVOPEN                      = 0x06410300, /* - Opening file %s.\n */
    PDF_M_CONVANALYZE                   = 0x06410301, /* - Analyzing %s.\n */
    PDF_M_CONVCOPY                      = 0x06410303, /* - File %s will be copied only since it is already conformant.\n */
    PDF_M_CONVCREATEFAIL                = 0x06410304, /* * File %s cannot be created.\n */
    PDF_M_CONVDOWNGRADE                 = 0x06410305, /* - Conformance level has been downgraded to level %S.\n */
    PDF_M_CONVCOLORSPACEPROFILE         = 0x06410306, /* - Setting color space profile %s.\n */
    PDF_M_PROFILEMISSING                = 0x06410307, /* * Missing color space profile for device color space %S.\n */
    PDF_M_CONVOUTINTENT                 = 0x06410308, /* - Setting output intent profile %s.\n */
    PDF_M_CONVOIFAILED                  = 0x06410309, /* * Failed to set output intent profile %s.\n */
    PDF_M_CONVCALIBRCOLORSPACE          = 0x0641030A, /* - Creating calibrated color space substitute for %s.\n */
    PDF_M_CONVSIGNATURE                 = 0x0641030B, /* - Adding signature %s.\n */
    PDF_M_CONVERROR                     = 0x0641030C, /* * Conversion errors in %s.\n */
    PDF_M_CONVPASUCCESS                 = 0x0641030D, /* - Post analysis for %s has been successful.\n */
    PDF_M_CONVPAFAIL                    = 0x0641030E, /* * Post analysis errors in %s.\n */
    PDF_M_CONVOPENFAIL                  = 0x0641030F, /* * Cannot open file %s.\n */
    PDF_M_CONVSUCCESS                   = 0x06410310, /* - File %s converted successfully.\n */
    PDF_M_CONVOUTINTCPY                 = 0x06410311, /* - Copied output intent from input file.\n */
    PDF_M_CONVERRORXMPLOSS              = 0x06410312, /*   %S Parts of the XMP metadata could not be repaired and had to be removed.\n */
    PDF_M_CONVCLIP                      = 0x06410313, /* - Clipped too large coordinates.\n */
    PDF_M_CONVERRORSIG                  = 0x06410314, /*   %S Removed signatures of signed input file.\n */
    PDF_M_CONVEF                        = 0x06410315, /* - Processing embedded file %s.\n */
    PDF_M_CONVEFERR                     = 0x06410316, /* - Embedded file %s has to be removed.\n */
    PDF_M_CONVUPGRADE                   = 0x06410318, /* - Conformance has been upgraded to PDF/A-2. Restarting conversion.\n */
    PDF_M_CONVERRORS                    = 0x06410319, /* - Conversion events:\n */
    PDF_M_CONVERRORVISUALDIFF           = 0x0641031A, /*   %S Visual differences in output file.\n */
    PDF_M_CONVERRORCOLORANTS            = 0x0641031B, /*   %S Resolved name collisions of colorants.\n */
    PDF_M_CONVERROROCG                  = 0x0641031C, /*   %S Removed optional content groups.\n */
    PDF_M_CONVERRORTRANSP               = 0x0641031D, /*   %S Removed transparency.\n */
    PDF_M_CONVERROREF                   = 0x0641031E, /*   %S Removed embedded files.\n */
    PDF_M_CONVERRORCORRUPT              = 0x06410322, /*   %S The input document is corrupt.\n */
    PDF_M_CONVCORRUPT                   = 0x06410323, /* * Corrupt input document: '%s'\n */
    PDF_M_CONVCORRUPTMAX                = 0x06410324, /* * Corrupt input document, see validation log for complete list of errors.\n */
    PDF_M_CONVSTAMP                     = 0x06410325, /* - Applying stamps.\n */
    PDF_M_CONVSTAMPERR                  = 0x06410326, /* * Failed to apply stamps: '%s'.\n */
    PDF_M_CONVEFCOPY                    = 0x06410327, /* - Copied embedded file %s to output file.\n */
    PDF_M_CONVPA                        = 0x06410328, /* - Performing post analysis for %s.\n */
    PDF_M_CONVVISUALDIFF                = 0x06410329, /* * Visual differences: '%s'\n */
    PDF_M_CONVSTOP                      = 0x0641032A, /* * Abort conversion because file is corrupt and cannot be converted.\n */
    PDF_M_CONVOCRERROR                  = 0x0641032C, /* * OCR error: '%s'.\n */
    PDF_M_CONVERRORFONTSUBST            = 0x0641032D, /*   %S Font substituted.\n */
    PDF_M_CONVFONTSUBST                 = 0x0641032E, /* - Font '%s' not found and substituted with '%s'.\n */
    PDF_M_CONVSIGERROR                  = 0x0641032F, /* * Cannot create signature: %s\n */
    PDF_M_CONVERRORACTION               = 0x06410330, /*   %S Removed interactive elements.\n */
    PDF_M_CONVSTOPMSG                   = 0x06410332, /* * Abort conversion: %s\n */

/** JBIG2 compression module */
    BSE_INFO_JB2                        = 0x0A030013, /* The JB2 stream issued a message: '%msg%'. */
    BSE_WARNING_JB2                     = 0x0A038014, /* The JB2 stream caused a warning: '%msg%'. */
    BSE_ERROR_JB2                       = 0x8A030015, /* The JB2 stream caused an error: '%msg%'. */

/** DCT compression module */
    JPEG_E_JMSG_NOMESSAGE               = 0x8A040000, /* Error in DCT stream: Bogus message code %d. */
    JPEG_E_JERR_BAD_ALIGN_TYPE          = 0x8A040001, /* Error in DCT stream: ALIGN_TYPE is wrong, please fix. */
    JPEG_E_JERR_BAD_ALLOC_CHUNK         = 0x8A040002, /* Error in DCT stream: MAX_ALLOC_CHUNK is wrong, please fix. */
    JPEG_E_JERR_BAD_BUFFER_MODE         = 0x8A040003, /* Error in DCT stream: Bogus buffer control mode. */
    JPEG_E_JERR_BAD_COMPONENT_ID        = 0x8A040004, /* Error in DCT stream: Invalid component ID %d in SOS. */
    JPEG_E_JERR_BAD_CROP_SPEC           = 0x8A040005, /* Error in DCT stream: Invalid crop request. */
    JPEG_E_JERR_BAD_DCT_COEF            = 0x8A040006, /* Error in DCT stream: DCT coefficient out of range. */
    JPEG_E_JERR_BAD_DCTSIZE             = 0x8A040007, /* Error in DCT stream: DCT scaled block size %dx%d not supported. */
    JPEG_E_JERR_BAD_DROP_SAMPLING       = 0x8A040008, /* Error in DCT stream: Component index %d: mismatching sampling ratio %d:%d, %d:%d, %C. */
    JPEG_E_JERR_BAD_HUFF_TABLE          = 0x8A040009, /* Error in DCT stream: Bogus Huffman table definition. */
    JPEG_E_JERR_BAD_IN_COLORSPACE       = 0x8A04000A, /* Error in DCT stream: Bogus input colorspace. */
    JPEG_E_JERR_BAD_J_COLORSPACE        = 0x8A04000B, /* Error in DCT stream: Bogus JPEG colorspace. */
    JPEG_E_JERR_BAD_LENGTH              = 0x8A04000C, /* Error in DCT stream: Bogus marker length. */
    JPEG_E_JERR_BAD_LIB_VERSION         = 0x8A04000D, /* Error in DCT stream: Wrong JPEG library version: library is %d, caller expects %d. */
    JPEG_E_JERR_BAD_MCU_SIZE            = 0x8A04000E, /* Error in DCT stream: Sampling factors too large for interleaved scan. */
    JPEG_E_JERR_BAD_POOL_ID             = 0x8A04000F, /* Error in DCT stream: Invalid memory pool code %d. */
    JPEG_E_JERR_BAD_PRECISION           = 0x8A040010, /* Error in DCT stream: Unsupported JPEG data precision %d. */
    JPEG_E_JERR_BAD_PROGRESSION         = 0x8A040011, /* Error in DCT stream: Invalid progressive parameters Ss=%d Se=%d Ah=%d Al=%d. */
    JPEG_E_JERR_BAD_PROG_SCRIPT         = 0x8A040012, /* Error in DCT stream: Invalid progressive parameters at scan script entry %d. */
    JPEG_E_JERR_BAD_SAMPLING            = 0x8A040013, /* Error in DCT stream: Bogus sampling factors. */
    JPEG_E_JERR_BAD_SCAN_SCRIPT         = 0x8A040014, /* Error in DCT stream: Invalid scan script at entry %d. */
    JPEG_E_JERR_BAD_STATE               = 0x8A040015, /* Error in DCT stream: Improper call to JPEG library in state %d. */
    JPEG_E_JERR_BAD_STRUCT_SIZE         = 0x8A040016, /* Error in DCT stream: JPEG parameter struct mismatch: library thinks size is %u, caller expects %u. */
    JPEG_E_JERR_BAD_VIRTUAL_ACCESS      = 0x8A040017, /* Error in DCT stream: Bogus virtual array access. */
    JPEG_E_JERR_BUFFER_SIZE             = 0x8A040018, /* Error in DCT stream: Buffer passed to JPEG library is too small. */
    JPEG_E_JERR_CANT_SUSPEND            = 0x8A040019, /* Error in DCT stream: Suspension not allowed here. */
    JPEG_E_JERR_CCIR601_NOTIMPL         = 0x8A04001A, /* Error in DCT stream: CCIR601 sampling not implemented yet. */
    JPEG_E_JERR_COMPONENT_COUNT         = 0x8A04001B, /* Error in DCT stream: Too many color components: %d, max %d. */
    JPEG_E_JERR_CONVERSION_NOTIMPL      = 0x8A04001C, /* Error in DCT stream: Unsupported color conversion request. */
    JPEG_E_JERR_DAC_INDEX               = 0x8A04001D, /* Error in DCT stream: Bogus DAC index %d. */
    JPEG_E_JERR_DAC_VALUE               = 0x8A04001E, /* Error in DCT stream: Bogus DAC value 0x%x. */
    JPEG_E_JERR_DHT_INDEX               = 0x8A04001F, /* Error in DCT stream: Bogus DHT index %d. */
    JPEG_E_JERR_DQT_INDEX               = 0x8A040020, /* Error in DCT stream: Bogus DQT index %d. */
    JPEG_E_JERR_EMPTY_IMAGE             = 0x8A040021, /* Error in DCT stream: Empty JPEG image (DNL not supported). */
    JPEG_E_JERR_EMS_READ                = 0x8A040022, /* Error in DCT stream: Read from EMS failed. */
    JPEG_E_JERR_EMS_WRITE               = 0x8A040023, /* Error in DCT stream: Write to EMS failed. */
    JPEG_E_JERR_EOI_EXPECTED            = 0x8A040024, /* Error in DCT stream: Didn't expect more than one scan. */
    JPEG_E_JERR_FILE_READ               = 0x8A040025, /* Error in DCT stream: Input file read error. */
    JPEG_E_JERR_FILE_WRITE              = 0x8A040026, /* Error in DCT stream: Output file write error --- out of disk space?. */
    JPEG_E_JERR_FRACT_SAMPLE_NOTIMPL    = 0x8A040027, /* Error in DCT stream: Fractional sampling not implemented yet. */
    JPEG_E_JERR_HUFF_CLEN_OVERFLOW      = 0x8A040028, /* Error in DCT stream: Huffman code size table overflow. */
    JPEG_E_JERR_HUFF_MISSING_CODE       = 0x8A040029, /* Error in DCT stream: Missing Huffman code table entry. */
    JPEG_E_JERR_IMAGE_TOO_BIG           = 0x8A04002A, /* Error in DCT stream: Maximum supported image dimension is %u pixels. */
    JPEG_E_JERR_INPUT_EMPTY             = 0x8A04002B, /* Error in DCT stream: Empty input file. */
    JPEG_E_JERR_INPUT_EOF               = 0x8A04002C, /* Error in DCT stream: Premature end of input file. */
    JPEG_E_JERR_MISMATCHED_QUANT_TABLE  = 0x8A04002D, /* Error in DCT stream: Cannot transcode due to multiple use of quantization table %d. */
    JPEG_E_JERR_MISSING_DATA            = 0x8A04002E, /* Error in DCT stream: Scan script does not transmit all data. */
    JPEG_E_JERR_MODE_CHANGE             = 0x8A04002F, /* Error in DCT stream: Invalid color quantization mode change. */
    JPEG_E_JERR_NOTIMPL                 = 0x8A040030, /* Error in DCT stream: Not implemented yet. */
    JPEG_E_JERR_NOT_COMPILED            = 0x8A040031, /* Error in DCT stream: Requested feature was omitted at compile time. */
    JPEG_E_JERR_NO_ARITH_TABLE          = 0x8A040032, /* Error in DCT stream: Arithmetic table 0x%02x was not defined. */
    JPEG_E_JERR_NO_BACKING_STORE        = 0x8A040033, /* Error in DCT stream: Backing store not supported. */
    JPEG_E_JERR_NO_HUFF_TABLE           = 0x8A040034, /* Error in DCT stream: Huffman table 0x%02x was not defined. */
    JPEG_E_JERR_NO_IMAGE                = 0x8A040035, /* Error in DCT stream: JPEG datastream contains no image. */
    JPEG_E_JERR_NO_QUANT_TABLE          = 0x8A040036, /* Error in DCT stream: Quantization table 0x%02x was not defined. */
    JPEG_E_JERR_NO_SOI                  = 0x8A040037, /* Error in DCT stream: Not a JPEG file: starts with 0x%02x 0x%02x. */
    JPEG_E_JERR_OUT_OF_MEMORY           = 0x8A040038, /* Error in DCT stream: Insufficient memory (case %d). */
    JPEG_E_JERR_QUANT_COMPONENTS        = 0x8A040039, /* Error in DCT stream: Cannot quantize more than %d color components. */
    JPEG_E_JERR_QUANT_FEW_COLORS        = 0x8A04003A, /* Error in DCT stream: Cannot quantize to fewer than %d colors. */
    JPEG_E_JERR_QUANT_MANY_COLORS       = 0x8A04003B, /* Error in DCT stream: Cannot quantize to more than %d colors. */
    JPEG_E_JERR_SOF_BEFORE              = 0x8A04003C, /* Error in DCT stream: Invalid JPEG file structure: %S before SOF. */
    JPEG_E_JERR_SOF_DUPLICATE           = 0x8A04003D, /* Error in DCT stream: Invalid JPEG file structure: two SOF markers. */
    JPEG_E_JERR_SOF_NO_SOS              = 0x8A04003E, /* Error in DCT stream: Invalid JPEG file structure: missing SOS marker. */
    JPEG_E_JERR_SOF_UNSUPPORTED         = 0x8A04003F, /* Error in DCT stream: Unsupported JPEG process: SOF type 0x%02x. */
    JPEG_E_JERR_SOI_DUPLICATE           = 0x8A040040, /* Error in DCT stream: Invalid JPEG file structure: two SOI markers. */
    JPEG_E_JERR_TFILE_CREATE            = 0x8A040041, /* Error in DCT stream: Failed to create temporary file %S. */
    JPEG_E_JERR_TFILE_READ              = 0x8A040042, /* Error in DCT stream: Read failed on temporary file. */
    JPEG_E_JERR_TFILE_SEEK              = 0x8A040043, /* Error in DCT stream: Seek failed on temporary file. */
    JPEG_E_JERR_TFILE_WRITE             = 0x8A040044, /* Error in DCT stream: Write failed on temporary file --- out of disk space?. */
    JPEG_E_JERR_TOO_LITTLE_DATA         = 0x8A040045, /* Error in DCT stream: Application transferred too few scanlines. */
    JPEG_E_JERR_UNKNOWN_MARKER          = 0x8A040046, /* Error in DCT stream: Unsupported marker type 0x%02x. */
    JPEG_E_JERR_VIRTUAL_BUG             = 0x8A040047, /* Error in DCT stream: Virtual array controller messed up. */
    JPEG_E_JERR_WIDTH_OVERFLOW          = 0x8A040048, /* Error in DCT stream: Image too wide for this implementation. */
    JPEG_E_JERR_XMS_READ                = 0x8A040049, /* Error in DCT stream: Read from XMS failed. */
    JPEG_E_JERR_XMS_WRITE               = 0x8A04004A, /* Error in DCT stream: Write to XMS failed. */
    JPEG_E_JMSG_COPYRIGHT               = 0x8A04004B, /* Copyright (C) 2011, Thomas G. Lane, Guido Vollbeding. */
    JPEG_E_JMSG_VERSION                 = 0x8A04004C, /* 8c  16-Jan-2011. */
    JPEG_E_JTRC_16BIT_TABLES            = 0x8A04004D, /* Error in DCT stream: Caution: quantization tables are too coarse for baseline JPEG. */
    JPEG_E_JTRC_ADOBE                   = 0x8A04004E, /* Error in DCT stream: Adobe APP14 marker: version %d, flags 0x%04x 0x%04x, transform %d. */
    JPEG_E_JTRC_APP0                    = 0x8A04004F, /* Error in DCT stream: Unknown APP0 marker (not JFIF), length %u. */
    JPEG_E_JTRC_APP14                   = 0x8A040050, /* Error in DCT stream: Unknown APP14 marker (not Adobe), length %u. */
    JPEG_E_JTRC_DAC                     = 0x8A040051, /* Error in DCT stream: Define Arithmetic Table 0x%02x: 0x%02x. */
    JPEG_E_JTRC_DHT                     = 0x8A040052, /* Error in DCT stream: Define Huffman Table 0x%02x. */
    JPEG_E_JTRC_DQT                     = 0x8A040053, /* Error in DCT stream: Define Quantization Table %d  precision %d. */
    JPEG_E_JTRC_DRI                     = 0x8A040054, /* Error in DCT stream: Define Restart Interval %u. */
    JPEG_E_JTRC_EMS_CLOSE               = 0x8A040055, /* Error in DCT stream: Freed EMS handle %u. */
    JPEG_E_JTRC_EMS_OPEN                = 0x8A040056, /* Error in DCT stream: Obtained EMS handle %u. */
    JPEG_E_JTRC_EOI                     = 0x8A040057, /* Error in DCT stream: End Of Image. */
    JPEG_E_JTRC_HUFFBITS                = 0x8A040058, /* Error in DCT stream:         %3d %3d %3d %3d %3d %3d %3d %3d. */
    JPEG_E_JTRC_JFIF                    = 0x8A040059, /* Error in DCT stream: JFIF APP0 marker: version %d.%02d, density %dx%d  %d. */
    JPEG_E_JTRC_JFIF_BADTHUMBNAILSIZE   = 0x8A04005A, /* Error in DCT stream: Warning: thumbnail image size does not match data length %u. */
    JPEG_E_JTRC_JFIF_EXTENSION          = 0x8A04005B, /* Error in DCT stream: JFIF extension marker: type 0x%02x, length %u. */
    JPEG_E_JTRC_JFIF_THUMBNAIL          = 0x8A04005C, /* Error in DCT stream:     with %d x %d thumbnail image. */
    JPEG_E_JTRC_MISC_MARKER             = 0x8A04005D, /* Error in DCT stream: Miscellaneous marker 0x%02x, length %u. */
    JPEG_E_JTRC_PARMLESS_MARKER         = 0x8A04005E, /* Error in DCT stream: Unexpected marker 0x%02x. */
    JPEG_E_JTRC_QUANTVALS               = 0x8A04005F, /* Error in DCT stream:         %4u %4u %4u %4u %4u %4u %4u %4u. */
    JPEG_E_JTRC_QUANT_3_NCOLORS         = 0x8A040060, /* Error in DCT stream: Quantizing to %d = %d*%d*%d colors. */
    JPEG_E_JTRC_QUANT_NCOLORS           = 0x8A040061, /* Error in DCT stream: Quantizing to %d colors. */
    JPEG_E_JTRC_QUANT_SELECTED          = 0x8A040062, /* Error in DCT stream: Selected %d colors for quantization. */
    JPEG_E_JTRC_RECOVERY_ACTION         = 0x8A040063, /* Error in DCT stream: At marker 0x%02x, recovery action %d. */
    JPEG_E_JTRC_RST                     = 0x8A040064, /* Error in DCT stream: RST%d. */
    JPEG_E_JTRC_SMOOTH_NOTIMPL          = 0x8A040065, /* Error in DCT stream: Smoothing not supported with nonstandard sampling ratios. */
    JPEG_E_JTRC_SOF                     = 0x8A040066, /* Error in DCT stream: Start Of Frame 0x%02x: width=%u, height=%u, components=%d. */
    JPEG_E_JTRC_SOF_COMPONENT           = 0x8A040067, /* Error in DCT stream:     Component %d: %dhx%dv q=%d. */
    JPEG_E_JTRC_SOI                     = 0x8A040068, /* Error in DCT stream: Start of Image. */
    JPEG_E_JTRC_SOS                     = 0x8A040069, /* Error in DCT stream: Start Of Scan: %d components. */
    JPEG_E_JTRC_SOS_COMPONENT           = 0x8A04006A, /* Error in DCT stream:     Component %d: dc=%d ac=%d. */
    JPEG_E_JTRC_SOS_PARAMS              = 0x8A04006B, /* Error in DCT stream:   Ss=%d, Se=%d, Ah=%d, Al=%d. */
    JPEG_E_JTRC_TFILE_CLOSE             = 0x8A04006C, /* Error in DCT stream: Closed temporary file %S. */
    JPEG_E_JTRC_TFILE_OPEN              = 0x8A04006D, /* Error in DCT stream: Opened temporary file %S. */
    JPEG_E_JTRC_THUMB_JPEG              = 0x8A04006E, /* Error in DCT stream: JFIF extension marker: JPEG-compressed thumbnail image, length %u. */
    JPEG_E_JTRC_THUMB_PALETTE           = 0x8A04006F, /* Error in DCT stream: JFIF extension marker: palette thumbnail image, length %u. */
    JPEG_E_JTRC_THUMB_RGB               = 0x8A040070, /* Error in DCT stream: JFIF extension marker: RGB thumbnail image, length %u. */
    JPEG_E_JTRC_UNKNOWN_IDS             = 0x8A040071, /* Error in DCT stream: Unrecognized component IDs %d %d %d, assuming YCbCr. */
    JPEG_E_JTRC_XMS_CLOSE               = 0x8A040072, /* Error in DCT stream: Freed XMS handle %u. */
    JPEG_E_JTRC_XMS_OPEN                = 0x8A040073, /* Error in DCT stream: Obtained XMS handle %u. */
    JPEG_E_JWRN_ADOBE_XFORM             = 0x8A040074, /* Warning in DCT stream: Unknown Adobe color transform code %d. */
    JPEG_E_JWRN_ARITH_BAD_CODE          = 0x8A040075, /* Warning in DCT stream: Corrupt JPEG data: bad arithmetic code. */
    JPEG_E_JWRN_BOGUS_PROGRESSION       = 0x8A040076, /* Warning in DCT stream: Inconsistent progression sequence for component %d coefficient %d. */
    JPEG_E_JWRN_EXTRANEOUS_DATA         = 0x8A040077, /* Warning in DCT stream: Corrupt JPEG data: %u extraneous bytes before marker 0x%02x. */
    JPEG_E_JWRN_HIT_MARKER              = 0x8A040078, /* Warning in DCT stream: Corrupt JPEG data: premature end of data segment. */
    JPEG_E_JWRN_HUFF_BAD_CODE           = 0x8A040079, /* Warning in DCT stream: Corrupt JPEG data: bad Huffman code. */
    JPEG_E_JWRN_JFIF_MAJOR              = 0x8A04007A, /* Warning in DCT stream: Warning: unknown JFIF revision number %d.%02d. */
    JPEG_E_JWRN_JPEG_EOF                = 0x8A04007B, /* Warning in DCT stream: Premature end of JPEG file. */
    JPEG_E_JWRN_MUST_RESYNC             = 0x8A04007C, /* Warning in DCT stream: Corrupt JPEG data: found marker 0x%02x instead of RST%d. */
    JPEG_E_JWRN_NOT_SEQUENTIAL          = 0x8A04007D, /* Warning in DCT stream: Invalid SOS parameters for sequential JPEG. */
    JPEG_E_JWRN_TOO_MUCH_DATA           = 0x8A04007E, /* Warning in DCT stream: Application transferred too many scanlines. */

/** JPX compression module */
    JPX_ERROR_0                         = 0x8A057F9C, /* Error in JPX stream: Fatal. */
    JPX_ERROR_1                         = 0x8A057FA5, /* Error in JPX stream: License_Level_Too_Low. */
    JPX_ERROR_2                         = 0x8A057FA6, /* Error in JPX stream: Invalid_License. */
    JPX_ERROR_3                         = 0x8A057FB7, /* Error in JPX stream: Invalid_Marker. */
    JPX_ERROR_4                         = 0x8A057FB8, /* Error in JPX stream: Incompatible_Format. */
    JPX_ERROR_5                         = 0x8A057FB9, /* Error in JPX stream: Invalid_Header. */
    JPX_ERROR_6                         = 0x8A057FBA, /* Error in JPX stream: Invalid_Label. */
    JPX_ERROR_7                         = 0x8A057FBB, /* Error in JPX stream: Maximum_Box_Size_Exceeded. */
    JPX_ERROR_8                         = 0x8A057FBC, /* Error in JPX stream: Lossless_Compression_Mode. */
    JPX_ERROR_9                         = 0x8A057FBD, /* Error in JPX stream: Invalid_Region. */
    JPX_ERROR_10                        = 0x8A057FBE, /* Error in JPX stream: Transcode_Scale_Palette_Images. */
    JPX_ERROR_11                        = 0x8A057FBF, /* Error in JPX stream: Transcoding_Finished. */
    JPX_ERROR_12                        = 0x8A057FC0, /* Error in JPX stream: Use_SetPalette. */
    JPX_ERROR_13                        = 0x8A057FC1, /* Error in JPX stream: Invalid_Channel_Definition. */
    JPX_ERROR_14                        = 0x8A057FC2, /* Error in JPX stream: Invalid_Component_Mapping. */
    JPX_ERROR_15                        = 0x8A057FC3, /* Error in JPX stream: Missing_Component_Mapping. */
    JPX_ERROR_16                        = 0x8A057FC4, /* Error in JPX stream: Invalid_Palette. */
    JPX_ERROR_17                        = 0x8A057FC5, /* Error in JPX stream: Missing_Palette. */
    JPX_ERROR_18                        = 0x8A057FC6, /* Error in JPX stream: Use_SetLAB_Function. */
    JPX_ERROR_19                        = 0x8A057FC7, /* Error in JPX stream: Use_SetICC_Function. */
    JPX_ERROR_20                        = 0x8A057FC8, /* Error in JPX stream: Invalid_ICC_Profile. */
    JPX_ERROR_21                        = 0x8A057FC9, /* Error in JPX stream: Invalid_Color_Spec_Index. */
    JPX_ERROR_22                        = 0x8A057FCA, /* Error in JPX stream: Invalid_Meta_Data_Box_Index. */
    JPX_ERROR_23                        = 0x8A057FCB, /* Error in JPX stream: JPX_File_Format_Required. */
    JPX_ERROR_24                        = 0x8A057FCC, /* Error in JPX stream: File_Format_Required. */
    JPX_ERROR_25                        = 0x8A057FCD, /* Error in JPX stream: Decompression_Cancelled. */
    JPX_ERROR_26                        = 0x8A057FCE, /* Error in JPX stream: More_Bytes_Required. */
    JPX_ERROR_27                        = 0x8A057FD3, /* Error in JPX stream: Max_Number_Of_ROIs_Reached. */
    JPX_ERROR_28                        = 0x8A057FD5, /* Error in JPX stream: Invalid_Resolution_Type. */
    JPX_ERROR_29                        = 0x8A057FD6, /* Error in JPX stream: Invalid_Resolution_Unit. */
    JPX_ERROR_30                        = 0x8A057FD7, /* Error in JPX stream: Invalid_Resolution. */
    JPX_ERROR_31                        = 0x8A057FD8, /* Error in JPX stream: Scale_Factor_Is_Too_Large. */
    JPX_ERROR_32                        = 0x8A057FD9, /* Error in JPX stream: ROI_Shift_Failed. */
    JPX_ERROR_33                        = 0x8A057FDA, /* Error in JPX stream: Invalid_Precinct_Dimensions. */
    JPX_ERROR_34                        = 0x8A057FDB, /* Error in JPX stream: Invalid_Quantization_Filter_Pair. */
    JPX_ERROR_35                        = 0x8A057FDC, /* Error in JPX stream: Trial_Time_Expired. */
    JPX_ERROR_36                        = 0x8A057FDD, /* Error in JPX stream: Not_Yet_Supported. */
    JPX_ERROR_37                        = 0x8A057FDE, /* Error in JPX stream: Invalid_Sample_Rate. */
    JPX_ERROR_38                        = 0x8A057FDF, /* Error in JPX stream: Requested_File_Size_Too_Small. */
    JPX_ERROR_39                        = 0x8A057FE0, /* Error in JPX stream: Byte_Compression_Mode. */
    JPX_ERROR_40                        = 0x8A057FE1, /* Error in JPX stream: Cannot_Find_Suitable_Grid. */
    JPX_ERROR_41                        = 0x8A057FE2, /* Error in JPX stream: Read_Callback_Undefined. */
    JPX_ERROR_42                        = 0x8A057FE3, /* Error in JPX stream: Write_Callback_Undefined. */
    JPX_ERROR_43                        = 0x8A057FE4, /* Error in JPX stream: Input_Callback_Undefined. */
    JPX_ERROR_44                        = 0x8A057FE5, /* Error in JPX stream: Bits_Per_Sample_Too_High. */
    JPX_ERROR_45                        = 0x8A057FE6, /* Error in JPX stream: Compression_Only_Property. */
    JPX_ERROR_46                        = 0x8A057FE7, /* Error in JPX stream: Decompression_Only_Property. */
    JPX_ERROR_47                        = 0x8A057FE8, /* Error in JPX stream: Quality_Compression_Mode. */
    JPX_ERROR_48                        = 0x8A057FE9, /* Error in JPX stream: Set_Only_Property. */
    JPX_ERROR_49                        = 0x8A057FEA, /* Error in JPX stream: Read_Only_Property. */
    JPX_ERROR_50                        = 0x8A057FEB, /* Error in JPX stream: Single_Value_For_All_Tiles. */
    JPX_ERROR_51                        = 0x8A057FEC, /* Error in JPX stream: Single_Value_For_All_Components. */
    JPX_ERROR_52                        = 0x8A057FED, /* Error in JPX stream: Invalid_Stream. */
    JPX_ERROR_53                        = 0x8A057FEE, /* Error in JPX stream: Invalid_Wavelet_Filter_Combination. */
    JPX_ERROR_54                        = 0x8A057FEF, /* Error in JPX stream: Invalid_Resolution_Level. */
    JPX_ERROR_55                        = 0x8A057FF0, /* Error in JPX stream: Invalid_Tile_Index. */
    JPX_ERROR_56                        = 0x8A057FF1, /* Error in JPX stream: Invalid_Component_Dimensions. */
    JPX_ERROR_57                        = 0x8A057FF2, /* Error in JPX stream: Invalid_Colorspace. */
    JPX_ERROR_58                        = 0x8A057FF3, /* Error in JPX stream: Invalid_Tile_Arrangement. */
    JPX_ERROR_59                        = 0x8A057FF4, /* Error in JPX stream: Invalid_Bits_Per_Sample. */
    JPX_ERROR_60                        = 0x8A057FF5, /* Error in JPX stream: Invalid_Height. */
    JPX_ERROR_61                        = 0x8A057FF6, /* Error in JPX stream: Invalid_Width. */
    JPX_ERROR_62                        = 0x8A057FF7, /* Error in JPX stream: Invalid_Property_Key. */
    JPX_ERROR_63                        = 0x8A057FF8, /* Error in JPX stream: Invalid_Property_Value. */
    JPX_ERROR_64                        = 0x8A057FF9, /* Error in JPX stream: Invalid_Component_Index. */
    JPX_ERROR_65                        = 0x8A057FFA, /* Error in JPX stream: Invalid_Number_Of_Components. */
    JPX_ERROR_66                        = 0x8A057FFB, /* Error in JPX stream: Invalid_Pointer. */
    JPX_ERROR_67                        = 0x8A057FFC, /* Error in JPX stream: Invalid_Handle. */
    JPX_ERROR_68                        = 0x8A057FFD, /* Error in JPX stream: Failure_Write. */
    JPX_ERROR_69                        = 0x8A057FFE, /* Error in JPX stream: Failure_Read. */
    JPX_ERROR_70                        = 0x8A057FFF, /* Error in JPX stream: Failure_Malloc. */

/** not used */
    AFW_M_SVC_STARTING                  = 0x0A070001, /* Starting service.\n */
    AFW_M_SVC_STARTED                   = 0x0A070002, /* Service started.\n */
    AFW_M_SVC_STOPPING                  = 0x0A070003, /* Stopping service.\n */
    AFW_M_SVC_STOPPED                   = 0x0A070004, /* Service stopped.\n */

/** XML module */
    XML_I_GENERAL                       = 0x0A080000, /* XML line %line%:%column%: %message%. */
    XML_W_GENERAL                       = 0x0A088000, /* XML line %line%:%column%: %message%. */
    XML_E_GENERAL                       = 0x8A080000, /* XML line %line%:%column%: %message%. */

/** XMP module */
    RDF_E_FEATNIMPL                     = 0x8A090001, /* The RDF feature '%feature%' is not implemented. */
    RDF_E_ATTRLI                        = 0x8A090002, /* rdf:li is not allowed as attribute, only as element. */
    RDF_E_FEATREM                       = 0x8A090003, /* The RDF feature '%feature%' has been removed from the standard. */
    RDF_W_FEATNALLOW                    = 0x0A098004, /* The RDF feature '%feature%' is not allowed. */
    RDF_E_KEYWORD                       = 0x8A090005, /* Invalid use of the reserved RDF name 'rdf:%n1%' as %n2%. */
    RDF_I_RDFNS                         = 0x0A090006, /* The RDF namespace should not be used outside of the RDF syntax (rdf:%name%). */
    RDF_W_MULTID                        = 0x0A098007, /* RDF resources should have only one identifier (rdf:resource, rdf:nodeID, rdf:about, rdf:ID, ...). */
    RDF_E_PARSETYPE                     = 0x8A090008, /* rdf:parseType='%type%' not recognized. */
    RDF_I_NONS                          = 0x0A090009, /* Property '%property%' without namespace is ignored. */
    RDF_E_URI                           = 0x8A09000A, /* Invalid URI(-reference) <%uri%>. */
    RDF_I_UNQUAL                        = 0x0A09000B, /* The unqualified RDF attribute '%attr1%' is deprecated. Use 'rdf:%attr2%' instead. */
    XMP_W_PACKET_ENC                    = 0x0A09C001, /* Invalid encoding of XMP packet: %enc%, expected %exp%. */
    XMP_E_PACKET_ADDCONT                = 0x8A094002, /* Additional non-XMP content is not allowed in XMP packet. */
    XMP_W_PACKET_NOHEAD                 = 0x0A09C003, /* XMP packet header missing. */
    XMP_W_PACKET_NOTRAIL                = 0x0A09C004, /* XMP packet trailer missing. */
    XMP_W_PACKET_ATTRMISS               = 0x0A09C005, /* Missing attribute %attr% in XMP packet %packet%. */
    XMP_I_PACKET_ATTRCONT               = 0x0A094006, /* Deprecated content of XMP packet %packet% attribute '%attr%': '%content%'. */
    XMP_W_PACKET_ATTRCONT               = 0x0A09C006, /* Invalid content of XMP packet %packet% attribute '%attr%': '%content%'. */
    XMP_W_PACKET_ATTRORD                = 0x0A09C007, /* Invalid order of XMP packet %packet% attribute '%attr%'. */
    XMP_W_PACKET_ATTRSPACE              = 0x0A09C008, /* Invalid spacing of XMP packet %attr% attributes. There must be exactly one space between each attribute. */
    XMP_I_PACKET_ATTR                   = 0x0A094009, /* XMP packet %packet% attribute '%attr%' is deprecated. */
    XMP_W_PACKET_ATTR                   = 0x0A09C009, /* XMP packet %packet% attribute '%attr%' is forbidden. */
    XMP_I_PACKET_ATTRDUP                = 0x0A09400A, /* Duplicate attribute '%attr%' in XMP packet %packet%. */
    XMP_W_META_NORDFELEM                = 0x0A09C401, /* The rdf:RDF element must not be omitted in XMP. */
    XMP_E_META_ADDCONT                  = 0x8A094402, /* The x:xmpmeta element must not contain any elements other than rdf:RDF. */
    XMP_W_META_MULTRES                  = 0x0A09C403, /* There is only one RDF resource allowed in XMP. */
    XMP_E_META_MULTRES                  = 0x8A094403, /* There is only one RDF resource allowed in XMP. */
    XMP_W_META_MAINTYPED                = 0x0A09C404, /* Toplevel typed nodes are not allowed in XMP (%prefix%:%name%). */
    XMP_E_META_INVARRELEM               = 0x8A094405, /* '%prefix%:%name%' is not allowed in arrays. The elements must be rdf:li or rdf:_N, where N is a positive number. */
    XMP_I_META_XAPMETA                  = 0x0A094406, /* The x:xapmeta element is deprecated (but still allowed for compatibility). use x:xmpmeta instead. */
    XMP_W_META_INVTYPE                  = 0x0A09C407, /* Node type '%prefix%:%name%' is not allowed in XMP. The only node types allowed are rdf:Bag, rdf:Seq, and rdf:Alt. */
    XMP_W_META_DATATYPE                 = 0x0A09C408, /* Typed literals (with rdf:datatype) are not allowed in XMP. */
    XMP_I_META_INVNSURI                 = 0x0A09440B, /* Use of invalid namespace URI: <%uri%> does not end with '/' or '#'. */
    XMP_W_META_INVNSURI                 = 0x0A09C40B, /* Use of invalid namespace URI: <%uri%> does not end with '/' or '#'. */
    XMP_W_META_ANONRES                  = 0x0A09C40C, /* Anonymous RDF resources (rdf:Description without rdf:about attribute) are not allowed in XMP Metadata. */
    XMP_E_META_LOOP                     = 0x8A09440D, /* Circular references in RDF resources are not allowed in XMP Metadata. */
    XMP_W_META_MULTPROP                 = 0x0A09C40E, /* Multiple occurrences of property '%prefix%:%name%'. */
    XMP_E_SPARS_NOSCHEMURI              = 0x8A094841, /* Namespace URI missing in schema description for '%schema%'. */
    XMP_I_SPARS_MULTSCHEMURI            = 0x0A094842, /* Multiple schema descriptions for schema namespace '%uri%'. */
    XMP_E_SPARS_MULTSCHEMURI            = 0x8A094842, /* Multiple schema descriptions for schema namespace '%uri%'. */
    XMP_E_SPARS_PROPNONAME              = 0x8A094843, /* Property description without name found in schema '%schema%'. */
    XMP_W_SPARS_PROPNOTYPE              = 0x0A09C844, /* Value type missing in property description for '%prop%' in schema '%schema%'. */
    XMP_E_SPARS_TYPENONAME              = 0x8A094845, /* Value type description without name found in schema '%schema%'. */
    XMP_E_SPARS_FDNONAME                = 0x8A094846, /* Field description without name found in value type '%type%' in schema '%schema%'. */
    XMP_W_SPARS_FDNOTYPE                = 0x0A09C847, /* Value type missing in field description for '%field%' in value type '%type%' in schema '%schema%'. */
    XMP_W_SVAL_NOSCHEMA                 = 0x0A09C881, /* The schema description for namespace '%prefix%:' (%schema%) is missing. */
    XMP_W_SVAL_PROPNDEF                 = 0x0A09C882, /* The property '%prefix%:%name%' is not defined in schema '%schema%'. */
    XMP_I_SVAL_PROPDEPR                 = 0x0A094883, /* The property '%prefix%:%name%' is deprecated. Use '%reqprefix%:%reqname%' instead. */
    XMP_W_SVAL_PROPDEPR                 = 0x0A09C883, /* The property '%prefix%:%name%' is deprecated and thus forbidden. Use '%reqprefix%:%reqname%' instead. */
    XMP_W_SVAL_TYPE                     = 0x0A09C884, /* %XmpPath% :: Wrong value type. Expected type '%type%'. */
    XMP_W_SVAL_TYPENDEF                 = 0x0A09C885, /* The value type '%type%' used in an XMP extension schema is undefined. */
    XMP_W_SVAL_FDNDEF                   = 0x0A09C886, /* %XmpPath% :: The field '%field%' is not defined in value type '%type%'. */
    XMP_I_SVAL_PREFIX                   = 0x0A094887, /* Unusual prefix '%prefix%:' for namespace '%ns%'. The preferred prefix is '%pref%:'. */
    XMP_W_SVAL_PREFIX                   = 0x0A09C887, /* Wrong prefix '%prefix%:' for namespace '%ns%'. The required prefix is '%req%:'. */
    XMP_W_SVAL_FDNS                     = 0x0A09C888, /* Wrong namespace '%found%' for value type '%type%'. The required namespace is '%exp%'. */
    XMP_W_SVAL_FDMISS                   = 0x0A09C889, /* %XmpPath% :: Missing required field '%field%' in value type '%type%'. */
    XMP_W_SVAL_NOLANG                   = 0x0A09C88A, /* %XmpPath% :: Missing language qualifier. */
    XMP_I_SREP_RM                       = 0x0A0948C1, /* %XmpPath% :: Value removed. */
    XMP_W_SREP_RM                       = 0x0A09C8C1, /* %XmpPath% :: Value removed. */
    XMP_I_SREP_MVPROP                   = 0x0A0948C2, /* %XmpPath% :: Property renamed to '%prefix%:%name%'. */
    XMP_I_SREP_CHPREFIX                 = 0x0A0948C3, /* Changed prefix for schema '%schema%' from '%oldprefix%' to '%newprefix%'. */
    XMP_I_SREP_CHNS                     = 0x0A0948C4, /* %XmpPath% :: Changed field namespace from '%oldns%' to '%newns%'. */
    XMP_I_SREP_CHARR                    = 0x0A0948C5, /* %XmpPath% :: Changed array type from '%oldarr%' to '%newarr%'. */
    XMP_I_SREP_GENARR                   = 0x0A0948C6, /* %XmpPath% :: Wrapped value in array of type '%arrtype%'. */
    XMP_I_SREP_LANG                     = 0x0A0948C7, /* %XmpPath% :: Added xml:lang qualifier. */
    XMP_I_SREP_CHVAL                    = 0x0A0948C8, /* %XmpPath% :: Changed literal value from '%oldvalue%' to '%newvalue%'. */
    XMP_I_SREP_CHORD                    = 0x0A0948C9, /* %XmpPath% :: Reordered array elements. */
    XMP_W_COMP_TYPE                     = 0x0A09CC01, /* %XmpPath% :: Nodes have different types: '%type1%' vs. '%type2%'. */
    XMP_W_COMP_LITCONT                  = 0x0A09CC02, /* %XmpPath% :: Literals have different content: '%content1%' vs. '%content2%'. */
    XMP_W_COMP_LITLANG                  = 0x0A09CC03, /* %XmpPath% :: Literals have different language: '%language1%' vs. '%language2%'. */
    XMP_W_COMP_ARRSIZE                  = 0x0A09CC04, /* %XmpPath% :: Arrays have different size: '%size1%' vs. '%size2%'. */
    XMP_W_COMP_QUALIFIER                = 0x0A09CC05, /* %XmpPath% :: Node %lr% missing qualifier '%prefix%:%name%'. */
    XMP_W_COMP_PROPERTY                 = 0x0A09CC06, /* Metadata %lr% missing property '%prefix%:%name%'. */
    XMP_W_COMP_PREFIX                   = 0x0A09CC07, /* %XmpPath% :: Properties/fields have different prefix: '%prefix1%' vs. '%prefix2%'. */
    XMP_W_COMP_METAURI                  = 0x0A09CC08, /* Metadata objects have different URIs: '%uri1%' vs. '%uri2%'. */
    XMP_W_COMP_FIELD                    = 0x0A09CC09, /* %XmpPath% :: Structure %lr% missing field '%prefix%:%name%'. */
    XMP_W_COMP_FIELDNS                  = 0x0A09CC0A, /* %XmpPath% :: Structures have different field namespaces: '%ns1%' vs. '%ns2%'. */

/** CCITT Fax compression module */
    TIFF_E_ENTRYTYPE                    = 0x8A0D0000, /* Invalid entry type in TIFF dictionary. */

/** OCR module */
    PDF_E_OCRENGINE                     = 0x8A0E0001, /* OCR engine error: '%msg%'. */
    PDF_E_OCR                           = 0x8A0E0002, /* OCR error: '%msg%'. */
    PDF_W_OCRCREDITS                    = 0x0A0E8003, /* OCR credits low: %count% remaining. */

/** Flate compression module */
    FLATE_ERROR_0                       = 0x8A110001, /* Error in Flate stream: stream end. */
    FLATE_ERROR_1                       = 0x8A110002, /* Error in Flate stream: need dictionary. */
    FLATE_ERROR_2                       = 0x8A117FFA, /* Error in Flate stream: incompatible version. */
    FLATE_ERROR_3                       = 0x8A117FFB, /* Error in Flate stream: buffer error. */
    FLATE_ERROR_4                       = 0x8A117FFC, /* Error in Flate stream: insufficient memory. */
    FLATE_WARNING_6                     = 0x0A11FFFD, /* Error in Flate stream: data error. */
    FLATE_ERROR_5                       = 0x8A117FFD, /* Error in Flate stream: data error. */
    FLATE_ERROR_7                       = 0x8A117FFE, /* Error in Flate stream: stream error. */
    FLATE_ERROR_8                       = 0x8A117FFF, /* Error in Flate stream: file error. */

/** License management module */
    LIC_E_NOTINIT                       = 0x8A120001, /* License management is not initialized. */
    LIC_E_NOTSET                        = 0x8A120002, /* No license set. */
    LIC_E_NOTFOUND                      = 0x8A120003, /* License not found. */
    LIC_E_FORMAT                        = 0x8A120004, /* Invalid license format. */
    LIC_E_MANIP                         = 0x8A120005, /* License was manipulated. */
    LIC_E_VERSION                       = 0x8A120006, /* Unsupported license format version. */
    LIC_E_PRODUCT                       = 0x8A120007, /* The license does not match the product. */
    LIC_E_PLATFORM                      = 0x8A120008, /* The license does not match the current platform. */
    LIC_E_TYPE                          = 0x8A120009, /* The license type does not match. */
    LIC_E_EXPIRED                       = 0x8A12000A, /* The license has expired. */
    LIC_E_MEXPIRED                      = 0x8A12000B, /* The maintainance period has expired. Use an older version of the product. */
    LIC_E_LEVEL                         = 0x8A12000C, /* The current license does not permit the use of this function. */
    LIC_E_STORE_TYPE                    = 0x8A120101, /* Incorrect type in license store. */
    LIC_E_STORE_NAME                    = 0x8A120102, /* Could not determine product name from key. */

/** Signature module */
    SIG_CREA_E_SESSION                  = 0x8A130101, /* Cannot create a session: %msg% */
    SIG_CREA_E_STORE                    = 0x8A130102, /* Cannot open certificate store. */
    SIG_CREA_E_CERT                     = 0x8A130103, /* Certificate not found in store. */
    SIG_CREA_E_OCSP                     = 0x8A130104, /* Couldn't get response from OCSP server. */
    SIG_CREA_E_TSP                      = 0x8A130105, /* Couldn't get response from Time-stamp server. */
    SIG_CREA_E_PRIVKEY                  = 0x8A130106, /* Private key not available. */
    SIG_CREA_E_SERVER                   = 0x8A130107, /* Server error: %msg% */
    SIG_CREA_E_CRL                      = 0x8A130108, /* Couldn't get response from CRL server. */
    SIG_CREA_E_IDENTITY                 = 0x8A130109, /* Invalid identity. */
    SIG_CREA_E_PERMISSION               = 0x8A13010A, /* Permission denied. */
    SIG_CREA_E_INVCERT                  = 0x8A13010B, /* Invalid certificate: %msg% */
    SIG_CREA_E_ALGO                     = 0x8A1301FE, /* Unsupported algorithm found. */
    SIG_CREA_E_FAILURE                  = 0x8A1301FF, /* Program failure occurred. */
    SIG_VAL_E_CMS                       = 0x8A130201, /* Malformed cryptographic message syntax (CMS). */
    SIG_VAL_E_DIGEST                    = 0x8A130202, /* Digest mismatch (document has been modified). */
    SIG_VAL_E_SIGNERCERT                = 0x8A130203, /* Signer's certificate is missing. */
    SIG_VAL_E_SIGNATURE                 = 0x8A130204, /* Signature is not valid. */
    SIG_VAL_W_ISSUERCERT                = 0x0A138205, /* None of the certificates was found in the store. */
    SIG_VAL_W_NOTRUSTCHAIN              = 0x0A138206, /* The trust chain is not embedded. */
    SIG_VAL_W_TSP                       = 0x0A138207, /* The Time-stamp is invalid. */
    SIG_VAL_W_TSPCERT                   = 0x0A138208, /* The Time-stamp certificate was not found in the store. */
    SIG_VAL_W_NOTSP                     = 0x0A138209, /* The Time-stamp is not present. */
    SIG_VAL_W_PADES                     = 0x0A13820A, /* The signature does not conform to the PAdES standard. */
    SIG_VAL_E_ALGO                      = 0x8A1302FE, /* Unsupported algorithm found. */
    SIG_VAL_E_FAILURE                   = 0x8A1302FF, /* Program failure occurred. */

/** PDF split and merge module */
    PDF_SPLMRG_W_DOCSIGNED              = 0x0A148001, /* Document is signed. */
    PDF_SPLMRG_W_RMXFA                  = 0x0A148002, /* XFA stream was not copied. */
    PDF_SPLMRG_W_RMSUBMIT               = 0x0A148003, /* SubmitForm action was not copied. */
    PDF_SPLMRG_W_PARTSUBMIT             = 0x0A148004, /* Partial SubmitForm action altered to submit all fields. */
    PDF_SPLMRG_W_RMSIGANNOT             = 0x0A148005, /* Signature annotation was not copied. */
    PDF_SPLMRG_W_RMVALUE                = 0x0A148006, /* Value or default value of field "%field%" was discarded due to field name collision. */
    PDF_SPLMRG_W_MVFIELD                = 0x8A140007, /* Renamed field "%field%" to "%newname%" due to field name collision. */
    PDF_SPLMRG_E_ANNOTAPPEAR            = 0x8A140008, /* Cannot create appearance for annotation. */

/** XFA module */
    XFA_W_CHILDROOT                     = 0x0A158001, /* The element '%qname%' must contain one single child element. */
    XFA_E_MISSELEM                      = 0x8A150002, /* %XPath% :: Missing child element '%name%' in namespace '%ns%'. */
    XFA_E_FEATNSUP                      = 0x8A150003, /* The feature '%name%' is not implemented. */
    XFA_W_INVVAL                        = 0x0A158004, /* Invalid value '%value%' required is '%reqval%'. */
    XFA_E_INVSHORTCUT                   = 0x8A150005, /* Invalid shortcut '%shortcut%' in SOM expression. */
    XFA_E_INVSOM                        = 0x8A150006, /* Invalid SOM expression '%expression%'. */
    XFA_E_SNSOM                         = 0x8A150007, /* SOM expression '%expression%' does not yield a single node. */

/** PDF Viewer */
    VIEWER_E_SAVE_FILE                  = 0x8A160001, /* Failed to save file %s. */

/** Image to PDF converter module */
    PDF_I2P_W_OUTPUTINTENT              = 0x0A178001, /* Created CMYK color profile for PDF/A output intent. */
    PDF_I2P_W_SMASK                     = 0x0A178002, /* Image soft mask removed in order to meet PDF/A-1 conformance. */
    PDF_I2P_W_JPXDECODE                 = 0x0A178003, /* JPEG2000 compression changed to JPEG in order to meet PDF/A-1 conformance. */
    PDF_I2P_E_COMPRESSION               = 0x0A178004, /* Invalid choice of compression. */

/** PDF Compare module */
    PDF_COMP_W_TYPE                     = 0x0A188001, /* Object %objNum1% <-> %objNum2%: Different object type:  %context1% %type1% <-> %context2% %type2%. */
    PDF_COMP_W_VALUE                    = 0x0A188002, /* Object %objNum1% <-> %objNum2%: Different %context% %type% value: %value1% <-> %value2% in %pcontext% %ptype%. */
    PDF_COMP_W_KEY                      = 0x0A188003, /* Object %objNum1% <-> %objNum2%: Different %context% dictionary: %lr% missing key %name%. */
    PDF_COMP_W_ARRSIZE                  = 0x0A188004, /* Object %objNum1% <-> %objNum2%: Different %context% array size: %size1% <-> %size2%. */
    PDF_COMP_W_STREAM                   = 0x0A188005, /* Object %objNum1% <-> %objNum2%: Different %enc% %context% stream. */
    PDF_COMP_W_REF                      = 0x0A188007, /* Object %objNum1% <-> %objNum2% (%objNum2alt%): Different %context% references in %pcontext% %ptype%. */
    PDF_COMP_M_START                    = 0x0A180008, /* Comparing file %s with %s.\n */
    PDF_COMP_M_STOP                     = 0x0A180009, /* File compare terminated.\n */
    PDF_COMP_M_STARTPAGE                = 0x0A18000A, /* Comparing page %d.\n */
    PDF_COMP_M_STARTOBJ                 = 0x0A18000B, /* Comparing object %d with %d.\n */
    PDF_COMP_M_STARTEMB                 = 0x0A18000C, /* Comparing embedded files %s.\n */
    PDF_COMP_W_CONTEXT                  = 0x0A18800D, /* Object %objNum1% <-> %objNum2%: Different object context: %context1% %type1% <-> %context2% %type2%. */
    PDF_COMP_W_COMPLIANCE               = 0x0A18800E, /* Different PDF version: %v1% <-> %v2%. */
    PDF_COMP_W_REF2                     = 0x0A18800F, /* Object %objNum1% (%objNum1alt%) <-> %objNum2%: Different %context% references in %pcontext% %ptype%. */
    PDF_COMP_W_LINEARIZED               = 0x0A188010, /* Different linearization: %l1% <-> %l2%. */
    PDF_COMP_W_FILESIZE                 = 0x0A188011, /* Different file size: %n1% <-> %n2% (%dn% bytes, %dp%%). */

/** PDF Stamp module */
    PDF_STMP_E_PSXML                    = 0x8A190001, /* Invalid stamp xml data. */
    PDF_STMP_E_PSSTAMP                  = 0x8A190002, /* Invalid stamp description in ps:stamp. */
    PDF_STMP_E_PSOP                     = 0x8A190003, /* Invalid stamp content operator. */
    PDF_STMP_E_PS                       = 0x8A190004, /* Stamping error: %msg% */

/** PDF Creator module */
    PDF_CREATOR_E_FONTPROG              = 0x8A1A0001, /* The font program cannot be read. */
    PDF_CREATOR_E_FONTNOTFOUND          = 0x8A1A0002, /* The font cannot be found. */

/** PDF optimizer module */
    PDF_OPT_E_ANNOTAPPEAR               = 0x8A1B0001, /* Cannot create appearance for annotation. */
    PDF_OPT_W_RMSIGANNOT                = 0x0A1B8002  /* Signature annotation was not copied. */

} TPDFErrorCode;

typedef enum TPDFErrorCode TBSEErrorCode;


#endif /* _BSEERROR_INCLUDED */
