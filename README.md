Agile Planning Platform (AMOSWS2021)
======================================


Development
------------

In order to start developing, please make sure you have the following prerequisites:

###Windows:

* [Java 8 SDK (Oracle or OpenJDK)](https://adoptium.net/?variant=openjdk8&jvmVariant=hotspot)
* [The Atlassian SDK ](https://developer.atlassian.com/server/framework/atlassian-sdk/install-the-atlassian-sdk-on-a-windows-system/)
* Your favorite Java IDE (I recommand [IntelliJ](https://www.jetbrains.com/de-de/idea/download/#section=windows))
* Git (e.g. [Git-SCM](https://git-scm.com/))
* [A Github Developer Token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token#creating-a-token)(Select all permissions from the repository section)

Now do the following steps:

1. Clone this repository or refresh it (Use your username and the Github developer Token as a password)
2. Run ```npm install @atlassian/aui``` to add the AUI library to the project
3. You are done!!!

In order to execute the current state of the plugin run

```bash
cd frontend
atlas-mvn package

//In a second shell
cd backend
atlas-run //Run the current configuration

atlas-help //Shows other commands from the Atlassian 
```
I am still figuring out how to launch different Jira versions (The switch somehow does not work)

###Linux:

(Comming soon)

The Atlassian SDK features its own Maven Installation. I highly recommand to use it, however of course you can also setup your own Maven if you want to do so. If you want to use the Atlassian SDK Maven, you should include it in your IDE to do so:

1. Click File->Settings
2. In the Settings window, select Build, Execution, Deployment->Deployment Tools-> Maven
3. Set the Maven Home path to your Atlassian Maven installation (Default is C:/Applications/Atlassian/atlassian-plugin-sdk-8.2.7/apache-maven-3.5.4)

Create Request issue type
------------

1. Go to JIRA Administration/Issues (the Gear button)
2. Go to Issue types
3. Create a *Request* issue type
4. Go to Issue type schemes
    * Under your project click edit
    * Add Request type to the scheme and save

Full documentation for the SDK:

https://developer.atlassian.com/display/DOCS/Introduction+to+the+Atlassian+Plugin+SDK
