# Go Game System

### Overview

This project implements a system for playing the game of Go on a 19x19 board. The system follows the basic rules of Go and employs a client-server architecture. Players can connect to the server, join games, and conduct matches. The server validates moves, facilitates communication, and supports both human and bot players.

At first run server with:
	mvn exec:java -Dexec.mainClass="tp.AppServer"
Then you may connect as many clients as you want:
	mvn exec:java -Dexec.mainClass="tp.AppClient"
