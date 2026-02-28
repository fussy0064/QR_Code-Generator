QR MASTER — Mobile Application Project Documentation

Course: Mobile Computing  
Project Title:QR Master — QR Code Generator  
Platform: Android (Kotlin + WebView)  
Date:February 2026  


Problem Statement

The Real-World Problem

In Tanzanian communities and across East Africa, there is a growing need for quick, reliable information sharing in everyday life. People frequently need to:

Share phone numbers; Manually typing long phone numbers (e.g., +255759050666) leads to frequent errors, missed calls, and lost business contacts.

Share Wi-Fi passwords; Homes, offices, cafes, and hotels struggle to share complex Wi-Fi passwords with guests. Customers often misspell passwords, consume staff time, and get frustrated.

Share website links; Small businesses, event organizers, and content creators need to direct people to websites, but long URLs are impossible to communicate verbally.

Share payment details (Lipa Namba),Mobile money is the primary payment method in Tanzania (M-Pesa, Airtel Money, etc.). Shopkeepers print their till numbers on paper, which fades, tears, and leads to wrong-number payments.

Share text/messages; Confidential information like passwords, codes, or short messages need a secure way to be shared without typing.


QR codes solve all these problems by encoding information into a scannable image, but existing QR code generators require internet access, are loaded with ads, and do not support local payment systems like Lipa Namba.

Our Solution

QR Master is a lightweight, offline-capable Android application that generates QR codes for five essential use cases: Phone Numbers, URLs, Wi-Fi Passwords, Text/Passwords, and Lipa Namba (mobile money payment numbers). The app works entirely offline, has no ads, and is tailored to the Tanzanian community.


Why a Mobile Solution is Appropriate


Reason 	 Explanation
1.  Ubiquity	    • Over 85% of Tanzanians own a mobile phone. It is the most accessible computing device in the community.
      
2.  Portability	    • QR codes are needed "on the go" at shops, events, homes, and offices. A mobile app is always in the user's pocket.
      
3. Camera Integration	    • Mobile phones have built-in cameras that can scan QR codes instantly, creating a complete generation-and-scanning ecosystem
      
4. Offline Capability	    • Internet access is unreliable in rural Tanzania. QR Master works 100% offline once installed no server connection needed.
      
 5.  Sharing	    • Mobile phones have native sharing features (WhatsApp, Bluetooth, email) enabling instant distribution of generated QR codes.
      
6.  Cost Effective	    • The app is free, lightweight (2.6 MB), and runs on Android 7.0+ (API 24), covering even low-end devices
      
7.  Native Feel	    • The app uses a WebView wrapper, providing smooth performance and native back-button navigation.
      












3.1 Use Case Diagram


3.4 Validation Process
When a user taps "Generate," the system first clears any lingering error messages from previous attempts so the UI is fresh. Then, it checks which type of data the user is trying to process and routes them through specific validation rules.
Validation Rules by Type
    • Phone: The system checks if a number was entered. (Fails: "Phone number is required")
        ◦ If entered, it runs the input against a regular expression  to confirm it is a properly formatted phone number. If the number is invalid alert! (Fails: "Enter a valid phone number")
        ◦ If it passes, it proceeds to generate.
    • URL: It checks if an entry exists. (Fails: "URL is required")
        ◦ If a link is provided but is missing the https:// prefix, the system automatically appends it.
        ◦ It then verifies that the final string is a valid web address format. (Fails: "Enter a valid URL")
        ◦ If valid, it proceeds.
    • WiFi: This is a simpler check. It only verifies that an SSID (the network name) has been provided. If the SSID is empty alert! (Fails: "Network name required")
        ◦ If present, it proceeds. (Note: The logic here assumes open networks or handles passwords in a step not shown in this specific flow).
    • Text: It confirms content was entered. If content is empty alert (Fails: "Content is required")
        ◦ It enforces a hard character limit of 1 to 2,953 characters the maximum alphanumeric capacity for a standard QR code. (Fails: "Exceeds character limit")
        ◦ If within the limit, it proceeds.
    • Lipa (Mobile Payments): This requires two pieces of valid information. First, it checks for a Till number. (Fails: "Till number required")
        ◦ It verifies the Till number is between 4 and 10 digits long. (Fails: "Invalid till number")
        ◦ If the Till number is valid, it checks that a Business name was also provided. (Fails: "Business name required")
        ◦ If both are present and valid, it proceeds.
