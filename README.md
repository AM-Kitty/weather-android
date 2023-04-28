## Weather-App

<img src="https://user-images.githubusercontent.com/36249982/234433293-2d1e1eac-0e85-4bac-ad5a-625781afbbae.png" width="250" height="500" /> <img src="https://user-images.githubusercontent.com/36249982/234433657-f9812ca0-7386-4eea-883a-fe89a917d8bd.png" width="360" height="500" />

This is a simple project to study and play with Android components, architecture and tools for Android development. This application is used to present daily and future weather information.

### Tech Stack
This project uses MVVM as software design pattern for presentation layer, android embedded jetback component library for app navigation and shared preferences to store local user preference.

## Development setup

You require Android Studio 4.2 or higher with the Android 11 SDK to be able to build the application. 

### Libraries
- Application entirely written in [Kotlin](https://kotlinlang.org)
- Asynchronous work processing using [Coroutines](https://kotlin.github.io/kotlinx.coroutines/)
- Uses [Retrofit](https://square.github.io/retrofit/) to manage network calls 
- Uses [Material UI](https://m2.material.io/develop/android) for adapitive UI design 

### API keys
You need to create your own API / client keys for the service the app uses.
- [OpenWeatherMap](https://openweathermap.org/current#zip)

Once you obtain the key, you can set them in your `~/local.properties`:
```
# Get this from open weather map website
OPEN_WEATHER_MAP_API_KEY=<insert>
```

## 📃 License
```
Copyright 2023 Kelly Ge

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
