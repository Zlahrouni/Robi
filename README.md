# Robi

Robi is a prototype monitor for the creation, addition, and animation of basic graphic elements such as rectangles, ellipses, lines, or texts. The project is divided into three parts:

## Part 1: 2D Graphic Objects Management
Creation and addition of 2D graphic objects with various shapes, colors, and sizes, displayed on a JFrame window.

## Part 2: Robi Server
Implementation of a Robi server to handle requests for creating and adding 2D graphic objects and returning the results to clients via Sockets.

## Part 3: Robi Client
![javaw_uo0bKt8Fj5](https://user-images.githubusercontent.com/75903708/235365905-14256d06-b3b2-4efe-9dc9-2719df8cec46.png)
The Robi client features a graphical interface that includes:
- A text field for sending scripts
- Two buttons (send and execute) for running scripts in "step by step" or "block" mode
- A log section to display the history of actions performed
- A display area for created and modified graphic objects

## Features
- Creation, addition, and deletion of graphic objects
- Modification of objects (color, dimensions, position)
- Command management for each added object
- Display of the environment and the result on the client-side, including graphic objects
- Graphical interface for sending and executing scripts

## Technologies
- Client-server architecture
- Java (Swing, Jackson)
- Communication via JSON and Sockets
- GitLab for version control

## License

This project is licensed under the MIT License. For more information, please see the `License` file included in this repository.

## Authors
- Ziad Lahrouni
- Hanane Erraji
- Youenn Robitzer
- Gwendal Le Tareau