If any of these paths hit a "No" condition along the way, the process stops and displays the corresponding error message to the user. If all checks for the selected type pass, the system successfully reaches the final "Proceed to generate" state.

4. User Interface Design and Navigation

4.1 Screen Architecture

The app consists of three interactive screens with a smooth slide-in/slide-out navigation pattern:
This flowchart outlines the fundamental user journey and screen layout for your QR app. It represents a linear, three-step process.


Here is a breakdown of how the user navigates through the app:
The Forward Journey
    • Screen 1 (Home Screen): This is the starting point when the user opens the app. They are presented with a menu to choose the type of QR code they want to create (e.g., URL, Wi-Fi, Text, Lipa).
    • Screen 2 (Input Screen): Once the user selects a category, the app transitions here. This is where the user enters their data, and it is exactly where the input validation logic from your previous flowchart takes place.
    • Screen 3 (Result Screen): After the input passes validation and the user taps "Generate," they arrive at this final screen. The app takes the validated data and displays the freshly created QR code.
The Return Journey
The |Back| arrows show how the user can reverse their steps.
    • From the Result Screen, they can go back to the Input Screen to tweak their data or fix a typo.
    • From the Input Screen, they can go back to the Home Screen to select a completely different type of QR code.








4.2 Screen 1 — Home Screen


















  
  




























 5. Application Features and Functionality

5.1 Feature Summary

Feature | Description |

| 1 | Phone QR| Generates `tel:` URI — scanning auto-dials the number |
| 2 | URL QR |Generates URL — scanning opens browser automatically |
| 3 | WiFi QR | Generates standard `WIFI:` format — scanning triggers "Connect to WiFi" prompt on Android/iOS |
| 4 | Text QR | Encodes plain text up to 2,953 characters with optional "Secret Mode" |
| 5 | Lipa Namba QR | Encodes mobile money payment details (M-Pesa, Airtel, etc.) with till number, business name, amount |
| 6 | Save QR  | Downloads QR code as PNG image with white padding border |
| 7 | Share QR | Uses Android's native Share API to send QR via WhatsApp, email, Bluetooth, etc. |
| 8 | Back Navigation | Android hardware back button navigates through screens correctly |
| 9 | Offline Mode | App works 100% without internet via Service Worker caching |
| 10 | Input Validation| Every field has format-specific validation with clear error messages |


7. Technical Implementation

7.1 Technology Stack

| Layer | Technology | Purpose |

Platform Android (API 24+) | Target platform |
| **Language** | Kotlin | Android native code |
| **Build System** | Gradle 8.13 + AGP 8.13.2 | Build automation |
| **UI Engine** | HTML5 + CSS3 + JavaScript | App interface (loaded in WebView) |
| **QR Library** | QRCode.js (inlined) | QR code generation |
| **Wrapper** | Android WebView | Native wrapper displaying the web app |
| **IDE** | Android Studio | Development environment |

 7.2 Project Structure

QR_Master/
├── app/
│   ├── build.gradle.kts          # App-level build config
│   ├── src/main/
│   │   ├── AndroidManifest.xml    # App permissions & config
│   │   ├── java/com/fussy/qr_master/
│   │   │   ├── MainActivity.kt   # WebView wrapper (105 lines)
│   │   │   └── ui/theme/         # Theme files (Color, Theme, Type)
│   │   ├── assets/www/
│   │   │   ├── index.html         # Complete self-contained app (977 lines)
│   │   │   ├── manifest.json      # PWA manifest
│   │   │   └── sw.js              # Service worker for offline caching
│   │   └── res/                   # Android resources (icons, themes, strings)
├── build.gradle.kts               # Root build file
├── gradle/
│   ├── libs.versions.toml         # Version catalog
│   └── wrapper/gradle-wrapper.properties
├── settings.gradle.kts
└── gradle.properties


7.3 Key Code Components

