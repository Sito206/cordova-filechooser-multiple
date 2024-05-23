Proyecto original : https://github.com/ihadeed/cordova-filechooser/

# Cordova FileChooser Plugin

Requires Cordova >= 2.8.0

## Install with Cordova CLI
	$ cordova plugin add http://github.com/ihadeed/cordova-filechooser.git

## Install with Plugman
	$ plugman --platform android --project /path/to/project \ 
		--plugin https://github.com/Sito206/cordova-filechooser-multiple.git

## API

```javascript
fileChooser.open(filter, successCallback, failureCallback); // with mime filter

fileChooser.open(successCallback. failureCallback); // without mime filter
```

### Filter (Optional)

```javascript
{ "mime": "application/pdf" }  // text/plain, image/png, image/jpeg, audio/wav etc
```

Successful callback gets the uri of the selected files

```javascript
fileChooser.open(function(uri[]) {
  alert(uri);
});
```

## Screenshot

![Screenshot](filechooser.png "Screenshot")

## Supported Platforms

- Android
- Windows (UWP)

TODO rename `open` to pick, select, or choose.
