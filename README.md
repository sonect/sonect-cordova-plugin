# sonect-cordova-plugin
This is the repo for Cordova SDK

## Installation
To integrate the plugin: 
- clone repo to `YOUR_DIRECTORY`
- `cordova plugin add YOUR_DIRECTORY`

## Integration
To initialize and present the plugin add the following to your .js codebase. 
Additional documentation regarding credentials, themeing and 
payment methods is available [here](https://github.com/sonect/sonect-sdk-ios)

Check out the [Sample App](https://github.com/sonect/sonect-cordova-testapp) for an in depth implementation example. 

```
openSonect: function() {
    let credentials = {
        token: "YOUR_SDK_TOKEN",
        userId: "YOUR_USER_ID",
        signature: "YOUR_SIGNATURE"
    };

    let theme = {
        "type": "light",
        "detailColor1": "048b54",
        "detailColor2": "048b54",
        "detailColor3": "048b54",
        "detailColor4": "048b54",
        "detailColor5": "048b54",
        "navigationBarTintColor": "048b54",
        "navigationBarTitleImage": "Bank"
    };

    // Configure an array of payment methods with the metadata to be displayed in Withdraw screen
    let paymentMethods = [
        {
        uniqueIdentifier: "IBAN_1",
        name: "My Bank",
        detailDescription: "Balance: 20CHF",
        image: "Bank"
        },
    ];

    // Present the sonect SDK with the credentials obtained from your server, payment methods and a theme
    sonect.present(credentials, paymentMethods, theme,
        // When the user selects a payment method, this callback is asked if that payment method
        // has enough balance to process the transaction. If this is less than the picked value
	// then the Confirm button with be greyed out. uniqueIdentifier is the identifier of the payment
        // method that you passed when configuring the payment methods. 
        function(uniqueIdentifier, balanceCallback) {
     
            let balance = {
                uniqueIdentifier: uniqueIdentifier,
                value: "100.00",
                currency: "CHF"
            };
            balanceCallback(balance);
        },
	// This callback is called when user pressed Confirm in the Withdraw screen. 
	// It will pass back the uniqueIdentifier of the payment method, the value and the currency
 	// of the requested amount to be withdrawn. 
        function(uniqueIdentifier, value, currency, paymentCallback) {
            // 1. 
            // To process payment without leaving the SDK, follow this code path. 
	    // This will immediately present the Barcode screen. 
	    // You can also call the paymentCallback asynchronously.
            // let paymentReference = {
            //     uniqueIdentifier: uniqueIdentifier,
            //     paymentReference: "PAYMENT_REFERENCE"
            // };
            // paymentCallback(paymentReference);

            //2. 
	    //Alternatively, hide the SDK, and call sonect.pay when you have obtained the payment reference
	    //by processing it in your own UI. 
            sonect.hide(null, null);
            app.paymentUniqueIdentifier = uniqueIdentifier;
        },
        function(msg) {
        },
        function(err) {
        }
    )
},

paySonect: function() {  
    // The payment reference object needs to contain the uniqueIdentifier for the payment method that
    // is supposed to process the transaction. Also you should pass the paymentReference obtained  
    // from your server. 
    let paymentReference = {
        uniqueIdentifier: app.paymentUniqueIdentifier,
        paymentReference: "YOUR_PAYMENT_REFERENCE"
    };
  
    // This method will present the Sonect SDK in the Barcode screen. 
    sonect.presentTransaction(paymentReference, 
        function(msg) {
        },
        function(err) {
        }
    );
}
```

## Platform specific configuration
### iOS
To configure the iOS SDK do the following steps: 
- `open YOUR_CORDOVA_APP/platforms/ios/YOUR_PROJECT.workspace` in Xcode
- in Xcode, right click on the Resources folder and pick `New File...` from the menu
- pick `Property List`, and name it `SonectConfiguration`
- enter the values as following
```
<dict>
	<key>SonectAlpha2CountryCode</key>
	<string>ch</string>
	<key>SonectCurrency</key>
	<string>CHF</string>
	<key>SonectDefaultWithdrawAmountIndex</key>
	<integer>2</integer>
	<key>SonectAllowedCountryCodes</key>
	<array>
		<integer>41</integer>
	</array>
	<key>SonectEnvironment</key>
	<string>TEST</string>
</dict>
```
- to change to `PROD` environment, remove the `SonectEnvironment` key. 
