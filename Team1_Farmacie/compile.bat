start ant
timeout 5
start xcopy /y/i %CD% bin
cd bin
start jar cf Farmacie.jar *
::start jar cvf Farmacie.jar *.class
::move Farmacie.jar C:\Users\gantu\git\Pharmacy\Team1_Farmacie
::for /r %i in ("Team1_Farmacie\*.*") do move %i %~pi..


