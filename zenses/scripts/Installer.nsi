;NSIS Modern User Interface
;Basic Example Script
;Written by Joost Verburg

;--------------------------------
;Include Modern UI

  !include "MUI2.nsh"

;--------------------------------
;General

  ;Name and file
  Name "Zenses2 Beta Installer"
  OutFile "zenses2.0.5b2-installer.exe"

  ;Default installation folder
  InstallDir "$PROGRAMFILES\Zenses2"
  
  ;Get installation folder from registry if available
  InstallDirRegKey HKCU "Software\Zenses2" ""

  ;Request application privileges for Windows Vista
  RequestExecutionLevel admin

;--------------------------------
;Variables

  Var StartMenuFolder

;--------------------------------
;Interface Settings

  !define MUI_ABORTWARNING

;--------------------------------
;Pages

  !insertmacro MUI_PAGE_LICENSE "License.txt"
  !insertmacro MUI_PAGE_COMPONENTS
  !insertmacro MUI_PAGE_DIRECTORY

  ;Start Menu Folder Page Configuration
  !define MUI_STARTMENUPAGE_REGISTRY_ROOT "HKCU" 
  !define MUI_STARTMENUPAGE_REGISTRY_KEY "Software\Zenses2" 
  !define MUI_STARTMENUPAGE_REGISTRY_VALUENAME "Zenses2"
  
  ;!insertmacro MUI_PAGE_STARTMENU Application $StartMenuFolder

  !insertmacro MUI_PAGE_INSTFILES
  
  !insertmacro MUI_UNPAGE_CONFIRM
  !insertmacro MUI_UNPAGE_INSTFILES

    # These indented statements modify settings for MUI_PAGE_FINISH
    !define MUI_FINISHPAGE_NOAUTOCLOSE
    !define MUI_FINISHPAGE_RUN
    !define MUI_FINISHPAGE_RUN_NOTCHECKED
    !define MUI_FINISHPAGE_RUN_TEXT "Launch Zenses2 Beta2"
    !define MUI_FINISHPAGE_RUN_FUNCTION "LaunchLink"
    !define MUI_FINISHPAGE_SHOWREADME_NOTCHECKED
    !define MUI_FINISHPAGE_SHOWREADME $INSTDIR\Readme.txt

  !insertmacro MUI_PAGE_FINISH

  
;--------------------------------
;Languages
 
  !insertmacro MUI_LANGUAGE "English"

Function LaunchLink
  ExecShell "" "$SMPROGRAMS\Zenses2 Beta2.lnk"
FunctionEnd

;--------------------------------
;Installer Sections

Section "Zenses2 Beta2" SecDummy

  SetOutPath "$INSTDIR"
  
  file "Zenses2.exe"
  file "zenses2.jar"
  file "License.txt"
  file "Readme.txt"

  ${If} ${FileExists} `$INSTDIR\Zenses2 Beta1.exe`
	Delete "$INSTDIR\Zenses2 Beta1.exe"
	Delete "$INSTDIR\Zenses2 Beta1 Debug.exe"
  ${EndIf}

  ${If} ${FileExists} `$INSTDIR\data\zenses.data.script`
	; move the data
	CreateDirectory "$APPDATA\zenses"
	CreateDirectory "$APPDATA\zenses\data"

	Rename "$INSTDIR\data\zenses.data.script" "$APPDATA\zenses\data\zenses.data.script"
	Rename "$INSTDIR\data\zenses.data.properties" "$APPDATA\zenses\data\zenses.data.properties"
  ${Else}
	${If} ${FileExists} `$APPDATA\zenses\data\zenses.data.script`
	${Else}
    	SetOutPath "$APPDATA\zenses\data"
    	file "data\zenses.data.script"
	${EndIf}
  ${EndIf}

  SetOutPath "$INSTDIR\lib"

  file "lib\jmtp.dll"

  SetOutPath "$INSTDIR"
  
  ;Store installation folder
  WriteRegStr HKCU "Software\Zenses2" "" $INSTDIR

  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Zenses2" "DisplayName" "Zenses2 Beta2"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Zenses2" "Publisher" "Sixones"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Zenses2" "DisplayVersion" "2.0.3b2"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Zenses2" "UninstallString" "$\"$INSTDIR\Uninstall.exe$\""
				
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Zenses2" "QuietUninstallString" "$\"$INSTDIR\Uninstall.exe$\" /S"
  
  ;Create uninstaller
  WriteUninstaller "$INSTDIR\Uninstall.exe"

	;Remove old shortcuts
	Delete "$SMPROGRAMS\Zenses2 Beta1\Zenses2 Beta1.lnk"
	Delete "$SMPROGRAMS\Zenses2 Beta1\Zenses2 Beta1 (Debug).lnk"
	Delete "$SMPROGRAMS\Zenses2 Beta1\Zenses2 Beta1 Uninstall.lnk"

	CreateShortCut "$SMPROGRAMS\Zenses2 Beta2.lnk" "$INSTDIR\Zenses2.exe"

	;CreateDirectory "$SMPROGRAMS\$StartMenuFolder"
    ;CreateShortCut "$SMPROGRAMS\$StartMenuFolder\Zenses2 Beta2.lnk" "$INSTDIR\Zenses2.exe"
	;CreateShortCut "$SMPROGRAMS\$StartMenuFolder\Zenses2 Uninstaller.lnk" "$INSTDIR\Uninstall.exe"
SectionEnd

;--------------------------------
;Uninstaller Section

Section "Uninstall"

  Delete "$INSTDIR\Zenses2.exe"
  Delete "$INSTDIR\Zenses2 Beta1.exe"
  Delete "$INSTDIR\Zenses2 Beta1 Debug.exe"
  Delete "$INSTDIR\zenses2.jar"
  Delete "$INSTDIR\data\zenses.data.properties"
  Delete "$INSTDIR\data\zenses.data.script"
  Delete "$INSTDIR\lib\jmtp.dll"
  Delete "$APPDATA\zenses\data\zenses.data.properties"
  Delete "$APPDATA\zenses\data\zenses.data.script"
  Delete "$INSTDIR\License.txt"
  Delete "$INSTDIR\Readme.txt"

  RMDir "$INSTDIR\data"

  DeleteRegKey HKEY_CURRENT_USER "Software\JavaSoft\Prefs\org\zenses\session_token"
  DeleteRegKey HKEY_CURRENT_USER "Software\JavaSoft\Prefs\org\zenses\interval_between_scrobbles"
  DeleteRegKey HKEY_CURRENT_USER "Software\JavaSoft\Prefs\org\zenses\date_format"
  DeleteRegKey HKEY_CURRENT_USER "Software\JavaSoft\Prefs\org\zenses"

  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Zenses2"

  Delete "$INSTDIR\Uninstall.exe"

  !insertmacro MUI_STARTMENU_GETFOLDER Application $StartMenuFolder
    
  Delete "$SMPROGRAMS\$StartMenuFolder\Zenses2 Beta1.lnk"
  Delete "$SMPROGRAMS\$StartMenuFolder\Zenses2 Beta1 (Debug).lnk"
  Delete "$SMPROGRAMS\$StartMenuFolder\Zenses2 Beta1 Uninstall.lnk"
  Delete "$SMPROGRAMS\$StartMenuFolder"

  Delete "$SMPROGRAMS\Zenses2 Beta2.lnk"

  RMDir "$SMPROGRAMS\$StartMenuFolder"

  RMDir "$INSTDIR"

  DeleteRegKey /ifempty HKCU "Software\Zenses2"

SectionEnd