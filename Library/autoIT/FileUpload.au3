Sleep(2000);

If(WinExists("Open")) Then
	Sleep(2000);
	ControlSetText("Open", "", "Edit1", $CmdLine[1]);
	ControlClick("Open", "&Open", "Button1");
	Sleep(2000);
EndIf