<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Min%20SDK-24-blue?style=for-the-badge" />
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge" />
</p>

# ⬛ QR Master

> **Generate QR codes instantly — works completely offline.**

QR Master is a sleek, modern Android app that lets you generate QR codes for phone numbers, URLs, WiFi passwords, text, and mobile money payments (Lipa Namba). Built as a lightweight WebView-based app with a beautiful dark purple UI, it works entirely offline with no server or internet required.

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 📞 **Phone Number** | Generate dial-ready QR codes with international country code support |
| 🌐 **Website / URL** | Auto-detects and formats URLs — scanning opens the browser directly |
| 📶 **WiFi Password** | Create WiFi QR codes (WPA/WPA2, WEP, Open) — scan to auto-connect, no typing needed |
| 🔤 **Text / Secret Mode** | Share text or secrets with optional hidden-character mode (up to 2,953 characters) |
| 💳 **Lipa Namba** | Mobile money payment QR for M-Pesa, Airtel Money, Mix By Yas, Halopesa, CRDB & NMB Bank |
| 💾 **Save & Share** | Download QR codes as PNG images or share directly via WhatsApp, email, etc. |
| 🌙 **Dark Mode UI** | Stunning purple-themed dark interface with smooth animations |
| 📴 **Fully Offline** | No internet connection needed — everything runs locally on your device |

---

## 📱 Screenshots

<p align="center">
  <em>Home Screen → Input Screen → Generated QR Code</em>
</p>

> _Screenshots coming soon_

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI Engine:** WebView + HTML/CSS/JavaScript (single-file SPA)
- **QR Library:** [qrcode.js](https://github.com/davidshimjs/qrcodejs) (inlined, no external dependencies)
- **Min SDK:** 24 (Android 7.0 Nougat)
- **Target SDK:** 35 (Android 15)
- **Build System:** Gradle with Kotlin DSL

---

## 📂 Project Structure

```
QR_Master/
├── app/
│   ├── src/main/
│   │   ├── assets/www/
│   │   │   ├── index.html        # Main app UI (HTML + CSS + JS)
│   │   │   ├── manifest.json     # PWA manifest
│   │   │   └── sw.js             # Service worker for offline support
│   │   ├── java/com/fussy/qr_master/
│   │   │   └── MainActivity.kt   # WebView host activity
│   │   ├── res/                   # Android resources (icons, themes, etc.)
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml        # Version catalog
├── build.gradle.kts               # Root build file
├── settings.gradle.kts
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Ladybug (2024.2.1) or newer
- **JDK 17**
- **Android SDK** with API level 35

### Build & Run

1. **Clone the repository:**
   ```bash
   git clone https://github.com/fussy0064/QR_Code-Generator.git
   cd QR_Code-Generator
   ```

2. **Open in Android Studio:**
   - File → Open → Select the project folder

3. **Run the app:**
   - Connect an Android device or start an emulator
   - Click **Run ▶️** or press `Shift + F10`

### Build APK

```bash
./gradlew assembleRelease
```

The APK will be generated at:
```
app/build/outputs/apk/release/app-release.apk
```

---

## 💳 Lipa Namba (Mobile Money)

QR Master supports mobile money payment QR codes for Tanzanian networks:

| Network | Provider |
|---------|----------|
| 🟢 M-Pesa | Vodacom |
| 🟥 Airtel Money | Airtel |
| 🟦 Mix By Yas | Mix By Yas Tanzania |
| 🟡 Halopesa | Halotel |
| 🏦 CRDB Bank | Simbanking |
| 🏦 NMB Bank | Mobile |

Each Lipa Namba QR includes: **till number, business name**, and optionally **amount** and **reference/account**.

---

## 🔒 Permissions

| Permission | Purpose |
|------------|---------|
| `INTERNET` | Opening URLs from generated QR codes |
| `WRITE_EXTERNAL_STORAGE` | Saving QR code images (Android 9 and below only) |

---

## 🤝 Contributing

Contributions are welcome! Feel free to:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

---

## 👤 Author

**Fussy** — [@fussy0064](https://github.com/fussy0064)

---

<p align="center">
  Made with ❤️ in Tanzania 🇹🇿
</p>
