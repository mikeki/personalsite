# Personal Site in Finatra

* A simple finatra application that is deployable to [Heroku](https://heroku.com) and grabs owner information from Twitter using [Twitter4s](https://github.com/DanielaSfregola/twitter4s)
* The front end design was copied from [Cameron Hunter's personal site](https://github.com/cameronhunter/cameronhunter.co.uk) with his permission

### Run the server on Heroku ###

Create a `.gitignore` file (notice the newlines):

```
$ echo ".DS_Store
classes/
target/
sbt-launch.jar
.idea/" > .gitignore
```

Commit all the files to master:

```
$ git add .
$ git commit -m "Initial commit."
```

Compile and stage the application:

```
$ sbt compile stage
```

Make sure you have the [Heroku Toolbelt](https://toolbelt.heroku.com/) [installed](https://devcenter.heroku.com/articles/getting-started-with-scala#set-up).

Create a new app in Heroku:

```
$ heroku create
Creating nameless-lake-8055 in organization heroku... done, stack is cedar-14
http://nameless-lake-8055.herokuapp.com/ | https://git.heroku.com/nameless-lake-8055.git
Git remote heroku added
```

Then deploy the example application to Heroku:

```
$ git push heroku master
Counting objects: 480, done.
Delta compression using up to 4 threads.
Compressing objects: 100% (376/376), done.
Writing objects: 100% (480/480), 27.68 MiB | 16.24 MiB/s, done.
Total 480 (delta 101), reused 0 (delta 0)
remote: Compressing source files... done.
remote: Building source:
	...
```

After the application is deployed we there are a few config variables required to work properly:
- Twitter Consumer key and Consumer secret
- Twitter Access key and Access secret
- your Twitter Handle

We will add those variables in a minute, but first we need to generate the Twitter application:
- Go to http://apps.twitter.com/, login with your twitter account and register your application to get a consumer key and a consumer secret.
- Once the app has been created, generate a access key and access secret, we only need read access, no writes will be done.

Add the Configuration variables:
```
heroku config:set TWITTER_CONSUMER_KEY=YOUR_CONSUMER_KEY
heroku config:set TWITTER_CONSUMER_SECRET=YOUR_CONSUMER_SECRET
heroku config:set TWITTER_ACCESS_KEY=YOUR_ACCESS_KEY
heroku config:set TWITTER_ACCESS_SECRET=YOUR_ACCESS_SECRET
heroku config:set TWITTER_HANDLE=YOUR_TWITTER_HANDLE
```

You can now open the application in a browser with `heroku open`, you might need to wait a few seconds for the app to restart.

By default, the site will grab your Avatar and Banner from your Twitter profile, as well as your description and location, if you want to change any of the last two, you can do so by setting it in the config variables:
```
heroku config:set DESCRIPTION='this is my description'
heroku config:set LOCATION='Monterrey, Mexico'
```

You can also add links to your email, or other sites you own:
```
heroku config:set EMAIL=myemail@domain.com
heroku config:set FACEBOOK_HANDLE=MyFbUsername
heroku config:set GITHUB_HANDLE=MyGithub
heroku config:set LINKEDIN_HANDLE=MyLinkedIn
```

### DISCLAIMER ###
This simple app does not handle caching at the moment and serves static assets through the app itself, if you want to add any of those improvements, feel free to do so and send your Pull Request.