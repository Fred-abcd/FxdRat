# FxdRat

FxdRat is a tool designed to detect potential RATs in mods used for SkyBlock in Hypixel. It performs multiple checks to determine if a given file exhibits behaviors consistent with RATs.

## Features

- **Multiple Checks:** Utilizes a variety of checks to analyze suspicious behavior in files.
- **Logging:** Provides detailed logging using custom log levels (INFO, ERROR, DEBUG, FATAL).
- **Command Line Interface:** Supports command-line usage for easy integration and automation.

## Usage

To use FxdRat, follow these steps:

1. **Download:** Obtain the latest release from the [releases section](https://github.com/Fred-abcd/FxdRat/releases/latest).
2. **Run:** Execute the JAR file with the following command:

```bash
java -jar fxdrat.jar <filepath>
```

Replace `<filepath>` with the path to the file you want to scan.

3. **Optional Debug Mode:** You can enable debug mode by adding an additional argument:

```bash
java -jar fxdrat.jar <filepath> true
```
This will enable more detailed logging.


## Logging Levels

- **INFO:** General information about the progress and outcome of the scan.
- **ERROR:** Indicates errors such as incorrect usage or file access issues.
- **DEBUG:** Detailed debugging information useful for troubleshooting.
- **FATAL:** Indicates potentially critical findings, such as identifying a file as a RAT.

## TODO

- **GUI:** Integrate a graphical user interface for easier interaction.
- **Check Selector:** Allow users to choose specific checks to run.
- ~~Auto Update: Implement automatic update functionality for the tool.~~


## Author

- **Author:** Fred aka. FxD

## Wakatime
[![wakatime](https://wakatime.com/badge/user/018c9742-04ae-4af3-8234-447d5408f2a2/project/a5db316b-dea8-4add-903f-16d3a67ae029.svg)](https://wakatime.com/badge/user/018c9742-04ae-4af3-8234-447d5408f2a2/project/a5db316b-dea8-4add-903f-16d3a67ae029)

## License

This project is licensed under the MIT License - see the [LICENSE](https://choosealicense.com/licenses/mit/) file for details.

