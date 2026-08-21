# Geez Number Converter Word Ad-in

A word add-in to convert arabic numeral into geez numbers. 

## Android app

The publishable, dependency-free Android version lives in [`android-app/`](android-app/README.md).
It is an independent implementation of the W3C Ethiopic numeric algorithm; it does not reuse the
Word add-in conversion source.

# Usage 
install node.js and related materials, follow [this](https://phoenixnap.com/kb/install-node-js-npm-on-windows) tutorial. 

Run this command on cmd after navigating to the project directory, to run the add-in locally.
```
npm run dev-server
```
Open word and Navigate to ```Insert```-->```My Ad-ins```-->```My Office Ad-in``` 

Again on Word, on ```Home``` tab, click on the ```My Office Ad-in``` to open a taskpane to the right of the editor.

To convert a number, select the number in the word file, then click ```Run``` from the taskpane.
