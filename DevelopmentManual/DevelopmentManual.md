### Environment Setup

## Using Mac: Homebrew

The simplest way to set up the environment on a MacOS to use the Homebrew package manager in order to install the dependencies. To install Homebrew:

/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"

If the installation is unsuccessful, make sure you meet the installation requirements at https://docs.brew.sh/Installation.html or use it to find alternative installation instructions.
Once homebrew has successfully been installed, install any required packagaes with:

brew update

brew install \<package-name\>

## Java

This environment requires a downloaded version of a JDK and setting the JAVA_HOME environment variable. Use at least version 11. Download a JDK from https://jdk.java.net/
Find the path to the JDK folder and set it to the JAVA_HOME variable. 

export JAVA_HOME=/path/to/jdk

export PATH=$JAVA_HOME/bin:$PATH

restart the terminal and try to run 'java -version', if installed correctly, the proper JDK chosen should be displayed. 

## Maven
Maven should be able to be installed through the package manager Homebrew. Simply run:

brew install maven

Use 'mvn --version' to see if it correctly installed.

## NPM
Node Version Manager is a great project that helps you install and keep track of different NodeJS versions. To install:

curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.35.3/install.sh | bash

Use 'nvm --version' to see if it has correctly installed. Now install the latest LTS version of NodeJS using:

nvm install --lts

To check what got installed:

nvm list

And to make sure you have NodeJS and NPM working:

node -v

npm -v

## IntelliJ
As students, all of the developers get an IntelliJ licence for free using a JetBrains Student Account. Students can sign up at https://www.jetbrains.com/student/

Either edition of IntelliJ will suffice, however the Ultimate Edition has better integration for the JavaScript code. Both editions can be downloaded from https://www.jetbrains.com/idea/download

## Opening the project

Upon opening IntelliJ, a window should invite you to open a project. Select 'Check out from version control' and then select Github on the dropdown menu. 

## Final Steps and Running

Before running the the project for the first time go to the client directory and run:

npm install 

Now running the environmnet is simple, from the client directory run:

../bin/run.sh

or from the home directory run:

./bin/run.sh

## Using Docker
 * Install Docker following the instructions here: https://docs.docker.com/get-docker/
 * Install VS Code following the instructions here: https://code.visualstudio.com/download
 * Using PowerShell, run the command: docker pull csucs314/developer-image
 * Start the docker container inside the 'images' tab of Docker
 * Open VS Code and install the Remote Development extension
 * Use the extension to attach to the Docker container
 * Run 'npm install' from the client directory
 * Run './bin/run.sh' After completing all setup steps

## All Environments: GitHub
 * Setup a GitHub key-pair by following these instructions here: https://docs.github.com/en/enterprise-server@3.0/authentication/connecting-to-github-with-ssh/adding-a-new-ssh-key-to-your-github-account

## Other Relevant Info
 * For testing, users should make sure to use Chrome, Safari, or Microsoft Edge. Firefox blocks some cookies that our website uses.
 * Make sure that the app is running on port 8000 otherwise some requests will not go through properly.

