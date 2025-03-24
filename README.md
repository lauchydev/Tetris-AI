# Tetris Remastered



### A modern take on the classic Tetris game with new challenges and features!

## Features

✅ **Classic Tetris Gameplay** – Experience the traditional Tetris mechanics you know and love.\
✅ **Increasing Difficulty** – The game speeds up as you progress, testing your reflexes and strategy.\
✅ **Multiplayer Mode** – Peer-to-peer (P2P) multiplayer to challenge your friends in real-time.\
✅ **AI Mode** – Watch an AI play the game optimally, or challenge yourself against a powerful AI opponent.

---

## Installation & Setup

### Prerequisites

Ensure you have the following installed:

- [Java Development Kit (JDK)](https://adoptopenjdk.net/) (version 11 or higher)
- [Git](https://git-scm.com/)

### Cloning the Repository

```sh
git clone https://github.com/your-username/tetris-remastered.git
cd tetris-remastered
```

### Compiling the Game

```sh
javac -d bin src/com/tetris/*.java
```

### Running the Game

```sh
java -cp bin com.tetris.Main
```

### Running Multiplayer Mode

Start the first instance in host mode:

```sh
java -cp bin com.tetris.Main --host
```

Then, on the second machine, connect as a client:

```sh
java -cp bin com.tetris.Main --connect <HOST_IP>
```

### Running AI Mode

```sh
java -cp bin com.tetris.Main --ai
```

---

## Contact

💬 Have questions? Reach to [@lauchydev](https://github.com/lauchydev) on Github!



