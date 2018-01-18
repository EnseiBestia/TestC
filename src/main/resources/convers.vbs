Option Explicit

'//on error stop script,do not prompt
'On Error resume next
'WScript.Quit

Dim xlApp,xlBook
Set xlApp = CreateObject("Excel.Application")

dim fso: set fso = CreateObject("Scripting.FileSystemObject")

dim strFile
Set strFile = fso.GetFile(WScript.ScriptFullName)

Set xlBook = xlApp.Workbooks.Open(strFile.ParentFolder  & "\html2xlsx.xlsm")

Dim args
Set args = Wscript.Arguments

xlApp.Run "Open_HTML_Save_XLSX",args.Item(0),args.Item(1)
xlBook.Close
xlApp.Quit

Set xlBook = Nothing
Set xlApp = Nothing
'WScript.Echo "Finished."
WScript.Quit