### Lenkkifrendi | Documentation
---
### Deployment

- Clone the repository to your machine, also install Android Studio.
- In Android Studio, go to Build > Build Bundle /APK > Build APK.
- You will get a .apk file that you can transfer to your Android phone.
- Alternatively, you can also run the app on the Android Studio emulator, but note that GPS tracking does not work on the emulator.

---
## Project Goal

The main goal of the project is to create a simple and user-friendly Android mobile application for promoting physical activity.
With the application, users can record and monitor the duration and distance of their physical activities,
as well as create exercise groups that can invite friends and hobby groups to join.

The application collects information about the user's movements and exercise habits. Initially, the intention was to commercially exploit the user data by selling it to sports equipment manufacturers. However, after careful consideration, it was decided that user data would not be used for commercial purposes. Instead, the data would be applied to improve the user experience of the application. Through machine learning, the app aims to provide activity recommendations based on user profiles rather than advertising products, such as suggesting the Helsinki Marathon for someone who enjoys running.

## Application Definition

### Functionalities

During the project, the necessary functionalities will be developed, including:

| Functionality            | Description                                                                                             |
|--------------------------|---------------------------------------------------------------------------------------------------------|
| User Interface           | Create a user-friendly interface for the application.                                                    |
| Google Location API      | Implement the Google Location API for location services.                                                  |
| Database Connections     | Establish connections to a NoSQL Firebase database.                                                        |
| Application Logic        | Develop the core logic of the application.                                                               |
| Unit Testing             | Implement JUnit 4 tests to ensure code reliability.                                                      |
| Validation               | Validate user inputs and ensure data integrity.                                                          |
| Machine learning (Later) | Implement machine learning for personalized recommendations based on user profiles.                      |

---

### User Interface

The application opens to the login page, where the user can enter their email and password. Alternatively, if the user does not have an account yet, the login page includes a "Register" button that takes them to the registration page. On the registration page, the user fills in the required fields and completes the registration process.

After logging in, the user is directed to the default view, which corresponds to their profile page. The bottom panel contains a navigation bar.

In the navigation bar, in addition to the default view (profile page), there are also activity and group pages. On the activity page, there are "Start activity" and "Stop activity" buttons. As the names suggest, the "Start activity" button initiates the measurement of the user's activity duration and distance traveled. The "Stop activity" button halts the activity, visualizes the data for the user, and stores the information in the database.

The group page allows users to join groups with other members. When a user is part of a group, they can monitor the activities of other group members from a list that appears on the screen.

---       
# Github Actions

Pipeline configuration is done in the ./Github/workflows/android.yml file.
Github automatically locates the file and runs it in the pipeline as configured.

Our test cases in the pipeline include the following steps.

| Stage       | Job   | OS             | Steps                                                                                             |
|-------------|-------|----------------|---------------------------------------------------------------------------------------------------|
| Build       | build | ubuntu-latest  | 1. Checkout repository (`actions/checkout@v3`)                                                     |
|             |       |                | 2. Set up JDK 11 (`actions/setup-java@v3`) with Java 17, Temurin distribution, and Gradle cache   |
|             |       |                | 3. Run linter (`./gradlew lint`)                                                                   |
|             |       |                | 4. Grant execute permission for gradlew (`chmod +x gradlew`)                                       |
|             |       |                | 5. Build with Gradle (`./gradlew build`)                                                          |
| Test        | test  | macos-latest   | 1. Checkout repository (`actions/checkout@v3`)                                                     |
|             |       |                | 2. Set up JDK 11 (`actions/setup-java@v3`) with Java 17, Adopt distribution, and Gradle cache      |
|             |       |                | 3. Run connected tests with Android emulator (`./gradlew connectedCheck --stacktrace --scan`)    |
| Checkstyle  | checkstyle | ubuntu-latest | 1. Checkout repository (`actions/checkout@v4`)                                                    |
|             |            |                | 2. Run Checkstyle (`dbelyaev/action-checkstyle@master`) with GitHub token, GitHub PR review reporter, and warning level |

