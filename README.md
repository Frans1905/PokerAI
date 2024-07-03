To run in terminal:
```
mvn compile
mvn exec:java "-Dexec.mainClass=game.app.PokerAI"
```

In case special characters such as poker symbols aren't showing correctly in terminal, make sure you are using command line and not powershell, and before running input:
```
chcp 65001
```
