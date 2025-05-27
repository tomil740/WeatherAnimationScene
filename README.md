# 🌦️ Weather Animation Scene

A fully Compose-based UI that visualizes a smooth transition between a **rainy** and **sunny** scene.  
Designed for a clean user experience with animated backgrounds, rising sun, fading clouds, and dynamic raindrops — all optimized for screen size and performance.


👉 **Watch it in action:**  
[![Weather Animation Preview](https://img.shields.io/badge/Watch%20Demo-Click%20Here-blue?style=for-the-badge)](https://github.com/user-attachments/assets/2b0d9e9a-fd50-46a1-9652-9fe4f2668e73)
 
> Or download the [fin1.webm](https://github.com/user-attachments/assets/2b0d9e9a-fd50-46a1-9652-9fe4f2668e73) directly.


---

## ✨ Features

- 🌁 **Animated Gradient Background**  
  Changes dynamically to reflect the current weather view (rainy vs sunny)

- ☁️ **Cloud & Drop Generation Algorithm**  
  - Clouds are randomly distributed across ideal portion of the screen  
  - Each cloud manages its own raindrops  
  - Drop visuals vary in size, opacity, and blur for natural diversity  
  - A safe cleanup system ensures drops are removed when off-screen to prevent memory leaks

- 💨 **Wind-like Cloud Movement**  
  Subtle horizontal cloud drift to create life in the rain view

- 🌤️ **Smooth View Transition**  
  - Tap anywhere to toggle between rain and sun  
  - Sun rises from the bottom with glow  
  - Clouds fade out as background brightens  
  - All animations coordinated to create a pleasant UX

---

## 🛠️ Technical Overview

- Fully implemented using **Jetpack Compose**
- Composable structure with screen-aware sizing
- State-driven animation using `animate*AsState`, `AnimatedVisibility`, and `Canvas`
- ViewModel manages all animation states cleanly and safely
- Component-based design for clouds, raindrops, sun, and transitions
- Custom logic for:
  - Cloud positioning and generation
  - Drop behavior tied to their owning cloud
  - Memory-safe removal of finished animations