---
# Testing and Test Generation
#### NOTE: To be able to perform test recording the emulator and application needs to be up&running.

- Open Run in Android studio and choose record Espresso test.
- You can click and run the application and the recorder will automatically generate the test, once all steps are done stop recording and the test will appear in androidTest folder.
---
### The test is a public class, insert this block in the beginning of the class.
```
    private String test_user;
    private String test_pass;
    private final int timeout = 5000;

    @Before
    public void init() throws PackageManager.NameNotFoundException {
        ApplicationInfo ai = getApplicationContext().getPackageManager()
                .getApplicationInfo(getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);

        test_user = (String) ai.metaData.get("test_username");
        test_pass = (String) ai.metaData.get("test_password");
    }
```
- Next open app --> src --> AndroidManifest and inside the application and outside of activity add 

```
        <meta-data
            android:name = "test_username"
            android:value = "${test_username}"/>
        <meta-data
            android:name = "test_password"
            android:value = "${test_password}"/>
```
- Also in local.properties add 
```
test_username = testi@testi.fi(should excist in the database)
test_password = testi123(should be password of the given user)
```
#### NOTE: local.properties should always be gitignored, so the setup provided works only locally.

### Pipeline instructions.

- In your Github repository check settings ---> secrets and variables --> actions
- Create a new Environment and name it production or etc.
- add TEST_PASSWORD = password for a user in the database
- add TEST_USER = email of a excisting user

### Configure pipeline

- In youre workflow file add the following line where the test jobs use these secrets.
```
      - name: Add secrets to local properties
        run: |
          touch local.properties
          echo "test_username=${{ secrets.YOUR_SECRET_USERNAME_NAME }}" > local.properties
          echo "test_password=${{ secrets.YOURE_SECRET_PASSWORD_NAME }}" >> local.properties
```
#### More instructions ----> https://docs.github.com/en/actions/deployment/targeting-different-environments/using-environments-for-deployment
---


### Technical Spesifications

| Programming Languages | Java 17                    |
|-----------------------|----------------------------|
| Application Platform  | Android                    |
| Database              | NoSQL Firebase             |
| Android API Level     | 34                         |
| Other APIs            | Google Location            |
| Testing               | JUnit 4                    |
| Github Actions(test)  | run build ./gradlew        |
| Github Actions(test)  | run build ./connectedCheck |
| Github Actions(test)  | run checkStyle             |


---

### Project management and communication
- Trello
- Discord

---

### Roadmap

On going (Through out the project)
- Test cases & Reports
- Documentation and diagrams.
- P2P Testing

### Project Timeline

| Week | Tasks                                                  |
|------|--------------------------------------------------------|
| 1    | Project vision                                         |
| 2    | Gather resources, establish database connection        |
| 3    | Develop user interface, create user accounts           |
| 4    | Establish GPS sensor connection, add user groups       |
| 5    | Collect user data, visualize user data                 |
| 6    | Conduct Junit testing                                  |
| 7    | Release first test version, enhance application layout |
| 8    | Prepare for demo                                       |
| 1    | Localization                                           |
| 2    | Testing/Pipeline                                       |
| 3    | Documentation                                          |
| 4    | Refactoring                                            |
| 5    | Pipeline fixes / Presentations                         |
| 6    | Junit tests / Test reports                             |
| 7    | Preparing for presentation                             |
| 8    | Presentation                                           |

---

### Cost Estimate

The estimated cost for our project, lasting about 4 months, is 200,000 euros. The programming expenses alone account for approximately 140,000 euros.

---

### Key Risks

The major risks in the project include the tight schedule. Adhering to a tight schedule may lead to delays due to technical issues or the team's limited familiarity with specific technologies.

If the application doesn't meet the competitive standards within the set timeframe, it may fall behind competitors.

---

### Developers

| Developer          |
|--------------------|
| Niko Mäenpää       |
| Tristan Ellenberg  |
| Kaspar Tullus      |
| Samu Aikio         |

---

### Note
- You can find a finnish readme, in the root folder, named README_FI.
- For the sake of readability, images and diagrams are in a separate folder(root --> diagrams).

---


