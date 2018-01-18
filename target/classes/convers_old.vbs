Option Explicit
Dim xlApp,xlBook
Set xlApp = CreateObject("Excel.Application")

dim fso: set fso = CreateObject("Scripting.FileSystemObject")
dim CurrentDirectory
CurrentDirectory = fso.GetAbsolutePathName(".")

'WScript.Echo CurrentDirectory

Set xlBook = xlApp.Workbooks.Open(CurrentDirectory & "\src\main\resources\html2xlsx.xlsm")
Dim args
Set args = Wscript.Arguments

xlApp.Run "Open_HTML_Save_XLSX",args.Item(0),args.Item(1)
xlBook.Close
xlApp.Quit

Set xlBook = Nothing
Set xlApp = Nothing
'WScript.Echo "Finished."
WScript.Quit