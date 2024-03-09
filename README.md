This is News APP using NewsAPI as a data source, in this app i'm using Android Native Kotlin with Clean Architecture and also implement MVVM Pattern

1, Home Page

![image](https://github.com/Kafabih/NewsApi/assets/34999622/f9ebd362-4775-4b0a-ad2b-0a0e0eb15cdc)

you can search the topic of news and pressing enter in phone's keyboard to fetching the data again with desire topic. 
I did this using ime method to listen user when pressing "enter" in keyboard while the user finish writing in edittext.
and also there is category, when the category clicked it will fetch the selected category topic and refreshing news, 
notice when you press enter in Search Field the text you wrote will also appear in category.
the last one there is a List View using Recyclerview to show the user the data of the Articles and when click it, the apps will move to...

2. Detail Page

![image](https://github.com/Kafabih/NewsApi/assets/34999622/9d87736f-1b88-4a08-9451-cb4b12f95700)

this is to show the detail of the news + notice there is an overview to show the content of the news and 
also button to read it full and when the button pressed it will open the webview that shows the original Source of the news

![image](https://github.com/Kafabih/NewsApi/assets/34999622/b2218c32-d8e1-485f-a051-2812c68a9702)
