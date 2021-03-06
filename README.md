# AppTest for calc.jar file

A template of README best practices to make your README simple to understand and easy to use. 

## Table of Contents

- [AppTest for calc.jar file](#apptest-for-calcjar-file)
  - [Table of Contents](#table-of-contents)
  - [Installation](#installation)
  - [Usage](#usage)
  - [Support](#support)
  - [Contributing](#contributing)

## Installation

1. Download calc app from [Google Drive](https://drive.google.com/file/d/1XDE2lEmCt86SMY7wsyQ7jl3sU2D4uyEt/view).
2. Install Java 11.
3. Install Maven.
4. Add PATH variables for Java and Maven if needed.

## Usage

1. Navigate to downloaded `calc.jar` file location and launch the calc app:

    ```
    java -jar .\calc.jar
    ```
    The calc app is available in http://localhost:8080/calc/

    Launch example http://localhost:8080/calc/+1/-2/*5

2. Navigate to repository root folder and start tests with:
   
    ```
    mvn -Dtest=AppTest test
    ```

Additional plugins used:

- rest-assured 4.3.0
- junit4-dataprovider 2.6


## Support

Please [open an issue](https://github.com/notuxius/app-test-calc/issues/new) for support.

## Contributing

Create a branch, add commits, and [open a pull request](https://github.com/notuxius/app-test-calc/compare/).
