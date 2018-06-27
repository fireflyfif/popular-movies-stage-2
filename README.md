# Popular Movies App 
## Project 02, Part of the Android Developer Nanodegree Program by Udacity

![alt text][logo]

[logo]: https://github.com/fireflyfif/popular-movies-stage-2/blob/master/app/src/main/res/mipmap-xxhdpi/ic_launcher_round.png

### App Description
This app displays popular and top rated movies from [**The Movie Db**](https://www.themoviedb.org/) API. 
Details of each movie is displayed in the detail screen, where the user can view listed trailers for each 
movie and to read all reviews. Allows the user to safe movie in their favorites collection storring it 
into a local database.

## What I Learned

- Fetch data from the Internet with theMovieDB API.
- Use adapters and custom list layouts to populate list views.
- Incorporate libraries to simplify the amount of code I need to write
- Allow users to view and play trailers ( either in the youtube app or a web browser).
- Allow users to read reviews of a selected movie.
- Allow users to mark a movie as a favorite in the details view by tapping a button.
- Create a database to store the names and ids of the user's favorite movies.
- Modify the existing sorting criteria for the main view to include an additional pivot to show their 
favorites collection.

## Libraries

- [Retrofit2](https://github.com/square/retrofit)
- [Picasso](https://github.com/square/picasso)
- [Butterknife](https://github.com/JakeWharton/butterknife)

## API KEY Notes

In order to run this app please add your own **API key** from [**The Movie Db**](https://www.themoviedb.org/).

Place **YOUR_API_KEY** to **build.gradle** `System.getenv('API_KEY') ?: "\"[YOUR_API_KEY]\"")`, 
so that the app can fetch movie data from the API.

## Screenshots

![text](https://github.com/fireflyfif/popular-movies-stage-2/blob/master/art/screenshot-three.png)

# License

```
This project was submitted by Iva Ivanova as part of the Nanodegree at Udacity.

According to Udacity Honor Code we agree that we will not plagiarize 
(a form of cheating) the work of others.
Plagiarism at Udacity can range from submitting a project you didn’t create to 
copying code into a program without citation. Any action in which you misleadingly 
claim an idea or piece of work as your own when it is not constitutes plagiarism.
Read more here: https://udacity.zendesk.com/hc/en-us/articles/360001451091-What-is-plagiarism-

MIT License

Copyright (c) 2018 Iva Ivanova

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
