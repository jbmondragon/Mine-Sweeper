<h1 align="center">🧨 Mine Sweeper Game</h1>
<p align="center">
  Developed by <strong>Jake Benusa Mondragon</strong>
</p>

---

## 📦 Dependencies

- **Java 21 or higher**

---

## 📑 Table of Contents

- [📖 About](#-about)
- [✨ Features](#-features)
- [🎮 How to Play](#-how-to-play)
- [🚀 Installation & Setup Guide](#-installation--setup-guide)

---

## 📖 About

**Mine Sweeper** is a classic logic puzzle game where players uncover a grid while avoiding hidden mines.

The challenge lies in using the numbered clues to logically determine which tiles are safe to click. One wrong move could trigger a mine — game over!

---

## ✨ Features

- 🎯 Customizable grid sizes
- 🎚️ Multiple difficulty levels
- 🖱️ Intuitive, mouse-based UI
- 🔄 Replayable sessions

---

## 🎮 How to Play

1. Left-click to reveal a tile.
2. Numbers show how many adjacent tiles contain mines.
3. Right-click to place a flag where you suspect a mine.
4. Successfully reveal all safe tiles to win!

---

## 🚀 Installation & Setup Guide

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/mondragonjake/Mine-Sweeper.git
cd Mine-Sweeper
```

### 2️⃣ Open the Project in VS Code

Make sure you have the Java Extension Pack installed.

Navigate to the `src/` folder and open `Main.java`.

### 3️⃣ Run the Application

You can run the game by:

- Clicking the Run Java button in the top-right of VS Code, or
- Pressing F6 (VS Code keybind for Run Java)

### 4️⃣ Build a JAR File (Optional)

- Press Ctrl + Shift + P → open the Command Palette.
- Search: Java: Export Jar.
- Choose `Main` as the main class.
- Select all required files.
- A `Mine-Sweeper.jar` will be generated.

### 5️⃣ Run the JAR File

#### Option A: Terminal

```bash
java -jar Mine-Sweeper.jar
```

#### Option B: GUI

Double-click the `.jar` file (Java Runtime Environment required).

> ⚠️ Ensure Java 21 or later is installed, or the application will not run properly.

🎉 Happy Sweeping!
