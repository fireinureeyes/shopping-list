# shopping-list
Lightweight shopping list app with a local database synchronized between devices over Firebase based on https://github.com/mauriciotogneri/shoppinglist

<img src="https://raw.githubusercontent.com/fireinureeyes/shopping-list/main/app-screenshot.png" width="200">

## How to get started after pulling this repository:
1. Create a new Firebase project https://firebase.google.com/
2. Within the project, create a realtime database with the following structure:
'items' root node with 203 key-value pairs, where key is the item ID and value 0/1 expresses whether the item is currently in the shopping cart

![database](https://raw.githubusercontent.com/fireinureeyes/shopping-list/main/database.png)

3. Download google-services.json of from the project setttings
4. Place the google-services.json file to the 'app' folder
5. Get the database URL from the file and use it to replace 'database_url' string in shoppinglist-master\app\src\main\res\values\strings.xml
