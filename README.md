# Plotter

Plotter is a Java-based graphical function plotting application that allows users to visualize mathematical functions and their derivatives. It supports a wide range of functions, including linear, quadratic, exponential, and trigonometric functions. The application leverages the mxParser library to parse mathematical expressions and compute their values.

## Features

- Plot functions in a user-friendly interface
- Compute and display derivatives of functions
- Apply constants and variables to functions
- Toggle function visibility on the graph
- Remove functions from the graph
- Customize the appearance of the plot area
- Zoom in and out of the plot area

## Getting Started

### Prerequisites

- JDK 11 or later
- JavaFX 11 or later
- Maven (for building the project)
- mxParser library (already included in the project)

### Building and Running

1. Clone the repository:
git clone https://github.com/TheD4rkCoder/Plotter
cd plotter
2. Build the project with Maven:
mvn clean install
3. Run the application:
java --module-path /path/to/javafx-sdk-11/lib --add-modules javafx.controls,javafx.fxml -jar target/plotter-1.0-SNAPSHOT.jar
Replace `/path/to/javafx-sdk-11/lib` with the path to your JavaFX SDK `lib` directory.

## Usage

1. Enter a function in the text field and press Enter. The function will be added to the graph.
2. Click on the buttons next to the function to perform various actions:
- f': Compute and display the derivative of the function
- 🗑️: Remove the function from the graph
- 👁️: Toggle function visibility on the graph
- i: Display function analysis information
3. Use the zoom buttons to zoom in and out of the plot area.

## Running Tests

To run the test suite, execute the following command in the project directory:
mvn test

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.


