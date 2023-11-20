# BragDoc

![BragDoc](https://github.com/eevajonnapanula/BragDoc/assets/28345294/23a40276-6415-47bd-981d-fbeb44a1570d)



This project for [WWCode App Deploy Hackathon 2023](https://hopin.com/events/wwcode-app-deploy/registration) was put together in about two days, so don't treat it as the best code quality or the most polished project. But I must say that I'm super proud that I could put this together in such a short timeframe 🎉 You can find [the short video I did for the hackathon submission from Youtube.](https://www.youtube.com/watch?v=UnFLjTYSkeE)

The app is not currently available in the Google Play Store, but if you're interested in trying it out (but not building it from your own computer), send me a message, and I'll share the app.

## How to Run The Code

1. Clone the code.
2. You'll need some credentials:
  - Open AI-API key from [OpenAI's Platform](https://platform.openai.com/), you'll need to create an account there. Note: To be able to actually use the API, you'll need to pay. There's no free tier.
  - Google-services.json-file as `app/google-services.json`, you can either get it manually or connect Firebase from Android Studio. You'll need to enable Crashlytics. [You can find instructions for adding Firebase (and getting that `google-services.json`-file) from here.](https://firebase.google.com/docs/android/setup)
3. Create a file `secrets.properties` on the root of the project. Add the Open AI Api Key to the file:
```
OPENAI_API_KEY="Api key here"
```
4. Build the project, and run it on your phone or emulator.

## Details

This is a list of APIs, libraries and other relevant things is below.

### APIs and related

- Firebase
  - Analytics (needed for Crashlytics)
  - Crashlytics
- ChatGPT-API
- [Open AI Client for Kotlin](https://github.com/aallam/openai-kotlin)

### Jetpack and Android
- Compose
- Room
- Material3
- And several others


## Improvements
As mentioned before, this was initially a hackathon project, and due to time constraints, some corners have been cut here and there. There are a couple of improvement topics I already know:
- Accessibility. As this project was built in two days, I did not pay enough attention to accessibility, and I will improve it later.
- Code quality. Again, two days and a lot to do - there probably are many things that could be improved, but the main goal was to get the project working.
- UI. There are lots of things that can be improved in the app's UI. The goal was to make it work, so I decided not to spend much time on some of the details in the UI, and they should be improved.

