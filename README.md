# WaniKani-Android-Project
3rd Party App for the WaniKani Japanese Kanji/Vocab learning website

Test Account:
username: EChenAP
password: 123456AP
Access Token: ffef2121-13e6-409a-bd8d-78437dc4338e


Wanikani: An App for Learning Japanese 

Eric Chen

Wesley Chu

App Description:
This is a studying assistant app that is used to learn Japanese Kanji and Vocabulary. It utilizes a Spaced Repetition System to help users retain knowledge, and provides the user a mobile vehicle to the popular Japanese learning website, www.Wanikani.com .

Link to Demo Video: https://www.youtube.com/watch?v=a_7pzqXmwqk

API Information
This app relies heavily on WaniKani’s API, which can be found here: https://docs.api.wanikani.com/20170710/
We fetch data from the API to obtain the user’s available assignments, the information for the characters that need to be learned/quizzed, and send data back to WaniKani to update the user’s progress.

3rd Party Libraries
We utilize Glide in a special case to fetch certain images when a character is unavailable. More details on this in our debugging section. One small challenge we encountered was deciding whether or not to add in compatibility for the svg file format, since some character images were in that rather than png. However, since we had the png alternative url, we decided to just use that every time for simplicity.

UI/UX Discussion
The UI was based off of the web based Wanikani. Both lesson and review fragments should look familiar to Wanikani users. Using the left and right arrow, the user can navigate through the assignment characters. The info box will first display the name mnemonic then clicking right again will display the kanji examples. The user can start the quiz when they have reviewed all of the characters in the assignment. A toast will prompt them that the quiz can be started and the background color will change to green.

The same fragment was used for both the review and quiz cycle of the Wanikani app. The UI for review and quiz is also similar to the web based version of Wanikani. Like the web based version of Wanikani, the user is allowed two attempts for each question before they are moved onto the next question. These questions are marked as wrong and the user will be allowed to review them in a separate assignment. 

On the account settings the user can track what level they’re currently at. Another piece of information displayed is a timer that indicates how much time the user has to wait before their next assignment unlocks. If there are no assignments waiting to be unlocked then a message will indicate that the user has to complete all of their current assignments to unlock new assignments. 

Backend Discussion
Our app uses a lot of live data as it interacts with the Wanikani servers to obtain information on the current user and where they are in their assignments. To accomplish this, we used viewmodels to manage this information. Among the livedata that we store include: the subject IDs for both the lessons and reviews from which we can fetch the latest assignment subjects. These are the core to our lessons and review fragments. This also tells us how many assignments and reviews are available to the user. 

At the start of the fragment the viewmodel makes a call to the Wanikani servers to fetch the latest subjects using the available subject IDs. With this the fragment would have all of the data that it shows to the user. Left and right arrow buttons were hooked up to the array of subjects. As the user clicks on the left/right button, the fragment will increment/decrement the counter. The code checks if the user has visited all of the subjects. If the user has visited all of the subjects, then the button to start the quiz is enabled and turned. The review/quiz fragment has a similar logic except the user can only increment the counter.

For the timer in account settings, a chronometer was used to display the time. The time was calculated using data parsed from date parsers converted to UTC time zone. The Duration class was used to calculate the difference in time.

One challenge we faced during debugging was that some characters were not returning a value for the character text. We expected a character text and did receive one for the vast majority of our requests, which would look like: 大. We weren’t sure if something was malformed with our api request, or if maybe there was something wrong on WaniKani’s side. After posting a question to a WaniKani forum, we actually learned that these null characters values were actually on purpose- as a part of WaniKani’s learning system, they utilize “radicals” which are composed of both real and made up characters that can combine to form more complex characters. What we were seeing was a null character for these made up radicals, since the UTF-encoding did not exist! As an alternative, we then decided to use glide to follow a provided image url and display this instead of the character text when this would occur.

We found the online json view to be indispensable to figuring out what data is fetched through the API. Comparing the json structures to previous assignments such as Reddit and Trivia was an easy way to debug why API calls were not working. We found it challenging in figuring out the logistics in getting planning things out and what kind of data structures that we needed.

Another hurdle that we faced early on was how to deal with the authentication. We tried implementing an interceptor after reading some articles online. This seemed like a promising solution, but ultimately didn’t work. A TA was helpful in providing the line that was needed.

How to Build and Run Project
The build process is simple, as we show in the demo. For the purpose of this app, we didn’t include a login screen and instead have hardcoded the test user login information and its API-key. Normally one needs to generate this key through wanikani for their own account, but we opted to simplify this process for demonstration purposes.
