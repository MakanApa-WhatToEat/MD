<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-Android-7F52FF?style=for-the-badge&logo=kotlin" alt="Kotlin">
  <img src="https://img.shields.io/badge/Android-Studio-3DDC84?style=for-the-badge&logo=androidstudio" alt="Android Studio">
  <img src="https://img.shields.io/badge/Retrofit-API-48B983?style=for-the-badge" alt="Retrofit">
  <img src="https://img.shields.io/badge/CameraX-Capture-000000?style=for-the-badge&logo=android" alt="CameraX">
  <img src="https://img.shields.io/badge/Glide-ImageLoading-4285F4?style=for-the-badge" alt="Glide">
</p>

<h1 align="center">🍳 Makan Apa</h1>

<p align="center">
  <strong>Point your camera at your ingredients — we'll tell you what to cook.</strong>
  <br>
  An Android app that uses image recognition to identify ingredients and suggest
  recipes you can make right now, with what you already have.
</p>

---

## 📋 About

**Makan Apa** (Indonesian for "What should I eat?") is an Android app built with Kotlin
as part of the Mobile Development path. 

The idea is simple: instead of searching through recipes and checking if you have the
ingredients, you just take a photo of what's in your kitchen. The app sends the image
to a backend API, runs ingredient recognition, and returns a list of dishes you can
actually make — no missing ingredients, no grocery runs.

### How it works
📸 User takes a photo  →  📡 Image sent to backend API
→  🤖 Ingredient recognition runs  →  📋 Recipe list returned to user

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| **Ingredient Scanner** | Take a photo of your ingredients and get instant recognition |
| **Recipe Suggestions** | Get a list of dishes you can make from detected ingredients |
| **Onboarding Screen** | Swipeable welcome screen for first-time users |
| **Live Camera** | Smooth in-app camera experience powered by CameraX |

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|------------|
| Language | Kotlin |
| Camera | CameraX |
| Networking | Retrofit 2 |
| Image Loading | Glide |
| Onboarding UI | ViewPager |
| IDE | Android Studio |

---

## 🚀 How to Run

1. **Clone the repository**
```bash
   git clone <repository-url>
```

2. **Open in Android Studio**
   File → Open → select the project folder

3. **Wait for Gradle to sync**
   Android Studio will automatically download dependencies

4. **Run the app**
   Click the ▶ Run button or press `Shift + F10`

> Make sure you have an Android emulator running or a physical device connected via USB with developer mode enabled.
