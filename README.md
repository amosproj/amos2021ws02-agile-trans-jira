Agile Planning Platform (AMOSWS2021)
======================================


Development
------------

In order to start developing, please make sure you have the following prerequisites:

### Windows:

* [Java 8 SDK (Oracle or OpenJDK)](https://adoptium.net/?variant=openjdk8&jvmVariant=hotspot)
* [The Atlassian SDK ](https://developer.atlassian.com/server/framework/atlassian-sdk/install-the-atlassian-sdk-on-a-windows-system/)
* [Node and npm v16.13.1](https://nodejs.org/en/download/) with "Automatically install the necessary tools" checkbox checked
* Your favorite Java IDE (we recommand [IntelliJ](https://www.jetbrains.com/de-de/idea/download/#section=windows))
* Git (e.g. [Git-SCM](https://git-scm.com/))
* [A Github Developer Token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token#creating-a-token)(Select all permissions from the repository section)


Now do the following steps:

1. Clone this repository or refresh it (Use your username and the Github developer Token as a password).
2. Run ```npm install @atlassian/aui``` to add the AUI library to the project.
3. You are done!!!

In order to execute the current state of the plugin:
1. Go to frontend/, run 
```atlas-mvn package```
and leave it open at all times.
>If there is a problem with frontend, try running ```npm config set msvs_version 2019```.
2. Go to backend/, and in the second bash window run
```atlas-run```.


The Atlassian SDK features its own Maven Installation. We highly recommand to use it, however of course you can also setup your own Maven if you want to do so. If you want to use the Atlassian SDK Maven, you should include it in your IDE to do so:

1. Click File->Settings.
2. In the Settings window, select Build, Execution, Deployment->Deployment Tools-> Maven.
3. Set the Maven Home path to your Atlassian Maven installation (Default is C:/Applications/Atlassian/atlassian-plugin-sdk-8.2.7/apache-maven-3.5.4).

## Add Request issue type to your project

1. Go to _JIRA Administration_/_Issues_ (the Gear button).
2. Go to _Issue types_.
3. Go to _Issue type schemes_.
    * Under your project click **Edit**.
    * Add Request type to the scheme and save.

## Add APP Custom fields to project

1. Go to _JIRA Administration_/_Issues_ (the Gear button).
2. Go to _Custom fields_.
3. Under `Actions` of the respective custom field choose _Screens_.
4. Check the checkbox of the desired project and save.

### Enable searching for the APP custom fields

1. Go to _JIRA Administration_/_Issues_ (the Gear button).
2. Go to _Custom fields_.
3. Under `Actions` of the respective custom field choose **Edit**.
4. Choose the desired _Search template_ and save.


>Google Drive of the project development: https://drive.google.com/drive/folders/1ymzRdeml-ni5U-7vzeMzGOC9FRGrVFef


>Full documentation for the SDK:
https://developer.atlassian.com/display/DOCS/Introduction+to+the+Atlassian+Plugin+SDK
