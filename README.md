# JGLib
「JGLib」はJava言語用の2Dゲームライブラリです。
```mermaid
classDiagram
  JFrame <--o GameWindow
  JPanel <|-- GameScreen
  Runnable <|.. GameScreen
  GameWindow <|.. MainWindow
  GameScreen <|.. MainScreen
  GameScreen <-- GameWindow : use
  GameUtilities <-- MainScreen : use
  Clip <-- MainScreen :use
  BufferedImage <-- MainScreen : use
  SpriteSheet <-- MainScreen :use
  class GameWindow {
    <<abstract>>
    +setVisible()
    +switchGameScreen()
  }
  class GameScreen {
    <<abstract>>
    +startGameLoop()
    +stopGameLoop()
    #runGameLoop()*
  }
  class MainScreen {
    #runGameLoop()
  }
  class GameUtilities {
    +loadClip()$
    +loadImage()$
    +loadSpriteSheet()$
  }
  class SpriteSheet {
    +draw()
  }
```