**MainActivity.kt** — Configures the Android WebView:
- Sets status bar and navigation bar colors to match the app theme
- Enables JavaScript, DOM storage, and file access
- Handles Android hardware back button navigation
- Loads `index.html` from local assets

**index.html** — The complete self-contained application:
- 653 lines of inline CSS for the dark purple premium UI
- HTML structure for all 3 screens with dynamic field visibility
- QRCode.js library inlined for offline QR generation
- 110 lines of application logic (navigation, validation, generation, save/share)

8. Testing and Quality Assurance

9. Challenges Faced and Solutions

Challenge 1: Kotlin Version Incompatibility
Problem:
The project was initially configured with Kotlin 1.9.20, which is incompatible with Android Gradle Plugin (AGP) 8.13.2. The build failed with cryptic error messages about unresolved references.

Solution: Updated Kotlin version from 1.9.20 to 2.3.0 in `libs.versions.toml`. Also migrated from the deprecated `kotlinOptions { jvmTarget = "17" }` DSL to the new `kotlin { compilerOptions { jvmTarget.set(JvmTarget.JVM_17) } }` DSL required by Kotlin 2.3.

Challenge 2: Orphaned Legacy Files Causing Conflicts
Problem:Previous versions of the app had separate `app.js` and `style.css` files. When the app was refactored to a single self-contained `index.html`, these files were left behind. They referenced HTML element IDs that no longer existed, potentially causing JavaScript errors if accidentally loaded.

Solution:Deleted the orphaned `app.js` and `style.css` files, and updated the service worker (`sw.js`) to only cache files that actually exist.

Challenge 3: WiFi QR Special Character Handling
Problem:WiFi SSIDs and passwords can contain special characters (`;`, `,`, `\`, `:`, `"`) that conflict with the WiFi QR code format specification (`WIFI:T:...;S:...;P:...;;`).

Solution:Implemented a custom escape function that properly escapes all special characters according to the WiFi QR code standard:
```javascript
var esc = function(s) {
    return s.replace(/\\/g, '\\\\')
            .replace(/;/g, '\\;')
            .replace(/,/g, '\\,')
            .replace(/"/g, '\\"')
            .replace(/:/g, '\\:');
};


Challenge 4: QR Code Data Capacity Limits
Problem:QR codes have a maximum data capacity of 2,953 characters (alphanumeric). Users entering very long text could generate invalid or unscannable QR codes.

Solution:Implemented a real-time character counter that updates as the user types, showing "current / 2953". The counter turns red when the limit is exceeded, and validation prevents generation beyond the limit.

Challenge 5: Android Back Button Behavior
Problem:By default, pressing the Android back button exits the app immediately. Users expected it to navigate back through the app's screens first (Result → Input → Home → Exit).

Solution:Implemented a custom `OnBackPressedCallback` in `MainActivity.kt` that injects JavaScript into the WebView to detect the current screen and navigate backwards before allowing the app to exit:
kotlin
onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {
        webView.evaluateJavascript("...") { value ->
            if (value.contains("exit")) {
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
})


Challenge 6: Offline Functionality
Problem: Many QR generator apps require internet access, which is unreliable in rural Tanzanian communities.

Solution: Inlined the entire QRCode.js library directly into `index.html` (instead of loading from a CDN), implemented a service worker for offline caching, and ensured all QR generation happens purely client-side with zero server calls.

Challenge 7: Deprecated Android APIs
Problem:`window.statusBarColor` and `window.navigationBarColor` APIs are deprecated in newer Android SDK versions, causing build warnings.

Solution: Added `@Suppress("DEPRECATION")` annotations. These APIs are deprecated but still functional and are the simplest approach for non-Compose Android apps. The alternative would require migrating to Jetpack Compose's `MaterialTheme`, which was unnecessary for a WebView-based app.


10. Conclusion

QR Master successfully addresses the real-world problem of quick, reliable, and offline information sharing in communities. The application:
The app demonstrates that mobile solutions can effectively serve community needs even in areas with limited internet connectivity, using modern web technologies wrapped in a native Android experience.
Application Name: QR Master  
Package Name: com.fussy.qr_master  
Version: 1.0  
APK Size: 2.6 MB  
Minimum Android: 7.0 (API 24)  
Target Android: 15 (API 35)  
